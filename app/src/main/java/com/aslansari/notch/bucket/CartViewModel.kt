package com.aslansari.notch.bucket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.aslansari.notch.bucket.persistence.Item
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CartViewModel(private val dispatcher: CoroutineDispatcher, private val cartRepository: CartRepository): ViewModel() {

    val itemListFlow: StateFlow<List<Item>> = cartRepository.itemListFlow.stateIn(viewModelScope, SharingStarted.Eagerly, listOf())

    fun addItem(item: Item) = viewModelScope.launch(dispatcher) {
        cartRepository.addItem(item)
    }

    fun updateItem(item: Item) = viewModelScope.launch(dispatcher) {
        cartRepository.updateItem(item)
    }

    fun deleteItem(id: Int) = viewModelScope.launch(dispatcher) {
        cartRepository.deleteItem(id)
    }
}

class CartViewModelFactory(private val repository: CartRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CartViewModel(Dispatchers.IO, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}