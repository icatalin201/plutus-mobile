package com.finance.plutus.mobile.items.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.paging.PagingData
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.finance.plutus.mobile.R
import com.finance.plutus.mobile.app.util.Buttons
import com.finance.plutus.mobile.app.util.SwipeHelper
import com.finance.plutus.mobile.app.util.showConfirmationDialog
import com.finance.plutus.mobile.databinding.FragmentServicesBinding
import com.finance.plutus.mobile.items.data.model.Item
import org.koin.android.ext.android.inject

/**
 * Plutus Finance
 * Created by Catalin on 1/23/2021
 **/
class ServicesFragment : Fragment() {

    private val viewModel: ServicesViewModel by inject()
    private lateinit var binding: FragmentServicesBinding
    private lateinit var adapter: ItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_services, container, false
        )
        setupRecycler()
        binding.servicesAddBtn.setOnClickListener {
            openAddItemActivity()
        }
        viewModel.items.observe(viewLifecycleOwner) { setServices(it) }
        viewModel.fetchItems()
        return binding.root
    }

    private fun openAddItemActivity() {
        val intent = Intent(requireContext(), UpdateItemActivity::class.java)
        startActivity(intent)
    }

    private fun setServices(items: PagingData<Item>) {
        adapter.submitData(lifecycle, items)
    }

    private fun setupRecycler() {
        adapter = ItemAdapter(object : ItemSwipeListener {
            override fun delete(item: Item) {
                deleteItem(item)
            }

            override fun edit(item: Item) {
                editItem(item)
            }
        }, requireContext())
        binding.servicesRecyclerView.layoutManager =
            LinearLayoutManager(requireContext())
        binding.servicesRecyclerView.adapter = adapter
        setupSwipeActions()
    }

    private fun setupSwipeActions() {
        val itemTouchHelper = ItemTouchHelper(object : SwipeHelper(binding.servicesRecyclerView) {
            override fun instantiateUnderlayButton(position: Int): List<UnderlayButton> {
                val deleteButton = Buttons.deleteButton(requireContext()) {
                    adapter.onDelete(position)
                }
                val editButton = Buttons.editButton(requireContext()) {
                    adapter.onEdit(position)
                }
                return listOf(deleteButton, editButton)
            }
        })
        itemTouchHelper.attachToRecyclerView(binding.servicesRecyclerView)
    }

    private fun editItem(item: Item) {
        val intent = Intent(requireContext(), UpdateItemActivity::class.java)
        intent.putExtra(UpdateItemActivity.ITEM, item)
        startActivity(intent)
    }

    private fun deleteItem(item: Item) {
        showConfirmationDialog(requireContext(), R.string.delete_confirmation) {
            viewModel.delete(
                item
            )
        }
    }

}