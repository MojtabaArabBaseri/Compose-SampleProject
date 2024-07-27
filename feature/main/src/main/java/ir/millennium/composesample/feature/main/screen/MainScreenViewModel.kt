package ir.millennium.composesample.feature.main.screen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.millennium.composesample.core.datastore.UserPreferencesRepository
import javax.inject.Inject

@HiltViewModel
open class MainScreenViewModel @Inject constructor(
    val userPreferencesRepository: UserPreferencesRepository
) : ViewModel()