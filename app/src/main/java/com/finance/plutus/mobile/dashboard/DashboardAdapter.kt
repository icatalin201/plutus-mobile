package com.finance.plutus.mobile.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.finance.plutus.mobile.R
import com.finance.plutus.mobile.app.util.formatInLocalCurrency
import com.finance.plutus.mobile.databinding.StatViewBinding

/**
 * Plutus Finance
 * Created by Catalin on 1/27/2021
 **/
class DashboardAdapter : RecyclerView.Adapter<DashboardAdapter.DashboardViewHolder>() {

    private val stats = mutableListOf<DashboardStat>()

    inner class DashboardViewHolder(
        private val binding: StatViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun render(stat: DashboardStat) {
            binding.statName.text = stat.name
            binding.statValue.text = stat.value.formatInLocalCurrency()
        }

    }

    fun add(stat: DashboardStat) {
        this.stats.add(stat)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: StatViewBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.stat_view,
            parent, false
        )
        return DashboardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        holder.render(stats[position])
    }

    override fun getItemCount(): Int {
        return stats.size
    }

}