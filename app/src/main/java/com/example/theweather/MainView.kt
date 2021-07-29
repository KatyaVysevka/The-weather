package com.example.theweather

import android.location.Location

interface MainView {
   fun requestLocationPermissions()
   fun locationResult(location: Location)
   fun performIfNoLocationPermission() //show snackbar to open app settings
   fun performIfLocationDisabled() //show snackbar to open app sett
}