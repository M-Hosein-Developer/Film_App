package com.example.film_app.viewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.film_app.model.repository.MainRepository
import com.example.film_app.ui.intent.MainIntent
import com.example.film_app.ui.state.NowPlayingState
import com.example.film_app.ui.state.PopularState
import com.example.film_app.ui.state.TopRateState
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

    //Now Playing
    private val _nowPlayingState = MutableStateFlow<NowPlayingState>(NowPlayingState.Idle)
    val nowPlayingState: StateFlow<NowPlayingState> get() = _nowPlayingState

    //Popular
    private val _popularState = MutableStateFlow<PopularState>(PopularState.Idle)
    val popularState: StateFlow<PopularState> get() = _popularState

    //Top Rate
    private val _topRateState = MutableStateFlow<TopRateState>(TopRateState.Idle)
    val topRateState : StateFlow<TopRateState> get() = _topRateState


    init {
        handleIntent()
    }

    private fun handleIntent() {

        viewModelScope.launch {

            dataIntent.consumeAsFlow().collect {

                when (it) {

                    is MainIntent.fetchNowPlaying -> getNowPlayingData()
                    is MainIntent.fetchPopular -> getPopularData()
                    is MainIntent.fetchTopRate -> getTopRateData()

                }

            }

        }

    }

    //Now Playing
    private fun getNowPlayingData() {

        _nowPlayingState.value = NowPlayingState.Loading
        viewModelScope.launch {
            repository.nowPlaying.catch {
                _nowPlayingState.value = NowPlayingState.NowPlayingError(it.localizedMessage)
            }.collect {
                _nowPlayingState.value = NowPlayingState.NowPlayingData(it)
            }
        }

    }

    //Popular
    private fun getPopularData() {

        _popularState.value = PopularState.Loading
        viewModelScope.launch {
            repository.popular.catch {
                _popularState.value = PopularState.PopularError(it.localizedMessage)
            }.collect {
                _popularState.value = PopularState.PopularData(it)
            }
        }

    }

    //Top Rate
    private fun getTopRateData() {

        _topRateState.value = TopRateState.Idle
        viewModelScope.launch {
            repository.topRate.catch {
                _topRateState.value = TopRateState.TopRateError(it.localizedMessage)
            }.collect{
                _topRateState.value = TopRateState.TopRateData(it)
            }
        }

    }


}