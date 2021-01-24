package com.finance.plutus.mobile.transactions.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.finance.plutus.mobile.R
import com.finance.plutus.mobile.app.util.Buttons
import com.finance.plutus.mobile.app.util.SwipeHelper
import com.finance.plutus.mobile.app.util.showDeleteConfirmationDialog
import com.finance.plutus.mobile.databinding.FragmentTransactionsBinding
import com.finance.plutus.mobile.transactions.data.model.Transaction
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
        adapter = TransactionAdapter(object : TransactionSwipeListener {
            override fun delete(transaction: Transaction) {
                deleteTransaction(transaction)
            }

            override fun edit(transaction: Transaction) {
                editTransaction(transaction)
            }
        }, requireContext())
        setupRecycler()
        binding.transactionsSwipeLayout.setOnRefreshListener {
            triggerSwipeRefresh()
        }
        binding.transactionsAddBtn.setOnClickListener {
            openAddTransactionActivity()
        }
        viewModel.transactions.observe(viewLifecycleOwner) { setTransactions(it) }
        return binding.root
    }

    override fun onResume() {
        binding.transactionsSwipeLayout.post {
            triggerSwipeRefresh()
        }
        super.onResume()
    }

    private fun triggerSwipeRefresh() {
        binding.transactionsSwipeLayout.isRefreshing = true
        adapter.submitData(lifecycle, PagingData.empty())
        viewModel.fetchTransactions()
    }

    private fun openAddTransactionActivity() {
        val intent = Intent(requireContext(), UpdateTransactionActivity::class.java)
        startActivity(intent)
    }

    private fun setTransactions(transactions: PagingData<Transaction>) {
        binding.transactionsSwipeLayout.isRefreshing = false
        adapter.submitData(lifecycle, transactions)
    }

    private fun setupRecycler() {
        binding.transactionsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.transactionsRecyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        binding.transactionsRecyclerView.adapter = adapter
        setupSwipeActions()
        binding.transactionsRecyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0 || dy < 0 && binding.transactionsAddBtn.isShown) {
                    binding.transactionsAddBtn.hide()
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    binding.transactionsAddBtn.show()
                }
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
    }

    private fun setupSwipeActions() {
        val itemTouchHelper =
            ItemTouchHelper(object : SwipeHelper(binding.transactionsRecyclerView) {
                override fun instantiateUnderlayButton(position: Int): List<UnderlayButton> {
                    val buttons = mutableListOf<UnderlayButton>()
                    if (adapter.isEditable(position)) {
                        val editButton = Buttons.editButton(requireContext()) {
                            adapter.onEdit(position)
                        }
                        buttons.add(editButton)
                    }
                    if (adapter.isDeletable(position)) {
                        val deleteButton = Buttons.deleteButton(requireContext()) {
                            adapter.onDelete(position)
                        }
                        buttons.add(deleteButton)
                    }
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
        showDeleteConfirmationDialog(requireContext()) { viewModel.delete(transaction) }
    }

}