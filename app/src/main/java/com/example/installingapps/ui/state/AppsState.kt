package com.example.installingapps.ui.state

import com.example.installingapps.data.AppInfo

sealed class AppsListState() {
    object Loading: AppsListState()
    data class GetListAppInfo(val data: List<AppInfo>): AppsListState()
}

sealed class AppsDetailsState() {
    object Loading: AppsDetailsState()
    data class GetAppInfo(val data: AppInfo?): AppsDetailsState()
}

sealed class AppsDetailsEvent() {
    object StartApp: AppsDetailsEvent()
}