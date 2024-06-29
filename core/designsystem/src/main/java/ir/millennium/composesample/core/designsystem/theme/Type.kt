package ir.millennium.composesample.core.designsystem.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ir.millennium.composesample.core.designsystem.R

object AppFont {
    val FontPersian = FontFamily(
        Font(R.font.dana_fa_num_regular, FontWeight.Light),
        Font(R.font.dana_fa_num_regular, FontWeight.Normal),
        Font(R.font.dana_fa_num_regular, FontWeight.Medium),
        Font(R.font.dana_fa_num_bold, FontWeight.Bold),
        Font(R.font.dana_fa_num_bold, FontWeight.ExtraBold)
    )

    val FontEnglish = FontFamily(
        Font(R.font.dana_en_num_regular, FontWeight.Light),
        Font(R.font.dana_en_num_regular, FontWeight.Normal),
        Font(R.font.dana_en_num_regular, FontWeight.Medium),
        Font(R.font.dana_en_num_bold, FontWeight.Bold),
        Font(R.font.dana_en_num_bold, FontWeight.ExtraBold)
    )
}

val TypographyEnglish = androidx.compose.material3.Typography(
    titleLarge = TextStyle(
        fontFamily = AppFont.FontEnglish,
        fontSize = 20.sp,
        lineHeight = 24.0.sp,
        letterSpacing = 0.2.sp
    ),

    titleMedium = TextStyle(
        fontFamily = AppFont.FontEnglish,
        fontSize = 16.sp,
        lineHeight = 24.0.sp,
        letterSpacing = 0.2.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = AppFont.FontEnglish,
        fontSize = 14.sp,
        lineHeight = 20.0.sp,
        letterSpacing = 0.2.sp
    ),
    bodySmall = TextStyle(
        fontFamily = AppFont.FontEnglish,
        fontSize = 12.sp,
        lineHeight = 16.0.sp,
        letterSpacing = 0.4.sp
    )
)

val TypographyPersian = androidx.compose.material3.Typography(
    titleLarge = TextStyle(
        fontFamily = AppFont.FontPersian,
        fontSize = 20.sp,
        lineHeight = 24.0.sp,
        letterSpacing = 0.2.sp
    ),
    titleMedium = TextStyle(
        fontFamily = AppFont.FontPersian,
        fontSize = 16.sp,
        lineHeight = 24.0.sp,
        letterSpacing = 0.2.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = AppFont.FontPersian,
        fontSize = 14.sp,
        lineHeight = 20.0.sp,
        letterSpacing = 0.2.sp
    ),
    bodySmall = TextStyle(
        fontFamily = AppFont.FontPersian,
        fontSize = 12.sp,
        lineHeight = 16.0.sp,
        letterSpacing = 0.4.sp
    ),
)