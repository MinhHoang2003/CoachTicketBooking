package com.example.coachticketbooking.screen.map

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.coachticketbooking.R
import com.example.coachticketbooking.model.StopStation
import com.example.coachticketbooking.utils.Utils
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.activity_maps.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    private var location: Location? = null
    private var mCurrentRouteId = -1
    private var currentMarker: Marker? = null
    private lateinit var mMapViewModel: MapViewModel
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    companion object {
        const val REQUEST_PERMISSION_CODE = 101
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        mMapViewModel = ViewModelProvider(this).get(MapViewModel::class.java)
        imgExit.setOnClickListener {
            onBackPressed()
        }
        mCurrentRouteId = intent.getIntExtra("id", -1)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                REQUEST_PERMISSION_CODE
            )
        } else {
            getLastLocation()
        }
    }

    private fun getLastLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationProviderClient.lastLocation.addOnSuccessListener {
            location = it
            val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
            mapFragment.getMapAsync(this)
            if(mCurrentRouteId != -1) {
                mMapViewModel.getLocation(mCurrentRouteId)
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMapLoadedCallback {
            location?.let { currentLocation ->
                val current = LatLng(currentLocation.latitude, currentLocation.longitude)
                val builder = LatLngBounds.Builder()
                builder.include(current)
                mMap.addMarker(
                    MarkerOptions()
                        .position(current)
                ).tag = "you"
                mMapViewModel.location.observe(this, { locations ->
                    locations.forEach {
                        val first = LatLng(it.latitude.toDouble(), it.longitude.toDouble())
                        builder.include(first)
                        mMap.addMarker(
                            MarkerOptions()
                                .position(first)
                                .icon( BitmapDescriptorFactory.fromBitmap(
                                    if(it.stopStationType == 1) {
                                        createPickMarker(Utils.formatTime(it.time))
                                    } else createDestinationMarker(Utils.formatTime(it.time))
                                )).title(it.detailLocation)
                        ).tag = "1"
                    }
                    val bounds = builder.build()
                    val cu = CameraUpdateFactory.newLatLngBounds(bounds, 200)
                    mMap.moveCamera(cu)
                })
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_PERMISSION_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    getLastLocation()
                } else {
                    Log.d("location", "Permission has been denied")
                }
                return
            }
            else -> {
                Log.d("location", "Permission has been denied")
            }
        }
    }

    private fun createPickMarker(time : String): Bitmap {
        val marker = LayoutInflater.from(this).inflate(R.layout.layout_map_marker, null)
        val text: TextView = marker.findViewById(R.id.textMarkerPrice)
        text.text = time
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        marker.layoutParams = ViewGroup.LayoutParams(52, ViewGroup.LayoutParams.WRAP_CONTENT)
        marker.measure(displayMetrics.widthPixels, displayMetrics.heightPixels)
        marker.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)
        marker.buildLayer()
        val bitmap = Bitmap.createBitmap(
            marker.measuredWidth,
            marker.measuredHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        marker.draw(canvas)
        return bitmap
    }

    private fun createDestinationMarker(time : String): Bitmap {
        val marker = LayoutInflater.from(this).inflate(R.layout.layout_marker_destination, null)
        val text: TextView = marker.findViewById(R.id.textMarkerPrice)
        text.text = time
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        marker.layoutParams = ViewGroup.LayoutParams(52, ViewGroup.LayoutParams.WRAP_CONTENT)
        marker.measure(displayMetrics.widthPixels, displayMetrics.heightPixels)
        marker.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)
        marker.buildLayer()
        val bitmap = Bitmap.createBitmap(
            marker.measuredWidth,
            marker.measuredHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        marker.draw(canvas)
        return bitmap
    }
}