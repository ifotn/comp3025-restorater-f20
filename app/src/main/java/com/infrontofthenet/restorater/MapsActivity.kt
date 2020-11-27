package com.infrontofthenet.restorater

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        searchButton.setOnClickListener {
            // get location from search box
            var location = getLocationFromAddress(this, addressEditText.text.toString())

            // create a new marker on the google map at this location
            if (location != null) {
                placeMarkerOnMap(location)
            }
        }
    }

    // create function to accept a location and create a marker on the map
    private fun placeMarkerOnMap(location: LatLng) {
        // set up a marker in the current position for this lat/lng
        var markerOptions = MarkerOptions().position(location)

        // add marker to the map
        mMap?.addMarker(markerOptions)

        // zoom in to the new marker
        mMap?.moveCamera(CameraUpdateFactory.newLatLng(location))
        val cameraPosition = CameraPosition.Builder()
                .target(location)
                .zoom(17f).build()
        mMap?.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    // address string to lat/long location
    // https://stackoverflow.com/questions/24352192/android-google-maps-add-marker-by-address/34562369#34562369
    private fun getLocationFromAddress(context: Context?, strAddress: String?): LatLng? {
        val coder = Geocoder(context)
        val address: List<Address>?
        var p1: LatLng? = null
        try {
            address = coder.getFromLocationName(strAddress, 5)
            if (address == null) {
                return null
            }
            val location: Address = address[0]
            location.latitude
            location.longitude
            p1 = LatLng(location.latitude, location.longitude)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return p1
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val startLocation = LatLng(44.3591179, -79.7357619)
        mMap.addMarker(MarkerOptions().position(startLocation).title("Marker in Barrie"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(startLocation))

        // enable zoom
        mMap.uiSettings.isZoomControlsEnabled = true
    }
}