package com.finance.plutus.mobile.transactions.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.finance.plutus.mobile.R
import com.finance.plutus.mobile.app.data.model.Currency
import com.finance.plutus.mobile.app.util.showDateDialog
import com.finance.plutus.mobile.app.util.showListDialog
import com.finance.plutus.mobile.databinding.ActivityUpdateTransactionBinding
import com.finance.plutus.mobile.transactions.data.model.Transaction
import com.finance.plutus.mobile.transactions.data.model.TransactionMethod
import com.finance.plutus.mobile.transactions.data.model.TransactionResult
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
        title = when (transaction == null) {
            true -> getString(R.string.create_transaction_title)
            else -> getString(R.string.update_transaction_title)
        }
        setTransaction(transaction)
        binding.transactionSaveBtn.setOnClickListener {
            save()
        }
        binding.transactionDate.setOnClickListener {
            showDateDialog(this, viewModel.updateRequest.date) {
                viewModel.updateRequest.date = it
            }
        }
        binding.request = viewModel.updateRequest
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun save() {
        viewModel.updateRequest.type = when (binding.transactionTypeGroup.checkedButtonId) {
            R.id.transaction_income -> TransactionType.INCOME
            else -> TransactionType.EXPENSE
        }
        viewModel.updateRequest.method = when (binding.transactionMethodGroup.checkedButtonId) {
            R.id.transaction_cash -> TransactionMethod.CASH
            else -> TransactionMethod.BANK
        }
        viewModel.updateRequest.currency = when (binding.transactionCurrencyGroup.checkedButtonId) {
            R.id.transaction_eur -> Currency.EUR
            R.id.transaction_usd -> Currency.USD
            else -> Currency.RON
        }
        viewModel.updateRequest.deductible = binding.transactionDeductible.isChecked
        if (validate()) {
            viewModel.save()
        }
    }

    private fun validate(): Boolean {
        var valid = true
        if (binding.transactionDate.text.toString().isBlank()) {
            binding.transactionDate.error = getString(R.string.invalid_field)
            valid = false
        }
        if (binding.transactionDocument.text.toString().isBlank()) {
            binding.transactionDocument.error = getString(R.string.invalid_field)
            valid = false
        }
        if (binding.transactionDetails.text.toString().isBlank()) {
            binding.transactionDetails.error = getString(R.string.invalid_field)
            valid = false
        }
        return valid
    }

    private fun setTransaction(transaction: Transaction?) {
        viewModel.transaction = transaction
        transaction?.let {
            if (it.type == TransactionType.INCOME) {
                binding.transactionTypeGroup.check(R.id.transaction_income)
            } else {
                binding.transactionTypeGroup.check(R.id.transaction_expense)
            }
            if (it.method == TransactionMethod.CASH) {
                binding.transactionMethodGroup.check(R.id.transaction_cash)
            } else {
                binding.transactionMethodGroup.check(R.id.transaction_bank)
            }
            binding.transactionDeductible.isChecked = it.deductible
            val currency = when (it.currency?.currency) {
                Currency.EUR -> R.id.transaction_eur
                Currency.USD -> R.id.transaction_usd
                else -> R.id.transaction_ron
            }
            binding.transactionCurrencyGroup.check(currency)
        }
        viewModel.partners.observe(this) { data ->
            val partnersNames = mutableListOf<String>()
            partnersNames.addAll(data.stream().map { partner -> partner.name }
                .collect(Collectors.toList()))
            transaction?.let {
                viewModel.updateRequest.partnerId = it.partner.id
                binding.transactionPartner.setText(it.partner.name)
            }
            binding.transactionPartner.setOnClickListener {
                showListDialog(this, partnersNames.toTypedArray()) { position ->
                    val partner = data[position]
                    viewModel.updateRequest.partnerId = partner.id
                    binding.transactionPartner.setText(partner.name)
                }
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