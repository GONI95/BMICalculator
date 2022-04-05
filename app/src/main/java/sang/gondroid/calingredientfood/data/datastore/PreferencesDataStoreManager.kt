package sang.gondroid.calingredientfood.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.catch
import okhttp3.internal.toImmutableMap
import sang.gondroid.calingredientfood.presentation.util.Constants
import java.io.IOException

/**
 * Gon [22.03.22] : Koin으로 부터 DataStore<Preferences> 객체를 주입받아 DataStore와 관련된 작업을 담당하는
 *                  PreferencesDataStoreManager 정의
 */
class PreferencesDataStoreManager(private val preferencesDataStore: DataStore<Preferences>) {

    private val prefs by lazy { preferencesDataStore }

    private val keyArray: Array<Preferences.Key<String>> = with(Constants) {
        arrayOf(
            stringPreferencesKey(SERVING_WEIGHT_KEY),
            stringPreferencesKey(DESCRIPTION_KOR_KEY),
            stringPreferencesKey(COMPANY_KEY),
            stringPreferencesKey(CALORIE_KEY),
            stringPreferencesKey(CARBOHYDRATE_KEY),
            stringPreferencesKey(PROTEIN_KEY),
            stringPreferencesKey(FAT_KEY)
        )
    }

    suspend fun readInputTextData(): Map<String, String> {
        val foodNtrIrdntDataMap = mutableMapOf<String, String>()

        keyArray.forEach { key ->
            val text = prefs.data
                .catch {
                    if (it is IOException) {
                        it.printStackTrace()
                        emit(emptyPreferences())
                    } else {
                        throw it
                    }
                }
                .map {
                    it[key] ?: ""
                }.first()

            foodNtrIrdntDataMap[key.name] = text
        }

        return foodNtrIrdntDataMap.toImmutableMap()
    }

    suspend fun saveInputTextData(inputTextMap: Map<String, String>) {
        keyArray.forEach { key ->
            prefs.edit { mutablePreferences ->
                mutablePreferences[key] = (inputTextMap[key.name] as String)
            }
        }
    }

    suspend fun removeInputTextData() {
        keyArray.forEach { key ->
            prefs.edit { mutablePreferences ->
                mutablePreferences.remove(key)
            }
        }
    }
}
