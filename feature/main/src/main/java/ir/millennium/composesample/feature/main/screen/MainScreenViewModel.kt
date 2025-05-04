package ir.millennium.composesample.feature.main.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.millennium.composesample.core.datastore.UserPreferencesRepository
import ir.millennium.composesample.core.designsystem.components.dialogs.config.MyDialogConfig
import ir.millennium.composesample.core.designsystem.components.dialogs.factory.IMyDialogFactory
import ir.millennium.composesample.core.firebase.authentication.GoogleAuthUiClient
import ir.millennium.composesample.feature.main.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class MainScreenViewModel @Inject constructor(
    userPreferencesRepository: UserPreferencesRepository,
    private val googleAuthUiClient: GoogleAuthUiClient,
    private val myDialogFactory: IMyDialogFactory
) : ViewModel() {

    private val stateUserDataFlow = userPreferencesRepository.userData
    val stateUserData = stateUserDataFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = null
    )

    fun signOut() {
        viewModelScope.launch(Dispatchers.IO) { googleAuthUiClient.signOut() }
    }

    @Composable
    fun ShowDialogForExitApp(
        navToSplashScreen: () -> Unit,
        isShowExitAppDialog: MutableState<Boolean>
    ) {
        val dialogConfig = MyDialogConfig.MyDialogQuestionConfig(
            title = stringResource(id = R.string.attention),
            message = stringResource(id = R.string.message_exit_app),
            labelYesButton = stringResource(id = R.string.yes),
            labelNoButton = stringResource(id = R.string.no),
            stateDialog = isShowExitAppDialog,
            onClickYes = {
                signOut()
                navToSplashScreen()
            })
        myDialogFactory.createDialog(dialogConfig).Render()
    }
}