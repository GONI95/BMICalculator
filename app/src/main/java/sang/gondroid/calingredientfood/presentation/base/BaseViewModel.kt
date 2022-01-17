package sang.gondroid.calingredientfood.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import sang.gondroid.calingredientfood.presentation.util.DebugLog


open class BaseViewModel : ViewModel() {

    /**
     * Gon [21.12.27] : open 변경자를 통해 상속을 허용해 재정의가 가능하고 Coroutine Job을 반환하는 fetchData()
     */
    open fun fetchData(): Job = viewModelScope.launch {
        DebugLog.d("called : ${hashCode()}")
    }
}