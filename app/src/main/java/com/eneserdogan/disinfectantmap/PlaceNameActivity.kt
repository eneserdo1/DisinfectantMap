package com.eneserdogan.disinfectantmap

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_place_name.*
import java.lang.Exception
import java.util.jar.Manifest


    var globalName =""
    var globalImage :Bitmap?=null
    var globalTitle =""

class PlaceNameActivity : AppCompatActivity() {

    var chosenImage : Bitmap?=null
    var secilen =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_name)
        val dezenfektanlar=resources.getStringArray(R.array.Dezenfektanlar)
        val spinner=findViewById<Spinner>(R.id.placeSpinner)
        if (spinner != null) {
            val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, dezenfektanlar)
            spinner.adapter = adapter

        spinner.onItemSelectedListener=object :
            AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                secilen=dezenfektanlar[position]
                Toast.makeText(applicationContext,
                     dezenfektanlar[position], Toast.LENGTH_SHORT).show()
            }
        }
        }
        }

    fun selectImage(view:View){

        if(checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),2)

        }else{
            val intent=Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent,1)

        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 2){
            if (grantResults.size> 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                val intent=Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent,1)
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null){
            val selected=data.data
            try {

                chosenImage=MediaStore.Images.Media.getBitmap(this.contentResolver,selected)
                placeImage.setImageBitmap(chosenImage)



            }catch (e:Exception){
                e.printStackTrace()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun next(view:View){
        globalImage=chosenImage
        globalName=secilen
        println("seçilen ="+ globalName)
        globalTitle=placeTitle.text.toString()

        val intent=Intent(applicationContext,MapsActivity::class.java)
        startActivity(intent)

    }
}
