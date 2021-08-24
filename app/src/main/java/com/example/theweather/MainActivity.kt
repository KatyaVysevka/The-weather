package com.example.theweather

import android.content.Intent
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.theweather.common.LOCATION_PERMISSIONS
import com.example.theweather.common.LOCATION_PERMISSION_REQUEST_CODE
import com.example.theweather.common.logDebug
import com.example.theweather.databinding.ActivityMainBinding
import com.example.theweather.fragments.FirstFragment
import com.example.theweather.fragments.SecondFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.MutableStateFlow

class MainActivity : AppCompatActivity(), LocationView {

    private lateinit var locationHelper: LocationHelper
    lateinit var binding: ActivityMainBinding
   // lateinit var viewModel: WeatherViewModel

    private val locationResult: MutableStateFlow<Location?> = MutableStateFlow(null)


    private var resultEnableLocation = registerForActivityResult(
        ActivityResultContracts
            .StartActivityForResult()
    ) { result ->
        logDebug("resultEnableLocation")
        locationHelper.checkIfLocationEnabled()
    }

    var resultEnablePermissionLocation = registerForActivityResult(
        ActivityResultContracts
            .StartActivityForResult()
    ) { result ->
        logDebug("resultEnablePermissionLocation")
        locationHelper.getLocationPermission()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        locationHelper = LocationHelper(baseContext, this)
        locationHelper.getLocationPermission()
       // viewModel = WeatherViewModel()


        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, FirstFragment.newInstance(locationResult)).commit()

        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.today -> supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, FirstFragment.newInstance(locationResult)).commit()
                R.id.forecast -> supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, SecondFragment.newInstance(locationResult)).commit()
            }
            true
        }
    }

    override fun requestLocationPermissions() {
        ActivityCompat.requestPermissions(
            this,
            LOCATION_PERMISSIONS,
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    override fun locationResult(location: Location) {
        logDebug("locationResult: ${location.latitude}\t ${location.longitude}")
        locationResult.value = location

    }

    //handle the result from requested permissions.
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            when (true) {
                locationHelper.hasLocationPermission() -> {
                    logDebug("Location Permissions Result: Success!")
                    locationHelper.checkIfLocationEnabled()
                }
                ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0]) -> {
                    locationHelper.dialogExplanationRequestLocationPermission()
                }
                else -> {
                    logDebug("Location Permissions Result: Failed!")
                    performIfNoLocationPermission()
                }
            }
        } else {
            super.onRequestPermissionsResult(
                requestCode, permissions,
                grantResults
            )
        }
    }

    override fun performIfNoLocationPermission() {
        val snackbar: Snackbar = Snackbar.make(
            findViewById(R.id.root),
            R.string.noPermissions,
            Snackbar.LENGTH_INDEFINITE
        )
        snackbar.setAction(R.string.turnOn) { view: View? ->
            val intent = Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:$packageName")
            )
            resultEnablePermissionLocation.launch(intent)
        }.setActionTextColor(ContextCompat.getColor(this, R.color.white))
        snackbar.show()
    }

    override fun performIfLocationDisabled() {
        val snackbar: Snackbar = Snackbar.make(
            findViewById(R.id.root),
            R.string.noGPS,
            Snackbar.LENGTH_INDEFINITE
        )
        snackbar.setAction(R.string.turnOn) { view: View? ->
            resultEnableLocation.launch(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }.setActionTextColor(ContextCompat.getColor(this, R.color.white))
        snackbar.show()
    }
}