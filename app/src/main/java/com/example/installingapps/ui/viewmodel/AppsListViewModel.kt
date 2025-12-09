package com.example.installingapps.ui.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.installingapps.data.AppInfo
import com.example.installingapps.data.AppsListRepository
import com.example.installingapps.ui.state.AppsListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.P)
@HiltViewModel
class AppsListViewModel @Inject constructor(
    private val repository: AppsListRepository
) : ViewModel() {

    private val _state = MutableStateFlow<AppsListState>(AppsListState.Loading)
    val state: StateFlow<AppsListState> = _state
    private val _appsList = MutableStateFlow<List<AppInfo>>(emptyList())


    init {
        getInstalledApps()
    }

    fun getInstalledApps() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getInstalledApps().collect { _appsList.value = it }
            _state.update { AppsListState.GetListAppInfo(_appsList.value) }
        }
    }
}