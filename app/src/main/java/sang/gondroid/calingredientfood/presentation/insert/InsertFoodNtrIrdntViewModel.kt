package sang.gondroid.calingredientfood.presentation.insert

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sang.gondroid.calingredientfood.data.datastore.PreferencesDataStoreManager
import sang.gondroid.calingredientfood.presentation.base.BaseViewModel


internal class InsertFoodNtrIrdntViewModel(
    private val preferencesDataStoreManager: PreferencesDataStoreManager
) : BaseViewModel() {

    private var _foodNtrIrdntDataLiveData = MutableLiveData<Map<String, String>>()
    val foodNtrIrdntDataLiveData : LiveData<Map<String, String>>
        get() = _foodNtrIrdntDataLiveData

    private var state = true

    override fun fetchData() = viewModelScope.launch {
        val foodNtrIrdntDataMap = preferencesDataStoreManager.readFoodNtrIrdntData()
        _foodNtrIrdntDataLiveData.postValue(foodNtrIrdntDataMap)
    }

    fun saveFoodNtrIrdntData(inputData: Array<String>) = viewModelScope.launch {
        if (state)
            preferencesDataStoreManager.saveFoodNtrIrdntData(inputData)
    }

    fun removeFoodNtrIrdntData() = viewModelScope.launch {
        state = false   // Gon [22.03.22] : 비동기 처리로 인해 saveFoodNtrIrdntData() 호출 전 false를 먼저 저장해두는 것이 좋음
        preferencesDataStoreManager.removeFoodNtrIrdntData()
    }
}