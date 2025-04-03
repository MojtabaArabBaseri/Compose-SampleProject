package ir.millennium.composesample.core.utils.ui

import androidx.compose.ui.tooling.preview.Preview

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
//@Preview(
//    device = "spec:width=720dp,height=360dp,dpi=420,isRound=false,chinSize=0dp,orientation=landscape",
//    showSystemUi = true,
//    uiMode = Configuration.ORIENTATION_LANDSCAPE,
//)
@Preview(name = "Small Screen", widthDp = 240, heightDp = 320)
@Preview(name = "Normal Screen", widthDp = 320, heightDp = 480)
@Preview(name = "Large Screen", widthDp = 480, heightDp = 800)
@Preview(name = "XLarge Screen", widthDp = 720, heightDp = 1280)
annotation class MultiScreenPreview()
