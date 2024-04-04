package com.example.film_app.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.film_app.model.repository.searchRepo.SearchRepository
import com.example.film_app.ui.intent.SearchIntent
import com.example.film_app.ui.state.searchState.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: SearchRepository) : ViewModel() {

    val dataIntent = Channel<SearchIntent>()

    private val _getDataBySearch = MutableStateFlow<SearchState>(SearchState.Idle)
    val getDataBySearch : StateFlow<SearchState> get() = _getDataBySearch


    init {
        handleIntent()
    }

    private fun handleIntent() {

        viewModelScope.launch {

            dataIntent.consumeAsFlow().collect{

                when(it){

                    is SearchIntent.FetchDataBySearch -> getDataBySearch(it.search)

                }

            }

        }

    }

    private fun getDataBySearch(search: String?) {

        _getDataBySearch.value = SearchState.Loading

        viewModelScope.launch {

            repository.getAllDataBySearch(search).catch {
                _getDataBySearch.value = SearchState.Error(it.localizedMessage)
            }.collect{
                _getDataBySearch.value = SearchState.GetDataBySearch(it)
            }

        }

    }

}