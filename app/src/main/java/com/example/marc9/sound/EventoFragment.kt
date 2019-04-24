package com.example.marc9.sound

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.location.Location
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GooglePlayServicesUtil
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.*
import com.google.android.gms.location.places.AutocompleteFilter
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment
import com.google.android.gms.location.places.ui.PlaceSelectionListener
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.content_maps.*

class EventoFragment():SupportMapFragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener, PlaceSelectionListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener{

    private lateinit var places: PlaceAutocompleteFragment
    private lateinit var mMap: GoogleMap
    lateinit var  mapFragment: SupportMapFragment
    private lateinit var fm:FragmentManager

    //Location
    private lateinit var  googleApiClient: GoogleApiClient
    private lateinit var locationRequest: LocationRequest
    var mLatitude:Double=0.0
    var mLongitude:Double=0.0
    private lateinit var mLastLocation:Location
    private val MY_PERMISSION_REQUEST_CODE=7192
    private val PLAY_SERVICES_RESOLUTION_REQUEST=301093


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view=inflater.inflate(R.layout.fragment_evento,container,false)



        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getMapAsync(this)
        floating.setOnClickListener {
            buildGoogleApiClient()
        }



    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

/*        places = fragmentManager!!.findFragmentById(R.id.place_autocomplete_fragment) as PlaceAutocompleteFragment
        places.setOnPlaceSelectedListener(this)
        val typeFilter: AutocompleteFilter = AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS)
                .setCountry("CL")
                .build()
        places.setHint("Encuentra re")
        places.setFilter(typeFilter)*/
        setUpLocation()
        checkPlayServices()
    }

    fun setFragment(fragment: Fragment?) {
        if (fragment != null) {
            val ft = childFragmentManager.beginTransaction()
            ft.replace(R.id.content_main, fragment)
            ft.commit()
        }
    }

    override fun onLocationChanged(location: Location) {
        mLastLocation=location

        val lagLog= LatLng(location.latitude,location.longitude)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(lagLog))
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14F))

    }

    override fun onConnected(p0: Bundle?) {
        //requestLocationUpdates()
        locationRequest= LocationRequest()
        locationRequest.setInterval(9999999999)
        locationRequest.setFastestInterval(9999999999)
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)

        if(ActivityCompat.checkSelfPermission(context!!,android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context!!,android.Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {
            return
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient,locationRequest,this)
    }

    override fun onConnectionSuspended(p0: Int) {
    }

    override fun onConnectionFailed(p0: ConnectionResult) {

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setPadding(0,150,20,0)

        if(ActivityCompat.checkSelfPermission(context!!,android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context!!,android.Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {
            return
        }
        buildGoogleApiClient()
        mMap.isMyLocationEnabled=true
    }

    private fun buildGoogleApiClient() {
        synchronized(this){
            googleApiClient=GoogleApiClient.Builder(context!!)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build()
            googleApiClient.connect()
        }
    }

    override fun onMarkerClick(p0: Marker?)=false

    private fun placeMarker(location: LatLng){
        val marker= MarkerOptions().position(location)
        mMap.addMarker(marker)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location,16F))
    }

    override fun onPlaceSelected(place: Place) {
        Toast.makeText(context, "" + place.getName() + place.getLatLng(), Toast.LENGTH_LONG).show();
        placeMarker(place.latLng)

    }

    override fun onError(status: Status) {
        Toast.makeText(context, "" + status.toString(), Toast.LENGTH_LONG).show();
    }

    /* override fun onPause() {
         super.onPause()
         LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this)
     }*/

    private fun setUpLocation() {
        if(ActivityCompat.checkSelfPermission(context!!,android.Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context!!,android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED ){

            ActivityCompat.requestPermissions((context as Activity?)!!, arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION),MY_PERMISSION_REQUEST_CODE)
        }
    }

    private fun checkPlayServices(): Boolean{
        val resultCode= GooglePlayServicesUtil.isGooglePlayServicesAvailable(context!!)
        if(resultCode!= ConnectionResult.SUCCESS){
            if(GooglePlayServicesUtil.isUserRecoverableError(resultCode))
                GooglePlayServicesUtil.getErrorDialog(resultCode, (context as Activity?)!!,PLAY_SERVICES_RESOLUTION_REQUEST).show()
            else{
                Toast.makeText(context!!,"This device is not supported", Toast.LENGTH_LONG).show()
            }
            return false
        }
        return true

    }

    /*companion object {
        private var instance: MainActivity? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }*/

}