package com.example.film_app.viewModel


import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.film_app.model.repository.homeRepo.HomeRepository
import com.example.film_app.ui.intent.HomeIntent
import com.example.film_app.ui.state.homeState.NowPlayingState
import com.example.film_app.ui.state.homeState.PopularState
import com.example.film_app.ui.state.homeState.TopRateState
import com.example.film_app.ui.state.homeState.TrendState
import com.example.film_app.ui.state.homeState.UpcomingState
import com.example.film_app.util.NetworkChecker
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository, @ApplicationContext val context : Context) : ViewModel() {

    val dataIntent = Channel<HomeIntent>()

    //Now Playing
    private val _nowPlayingState = MutableStateFlow<NowPlayingState>(NowPlayingState.Idle)
    val nowPlayingState: StateFlow<NowPlayingState> get() = _nowPlayingState

    //Popular
    private val _popularState = MutableStateFlow<PopularState>(PopularState.Idle)
    val popularState: StateFlow<PopularState> get() = _popularState

    //Top Rate
    private val _topRateState = MutableStateFlow<TopRateState>(TopRateState.Idle)
    val topRateState : StateFlow<TopRateState> get() = _topRateState

    //Upcoming
    private val _upcomingState = MutableStateFlow<UpcomingState>(UpcomingState.Idle)
    val upcomingState : StateFlow<UpcomingState> get() = _upcomingState

    //Trend
    private val _trendState = MutableStateFlow<TrendState>(TrendState.Idle)
    val trendState : StateFlow<TrendState> get() = _trendState

    init {
        handleIntent()
    }

    private fun handleIntent() {

        viewModelScope.launch {

            dataIntent.consumeAsFlow().collect {

                when (it) {

                    is HomeIntent.FetchNowPlaying -> getNowPlayingData()
                    is HomeIntent.FetchPopular -> getPopularData()
                    is HomeIntent.FetchTopRate -> getTopRateData()
                    is HomeIntent.FetchUpcoming -> getUpcomingData()
                    is HomeIntent.FetchTrend -> getTrendData()

                }

            }

        }

    }



    //Now Playing
    private fun getNowPlayingData() {

        _nowPlayingState.value = NowPlayingState.Loading

        if (NetworkChecker(context).internetConnection) {

            viewModelScope.launch {
                repository.nowPlaying.catch {
                    _nowPlayingState.value = NowPlayingState.NowPlayingError(it.localizedMessage)
                }.collect {
                    _nowPlayingState.value = NowPlayingState.NowPlayingData(it)
                }
            }

        }else{

            viewModelScope.launch {
                repository.nowPlayingByDb.catch {
                    _nowPlayingState.value = NowPlayingState.NowPlayingError(it.localizedMessage)
                }.collect {
                    _nowPlayingState.value = NowPlayingState.NowPlayingData(it)
                }
            }

        }


    }

    //Popular
    private fun getPopularData() {

        _popularState.value = PopularState.Loading

        if (NetworkChecker(context).internetConnection) {

            viewModelScope.launch {
                repository.popular.catch {
                    _popularState.value = PopularState.PopularError(it.localizedMessage)
                }.collect {
                    _popularState.value = PopularState.PopularData(it)
                }
            }

        }else{

            viewModelScope.launch {
                repository.popularByDb.catch {
                    _popularState.value = PopularState.PopularError(it.localizedMessage)
                }.collect {
                    _popularState.value = PopularState.PopularData(it)
                }
            }

        }
    }

    //Top Rate
    private fun getTopRateData() {

        _topRateState.value = TopRateState.Idle

        if (NetworkChecker(context).internetConnection) {

            viewModelScope.launch {
                repository.topRate.catch {
                    _topRateState.value = TopRateState.TopRateError(it.localizedMessage)
                }.collect{
                    _topRateState.value = TopRateState.TopRateData(it)
                }
            }

        }else{

            viewModelScope.launch {
                repository.topRateByDb.catch {
                    _topRateState.value = TopRateState.TopRateError(it.localizedMessage)
                }.collect{
                    _topRateState.value = TopRateState.TopRateData(it)
                }
            }

        }

    }

    //Upcoming
    private fun getUpcomingData() {

        _upcomingState.value = UpcomingState.Loading

        if (NetworkChecker(context).internetConnection) {

            viewModelScope.launch {
                repository.upcoming.catch {
                    _upcomingState.value = UpcomingState.UpcomingError(it.localizedMessage)
                }.collect{
                    _upcomingState.value = UpcomingState.UpcomingData(it)
                }
            }

        }else{

            viewModelScope.launch {
                repository.upcomingByDb.catch {
                    _upcomingState.value = UpcomingState.UpcomingError(it.localizedMessage)
                }.collect{
                    _upcomingState.value = UpcomingState.UpcomingData(it)
                }
            }

        }

    }

    //Trend
    private fun getTrendData() {

        _trendState.value = TrendState.Loading

        if (NetworkChecker(context).internetConnection) {

            viewModelScope.launch {
                repository.trend.catch {
                    _trendState.value = TrendState.TrendError(it.localizedMessage)
                }.collect{
                    _trendState.value = TrendState.TrendData(it)
                }
            }

        }else{

            viewModelScope.launch {
                repository.trendByDb.catch {
                    _trendState.value = TrendState.TrendError(it.localizedMessage)
                }.collect{
                    _trendState.value = TrendState.TrendData(it)
                }
            }

        }

    }

}