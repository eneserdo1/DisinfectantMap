package com.eneserdogan.disinfectantmap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import com.parse.ParseObject
import com.parse.ParseQuery
import kotlinx.android.synthetic.main.activity_locations.*
import java.util.*
import kotlin.collections.ArrayList

class LocationsActivity : AppCompatActivity() {

    var namesArray= ArrayList<String>()

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val menuInflater=menuInflater
        menuInflater.inflate(R.menu.add_place,menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item?.itemId ==R.id.addPlace){
            val intent=Intent(applicationContext,PlaceNameActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_locations)

        getParseData();
        listview.setOnItemClickListener { adapterView, view, i, l ->
            val intent = Intent(applicationContext,DetailActivity::class.java)
            intent.putExtra("name",namesArray[i])
            startActivity(intent)
        }

    }

    fun getParseData(){

        val arrayAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,namesArray)
        listview.adapter = arrayAdapter

        val query=ParseQuery.getQuery<ParseObject>("Locations")
        query.findInBackground { objects, e ->
            if(e != null){
                Toast.makeText(applicationContext,e.localizedMessage,Toast.LENGTH_LONG).show()
            }else{
                if (objects.size>0){
                    namesArray.clear()

                    for (parseObject in objects) {
                        val name = parseObject.get("title") as String
                        namesArray.add(name)
                    }
                    arrayAdapter.notifyDataSetChanged()
                }
            }
        }
    }
}
