package com.finance.plutus.mobile.items.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.finance.plutus.mobile.R
import com.finance.plutus.mobile.databinding.ActivityUpdateItemBinding
import com.finance.plutus.mobile.items.data.model.Item
import com.finance.plutus.mobile.items.data.model.ItemResult
import com.finance.plutus.mobile.items.data.model.ItemType
import org.koin.android.ext.android.inject

class UpdateItemActivity : AppCompatActivity() {

    companion object {
        const val ITEM = "Item"
    }

    private val viewModel: UpdateItemViewModel by inject()
    private lateinit var binding: ActivityUpdateItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_update_item
        )
        setSupportActionBar(binding.itemToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_close_24)
        viewModel.result.observe(this) { handleResult(it) }
        val item: Item? = intent.getParcelableExtra(ITEM)
        title = when (item == null) {
            true -> getString(R.string.create_item_title)
            else -> getString(R.string.update_item_title)
        }
        setItem(item)
        binding.itemSaveBtn.setOnClickListener {
            save()
        }
        binding.request = viewModel.updateRequest
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun setItem(item: Item?) {
        viewModel.item = item
        item?.let {
            if (it.type == ItemType.PRODUCT) {
                binding.itemTypeGroup.check(R.id.item_product)
            } else {
                binding.itemTypeGroup.check(R.id.item_service)
            }
        }
    }

    private fun save() {
        viewModel.updateRequest.type = when (binding.itemTypeGroup.checkedButtonId) {
            R.id.item_product -> ItemType.PRODUCT
            else -> ItemType.SERVICE
        }
        if (binding.itemName.text.toString().isBlank()) {
            binding.itemName.error = getString(R.string.invalid_field)
        }
        viewModel.save()
    }

    private fun handleResult(result: ItemResult) {
        if (result.ok != null) {
            onBackPressed()
        } else {
            result.error?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        }
    }
}