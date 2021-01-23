package com.finance.plutus.mobile.view.services

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.finance.plutus.mobile.model.Item
import com.finance.plutus.mobile.repository.ItemRepository
import com.finance.plutus.mobile.view.BaseViewModel

/**
 * Plutus Finance
 * Created by Catalin on 1/23/2021
 **/
class ServicesViewModel(
    private val itemRepository: ItemRepository
) : BaseViewModel() {

    private val _items = MutableLiveData<List<Item>>()
    val items: LiveData<List<Item>> = _items

    fun fetchItems() {
        val disposable = itemRepository.findAllByPage(0, 100)
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