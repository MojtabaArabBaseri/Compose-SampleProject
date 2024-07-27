package ir.millennium.composesample.tools

import android.util.Log
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import timber.log.Timber

class ReleaseTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, throwable: Throwable?) {
        if (priority == Log.ERROR || priority == Log.WARN) {
            if (throwable != null) {
                Firebase.crashlytics.recordException(throwable)
            }
        }
    }
}
