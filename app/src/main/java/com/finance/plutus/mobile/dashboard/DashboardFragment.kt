package com.finance.plutus.mobile.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.finance.plutus.mobile.R
import com.finance.plutus.mobile.app.util.showInputDialog
import com.finance.plutus.mobile.databinding.FragmentDashboardBinding
import org.koin.android.ext.android.inject

/**
 * Plutus Finance
 * Created by Catalin on 1/23/2021
 **/
class DashboardFragment : Fragment() {

    private val viewModel: DashboardViewModel by inject()
    private lateinit var binding: FragmentDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_dashboard, container, false
        )
        viewModel.serial.observe(viewLifecycleOwner) { serial ->
            binding.dashboardSerialElement.serialName.text = viewModel.prepareSerialName(serial)
            binding.dashboardSerialElement.serialUpdateBtn.setOnClickListener {
                showInputDialog(requireActivity(), serial.nextNumber.toString()) { value ->
                    viewModel.updateSerial(serial, value)
                }
            }
        }
        binding.dashboardCurrencyElement.currencyDate.text = "25.01.2021"
        binding.dashboardCurrencyElement.currencyRate.text = "1 RON = 4.5 USD"
        return binding.root
    }

}