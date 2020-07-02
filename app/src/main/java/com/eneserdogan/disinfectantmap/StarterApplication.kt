package com.eneserdogan.disinfectantmap

import android.app.Application
import com.parse.Parse

class StarterApplication :Application(){

    override fun onCreate() {
        super.onCreate()

        Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG)
        Parse.initialize(Parse.Configuration.Builder(this)
            .applicationId("O7t3qQoaoFwqsRHtVmet7aOZZnOueUCZt3suAWR2")
            .clientKey("iEb0D2EJ1BPwsgEpK2sCbhzUgjs03MtKMZ6A4DiK")
            .server("https://parseapi.back4app.com/")
            .build())

    }

}