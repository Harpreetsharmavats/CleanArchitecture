package com.example.cleanarchitecture.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanarchitecture.domain.model.DomainUserResponse
import com.example.cleanarchitecture.domain.useCases.GetUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ViewModel : ViewModel() {

    val useCase: GetUserUseCase by lazy { GetUserUseCase() }

    private val _state = MutableStateFlow(UiState())
    val uiState = _state.asStateFlow()

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    init {
        viewModelScope.launch {
            _query.map { it.isNotEmpty() }
                .distinctUntilChanged()
                .debounce(1000)
                .collectLatest { query->

                }

        }
    }
    fun getUsers(q : Int){

    }

}

data class UiState(
    val isLoading: Boolean = false,
    val result: List<DomainUserResponse>? = emptyList(),
    val error: String = ""
)