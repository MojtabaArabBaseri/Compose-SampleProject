package ir.millennium.composesample

import android.app.Application
import androidx.multidex.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import ir.millennium.composesample.firebase.ManageChannelNotification
import ir.millennium.composesample.tools.ReleaseTree
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : Application() {

    @Inject
    lateinit var manageChannelNotification: ManageChannelNotification
    override fun onCreate() {
        super.onCreate()

        setupTimber()

        manageChannelNotification.createAllChannelNotification()
    }
}

private fun setupTimber() {
    if (BuildConfig.DEBUG) {
        Timber.plant(Timber.DebugTree())
    } else {
        Timber.plant(ReleaseTree())
    }
}