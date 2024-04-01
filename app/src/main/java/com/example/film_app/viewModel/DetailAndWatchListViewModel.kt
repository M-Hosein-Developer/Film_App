package com.example.film_app.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.film_app.model.database.entities.WatchListEntity
import com.example.film_app.model.repository.detailRepo.DetailAndWatchListRepository
import com.example.film_app.ui.intent.DetailAndWatchListIntent
import com.example.film_app.ui.state.detailState.DeleteFromWatchListState
import com.example.film_app.ui.state.detailState.DetailState
import com.example.film_app.ui.state.detailState.WatchListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailAndWatchListViewModel @Inject constructor(private val repository: DetailAndWatchListRepository) : ViewModel() {

    val dataIntent = Channel<DetailAndWatchListIntent>()

    //All Data
    private val _allDataState = MutableStateFlow<DetailState>(DetailState.Idle)
    val allDataState : StateFlow<DetailState> get() = _allDataState

    //Watch List
    private val _watchListState = MutableStateFlow<WatchListState>(WatchListState.Idle)
    val watchListState : StateFlow<WatchListState> get() = _watchListState

    private val _deleteFilmFromWatchList = MutableStateFlow<DeleteFromWatchListState>(DeleteFromWatchListState.Idle)
    val deleteFilmFromWatchList : StateFlow<DeleteFromWatchListState> get() = _deleteFilmFromWatchList


    init {
        handleIntent()
    }

    private fun handleIntent() = viewModelScope.launch {

            dataIntent.consumeAsFlow().collect{

                when(it){

                    is DetailAndWatchListIntent.FetchAllData -> getAllDataFromDb()
                    is DetailAndWatchListIntent.FetchWatchListId -> getAllWatchList(it.id)
                    is DetailAndWatchListIntent.DeleteWatchListId -> deleteFavoriteFilmFromWatchList(it.id)

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

    private fun getAllWatchList(id: WatchListEntity) {

        _watchListState.value = WatchListState.Loading

        if (id.moviesId != -1) {
            viewModelScope.launch {
                repository.insertWatchList(id)
            }
        }

        viewModelScope.launch {
            repository.watchList.catch {
                _watchListState.value = WatchListState.WatchListError(it.localizedMessage)
            }.collect{
                _watchListState.value = WatchListState.WatchList(it)
            }
        }

    }

    private fun deleteFavoriteFilmFromWatchList(id: WatchListEntity) {

        _deleteFilmFromWatchList.value = DeleteFromWatchListState.Loading

        viewModelScope.launch{

            try {
                if (id.moviesId != -1){
                    repository.deleteWatchListById(id)
                }
            }catch (e : Exception) {
                _deleteFilmFromWatchList.value = DeleteFromWatchListState.DeleteWatchListError(e.localizedMessage)
            }

        }

    }

}














