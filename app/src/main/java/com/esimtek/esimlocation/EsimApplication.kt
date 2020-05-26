package com.esimtek.esimlocation

import android.app.Application
import com.esimtek.esimlocation.util.PreferenceUtil
import com.esimtek.esimlocation.model.LoggedBean
import java.util.*

class EsimApplication : Application() {

    var uuid by PreferenceUtil("uuid", "")
    var token: String? = null
    var logged: LoggedBean? = null

    companion object {
        lateinit var instance: EsimApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        if (uuid.isEmpty()) {
            uuid = UUID.randomUUID().toString()
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        System.exit(0)
    }

}