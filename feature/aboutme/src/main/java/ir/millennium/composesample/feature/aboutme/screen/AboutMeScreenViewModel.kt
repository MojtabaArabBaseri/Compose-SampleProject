package ir.millennium.composesample.feature.aboutme.screen

import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.millennium.composesample.core.datastore.UserPreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
open class AboutMeScreenViewModel @Inject constructor(
    userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    val stateLazyColumn = LazyListState()

    private val languageAppFlow = userPreferencesRepository.languageApp
    private val _languageApp = runBlocking { MutableStateFlow(languageAppFlow.first()) }
    val languageApp: StateFlow<String> = _languageApp

}

