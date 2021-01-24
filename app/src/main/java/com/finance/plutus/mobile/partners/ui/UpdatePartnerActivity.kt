package com.finance.plutus.mobile.partners.ui

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.finance.plutus.mobile.R
import com.finance.plutus.mobile.app.data.model.Bank
import com.finance.plutus.mobile.app.data.model.BusinessType
import com.finance.plutus.mobile.app.data.model.Country
import com.finance.plutus.mobile.databinding.ActivityUpdatePartnerBinding
import com.finance.plutus.mobile.partners.data.model.Partner
import com.finance.plutus.mobile.partners.data.model.PartnerResult
import com.finance.plutus.mobile.partners.data.model.PartnerType
import org.koin.android.ext.android.inject
import java.util.stream.Collectors

class UpdatePartnerActivity : AppCompatActivity() {

    companion object {
        const val PARTNER = "Partner"
    }

    private val viewModel: UpdatePartnerViewModel by inject()
    private lateinit var binding: ActivityUpdatePartnerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_update_partner
        )
        setSupportActionBar(binding.partnerToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_close_24)
        viewModel.result.observe(this) { handleResult(it) }
        val partner: Partner? = intent.getParcelableExtra(PARTNER)
        setPartner(partner)
        binding.partnerSaveBtn.setOnClickListener {
            save()
        }
        binding.request = viewModel.updateRequest
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun save() {
        viewModel.updateRequest.type = when (binding.partnerCustomerRadio.isChecked) {
            true -> PartnerType.CLIENT
            else -> PartnerType.VENDOR
        }
        viewModel.updateRequest.businessType =
            when (binding.partnerBusinessIndividualRadio.isChecked) {
                true -> BusinessType.INDIVIDUAL
                else -> BusinessType.LEGAL
            }
        if (binding.partnerName.text.toString().isBlank()) {
            binding.partnerName.error = getString(R.string.invalid_field)
        }
        viewModel.save()
    }

    private fun setPartner(partner: Partner?) {
        viewModel.partner = partner
        viewModel.banks.observe(this) { setBanks(it, partner) }
        viewModel.countries.observe(this) { setCountries(it, partner) }
        partner?.let {
            binding.partnerCustomerRadio.isChecked = it.type == PartnerType.CLIENT
            binding.partnerVendorRadio.isChecked = it.type == PartnerType.VENDOR
            binding.partnerBusinessIndividualRadio.isChecked =
                it.businessType == BusinessType.INDIVIDUAL
            binding.partnerBusinessLegalRadio.isChecked = it.businessType == BusinessType.LEGAL
        }
    }

    private fun setBanks(banks: List<Bank>, partner: Partner?) {
        val banksNames = mutableListOf("Selecteaza Banca")
        banksNames.addAll(banks.stream().map { bank -> bank.name }
            .collect(Collectors.toList()))
        val adapter = ArrayAdapter(
            this, R.layout.spinner_item,
            banksNames
        )
        adapter.setDropDownViewResource(R.layout.spinner_item)
        binding.partnerBankSpinner.adapter = adapter
        partner?.let {
            it.bank?.let { bank ->
                var position: Int = banks.indexOf(bank)
                if (position == -1) {
                    position = 0
                }
                viewModel.updateRequest.bankId = bank.id
                binding.partnerBankSpinner.setSelection(position)
            }
        }
        binding.partnerBankSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position == 0) return
                    val bank = banks[position]
                    viewModel.updateRequest.bankId = bank.id
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }

    private fun setCountries(countries: List<Country>, partner: Partner?) {
        val countriesNames = mutableListOf<String>()
        countriesNames.addAll(countries.stream().map { country -> country.name }
            .collect(Collectors.toList()))
        val adapter = ArrayAdapter(
            this, R.layout.spinner_item,
            countriesNames
        )
        adapter.setDropDownViewResource(R.layout.spinner_item)
        binding.partnerCountrySpinner.adapter = adapter
        partner?.let {
            var position: Int = countries.indexOf(it.country)
            if (position == -1) {
                position = 0
            }
            viewModel.updateRequest.countryCode = it.country.code
            binding.partnerCountrySpinner.setSelection(position)
        }
        binding.partnerCountrySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val country = countries[position]
                    viewModel.updateRequest.countryCode = country.code
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }

    private fun handleResult(result: PartnerResult) {
        if (result.ok != null) {
            onBackPressed()
        } else {
            result.error?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        }
    }
}