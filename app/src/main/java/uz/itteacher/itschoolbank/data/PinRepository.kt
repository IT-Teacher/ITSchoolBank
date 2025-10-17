package uz.itteacher.itschoolbank.data


import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.pinDataStore by preferencesDataStore("pin_prefs")

class PinRepository(private val context: Context) {

    private val PIN_KEY = stringPreferencesKey("saved_pin")


    suspend fun savePin(pin: String) {
        context.pinDataStore.edit { prefs ->
            prefs[PIN_KEY] = pin
        }
    }


    val getSavedPin: Flow<String?> = context.pinDataStore.data.map { prefs ->
        prefs[PIN_KEY]
    }
}
