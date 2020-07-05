package com.eneserdogan.disinfectantmap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.parse.ParseException
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
    }

    fun KayıtOl(view:View){
        val user= ParseUser()
        user.username=userNameSignUp.text.toString()
        user.setPassword(userPasswordSignUp.text.toString())

        user.signUpInBackground { e: ParseException? ->
            if(e  != null){
                Toast.makeText(applicationContext,e.localizedMessage, Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(applicationContext,"Kullanıcı Oluşturuldu", Toast.LENGTH_LONG).show()
                val intent= Intent(applicationContext,LocationsActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
