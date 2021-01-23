package com.finance.plutus.mobile.view.partners

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.finance.plutus.mobile.R
import com.finance.plutus.mobile.databinding.FragmentPartnersBinding
import com.finance.plutus.mobile.model.Partner
import com.finance.plutus.mobile.util.Buttons
import com.finance.plutus.mobile.util.SwipeHelper
import com.finance.plutus.mobile.view.partner.UpdatePartnerActivity
import org.koin.android.ext.android.inject

/**
 * Plutus Finance
 * Created by Catalin on 1/23/2021
 **/
class PartnersFragment : Fragment() {

    private val viewModel: PartnersViewModel by inject()
    private lateinit var binding: FragmentPartnersBinding
    private val adapter = PartnerAdapter(object : PartnerClickListener {
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
            binding.partnersSwipeLayout.isRefreshing = true
            viewModel.fetchPartners()
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
            binding.partnersSwipeLayout.isRefreshing = true
            viewModel.fetchPartners()
        }
    }

    private fun openAddPartnerActivity() {
        val intent = Intent(requireContext(), UpdatePartnerActivity::class.java)
        startActivity(intent)
    }

    private fun setPartners(partners: List<Partner>) {
        binding.partnersSwipeLayout.isRefreshing = false
        adapter.submit(partners)
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
        val dialog = AlertDialog.Builder(requireContext(), R.style.Theme_Plutus_Dialog)
            .setTitle(R.string.delete)
            .setMessage(R.string.delete_confirmation)
            .setNegativeButton(R.string.cancel) { dialog, _ -> dialog.dismiss() }
            .setPositiveButton(R.string.confirm) { dialog, _ ->
                dialog.dismiss()
                viewModel.delete(partner)
            }
        dialog.show()
    }

}