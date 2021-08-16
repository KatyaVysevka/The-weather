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
import com.example.theweather.repository.WeatherRepository
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.MutableStateFlow

class MainActivity : AppCompatActivity(), LocationView {

    private lateinit var locationHelper: LocationHelper
    lateinit var binding: ActivityMainBinding

    private val repository = WeatherRepository()

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
//        retrofit
//            .getWeatherRetrofit(location.latitude.toString(),
//            location.longitude.toString(),
//            API_WEATHER_KEY)
//            .enqueue(object : Callback<WeatherResponse> {
//            override fun onResponse(
//                call: Call<WeatherResponse>,
//                response: Response<WeatherResponse>
//            ) {
//                logDebug("response.code(): ${response.code()}\n")
//                logDebug("${response.body()}")
//            }
//
//            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
//                logDebug("onFailure: ${t.message}")
//            }
//        })

//        val disposable = repository
//            .getWeather(
//                location.latitude.toString(),
//                location.longitude.toString(),
//                API_WEATHER_KEY
//            )
//            .subscribe(
//                {
//                    logDebug("WeatherResponse:\n $it")
//                },
//                { e ->
//                    logDebug("error:\n ${e.localizedMessage}")
//                }
//            )
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

    public fun imageChoice (description: String): Int = when (description){
        "Clear" ->  R.drawable.ic_sun  //ResourcesCompat.getDrawable(context?.resources!!, R.drawable.ic_sun, null)
        "Clouds" ->  R.drawable.ic_cloudy
        "Clouds" ->  R.drawable.ic_raining
        else -> R.drawable.ic_snow

    }
}