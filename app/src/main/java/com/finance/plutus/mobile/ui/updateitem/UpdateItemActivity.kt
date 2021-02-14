package com.finance.plutus.mobile.ui.updateitem

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.finance.plutus.mobile.R
import com.finance.plutus.mobile.data.model.Item
import com.finance.plutus.mobile.data.model.ItemType
import com.finance.plutus.mobile.data.model.Result
import com.finance.plutus.mobile.databinding.ActivityUpdateItemBinding
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
        if (validate()) {
            viewModel.save()
        }
    }

    private fun validate(): Boolean {
        if (binding.itemName.text.toString().isBlank()) {
            binding.itemName.error = getString(R.string.invalid_field)
            return false
        }
        return true
    }

    private fun handleResult(result: Result) {
        if (result.ok != null) {
            onBackPressed()
        } else {
            result.error?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        }
    }
}