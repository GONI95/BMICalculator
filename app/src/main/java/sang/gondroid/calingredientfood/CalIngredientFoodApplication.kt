package sang.gondroid.calingredientfood

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.ApplicationInfo
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import sang.gondroid.calingredientfood.di.appModule

class CalIngredientFoodApplication : Application() {
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
        var DEBUG = false
            private set
    }
}
