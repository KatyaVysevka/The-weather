package com.example.theweather

import android.location.Location

interface LocationView {
   fun requestLocationPermissions()
   fun dialogExplanationRequestLocationPermission()
   fun locationResult(location: Location)
   fun performIfNoLocationPermission() //show snackbar to open app settings
   fun performIfLocationDisabled() //show snackbar to open app sett
}