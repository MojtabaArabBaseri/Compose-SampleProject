package ir.millennium.composesample.ui.activities.mainActivity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ir.millennium.composesample.core.designsystem.theme.AppTheme
import ir.millennium.composesample.core.utils.ui.BaseActivity
import ir.millennium.composesample.feature.login.navigation.LOGIN_SCREEN_ROUTE
import ir.millennium.composesample.feature.splash.navigation.SPLASH_SCREEN_ROUTE
import ir.millennium.composesample.navigation.NavGraph

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val mainActivityViewModel by viewModels<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()

            when (navBackStackEntry?.destination?.route) {
                SPLASH_SCREEN_ROUTE -> mainActivityViewModel.onAuthScreen(true)
                LOGIN_SCREEN_ROUTE -> mainActivityViewModel.onAuthScreen(true)
                else -> mainActivityViewModel.onAuthScreen(false)
            }

            AppTheme(
                typeTheme = mainActivityViewModel.typeTheme.collectAsState().value,
                authScreens = mainActivityViewModel.authScreen.collectAsState().value,
                languageApp = mainActivityViewModel.languageApp.collectAsState().value
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavGraph(navController)
                }
            }
        }
    }
}