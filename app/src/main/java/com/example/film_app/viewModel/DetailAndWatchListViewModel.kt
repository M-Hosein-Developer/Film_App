package com.example.film_app.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.film_app.model.database.entities.WatchListEntity
import com.example.film_app.model.repository.detailRepo.DetailAndWatchListRepository
import com.example.film_app.ui.intent.DetailAndWatchListIntent
import com.example.film_app.ui.state.detailState.DeleteFromWatchListState
import com.example.film_app.ui.state.detailState.DetailState
import com.example.film_app.ui.state.detailState.TrailerState
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

    //Delete Favorite Film
    private val _deleteFilmFromWatchListState = MutableStateFlow<DeleteFromWatchListState>(DeleteFromWatchListState.Idle)
    val deleteFilmFromWatchListState : StateFlow<DeleteFromWatchListState> get() = _deleteFilmFromWatchListState

    //Trailer
    private val _trailerState = MutableStateFlow<TrailerState>(TrailerState.Idle)
    val trailerState : StateFlow<TrailerState> get() = _trailerState


    init {
        handleIntent()
    }

    private fun handleIntent() = viewModelScope.launch {

            dataIntent.consumeAsFlow().collect{

                when(it){

                    is DetailAndWatchListIntent.FetchAllData -> getAllDataFromDb()
                    is DetailAndWatchListIntent.FetchWatchListId -> getAllWatchList(it.id)
                    is DetailAndWatchListIntent.DeleteWatchListId -> deleteFavoriteFilmFromWatchList(it.id)
                    is DetailAndWatchListIntent.FetchTrailerById -> getTrailerList(it.id)

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

    private fun getAllWatchList(watchList: WatchListEntity) {

        Log.v("VMData2" ,watchList.toString())


        _watchListState.value = WatchListState.Loading

        if (watchList.id != -1) {
            viewModelScope.launch {
                repository.insertWatchList(watchList)
            } }

        viewModelScope.launch {
            repository.watchList.catch {
                _watchListState.value = WatchListState.WatchListError(it.localizedMessage)
            }.collect{
                Log.v("VMData" , it.toString())
                _watchListState.value = WatchListState.WatchList(it)
            }
        }

    }

    private fun deleteFavoriteFilmFromWatchList(id: WatchListEntity) {

        _deleteFilmFromWatchListState.value = DeleteFromWatchListState.Loading

        viewModelScope.launch{

            try {
                if (id.id != -1){
                    repository.deleteWatchListById(id.id)
                }
            }catch (e : Exception) {
                _deleteFilmFromWatchListState.value = DeleteFromWatchListState.DeleteWatchListError(e.localizedMessage)
            }

        }

    }

    private fun getTrailerList(id: Int) {

        _trailerState.value = TrailerState.Loading

        viewModelScope.launch {

            repository.trailerById(id).catch {
                _trailerState.value = TrailerState.TrailerError(it.localizedMessage)
            }.collect{
                _trailerState.value = TrailerState.TrailerDataById(it)
            }

        }

    }

}














