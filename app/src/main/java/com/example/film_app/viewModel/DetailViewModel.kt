package com.example.film_app.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.film_app.model.repository.detailRepo.DetailRepository
import com.example.film_app.ui.intent.DetailIntent
import com.example.film_app.ui.state.detailState.DetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: DetailRepository) : ViewModel() {

    val dataIntent = Channel<DetailIntent>()

    //All Data
    private val _allDataState = MutableStateFlow<DetailState>(DetailState.Idle)
    val allDataState : StateFlow<DetailState> get() = _allDataState

    init {
        handleIntent()
    }

    private fun handleIntent() = viewModelScope.launch {

            dataIntent.consumeAsFlow().collect{

                when(it){

                    DetailIntent.fetchAllData -> getAllDataFromDb()

                }

            }

    }

    private fun getAllDataFromDb() {

        _allDataState.value = DetailState.Loading
        viewModelScope.launch {

            repository.allDate.catch {
                _allDataState.value = DetailState.AllDataError(it.localizedMessage)
            }.collect{
                _allDataState.value = DetailState.AllData(it)
            }

        }

    }

}














