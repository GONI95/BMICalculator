package sang.gondroid.calingredientfood

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.ApplicationInfo
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import sang.gondroid.calingredientfood.di.appModule
import sang.gondroid.calingredientfood.presentation.util.Constants

internal class CalIngredientFoodApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DEBUG = isDebuggable(this)

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@CalIngredientFoodApplication)
            modules(appModule)
        }
    }

    /**
     * Gon [21.12.27] : Debug 모드를 가져오는 메소드
     */
    private fun isDebuggable(context: Context): Boolean {
        var debuggable = false
        val pm = context.packageManager
        try {
            val appInfo = pm.getApplicationInfo(context.packageName, 0)
            debuggable = 0 != appInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE
        } catch (e: PackageManager.NameNotFoundException) {
            /* debuggable variable will remain false */
        }

        return debuggable
    }

    companion object {
        //Gon [22.03.22] : DataStore 인스턴스를 생성하기 위해 preferencesDataStore() 메서드 호출
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.PREFERENCES_DATASTORE_NAME)

        var DEBUG = false
            private set
    }
}
