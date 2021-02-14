package com.finance.plutus.mobile.ui.partners

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.paging.PagingData
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.finance.plutus.mobile.R
import com.finance.plutus.mobile.data.model.Partner
import com.finance.plutus.mobile.databinding.FragmentPartnersBinding
import com.finance.plutus.mobile.ext.showConfirmationDialog
import com.finance.plutus.mobile.ui.updatepartner.UpdatePartnerActivity
import com.finance.plutus.mobile.util.Buttons
import com.finance.plutus.mobile.util.SwipeHelper
import org.koin.android.ext.android.inject

/**
 * Plutus Finance
 * Created by Catalin on 1/23/2021
 **/
class PartnersFragment : Fragment() {

    private val viewModel: PartnersViewModel by inject()
    private lateinit var binding: FragmentPartnersBinding
    private lateinit var adapter: PartnerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_partners, container, false
        )
        setHasOptionsMenu(true)
        setupRecycler()
        viewModel.partners.observe(viewLifecycleOwner) { setPartners(it) }
        return binding.root
    }

    override fun onResume() {
        viewModel.fetchPartners()
        super.onResume()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_add, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add) {
            openAddPartnerActivity()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openAddPartnerActivity() {
        val intent = Intent(requireContext(), UpdatePartnerActivity::class.java)
        startActivity(intent)
    }

    private fun setPartners(partners: PagingData<Partner>) {
        adapter.submitData(lifecycle, partners)
    }

    private fun setupRecycler() {
        adapter = PartnerAdapter(object : PartnerSwipeListener {
            override fun delete(partner: Partner) {
                deletePartner(partner)
            }

            override fun edit(partner: Partner) {
                editPartner(partner)
            }
        }, requireContext())
        binding.partnersRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.partnersRecyclerView.adapter = adapter
        setupSwipeActions()
    }

    private fun setupSwipeActions() {
        val itemTouchHelper = ItemTouchHelper(object : SwipeHelper(binding.partnersRecyclerView) {
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
        itemTouchHelper.attachToRecyclerView(binding.partnersRecyclerView)
    }

    private fun editPartner(partner: Partner) {
        val intent = Intent(requireContext(), UpdatePartnerActivity::class.java)
        intent.putExtra(UpdatePartnerActivity.PARTNER, partner)
        startActivity(intent)
    }

    private fun deletePartner(partner: Partner) {
        showConfirmationDialog(requireContext(), R.string.delete_confirmation) {
            viewModel.delete(
                partner
            )
        }
    }

}