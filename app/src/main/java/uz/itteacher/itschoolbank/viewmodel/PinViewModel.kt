package uz.itteacher.itschoolbank.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import uz.itteacher.itschoolbank.data.PinRepository

data class PinState(
    val savedPin: String? = null,
    val failedAttempts: Int = 0,
    val blockEndTime: Long? = null,
    val blockStage: Int = 0,
    val accessGranted: Boolean = false
)

class PinViewModel(private val repo: PinRepository) : ViewModel() {

    var state by androidx.compose.runtime.mutableStateOf(PinState())
        private set

    init {
        loadSavedPin()
    }

    private fun loadSavedPin() {
        viewModelScope.launch {
            val pin = repo.getSavedPin.first()
            state = state.copy(savedPin = pin)
        }
    }

    fun createPin(pin: String) {
        viewModelScope.launch {
            repo.savePin(pin)
            state = state.copy(savedPin = pin)
        }
    }

    fun resetAccess() {
        state = state.copy(accessGranted = false)
    }

    fun tryPin(entered: String) {
        val now = System.currentTimeMillis()
        if (state.blockEndTime != null && now < state.blockEndTime!!) return

        if (entered == state.savedPin) {
            state = state.copy(
                failedAttempts = 0,
                blockEndTime = null,
                blockStage = 0,
                accessGranted = true
            )
        } else {
            val newFails = state.failedAttempts + 1
            if (newFails >= 3) {
                val blockDuration = if (state.blockStage == 0) 60_000L else 300_000L
                state = state.copy(
                    failedAttempts = 0,
                    blockEndTime = now + blockDuration,
                    blockStage = state.blockStage + 1
                )
            } else {
                state = state.copy(failedAttempts = newFails)
            }
        }
    }

    fun remainingBlockSeconds(): Long {
        val now = System.currentTimeMillis()
        return ((state.blockEndTime ?: 0L) - now).coerceAtLeast(0L) / 1000L
    }
}
