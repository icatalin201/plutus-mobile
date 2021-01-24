package com.finance.plutus.mobile.partners.ui

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
import com.finance.plutus.mobile.databinding.FragmentPartnersBinding
import com.finance.plutus.mobile.partners.data.model.Partner
import org.koin.android.ext.android.inject

/**
 * Plutus Finance
 * Created by Catalin on 1/23/2021
 **/
class PartnersFragment : Fragment() {

    private val viewModel: PartnersViewModel by inject()
    private lateinit var binding: FragmentPartnersBinding
    private val adapter = PartnerAdapter(object : PartnerSwipeListener {
        override fun delete(partner: Partner) {
            deletePartner(partner)
        }

        override fun edit(partner: Partner) {
            editPartner(partner)
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_partners, container, false
        )
        setupRecycler()
        binding.partnersSwipeLayout.setOnRefreshListener {
            triggerSwipeRefresh()
        }
        binding.partnersAddBtn.setOnClickListener {
            openAddPartnerActivity()
        }
        viewModel.partners.observe(viewLifecycleOwner) { setPartners(it) }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.partnersSwipeLayout.post {
            triggerSwipeRefresh()
        }
    }

    private fun triggerSwipeRefresh() {
        binding.partnersSwipeLayout.isRefreshing = true
        viewModel.fetchPartners()
    }

    private fun openAddPartnerActivity() {
        val intent = Intent(requireContext(), UpdatePartnerActivity::class.java)
        startActivity(intent)
    }

    private fun setPartners(partners: PagingData<Partner>) {
        binding.partnersSwipeLayout.isRefreshing = false
        adapter.submitData(lifecycle, partners)
    }

    private fun setupRecycler() {
        binding.partnersRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.partnersRecyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        binding.partnersRecyclerView.adapter = adapter
        setupSwipeActions()
        binding.partnersRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0 || dy < 0 && binding.partnersAddBtn.isShown) {
                    binding.partnersAddBtn.hide()
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    binding.partnersAddBtn.show()
                }
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
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
                return listOf(editButton, deleteButton)
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
        showDeleteConfirmationDialog(requireContext()) { viewModel.delete(partner) }
    }

}