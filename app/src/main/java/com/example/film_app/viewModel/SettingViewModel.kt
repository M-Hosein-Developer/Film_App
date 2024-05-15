package com.example.film_app.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.film_app.model.database.entities.DynamicTheme
import com.example.film_app.model.repository.themeRepo.ThemeRepository
import com.example.film_app.ui.intent.SettingIntent
import com.example.film_app.ui.state.settingState.GetDynamicThemeState
import com.example.film_app.ui.state.settingState.SetDynamicThemeState
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
    private val _setDynamicThemeState =
        MutableStateFlow<SetDynamicThemeState>(SetDynamicThemeState.Idle)
    val setDynamicThemeState: StateFlow<SetDynamicThemeState> get() = _setDynamicThemeState

    private val _getDynamicThemeState =
        MutableStateFlow<GetDynamicThemeState>(GetDynamicThemeState.Idle)
    val getDynamicThemeState: StateFlow<GetDynamicThemeState> get() = _getDynamicThemeState

    init {
        handleIntent()
    }

    private fun handleIntent() {

        viewModelScope.launch {

            dataIntent.consumeAsFlow().collect {

                when (it) {

                    is SettingIntent.SendDynamicThemeIntent -> sendDynamicTheme(it.state)
                    is SettingIntent.GetDynamicThemeIntent -> getDynamicTheme()

                }

            }

        }

    }

    private fun getDynamicTheme() = viewModelScope.launch {
        while (true) {
            _getDynamicThemeState.value =
                GetDynamicThemeState.ThemeStateSet(themeRepository.getDynamicThemeState().dynamicThemeState)
            delay(1000)
        }
    }


    private fun sendDynamicTheme(state: DynamicTheme) = viewModelScope.launch {
        themeRepository.insertDynamicThemeStateRep(state)
        _setDynamicThemeState.value = SetDynamicThemeState.ThemeStateSet("ok")
    }


}