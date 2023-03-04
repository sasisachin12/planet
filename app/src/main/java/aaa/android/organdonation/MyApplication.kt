package aaa.android.organdonation

import aaa.android.organdonation.util.Prefs
import aaa.android.organdonation.util.Prefs.init
import android.app.Application
import com.facebook.stetho.Stetho

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        Prefs.init(this)
    }
}