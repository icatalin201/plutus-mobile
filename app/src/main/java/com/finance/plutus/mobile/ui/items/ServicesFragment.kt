package com.finance.plutus.mobile.ui.items

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.paging.PagingData
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.finance.plutus.mobile.R
import com.finance.plutus.mobile.data.model.Item
import com.finance.plutus.mobile.databinding.FragmentServicesBinding
import com.finance.plutus.mobile.ext.showConfirmationDialog
import com.finance.plutus.mobile.ui.updateitem.UpdateItemActivity
import com.finance.plutus.mobile.util.Buttons
import com.finance.plutus.mobile.util.SwipeHelper
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
        setHasOptionsMenu(true)
        setupRecycler()
        viewModel.items.observe(viewLifecycleOwner) { setServices(it) }
        return binding.root
    }

    override fun onResume() {
        viewModel.fetchItems()
        super.onResume()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_add, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add) {
            openAddItemActivity()
        }
        return super.onOptionsItemSelected(item)
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