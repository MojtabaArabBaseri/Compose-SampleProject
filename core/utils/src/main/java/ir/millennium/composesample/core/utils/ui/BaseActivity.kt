package ir.millennium.composesample.core.utils.ui

import android.content.Context
import android.content.res.Configuration
import android.view.View
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.google.gson.GsonBuilder
import dagger.hilt.android.AndroidEntryPoint
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import ir.millennium.composesample.core.datastore.UserPreferencesRepository
import ir.millennium.composesample.core.model.TypeLanguage
import ir.millennium.composesample.core.utils.utils.AuxiliaryFunctionsManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseActivity : ComponentActivity() {

    @Inject
    lateinit var userPreferencesRepository: UserPreferencesRepository

    @Inject
    lateinit var auxiliaryFunctionsManager: AuxiliaryFunctionsManager

    override fun attachBaseContext(newBase: Context) {
        val newConfiguration = Configuration(newBase.resources?.configuration)
        val gson = GsonBuilder().serializeNulls().create()
        val userPreferencesRepository = UserPreferencesRepository(newBase, gson)
        val locale =
            runBlocking { Locale(userPreferencesRepository.languageApp.first()) }
        newConfiguration.fontScale = 1.0f
        Locale.setDefault(locale)
        newConfiguration.setLocale(locale)
        newConfiguration.setLayoutDirection(locale)
        applyOverrideConfiguration(newConfiguration)
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launch {
            userPreferencesRepository.languageApp.collect { language ->
                if (language == TypeLanguage.ENGLISH.typeLanguage)
                    window.decorView.layoutDirection = View.LAYOUT_DIRECTION_LTR
                else
                    window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL
            }
        }
    }
}
