package com.finance.plutus.mobile.transactions.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.finance.plutus.mobile.R
import com.finance.plutus.mobile.app.util.formatInLocalCurrency
import com.finance.plutus.mobile.databinding.TransactionBottomSheetBinding
import com.finance.plutus.mobile.transactions.data.model.Transaction
import com.finance.plutus.mobile.transactions.data.model.TransactionType
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
Plutus Finance
Created by Catalin on 2/6/2021
 **/
class BottomSheetTransaction : BottomSheetDialogFragment() {

    companion object {

        private const val TRANSACTION = "Transaction"

        fun getInstance(transaction: Transaction): BottomSheetTransaction {
            val bottomSheet = BottomSheetTransaction()
            val bundle = Bundle()
            bundle.putParcelable(TRANSACTION, transaction)
            bottomSheet.arguments = bundle
            return bottomSheet
        }
    }

    private lateinit var binding: TransactionBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.transaction_bottom_sheet,
            container,
            false
        )
        val transaction = arguments?.getParcelable<Transaction>(TRANSACTION)
        transaction?.let {
            binding.transaction = transaction
            val color = when (transaction.type) {
                TransactionType.INCOME -> android.R.color.holo_green_dark
                TransactionType.EXPENSE -> android.R.color.holo_red_dark
            }
            binding.transactionValueTv.text = transaction.value.formatInLocalCurrency()
            binding.transactionValueTv.setTextColor(requireContext().getColor(color))
        }
        return binding.root
    }

}