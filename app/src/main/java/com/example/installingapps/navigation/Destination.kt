package com.example.installingapps.navigation

sealed class Destination(val route: String) {

    object Home : Destination("home")
    object Details : Destination("details/{packageName}")
}