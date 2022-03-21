package sang.gondroid.calingredientfood.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

/**
 * Gon [22.03.22] : Koin으로 부터 DataStore<Preferences> 객체를 주입받아 DataStore와 관련된 작업을 담당하는
 *                  PreferencesDataStoreManager 정의
 */
class PreferencesDataStoreManager(private val preferencesDataStore: DataStore<Preferences>) {

    private val prefs by lazy { preferencesDataStore }

}