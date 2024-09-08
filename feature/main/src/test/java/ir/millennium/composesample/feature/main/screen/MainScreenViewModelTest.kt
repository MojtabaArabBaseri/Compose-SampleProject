package ir.millennium.composesample.feature.main.screen

import ir.millennium.composesample.core.datastore.UserPreferencesRepository
import ir.millennium.composesample.core.firebase.authentication.GoogleAuthUiClient
import ir.millennium.composesample.core.model.UserData
import junit.framework.TestCase.assertEquals
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
class MainScreenViewModelTest {

    @Mock
    lateinit var mockUserPreferencesRepository: UserPreferencesRepository

    @Mock
    lateinit var mockGoogleAuthUiClient: GoogleAuthUiClient

    private lateinit var viewModel: MainScreenViewModel

    private val testStateUserData = MutableStateFlow<UserData?>(null)

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.openMocks(this)
        whenever(mockUserPreferencesRepository.userData).thenReturn(testStateUserData)
        viewModel = MainScreenViewModel(mockUserPreferencesRepository, mockGoogleAuthUiClient)
    }

    @Test
    fun `stateUserData has been initialized with null`() = runTest {
        assertNull(viewModel.stateUserData.value)
    }

    @Test
    fun `stateUserData is initialized correctly`() = runTest {
        val expectedUserData = UserData("id", "username", "email")
        testStateUserData.value = expectedUserData
//        whenever(mockUserPreferencesRepository.userData).thenReturn(testStateUserData)
        val viewModel = MainScreenViewModel(mockUserPreferencesRepository, mockGoogleAuthUiClient)
        assertEquals(expectedUserData, viewModel.stateUserData.value)
    }


    @Test
    fun `signOut should call signOut on googleAuthUiClient`() = runTest {
        viewModel.signOut()
        advanceUntilIdle()
        verify(mockGoogleAuthUiClient, times(1)).signOut()
    }

    @Test
    fun `signOut should call on IO dispatcher`() = runTest {
        viewModel.signOut()
        advanceUntilIdle()
        verifyBlocking(mockGoogleAuthUiClient) { signOut() }
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
        reset(mockUserPreferencesRepository)
    }
}