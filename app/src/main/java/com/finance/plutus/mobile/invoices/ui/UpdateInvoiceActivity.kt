package com.finance.plutus.mobile.invoices.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.finance.plutus.mobile.R
import com.finance.plutus.mobile.app.data.model.Currency
import com.finance.plutus.mobile.app.util.showDateDialog
import com.finance.plutus.mobile.app.util.showListDialog
import com.finance.plutus.mobile.databinding.ActivityUpdateInvoiceBinding
import com.finance.plutus.mobile.invoices.data.model.InvoiceResult
import com.finance.plutus.mobile.partners.data.model.Partner
import com.finance.plutus.mobile.partners.data.model.PartnerType
import org.koin.android.ext.android.inject
import java.time.LocalDate
import java.util.stream.Collectors

class UpdateInvoiceActivity : AppCompatActivity() {

    private val viewModel: UpdateInvoiceViewModel by inject()
    private lateinit var binding: ActivityUpdateInvoiceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_update_invoice
        )
        setSupportActionBar(binding.invoiceToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_close_24)
        viewModel.result.observe(this) { handleResult(it) }
        viewModel.partners.observe(this) { setPartners(it) }
        title = getString(R.string.create_invoice_title)
        binding.invoiceSaveBtn.setOnClickListener {
            save()
        }
        binding.invoiceDate.setOnClickListener {
            showDateDialog(this, viewModel.updateRequest.date) {
                viewModel.updateRequest.date = it
            }
        }
        binding.invoiceDueDate.setOnClickListener {
            showDateDialog(
                this, viewModel.updateRequest.dueDate ?: LocalDate.now().toString()
            ) {
                viewModel.updateRequest.dueDate = it
            }
        }
        binding.request = viewModel.updateRequest
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun save() {
        viewModel.updateRequest.currency = when (binding.invoiceCurrencyGroup.checkedButtonId) {
            R.id.invoice_eur -> Currency.EUR
            R.id.invoice_usd -> Currency.USD
            else -> Currency.RON
        }
        if (binding.invoicePartner.text.toString().isBlank()) {
            binding.invoicePartner.error = getString(R.string.invalid_field)
        }
        viewModel.save()
    }

    private fun setPartners(partners: List<Partner>) {
        val partnersNames = mutableListOf<String>()
        partnersNames.addAll(partners.stream()
            .filter { partner -> partner.type == PartnerType.CLIENT }
            .map { partner -> partner.name }
            .collect(Collectors.toList()))
        binding.invoicePartner.setOnClickListener {
            showListDialog(this, partnersNames.toTypedArray()) { position ->
                val partner = partners[position]
                viewModel.updateRequest.partnerId = partner.id
                binding.invoicePartner.setText(partner.name)
            }
        }
    }

    private fun handleResult(result: InvoiceResult) {
        if (result.ok != null) {
            onBackPressed()
        } else {
            result.error?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        }
    }
}