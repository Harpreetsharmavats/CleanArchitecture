package com.example.cleanarchitecture.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanarchitecture.domain.model.DomainUserResponse
import com.example.cleanarchitecture.domain.useCases.GetUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ViewModel : ViewModel() {

    val useCase: GetUserUseCase by lazy { GetUserUseCase() }

    private val _state = MutableStateFlow(UiState())
    val uiState = _state.asStateFlow()

    private val query = MutableStateFlow(0)

    fun updateQuery(q : Int){
        query.update { q }
    }

    init {
        viewModelScope.launch {
            query.map { it }
                .distinctUntilChanged()
                .debounce(1000)
                .collectLatest { query->
                 getUsers(query)
                }

        }
    }
    fun getUsers(q : Int){
        useCase(q)
            .onStart { _state.update { UiState(isLoading = true) } }
            .onEach { result ->
                if (result.isSuccess){
                    _state.update { UiState(result = result.getOrThrow()) }
                }else{
                    _state.update { UiState(error = result.exceptionOrNull()?.message.toString()) }
                }
            }.catch{
                _state.update { UiState(error = it.toString()) }
            }.launchIn(viewModelScope)

    }

}

data class UiState(
    val isLoading: Boolean = false,
    val result: List<DomainUserResponse> = emptyList(),
    val error: String = ""
)