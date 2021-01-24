package com.finance.plutus.mobile.transactions.ui

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.finance.plutus.mobile.R
import com.finance.plutus.mobile.app.util.showDateDialog
import com.finance.plutus.mobile.databinding.ActivityUpdateTransactionBinding
import com.finance.plutus.mobile.transactions.data.TransactionResult
import com.finance.plutus.mobile.transactions.data.model.Transaction
import com.finance.plutus.mobile.transactions.data.model.TransactionMethod
import com.finance.plutus.mobile.transactions.data.model.TransactionType
import org.koin.android.ext.android.inject
import java.util.stream.Collectors

class UpdateTransactionActivity : AppCompatActivity() {

    companion object {
        const val TRANSACTION = "Transaction"
    }

    private val viewModel: UpdateTransactionViewModel by inject()
    private lateinit var binding: ActivityUpdateTransactionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_update_transaction
        )
        setSupportActionBar(binding.transactionToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_close_24)
        viewModel.result.observe(this) { handleResult(it) }
        val transaction: Transaction? = intent.getParcelableExtra(TRANSACTION)
        setTransaction(transaction)
        binding.transactionSaveBtn.setOnClickListener {
            save()
        }
        binding.transactionDate.setOnClickListener {
            showDateDialog(this, viewModel.updateRequest.date) {
                viewModel.updateRequest.date = it
                binding.transactionDate.setText(it, TextView.BufferType.NORMAL)
            }
        }
        binding.request = viewModel.updateRequest
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun save() {
        viewModel.updateRequest.type = when (binding.transactionIncomeRadio.isChecked) {
            true -> TransactionType.INCOME
            else -> TransactionType.EXPENSE
        }
        viewModel.updateRequest.method = when (binding.transactionCashRadio.isChecked) {
            true -> TransactionMethod.CASH
            else -> TransactionMethod.BANK
        }
        viewModel.updateRequest.deductible = binding.transactionDeductible.isChecked
        if (binding.transactionDate.text.toString().isBlank()) {
            binding.transactionDate.error = getString(R.string.invalid_field)
        }
        if (binding.transactionDocument.text.toString().isBlank()) {
            binding.transactionDocument.error = getString(R.string.invalid_field)
        }
        if (binding.transactionDetails.text.toString().isBlank()) {
            binding.transactionDetails.error = getString(R.string.invalid_field)
        }
        viewModel.save()
    }

    private fun setTransaction(transaction: Transaction?) {
        viewModel.transaction = transaction
        transaction?.let {
            binding.transactionIncomeRadio.isChecked = it.type == TransactionType.INCOME
            binding.transactionExpenseRadio.isChecked = it.type == TransactionType.EXPENSE
            binding.transactionCashRadio.isChecked = it.method == TransactionMethod.CASH
            binding.transactionBankRadio.isChecked = it.method == TransactionMethod.BANK
            binding.transactionDeductible.isChecked = it.deductible
        }
        viewModel.partners.observe(this) { data ->
            val partnersNames = mutableListOf<String>()
            partnersNames.addAll(data.stream().map { partner -> partner.name }
                .collect(Collectors.toList()))
            val adapter = ArrayAdapter(
                this, R.layout.spinner_item,
                partnersNames
            )
            adapter.setDropDownViewResource(R.layout.spinner_item)
            binding.transactionPartnerSpinner.adapter = adapter
            transaction?.let {
                var position: Int = data.indexOf(it.partner)
                if (position == -1) {
                    position = 0
                }
                viewModel.updateRequest.partnerId = it.partner.id
                binding.transactionPartnerSpinner.setSelection(position)
            }
            binding.transactionPartnerSpinner.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        val partner = data[position]
                        viewModel.updateRequest.partnerId = partner.id
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }
        }
    }

    private fun handleResult(result: TransactionResult) {
        if (result.ok != null) {
            onBackPressed()
        } else {
            result.error?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        }
    }
}