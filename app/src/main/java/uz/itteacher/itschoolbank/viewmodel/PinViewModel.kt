package uz.itteacher.itschoolbank.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

data class PinState(
    val savedPin: String? = null,
    val failedAttempts: Int = 0,
    val blockEndTime: Long? = null,
    val blockStage: Int = 0, // 0 = none, 1 = 1min done, 2 = 5min done
    val accessGranted: Boolean = false
)

class PinViewModel : ViewModel() {

    // ✅ Make it reactive so UI recomposes when this changes
    var state by mutableStateOf(PinState())
        private set

    fun createPin(pin: String) {
        state = state.copy(savedPin = pin)
    }

    fun resetAccess() {
        state = state.copy(accessGranted = false)
    }

    fun tryPin(entered: String) {
        val now = System.currentTimeMillis()

        // If currently blocked, ignore attempts
        if (state.blockEndTime != null && now < state.blockEndTime!!) return

        if (entered == state.savedPin) {
            // ✅ Correct PIN — reset everything
            state = state.copy(
                failedAttempts = 0,
                blockEndTime = null,
                blockStage = 0,
                accessGranted = true
            )
        } else {
            // ❌ Wrong PIN
            val newFails = state.failedAttempts + 1
            if (newFails >= 3) {
                // Block logic
                val blockDuration = if (state.blockStage == 0) 60_000L else 300_000L // 1 or 5 mins
                state = state.copy(
                    failedAttempts = 0,
                    blockEndTime = now + blockDuration,
                    blockStage = state.blockStage + 1
                )
            } else {
                // Just increment failed attempts
                state = state.copy(failedAttempts = newFails)
            }
        }
    }

    fun remainingBlockSeconds(): Long {
        val now = System.currentTimeMillis()
        return ((state.blockEndTime ?: 0L) - now).coerceAtLeast(0L) / 1000L
    }
}
