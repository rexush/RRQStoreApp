package com.example.rrqstore.ui.theme.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rrqstore.data.RrqRepository
import com.example.rrqstore.model.MerchOrder
import com.example.rrqstore.ui.theme.common.StateHolder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: RrqRepository) : ViewModel() {
    private val _stateHome: MutableStateFlow<StateHolder<List<MerchOrder>>> =
        MutableStateFlow(StateHolder.Loading)
    val stateHome: StateFlow<StateHolder<List<MerchOrder>>>
        get() = _stateHome

    fun getAllMerch() {
        viewModelScope.launch {
            repository.getAllMerch()
                .catch {
                    _stateHome.value = StateHolder.Error(it.message.toString())
                }
                .collect { merchOrder ->
                    _stateHome.value = StateHolder.Success(merchOrder)
                }
        }
    }

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query
    fun findMerch(newQuery: String) {
        viewModelScope.launch {
            _query.value = newQuery
            repository.findMerch(_query.value)
                .catch {
                    _stateHome.value = StateHolder.Error(it.message.toString())
                }
                .collect { merch ->
                    _stateHome.value = StateHolder.Success(merch)
                }
        }
    }
}