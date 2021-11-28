package com.aslansari.notch

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {

    private val _inputFieldShouldShown = MutableStateFlow(false)
    val inputFieldShouldShown: StateFlow<Boolean> = _inputFieldShouldShown

    fun pushFloatingActionButton() {
        _inputFieldShouldShown.value = true
    }

    fun itemAdded() {
        _inputFieldShouldShown.value = false
    }
}