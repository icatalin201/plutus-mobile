package com.finance.plutus.mobile.view.partner

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.finance.plutus.mobile.R
import com.finance.plutus.mobile.databinding.ActivityUpdatePartnerBinding
import com.finance.plutus.mobile.model.Partner
import org.koin.android.ext.android.inject

class UpdatePartnerActivity : AppCompatActivity() {

    companion object {
        const val PARTNER = "Partner"
    }

    private val viewModel: UpdatePartnerViewModel by inject()
    private lateinit var binding: ActivityUpdatePartnerBinding
    private var partner: Partner? = null

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
        partner = intent.getParcelableExtra(PARTNER)
        setPartner(partner)
        binding.partnerSaveBtn.setOnClickListener {
            save()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun save() {
//        val request = PartnerUpdateRequest()
//        if (partner != null) {
//            viewModel.update(partner!!.id, request)
//        } else {
//            viewModel.create(request)
//        }
    }

    private fun setPartner(partner: Partner?) {
        partner?.let {
            binding.partnerName.setText(it.name, TextView.BufferType.EDITABLE)
            binding.partnerEmail.setText(it.email, TextView.BufferType.EDITABLE)
            binding.partnerPhone.setText(it.phone, TextView.BufferType.EDITABLE)
            binding.partnerAddress.setText(it.address, TextView.BufferType.EDITABLE)
            binding.partnerBankAccount.setText(it.bankAccount, TextView.BufferType.EDITABLE)
            it.termInDays?.let { days ->
                binding.partnerTermInDays.setText(
                    days.toString(),
                    TextView.BufferType.EDITABLE
                )
            }
            binding.partnerCommercialRegistry.setText(
                it.commercialRegistry,
                TextView.BufferType.EDITABLE
            )
            binding.partnerVat.setText(it.vat, TextView.BufferType.EDITABLE)
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