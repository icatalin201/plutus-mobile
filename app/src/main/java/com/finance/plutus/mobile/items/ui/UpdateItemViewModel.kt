package com.finance.plutus.mobile.items.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.finance.plutus.mobile.R
import com.finance.plutus.mobile.app.network.payload.ItemUpdateRequest
import com.finance.plutus.mobile.app.ui.BaseViewModel
import com.finance.plutus.mobile.items.data.ItemRepository
import com.finance.plutus.mobile.items.data.model.Item
import com.finance.plutus.mobile.items.data.model.ItemResult
import java.util.*

/**
Plutus Finance
Created by Catalin on 1/24/2021
 **/
class UpdateItemViewModel(
    private val itemRepository: ItemRepository
) : BaseViewModel() {

    private val _result = MutableLiveData<ItemResult>()
    val result: LiveData<ItemResult> = _result

    val updateRequest = ItemUpdateRequest()
    var item: Item? = null
        set(value) {
            field = value
            updateRequest.sync(value)
        }

    fun save() {
        if (item != null) {
            update(item!!.id)
        } else {
            create()
        }
    }

    private fun create() {
        val disposable = itemRepository
            .create(updateRequest)
            .subscribe(
                { _result.value = ItemResult(ok = true) },
                { error ->
                    error.printStackTrace()
                    _result.value = ItemResult(error = R.string.save_failed)
                }
            )
        compositeDisposable.add(disposable)
    }

    private fun update(id: UUID) {
        val disposable = itemRepository
            .update(id, updateRequest)
            .subscribe(
                { _result.value = ItemResult(ok = true) },
                { error ->
                    error.printStackTrace()
                    _result.value = ItemResult(error = R.string.save_failed)
                }
            )
        compositeDisposable.add(disposable)
    }

}