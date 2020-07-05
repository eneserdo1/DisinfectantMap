package com.eneserdogan.disinfectantmap

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_maps.*
import java.io.ByteArrayOutputStream
import java.util.jar.Manifest

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    var locationManager : LocationManager? = null
    var locationListener : LocationListener? = null
    var latitude = ""
    var longitude = ""
    var deger =true

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater=menuInflater
        menuInflater.inflate(R.menu.save_place,menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item?.itemId == R.id.save_place){
            saveToParse()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        println("oncretede")

    }
    override fun onMapReady(googleMap: GoogleMap) {
        println("onmapreadydee")
        mMap = googleMap

        mMap.setOnMapLongClickListener(myListener)

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager


        locationListener= object :LocationListener{
            override fun onLocationChanged(p0: Location?) {
                println("location listener giriş")

                if (p0 != null){
                    if (deger==true) {
                        println("location listener içinde")

                        var userLocation = LatLng(p0.latitude, p0.longitude)
                        mMap.clear()
                        mMap.addMarker(MarkerOptions().position(userLocation).title("Konumunuz"))
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 17f))
                        deger=false
                    }

                }


            }

            override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
            }

            override fun onProviderEnabled(p0: String?) {
            }

            override fun onProviderDisabled(p0: String?) {
            }
        }
        if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),1)
            println("ready sonraso if")

        }else{
            println("ready sonraso else")

            locationManager!!.requestLocationUpdates(LocationManager.GPS_PROVIDER,2,2f,locationListener)
            mMap.clear()
            var lastLocation = locationManager!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            var lastUserLocation= LatLng(lastLocation.latitude,lastLocation.longitude)
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastUserLocation,17f))
        }

    }
    fun saveToParse(){
        val byteArrayOutputStream = ByteArrayOutputStream()
        globalImage!!.compress(Bitmap.CompressFormat.PNG,50,byteArrayOutputStream)
        val bytes = byteArrayOutputStream.toByteArray()

        val parseFile = ParseFile("image.png",bytes)

        val parseObj = ParseObject("Locations")
        parseObj.put("type", globalName)
        parseObj.put("title", globalTitle)
        parseObj.put("latitude",latitude)
        parseObj.put("longitude",longitude)
        parseObj.put("username", ParseUser.getCurrentUser().username.toString())
        parseObj.put("image",parseFile)
        println("Save geldi")
        parseObj.saveInBackground { e ->
            if (e != null) {
                Toast.makeText(applicationContext,e.localizedMessage,Toast.LENGTH_LONG).show()
            } else {
                println("Save elsedeee")
                Toast.makeText(applicationContext,"Location Created",Toast.LENGTH_LONG).show()
                val intent = Intent(applicationContext,LocationsActivity::class.java)
                startActivity(intent)
            }
        }

    }


    val myListener = object : GoogleMap.OnMapLongClickListener {
        override fun onMapLongClick(p0: LatLng?) {

            mMap.clear()

            mMap.addMarker(MarkerOptions().position(p0!!).title(globalName))

            latitude = p0.latitude.toString()
            longitude = p0.longitude.toString()
            deger=false
            println("Latitude "+latitude)
            println("Longitude "+longitude)

            Toast.makeText(applicationContext,"Now Save This Place!",Toast.LENGTH_LONG).show()

        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (grantResults.size > 0){
            if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                locationManager!!.requestLocationUpdates(LocationManager.GPS_PROVIDER,2,2f,locationListener)
                println("request permission result içinde")

            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }




}
