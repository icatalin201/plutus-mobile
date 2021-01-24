package com.finance.plutus.mobile.items.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.finance.plutus.mobile.app.ui.BaseViewModel
import com.finance.plutus.mobile.items.data.ItemRepository
import com.finance.plutus.mobile.items.data.model.Item

/**
 * Plutus Finance
 * Created by Catalin on 1/23/2021
 **/
class ServicesViewModel(
    private val itemRepository: ItemRepository
) : BaseViewModel() {

    private val _items = MutableLiveData<PagingData<Item>>()
    val items: LiveData<PagingData<Item>> = _items

    fun fetchItems() {
        val disposable = itemRepository.findAll()
            .subscribe(
                { items -> _items.value = items },
                { error -> error.printStackTrace() }
            )
        compositeDisposable.add(disposable)
    }

    fun delete(item: Item) {
        val disposable = itemRepository.delete(item.id)
            .subscribe {
                fetchItems()
            }
        compositeDisposable.add(disposable)
    }

}