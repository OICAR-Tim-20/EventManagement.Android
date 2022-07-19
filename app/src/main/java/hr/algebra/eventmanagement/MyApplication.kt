package hr.algebra.eventmanagement

import android.app.Application
import hr.algebra.eventmanagement.helpers.applicationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApplication)
            modules(applicationModule)
        }
    }

}
