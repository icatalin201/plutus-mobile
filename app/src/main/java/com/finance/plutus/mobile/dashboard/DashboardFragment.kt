package com.finance.plutus.mobile.dashboard

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.finance.plutus.mobile.R
import com.finance.plutus.mobile.app.data.model.Currency
import com.finance.plutus.mobile.app.data.model.CurrencyRate
import com.finance.plutus.mobile.app.data.model.Serial
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
    private val adapter = DashboardAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_dashboard, container, false
        )
        setHasOptionsMenu(true)
        viewModel.serial.observe(viewLifecycleOwner) { serial ->
            showSerial(serial)
        }
        viewModel.rates.observe(viewLifecycleOwner) { rates ->
            showRates(rates)
        }
        binding.dashboardStatRecycler.layoutManager =
            GridLayoutManager(requireContext(), 2)
        binding.dashboardStatRecycler.adapter = adapter
        viewModel.stats.observe(viewLifecycleOwner) { stat ->
            adapter.add(stat)
        }
        viewModel.findTotalExpense()
        viewModel.findTotalExpenseForLastYear()
        viewModel.findTotalIncome()
        viewModel.findTotalIncomeForLastYear()
        viewModel.findDeductibleExpensesForLastYear()
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.download_invoices) {
            viewModel.downloadInvoicesArchive(requireContext())
        } else if (item.itemId == R.id.download_transactions) {
            viewModel.downloadTransactionsReport(requireContext())
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showSerial(serial: Serial) {
        binding.dashboardSerialElement.serialName.text = viewModel.prepareSerialName(serial)
        binding.dashboardSerialElement.serialUpdateBtn.setOnClickListener {
            showInputDialog(requireActivity(), serial.nextNumber.toString()) { value ->
                viewModel.updateSerial(serial, value)
            }
        }
    }

    private fun showRates(rates: List<CurrencyRate>) {
        val rateUsd = rates.findLast { rate -> rate.currency == Currency.USD }
        val rateEur = rates.findLast { rate -> rate.currency == Currency.EUR }
        binding.dashboardCurrencyElement.currencyDate.text = rateUsd?.date
        rateEur?.let {
            val value = "1 EUR = ${it.rate} RON"
            binding.dashboardCurrencyElement.currencyRateEur.text = value
        }
        rateUsd?.let {
            val value = "1 USD = ${it.rate} RON"
            binding.dashboardCurrencyElement.currencyRateUsd.text = value
        }
    }

}