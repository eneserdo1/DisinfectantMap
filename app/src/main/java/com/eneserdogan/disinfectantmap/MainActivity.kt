package com.eneserdogan.disinfectantmap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.parse.LogInCallback
import com.parse.ParseException
import com.parse.ParseObject
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



    }

    fun girisYap(view:View){

        ParseUser.logInInBackground(userNameText.text.toString(),userPasswordText.text.toString(),
            LogInCallback { user, e ->
                if (e!= null){

                    Toast.makeText(applicationContext,e.localizedMessage,Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(applicationContext,"Hoşgeldin "+userNameText.text.toString(),Toast.LENGTH_LONG).show()
                    val intent=Intent(applicationContext,LocationsActivity::class.java)
                    startActivity(intent)
                }



            })

    }

    fun kayıtOl(view:View){
        val intent=Intent(applicationContext,SignUpActivity::class.java)
        startActivity(intent)
    }
}
