package sang.gondroid.calingredientfood.util

import android.util.Log
import sang.gondroid.calingredientfood.CalIngredientFoodApplication

/**
 * Gon [21.12.27] : CalIngredientFoodApplication DEBUG 값에 따라 DebugLog 실행
 */
object DebugLog {
    private const val TAG = "DebugLog"

    /** Log Level Error  */
    fun e(message: String?) {
        if (CalIngredientFoodApplication.DEBUG) Log.e(TAG, buildMessage(message))
    }

    /** Log Level Warning  */
    fun w(message: String?) {
        if (CalIngredientFoodApplication.DEBUG) Log.w(TAG, buildMessage(message))
    }

    /** Log Level Information  */
    fun i(message: String?) {
        if (CalIngredientFoodApplication.DEBUG) Log.i(TAG, buildMessage(message))
    }

    /** Log Level Debug  */
    fun d(message: String?) {
        print("========= ${CalIngredientFoodApplication.DEBUG}")
        if (CalIngredientFoodApplication.DEBUG) Log.d(TAG, buildMessage(message))
    }

    /** Log Level Verbose  */
    fun v(message: String?) {
        if (CalIngredientFoodApplication.DEBUG) Log.v(TAG, buildMessage(message))
    }

    private fun buildMessage(message: String?): String {
        val ste = Thread.currentThread().stackTrace[4]
        val sb = StringBuilder()
        sb.append("[ ")
        sb.append(ste.fileName)
        sb.append(" ] ")
        sb.append(ste.methodName)
        sb.append(" : ")
        sb.append(message)
        return sb.toString()
    }

    /*


     */
}