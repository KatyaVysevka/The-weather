package com.example.theweather

import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Looper
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.theweather.common.*
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import java.lang.ref.WeakReference

class LocationHelper(base: Context?, view: LocationView?) : ContextWrapper(base) {

    private var locationView: WeakReference<LocationView>? = WeakReference(view)

//    fun dialogExplanationRequestLocationPermission() {
//        AlertDialog.Builder(this)
//            .setMessage(getString(R.string.explanationRequestLocationPermission))
//            .setTitle(getString(R.string.attention))
//            .setOnCancelListener { dialogInterface: DialogInterface? ->
//                locationView?.get()?.requestLocationPermissions()
//            }
//            .setPositiveButton(
//                getString(R.string.ok)
//            ) { dialogInterface: DialogInterface?, i: Int ->
//                locationView?.get()?.requestLocationPermissions()
//            }
//            .create()
//            .show()
//    }

//    fun attachView(view: MainView) {
//        viewState = WeakReference(view)
//    }

    fun getLocationPermission() {
        if (hasLocationPermission()) {
            logDebug("Location permission granted")
            checkIfLocationEnabled()
        } else {
            logDebug("Location permission not granted")
            locationView?.get()?.requestLocationPermissions()
        }
    }

     fun checkIfLocationEnabled() {
        logDebug("checkIfLocationEnabled()")
         if (isLocationEnabled()) {
             getUserCurrentLocation()
         } else {
             locationView?.get()?.performIfLocationDisabled()
         }
    }

    private fun isLocationEnabled(): Boolean {
        val manager = getSystemService(LOCATION_SERVICE) as LocationManager
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER) || manager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    //we don't need to check location permission again,
    //because the method is only called if permission received
    @SuppressLint("MissingPermission")
    fun getUserCurrentLocation() {
        LocationServices.getFusedLocationProviderClient(this)
            .requestLocationUpdates(getLocationRequest(), locationCallback, Looper.getMainLooper())
    }

    fun hasLocationPermission(): Boolean {
        val result = ContextCompat
            .checkSelfPermission(
                applicationContext,
                LOCATION_PERMISSIONS[0]
            )
        return result == PackageManager.PERMISSION_GRANTED
    }

    private val locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            removeLocationUpdates()
            locationView?.get()?.locationResult(locationResult.lastLocation)

        }
    }

    private fun removeLocationUpdates() {
        LocationServices
            .getFusedLocationProviderClient(this)
            .removeLocationUpdates(locationCallback)
    }
}