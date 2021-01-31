package com.finance.plutus.mobile.invoices.ui

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.paging.PagingData
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.finance.plutus.mobile.R
import com.finance.plutus.mobile.app.util.Buttons
import com.finance.plutus.mobile.app.util.SwipeHelper
import com.finance.plutus.mobile.app.util.showConfirmationDialog
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
    private lateinit var adapter: InvoiceAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_invoices, container, false
        )
        setHasOptionsMenu(true)
        setupRecycler()
        viewModel.invoices.observe(viewLifecycleOwner) { setInvoices(it) }
        viewModel.fetchInvoices()
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_add, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add) {
            openAddInvoiceActivity()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openAddInvoiceActivity() {
        val intent = Intent(requireContext(), UpdateInvoiceActivity::class.java)
        startActivity(intent)
    }

    private fun setInvoices(invoices: PagingData<Invoice>) {
        adapter.submitData(lifecycle, invoices)
    }

    private fun setupRecycler() {
        adapter = InvoiceAdapter(object : InvoiceSwipeListener {
            override fun delete(invoice: Invoice) {
                deleteInvoice(invoice)
            }

            override fun edit(invoice: Invoice) {
                editInvoice(invoice)
            }

            override fun collect(invoice: Invoice) {
                collectInvoice(invoice)
            }
        }, requireContext())
        binding.invoicesRecyclerView.layoutManager =
            LinearLayoutManager(requireContext())
        binding.invoicesRecyclerView.adapter = adapter
        setupSwipeActions()
    }

    private fun setupSwipeActions() {
        val itemTouchHelper = ItemTouchHelper(object : SwipeHelper(binding.invoicesRecyclerView) {
            override fun instantiateUnderlayButton(position: Int): List<UnderlayButton> {
                val buttons = mutableListOf<UnderlayButton>()
                if (adapter.isDraft(position)) {
                    buttons.add(Buttons.deleteButton(requireContext()) {
                        adapter.onDelete(position)
                    })
                    buttons.add(Buttons.editButton(requireContext()) {
                        adapter.onEdit(position)
                    })
//                    buttons.add(Buttons.cashingButton(requireContext()) {
//                        adapter.onCashing(position)
//                    })
                }
                return buttons
            }
        })
        itemTouchHelper.attachToRecyclerView(binding.invoicesRecyclerView)
    }

    private fun editInvoice(invoice: Invoice) {
        val intent = Intent(requireContext(), UpdateInvoiceActivity::class.java)
        intent.putExtra(UpdateInvoiceActivity.INVOICE, invoice)
        startActivity(intent)
    }

    private fun deleteInvoice(invoice: Invoice) {
        showConfirmationDialog(requireContext(), R.string.delete_confirmation) {
            viewModel.delete(
                invoice
            )
        }
    }

    private fun collectInvoice(invoice: Invoice) {
        viewModel.collect(invoice)
    }

}