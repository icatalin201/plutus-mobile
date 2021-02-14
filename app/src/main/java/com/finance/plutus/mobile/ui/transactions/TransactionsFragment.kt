package com.finance.plutus.mobile.ui.transactions

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.paging.PagingData
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.finance.plutus.mobile.R
import com.finance.plutus.mobile.data.model.Transaction
import com.finance.plutus.mobile.data.model.TransactionMonth
import com.finance.plutus.mobile.databinding.FragmentTransactionsBinding
import com.finance.plutus.mobile.ext.showConfirmationDialog
import com.finance.plutus.mobile.ui.updatetransaction.UpdateTransactionActivity
import com.finance.plutus.mobile.util.Buttons
import com.finance.plutus.mobile.util.SwipeHelper
import org.koin.android.ext.android.inject

/**
 * Plutus Finance
 * Created by Catalin on 1/23/2021
 **/
class TransactionsFragment : Fragment() {

    private val viewModel: TransactionsViewModel by inject()
    private lateinit var binding: FragmentTransactionsBinding
    private lateinit var adapter: TransactionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_transactions, container, false
        )
        setHasOptionsMenu(true)
        setupRecycler()
        viewModel.transactions.observe(viewLifecycleOwner) { setTransactions(it) }
        viewModel.months.observe(viewLifecycleOwner) { setMonths(it) }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchTransactions()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_add, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add) {
            openAddTransactionActivity()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openAddTransactionActivity() {
        val intent = Intent(requireContext(), UpdateTransactionActivity::class.java)
        startActivity(intent)
    }

    private fun setMonths(months: List<TransactionMonth>) {
        binding.transactionsMonthsRecyclerView.layoutManager =
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                true
            )
        val adapter = MonthAdapter(
            months,
            requireContext(),
            object : MonthClickListener {
                override fun onClick(month: TransactionMonth) {
                    viewModel.month = month
                    viewModel.fetchTransactions()
                }
            }
        )
        binding.transactionsMonthsRecyclerView.adapter = adapter
        adapter.select(months[0])
    }

    private fun setTransactions(transactions: PagingData<Transaction>) {
        adapter.submitData(lifecycle, transactions)
    }

    private fun setupRecycler() {
        adapter = TransactionAdapter(
            object : TransactionSwipeListener {
                override fun delete(transaction: Transaction) {
                    deleteTransaction(transaction)
                }

                override fun edit(transaction: Transaction) {
                    editTransaction(transaction)
                }

                override fun collect(transaction: Transaction) {
                    collectTransaction(transaction)
                }
            },
            object : TransactionClickListener {
                override fun onClick(transaction: Transaction) {
                    val fragment = BottomSheetTransaction.getInstance(transaction)
                    fragment.show(parentFragmentManager, "Transaction")
                }
            },
            requireContext()
        )
        binding.transactionsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.transactionsRecyclerView.adapter = adapter
        setupSwipeActions()
    }

    private fun setupSwipeActions() {
        val itemTouchHelper =
            ItemTouchHelper(object : SwipeHelper(binding.transactionsRecyclerView) {
                override fun instantiateUnderlayButton(position: Int): List<UnderlayButton> {
                    val buttons = mutableListOf<UnderlayButton>()
                    buttons.add(Buttons.deleteButton(requireContext()) {
                        adapter.onDelete(position)
                    })
                    buttons.add(Buttons.editButton(requireContext()) {
                        adapter.onEdit(position)
                    })
                    buttons.add(Buttons.cashingButton(requireContext()) {
                        adapter.onCashing(position)
                    })
                    return buttons
                }
            })
        itemTouchHelper.attachToRecyclerView(binding.transactionsRecyclerView)
    }

    private fun editTransaction(transaction: Transaction) {
        val intent = Intent(requireContext(), UpdateTransactionActivity::class.java)
        intent.putExtra(UpdateTransactionActivity.TRANSACTION, transaction)
        startActivity(intent)
    }

    private fun deleteTransaction(transaction: Transaction) {
        showConfirmationDialog(requireContext(), R.string.delete_confirmation) {
            viewModel.delete(
                transaction
            )
        }
    }

    private fun collectTransaction(transaction: Transaction) {
        showConfirmationDialog(requireContext(), R.string.collect_confirmation) {
            viewModel.collect(transaction)
        }
    }

}