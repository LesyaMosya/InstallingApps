package com.example.installingapps.navigation

import androidx.lifecycle.SavedStateHandle
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object DetailsAppNavigationArgModule {

    @Provides
    @ViewModelScoped
    fun provideAppInfo(
        savedStateHandle: SavedStateHandle
    ): String {
        val packageName: String = checkNotNull(savedStateHandle["packageName"])
        return packageName
    }
}