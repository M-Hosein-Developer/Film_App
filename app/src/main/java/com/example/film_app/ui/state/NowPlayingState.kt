package com.example.film_app.ui.state

import com.example.film_app.model.database.entities.NowPlayingEntity

sealed class NowPlayingState {

    data object Idle : NowPlayingState()
    data object Loading : NowPlayingState()
    data class NowPlayingData(val nowPlying : List<NowPlayingEntity>) : NowPlayingState()
    data class NowPlayingError(val error: String?) : NowPlayingState()

}