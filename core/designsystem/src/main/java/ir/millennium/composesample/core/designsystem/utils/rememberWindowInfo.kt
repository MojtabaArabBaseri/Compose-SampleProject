package ir.millennium.composesample.core.designsystem.utils

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun rememberWindowInfo(): WindowInfo {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    val screenHeightDp = configuration.screenHeightDp
    val orientation = configuration.orientation

    val screenWidthInfo = when {
        screenWidthDp < 480 -> WindowInfo.WindowType.Compact
        screenWidthDp in 480..599 -> WindowInfo.WindowType.Medium
        screenWidthDp in 600..839 -> WindowInfo.WindowType.Large
        else -> WindowInfo.WindowType.ExtraLarge
    }

    val screenHeightInfo = when {
        screenHeightDp < 800 -> WindowInfo.WindowType.Compact
        screenHeightDp in 800..1279 -> WindowInfo.WindowType.Medium
        screenHeightDp in 1280..1919 -> WindowInfo.WindowType.Large
        else -> WindowInfo.WindowType.ExtraLarge
    }

    val deviceType = when (orientation) {
        Configuration.ORIENTATION_PORTRAIT -> screenWidthInfo
        Configuration.ORIENTATION_LANDSCAPE -> screenHeightInfo
        else -> WindowInfo.WindowType.Medium
    }

    return WindowInfo(
        screenWidthInfo = screenWidthInfo,
        screenHeightInfo = screenHeightInfo,
        screenWidth = screenWidthDp.dp,
        screenHeight = screenHeightDp.dp,
        deviceType = deviceType,
        orientation = orientation
    )
}

data class WindowInfo(
    val screenWidthInfo: WindowType,
    val screenHeightInfo: WindowType,
    val screenWidth: Dp,
    val screenHeight: Dp,
    val deviceType: WindowType,
    val orientation: Int
) {
    sealed class WindowType {
        data object Compact : WindowType()      // Small phones
        data object Medium : WindowType()       // Larger phones
        data object Large : WindowType()        // Small tablets
        data object ExtraLarge : WindowType()   // Large tablets or desktops
    }
}


//----------How use this component

/*
        val windowInfo = rememberWindowInfo()

        when (windowInfo.screenWidthInfo) {
            WindowInfo.WindowType.ExtraLarge -> {
                when (windowInfo.orientation) {
                    Configuration.ORIENTATION_PORTRAIT -> {
                        // Tablet-specific design for portrait mode
                    }
                    Configuration.ORIENTATION_LANDSCAPE -> {
                        // Tablet-specific design for landscape mode
                    }
                }
            }
            else -> {
                // Design for other devices, can be the same for both orientations
                when (windowInfo.orientation) {
                    Configuration.ORIENTATION_PORTRAIT -> {
                        // Design for portrait mode on non-tablet devices
                    }
                    Configuration.ORIENTATION_LANDSCAPE -> {
                        // Design for landscape mode on non-tablet devices
                    }
                }
            }
        }
 */