package com.esimtek.gemaltolocation

import android.app.Application
import com.esimtek.gemalto.util.PreferenceUtil
import com.esimtek.gemaltolocation.model.LoggedBean
import java.util.*

class GemaltoApplication : Application() {

    var uuid by PreferenceUtil("uuid", "")
    var token: String? = null
    var logged: LoggedBean? = null

    companion object {
        lateinit var instance: GemaltoApplication
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