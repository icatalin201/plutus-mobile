package com.finance.plutus.mobile.ui.updateinvoice

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.finance.plutus.mobile.R
import com.finance.plutus.mobile.data.model.*
import com.finance.plutus.mobile.databinding.ActivityUpdateInvoiceBinding
import com.finance.plutus.mobile.ext.showDateDialog
import com.finance.plutus.mobile.ext.showListDialog
import org.koin.android.ext.android.inject
import java.time.LocalDate
import java.util.stream.Collectors

class UpdateInvoiceActivity : AppCompatActivity() {

    companion object {
        const val INVOICE = "Invoice"
    }

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
        val invoice = intent.getParcelableExtra<Invoice>(INVOICE)
        setInvoice(invoice)
        viewModel.result.observe(this) { handleResult(it) }
        viewModel.partners.observe(this) { setPartners(it) }
        viewModel.items.observe(this) { setItems(it) }
        viewModel.serial.observe(this) { serial ->
            title = serial
        }
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

    private fun setInvoice(invoice: Invoice?) {
        viewModel.submitInvoice(invoice)
        invoice?.let {
            val currency = when (invoice.currency?.currency) {
                Currency.EUR -> R.id.invoice_eur
                Currency.RON -> R.id.invoice_ron
                else -> R.id.invoice_usd
            }
            binding.invoiceCurrencyGroup.check(currency)
        }
    }

    private fun save() {
        viewModel.updateRequest.currency = when (binding.invoiceCurrencyGroup.checkedButtonId) {
            R.id.invoice_eur -> Currency.EUR
            R.id.invoice_usd -> Currency.USD
            else -> Currency.RON
        }
        if (validate()) {
            viewModel.save()
        }
    }

    private fun validate(): Boolean {
        if (binding.invoicePartner.text.toString().isBlank()) {
            binding.invoicePartner.error = getString(R.string.invalid_field)
            return false
        }
        if (binding.invoiceLineItem.text.toString().isBlank()) {
            binding.invoiceLineItem.error = getString(R.string.invalid_field)
            return false
        }
        return true
    }

    private fun setPartners(partners: List<Partner>) {
        val partnersNames = mutableListOf<String>()
        partnersNames.addAll(partners.stream()
                .filter { partner -> partner.type == PartnerType.CLIENT }
                .map { partner -> partner.name }
                .collect(Collectors.toList()))
        viewModel.invoice?.let {
            binding.invoicePartner.setText(it.partner.name)
        }
        binding.invoicePartner.setOnClickListener {
            showListDialog(this, partnersNames.toTypedArray()) { position ->
                val partner = partners[position]
                viewModel.updateRequest.partnerId = partner.id
                binding.invoicePartner.setText(partner.name)
            }
        }
    }

    private fun setItems(items: List<Item>) {
        val itemsNames = mutableListOf<String>()
        itemsNames.addAll(items.stream()
                .map { item -> item.name }
                .collect(Collectors.toList()))
        val line = viewModel.updateRequest.lines[0]
        binding.line = line
        val initialItem = items.findLast { item -> item.id == line.itemId }
        binding.invoiceLineItem.setText(initialItem?.name)
        binding.invoiceLineItem.setOnClickListener {
            showListDialog(
                    this@UpdateInvoiceActivity,
                    itemsNames.toTypedArray()
            ) { position ->
                val item = items[position]
                viewModel.updateRequest.lines[0].itemId = item.id
                binding.invoiceLineItem.setText(item.name)
            }
        }
    }

    private fun handleResult(result: Result) {
        if (result.ok != null) {
            onBackPressed()
        } else {
            result.error?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        }
    }
}