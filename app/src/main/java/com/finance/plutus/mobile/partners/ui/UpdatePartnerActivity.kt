package com.finance.plutus.mobile.partners.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.finance.plutus.mobile.R
import com.finance.plutus.mobile.app.data.model.Bank
import com.finance.plutus.mobile.app.data.model.BusinessType
import com.finance.plutus.mobile.app.data.model.Country
import com.finance.plutus.mobile.app.util.showListDialog
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
        title = when (partner == null) {
            true -> getString(R.string.create_partner_title)
            else -> getString(R.string.update_partner_title)
        }
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
        viewModel.updateRequest.type = when (binding.partnerTypeGroup.checkedButtonId) {
            R.id.partner_customer -> PartnerType.CLIENT
            else -> PartnerType.VENDOR
        }
        viewModel.updateRequest.businessType =
            when (binding.partnerBusinessTypeGroup.checkedButtonId) {
                R.id.partner_business_individual -> BusinessType.INDIVIDUAL
                else -> BusinessType.LEGAL
            }
        if (binding.partnerName.text.toString().isBlank()) {
            binding.partnerName.error = getString(R.string.invalid_field)
        }
        if (binding.partnerCountry.text.toString().isBlank()) {
            binding.partnerCountry.error = getString(R.string.invalid_field)
        }
        viewModel.save()
    }

    private fun setPartner(partner: Partner?) {
        viewModel.partner = partner
        viewModel.banks.observe(this) { setBanks(it, partner) }
        viewModel.countries.observe(this) { setCountries(it, partner) }
        partner?.let {
            if (it.type == PartnerType.CLIENT) {
                binding.partnerTypeGroup.check(R.id.partner_customer)
            } else {
                binding.partnerTypeGroup.check(R.id.partner_vendor)
            }
            if (it.businessType == BusinessType.INDIVIDUAL) {
                binding.partnerBusinessTypeGroup.check(R.id.partner_business_individual)
            } else {
                binding.partnerBusinessTypeGroup.check(R.id.partner_business_legal)
            }
        }
    }

    private fun setBanks(banks: List<Bank>, partner: Partner?) {
        val banksNames = banks.stream().map { bank -> bank.name }
            .collect(Collectors.toList())
        partner?.let {
            it.bank?.let { bank ->
                viewModel.updateRequest.bankId = bank.id
                binding.partnerBank.setText(bank.name)
            }
        }
        binding.partnerBank.setOnClickListener {
            showListDialog(this, banksNames.toTypedArray()) { position ->
                val bank = banks[position]
                viewModel.updateRequest.bankId = bank.id
                binding.partnerBank.setText(bank.name)
            }
        }
    }

    private fun setCountries(countries: List<Country>, partner: Partner?) {
        val countriesNames = mutableListOf<String>()
        countriesNames.addAll(countries.stream().map { country -> country.name }
            .collect(Collectors.toList()))
        partner?.let {
            viewModel.updateRequest.countryCode = it.country.code
            binding.partnerCountry.setText(it.country.name)
        }
        binding.partnerCountry.setOnClickListener {
            showListDialog(this, countriesNames.toTypedArray()) { position ->
                val country = countries[position]
                viewModel.updateRequest.countryCode = country.code
                binding.partnerCountry.setText(country.name)
            }
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