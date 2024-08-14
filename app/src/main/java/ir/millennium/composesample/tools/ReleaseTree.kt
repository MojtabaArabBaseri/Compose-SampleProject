package ir.millennium.composesample.tools

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber
import javax.inject.Inject

class ReleaseTree @Inject constructor(private val firebaseCrashlytics: FirebaseCrashlytics) :
    Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, throwable: Throwable?) {
        if (priority == Log.ERROR || priority == Log.WARN) {
            if (throwable != null) {
                firebaseCrashlytics.recordException(throwable)
            }
        }
    }
}
