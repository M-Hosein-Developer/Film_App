package com.example.film_app.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.film_app.model.repository.MainRepository
import com.example.film_app.ui.intent.MainIntent
import com.example.film_app.ui.state.NowPlayingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    val dataIntent = Channel<MainIntent>()

    private val _nowPlayingState = MutableStateFlow<NowPlayingState>(NowPlayingState.Idle)
    val nowPlayingState: StateFlow<NowPlayingState> get() = _nowPlayingState


    init {
        handleIntent()
    }

    private fun handleIntent() {

        viewModelScope.launch {

            dataIntent.consumeAsFlow().collect {

                when (it) {

                    is MainIntent.fetchNowPlaying -> getNowPlayingData()


                }

            }

        }

    }

    private fun getNowPlayingData() {

        _nowPlayingState.value = NowPlayingState.Loading
        viewModelScope.launch {
            repository.nowPlaying.catch {
                _nowPlayingState.value = NowPlayingState.NowPlayingError(it.localizedMessage)
            }
                .collect {
                    _nowPlayingState.value = NowPlayingState.NowPlayingData(it)
                }
        }

    }

}