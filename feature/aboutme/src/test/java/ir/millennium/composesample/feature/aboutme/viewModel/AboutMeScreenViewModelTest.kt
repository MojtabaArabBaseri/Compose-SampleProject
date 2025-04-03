package ir.millennium.composesample.feature.aboutme.viewModel

import ir.millennium.composesample.core.datastore.UserPreferencesRepository
import ir.millennium.composesample.core.model.TypeLanguage
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
import org.mockito.kotlin.atLeastOnce
import org.mockito.kotlin.reset
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AboutMeScreenViewModelTest {

    @Mock
    lateinit var mockUserPreferencesRepository: UserPreferencesRepository

    private lateinit var viewModel: AboutMeScreenViewModel

    private val stateLanguageFlow = MutableStateFlow(TypeLanguage.ENGLISH.typeLanguage)

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        MockitoAnnotations.openMocks(this)
        whenever(mockUserPreferencesRepository.languageApp).thenReturn(stateLanguageFlow)
        viewModel = AboutMeScreenViewModel(mockUserPreferencesRepository)
    }

    @Test
    fun `languageApp has been initialize with ENGLISH type`() = runTest {
        assertEquals(TypeLanguage.ENGLISH.typeLanguage, viewModel.languageApp.value)
    }

    @Test
    fun `languageApp should be changed to Persian when change languageApp method in userPreferencesRepository to persian`() =
        runTest {
            stateLanguageFlow.emit(TypeLanguage.PERSIAN.typeLanguage)
            advanceUntilIdle()
            assertEquals(TypeLanguage.PERSIAN.typeLanguage, viewModel.languageApp.value)
        }

    @Test
    fun `lazyColumn state should be initialized correctly`() {
        assertEquals(0, viewModel.stateLazyColumn.firstVisibleItemIndex)
        assertEquals(0, viewModel.stateLazyColumn.firstVisibleItemScrollOffset)
    }

    @Test
    fun `verify no extra repository calls are made during initialization`() = runTest {
        viewModel = AboutMeScreenViewModel(mockUserPreferencesRepository)
        advanceUntilIdle()
        verify(mockUserPreferencesRepository, atLeastOnce()).languageApp
        verifyNoMoreInteractions(mockUserPreferencesRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        reset(mockUserPreferencesRepository)
    }
}