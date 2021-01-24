package com.finance.plutus.mobile.invoices.ui

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
import com.finance.plutus.mobile.R
import com.finance.plutus.mobile.app.util.Buttons
import com.finance.plutus.mobile.app.util.SwipeHelper
import com.finance.plutus.mobile.app.util.showDeleteConfirmationDialog
import com.finance.plutus.mobile.databinding.FragmentInvoicesBinding
import com.finance.plutus.mobile.invoices.data.model.Invoice
import org.koin.android.ext.android.inject

/**
 * Plutus Finance
 * Created by Catalin on 1/23/2021
 **/
class InvoicesFragment : Fragment() {

    private val viewModel: InvoicesViewModel by inject()
    private lateinit var binding: FragmentInvoicesBinding
    private val adapter = InvoiceAdapter(object : InvoiceSwipeListener {
        override fun delete(invoice: Invoice) {
            deleteInvoice(invoice)
        }

        override fun edit(invoice: Invoice) {
            editInvoice(invoice)
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_invoices, container, false
        )
        setupRecycler()
        binding.invoicesSwipeLayout.setOnRefreshListener {
            triggerSwipeRefresh()
        }
        binding.invoicesAddBtn.setOnClickListener {
            openAddInvoiceActivity()
        }
        viewModel.invoices.observe(viewLifecycleOwner) { setInvoices(it) }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.invoicesSwipeLayout.post {
            triggerSwipeRefresh()
        }
    }

    private fun triggerSwipeRefresh() {
        binding.invoicesSwipeLayout.isRefreshing = true
        viewModel.fetchInvoices()
    }

    private fun openAddInvoiceActivity() {
        val intent = Intent(requireContext(), UpdateInvoiceActivity::class.java)
        startActivity(intent)
    }

    private fun setInvoices(invoices: PagingData<Invoice>) {
        binding.invoicesSwipeLayout.isRefreshing = false
        adapter.submitData(lifecycle, invoices)
    }

    private fun setupRecycler() {
        binding.invoicesRecyclerView.layoutManager =
            LinearLayoutManager(requireContext())
        binding.invoicesRecyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        binding.invoicesRecyclerView.adapter = adapter
        setupSwipeActions()
    }

    private fun setupSwipeActions() {
        val itemTouchHelper = ItemTouchHelper(object : SwipeHelper(binding.invoicesRecyclerView) {
            override fun instantiateUnderlayButton(position: Int): List<UnderlayButton> {
                val deleteButton = Buttons.deleteButton(requireContext()) {
                    adapter.onDelete(position)
                }
                val editButton = Buttons.editButton(requireContext()) {
                    adapter.onEdit(position)
                }
                return listOf(editButton, deleteButton)
            }
        })
        itemTouchHelper.attachToRecyclerView(binding.invoicesRecyclerView)
    }

    private fun editInvoice(invoice: Invoice) {

    }

    private fun deleteInvoice(invoice: Invoice) {
        showDeleteConfirmationDialog(requireContext()) { viewModel.delete(invoice) }
    }

}