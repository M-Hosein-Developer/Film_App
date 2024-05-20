package com.example.film_app.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.film_app.model.repository.filmCoverRepo.FilmCoverRepository
import com.example.film_app.ui.intent.FilmCoverIntent
import com.example.film_app.ui.state.filmCover.FilmCoverState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilmCoverViewModel @Inject constructor(private val repository: FilmCoverRepository) : ViewModel(){

    val dataIntent = Channel<FilmCoverIntent>()

    private val _filmCoverState = MutableStateFlow<FilmCoverState>(FilmCoverState.Idle)
    val filmCoverState : StateFlow<FilmCoverState> get() = _filmCoverState


    init {
        handleIntent()
    }

    private fun handleIntent() {

        viewModelScope.launch {

            dataIntent.consumeAsFlow().collect{

                when(it){

                    is FilmCoverIntent.FetchFilmCover -> getFilmCover()

                }

            }

        }

    }

    private fun getFilmCover() {

        viewModelScope.launch {

            repository.getAllDataCoverFilm.catch {
                _filmCoverState.value = FilmCoverState.Error(it.localizedMessage)
            }.collect{
                _filmCoverState.value = FilmCoverState.FetchData(it)
            }

        }

    }

}