package ir.millennium.composesample

import android.app.Application
import android.content.pm.ApplicationInfo
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.hilt.android.HiltAndroidApp
import ir.millennium.composesample.firebase.ManageChannelNotification
import ir.millennium.composesample.tools.ReleaseTree
import timber.log.Timber
import timber.log.Timber.DebugTree
import javax.inject.Inject


@HiltAndroidApp
class MyApplication : Application() {

    @Inject
    lateinit var manageChannelNotification: ManageChannelNotification

    @Inject
    lateinit var releaseTree: ReleaseTree

    private val isDebuggable by lazy { 0 != applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE }

    override fun onCreate() {
        super.onCreate()
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)

        setupTimber(isDebuggable, releaseTree)

        manageChannelNotification.createAllChannelNotification()
    }
}

private fun setupTimber(isDebuggable: Boolean, releaseTree: ReleaseTree) {

    if (isDebuggable) {
        Timber.plant(DebugTree())
    } else {
        Timber.plant(releaseTree)
    }
}