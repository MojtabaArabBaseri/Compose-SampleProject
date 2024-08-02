package ir.millennium.composesample.core.network.dataSource

sealed class UiState {
    data object Loading : UiState()
    data class Success(val data: Any) : UiState()
    data class Error(val throwable: Throwable) : UiState()
}