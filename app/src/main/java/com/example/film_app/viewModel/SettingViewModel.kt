package com.example.film_app.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.film_app.model.database.entities.DynamicTheme
import com.example.film_app.model.repository.themeRepo.ThemeRepository
import com.example.film_app.ui.intent.SettingIntent
import com.example.film_app.ui.state.settingState.DynamicThemeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(private val themeRepository: ThemeRepository) :
    ViewModel() {

    val dataIntent = Channel<SettingIntent>()

    //Dynamic Theme
    private val _dynamicThemeState = MutableStateFlow<DynamicThemeState>(DynamicThemeState.Idle)
    val dynamicThemeState: StateFlow<DynamicThemeState> get() = _dynamicThemeState

    init {
        handleIntent()
    }

    private fun handleIntent() {

        viewModelScope.launch {

            dataIntent.consumeAsFlow().collect {

                when (it) {

                    is SettingIntent.DynamicThemeIntent -> dynamicTheme(it.state)

                }

            }

        }

    }

    private fun dynamicTheme(state: DynamicTheme) = viewModelScope.launch {

        themeRepository.insertDynamicThemeStateRep(state)

        while (true) {

            _dynamicThemeState.value =
                DynamicThemeState.ThemeState(themeRepository.getDynamicThemeState().dynamicThemeState)

            delay(1000)
        }

    }


}