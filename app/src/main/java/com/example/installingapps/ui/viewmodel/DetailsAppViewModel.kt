package com.example.installingapps.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.installingapps.data.AppInfo
import com.example.installingapps.data.AppsDetailsRepository
import com.example.installingapps.ui.state.AppsDetailsEvent
import com.example.installingapps.ui.state.AppsDetailsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsAppViewModel @Inject constructor(
    private val repository: AppsDetailsRepository
): ViewModel() {

    private val _state = MutableStateFlow<AppsDetailsState>(AppsDetailsState.Loading)
    val state: StateFlow<AppsDetailsState> = _state

    private val _appInfo = MutableStateFlow<AppInfo?>(null)

    init {
        fetchData()
    }

    fun onEvent(e: AppsDetailsEvent) {
        createEvent(e)
    }

    private fun createEvent(e: AppsDetailsEvent) {
        when(e) {
            AppsDetailsEvent.StartApp -> repository.startApp()
        }
    }

    private fun fetchData() {
        viewModelScope.launch {
            repository.getAppsDetails().collect {
                _appInfo.value = it
            }
            _state.update { AppsDetailsState.GetAppInfo(_appInfo.value) }
        }
    }
}