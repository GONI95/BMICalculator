package sang.gondroid.calingredientfood.presentation.util

import androidx.annotation.StringRes

/**
 * Gon [22.02.20] : UI 상태 변경 관리를 위한 sealed class
 */
sealed class UIState {
    object Init : UIState()
    object Loading : UIState()
    data class Success<T>(val data : T) : UIState()
    data class Empty(@StringRes val message: Int, val value: String) : UIState()
    data class Error(@StringRes val message: Int) : UIState()
}