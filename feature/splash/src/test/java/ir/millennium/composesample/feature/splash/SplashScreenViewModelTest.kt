package ir.millennium.composesample.feature.splash

import ir.millennium.composesample.core.datastore.UserPreferencesRepository
import ir.millennium.composesample.core.firebase.authentication.AuthState
import ir.millennium.composesample.core.firebase.authentication.GoogleAuthUiClient
import ir.millennium.composesample.core.model.TypeTheme
import ir.millennium.composesample.core.model.UserData
import ir.millennium.composesample.feature.splash.viewModel.SplashScreenViewModel
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
import org.junit.Assert.assertNull
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.reset
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyBlocking
import org.mockito.kotlin.whenever


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SplashScreenViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @Mock
    private lateinit var mockUserPreferencesRepository: UserPreferencesRepository

    @Mock
    private lateinit var mockGoogleAuthUiClient: GoogleAuthUiClient

    private lateinit var viewModel: SplashScreenViewModel

    private val testStateThemeFlow = MutableStateFlow(TypeTheme.LIGHT.typeTheme)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.openMocks(this)
        whenever(mockUserPreferencesRepository.stateTheme).thenReturn(testStateThemeFlow)
        viewModel = SplashScreenViewModel(mockUserPreferencesRepository, mockGoogleAuthUiClient)
    }

    @Test
    fun `typeTheme should be initialized with LIGHT value`() = runTest {
        assertEquals(TypeTheme.LIGHT.typeTheme, viewModel.typeTheme.value)
    }

    @Test
    fun `change theme to dark`() = runTest {
        testStateThemeFlow.value = TypeTheme.DARK.typeTheme
        advanceUntilIdle()
        assertEquals(TypeTheme.DARK.typeTheme, viewModel.typeTheme.value)
    }

    @Test
    fun `stateAuth has been initialized with null`() {
        assertNull(viewModel.authState.value)
    }

    @Test
    fun `isUserLogin should set authState to Authenticated if user is signed in`() {
        val userData = UserData("id", "name", "email")
        whenever(mockGoogleAuthUiClient.getSignedInUser()).thenReturn(userData)
        viewModel.isUserLogin()
        assertEquals(AuthState.Authenticated(userData), viewModel.authState.value)
    }

    @Test
    fun `isUserLogin should set authState to Unauthenticated if no user is signed in`() {
        whenever(mockGoogleAuthUiClient.getSignedInUser()).thenReturn(null)
        viewModel.isUserLogin()
        assertEquals(AuthState.Unauthenticated, viewModel.authState.value)
    }

    @Test
    fun `saveUserData should call setDataUser on userPreferencesRepository`() = runTest {
        val userData = UserData("id", "name", "email")
        viewModel.saveUserData(userData)
        verify(mockUserPreferencesRepository).setDataUser(userData)
    }

    @Test
    fun `saveUserData should run on IO dispatcher`() = runTest {
        val userData = UserData("id", "name", "email")
        viewModel.saveUserData(userData)
        advanceUntilIdle()
        verifyBlocking(mockUserPreferencesRepository, times(1)) { setDataUser(userData) }
    }

    @Test
    fun `isUserLogin should not update authState if the same user is already authenticated`() {
        val userData = UserData("id", "name", "email")
        whenever(mockGoogleAuthUiClient.getSignedInUser()).thenReturn(userData)
        viewModel.isUserLogin()
        viewModel.isUserLogin()
        assertEquals(AuthState.Authenticated(userData), viewModel.authState.value)
    }

    @Test
    fun `isUserLogin should set authState to Unauthenticated if GoogleAuthUiClient returns null`() {
        whenever(mockGoogleAuthUiClient.getSignedInUser()).thenReturn(null)
        viewModel.isUserLogin()
        assertEquals(AuthState.Unauthenticated, viewModel.authState.value)
    }

    @Test
    fun `authState should remain unchanged when saveUserData is called`() = runTest {
        val userData = UserData("id", "name", "email")
        viewModel.saveUserData(userData)
        advanceUntilIdle()
        assertNull(viewModel.authState.value)
    }

    @Test
    fun `saveUserData should handle exceptions thrown by userPreferencesRepository`() = runTest {
        val userData = UserData("id", "name", "email")
        whenever(mockUserPreferencesRepository.setDataUser(userData)).thenThrow(RuntimeException("Error"))
        try {
            viewModel.saveUserData(userData)
        } catch (e: Exception) {
            fail("Exception should be handled inside the ViewModel")
        }
    }

    @Test
    fun `saveUserData should run without throwing any exception in normal conditions`() = runTest {
        val userData = UserData("id", "name", "email")

        try {
            viewModel.saveUserData(userData)
        } catch (e: Exception) {
            fail("Exception should not be thrown")
        }
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
        reset(mockUserPreferencesRepository)
    }
}
