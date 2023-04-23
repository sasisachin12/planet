package aaa.android.plant

import aaa.android.plant.util.Prefs
import android.app.Application
import com.facebook.stetho.Stetho

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        Prefs.init(this)
    }
}