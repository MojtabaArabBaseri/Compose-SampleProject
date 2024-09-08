package ir.millennium.composesample.feature.login.screen

import ir.millennium.composesample.core.datastore.UserPreferencesRepository
import ir.millennium.composesample.core.firebase.authentication.AuthState
import ir.millennium.composesample.core.firebase.authentication.GoogleAuthUiClient
import ir.millennium.composesample.core.firebase.authentication.SignInResult
import ir.millennium.composesample.core.model.TypeTheme
import ir.millennium.composesample.core.model.UserData
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.reset
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyBlocking
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class LoginScreenViewModelTest {

    @Mock
    lateinit var mockUserPreferencesRepository: UserPreferencesRepository

    @Mock
    lateinit var mockGoogleAuthUiClient: GoogleAuthUiClient

    private lateinit var viewModel: LoginScreenViewModel

    private val testStateThemeFlow = MutableStateFlow(TypeTheme.LIGHT.typeTheme)

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        MockitoAnnotations.openMocks(this)
        whenever(mockUserPreferencesRepository.stateTheme).thenReturn(testStateThemeFlow)

        viewModel = LoginScreenViewModel(
            userPreferencesRepository = mockUserPreferencesRepository,
            googleAuthUiClient = mockGoogleAuthUiClient
        )
    }

    @Test
    fun `stateAuth has been initialized with null`() = runTest {
        assertNull(viewModel.authState.value)
    }

    @Test
    fun `typeTheme should be initialized with initial value`() = runTest {
        val initialTheme = TypeTheme.LIGHT.typeTheme
        assertEquals(initialTheme, viewModel.typeTheme.value)
    }

    @Test
    fun `change theme to dark`() = runTest {
        testStateThemeFlow.value = TypeTheme.DARK.typeTheme
        advanceUntilIdle()
        assertEquals(TypeTheme.DARK.typeTheme, viewModel.typeTheme.value)
    }

    @Test
    fun `onSignInResult with success result`() = runTest {
        val userData = UserData("id", "username", "image")
        val signInResult = SignInResult(data = userData, errorMessage = null)
        viewModel.onSignInResult(signInResult)
        advanceUntilIdle()
        assertEquals(AuthState.Authenticated(userData), viewModel.authState.value)
    }

    @Test
    fun `onSignInResult with error result`() = runTest {
        val exception = RuntimeException()
        val signInResult = SignInResult(data = null, errorMessage = exception)
        viewModel.onSignInResult(signInResult)
        advanceUntilIdle()
        assertEquals(AuthState.Error(exception), viewModel.authState.value)
    }

    @Test
    fun `saveDataUser should call setDataUser on userPreferencesRepository`() = runTest {
        val userData = UserData("id", "username", "image")
        viewModel.saveDataUser(userData)
        advanceUntilIdle()
        verify(mockUserPreferencesRepository).setDataUser(userData)
    }

    @Test
    fun `saveDataUser should call on IO dispatcher`() = runTest {
        val userData = UserData("id", "username", "image")
        viewModel.saveDataUser(userData)
        advanceUntilIdle()
        verifyBlocking(mockUserPreferencesRepository) { setDataUser(userData) }
    }

    @Test
    fun `authState should be null after reset state`() = runTest {
        viewModel.resetState()
        advanceUntilIdle()
        assertNull(viewModel.authState.value)
    }

    @Test
    fun `resetState followed by onSignInResult should correctly update authState`() = runTest {
        val userData = UserData("id", "username", "image")
        val signInResult = SignInResult(data = userData, errorMessage = null)
        viewModel.resetState()
        viewModel.onSignInResult(signInResult)
        advanceUntilIdle()
        assertEquals(AuthState.Authenticated(userData), viewModel.authState.value)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        reset(mockUserPreferencesRepository)
    }
}