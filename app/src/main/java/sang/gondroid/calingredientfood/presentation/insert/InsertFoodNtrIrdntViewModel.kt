package sang.gondroid.calingredientfood.presentation.insert

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import sang.gondroid.calingredientfood.data.datastore.PreferencesDataStoreManager
import sang.gondroid.calingredientfood.domain.model.FoodNtrIrdntModel
import sang.gondroid.calingredientfood.domain.use_case.InsertCustomFoodNtrIrdntUseCase
import sang.gondroid.calingredientfood.domain.util.ViewType
import sang.gondroid.calingredientfood.presentation.base.BaseViewModel
import sang.gondroid.calingredientfood.presentation.util.Constants
import sang.gondroid.calingredientfood.presentation.util.UIState
import java.util.*


internal class InsertFoodNtrIrdntViewModel(
    private val preferencesDataStoreManager: PreferencesDataStoreManager,
    private val insertCustomFoodNtrIrdntUseCase: InsertCustomFoodNtrIrdntUseCase
) : BaseViewModel() {

    private var savableState = true

    val servingWeight = MutableLiveData("")
    val descriptionKor = MutableLiveData("")
    val company = MutableLiveData("")
    val calorie = MutableLiveData("")
    val carbohydrate = MutableLiveData("")
    val protein = MutableLiveData("")
    val fat = MutableLiveData("")

    private val _event = MutableSharedFlow<Event>()
    val event = _event.asSharedFlow()

    private val _uiState = MutableLiveData<UIState>(UIState.Init)
    val uiState : LiveData<UIState>
        get() = _uiState

    override fun fetchData() = viewModelScope.launch {
        preferencesDataStoreManager.readInputTextData().let { map ->

            servingWeight.postValue(map[Constants.SERVING_WEIGHT_KEY])
            descriptionKor.postValue(map[Constants.DESCRIPTION_KOR_KEY])
            company.postValue(map[Constants.COMPANY_KEY])
            calorie.postValue(map[Constants.CALORIE_KEY])
            carbohydrate.postValue(map[Constants.CARBOHYDRATE_KEY])
            protein.postValue(map[Constants.PROTEIN_KEY])
            fat.postValue(map[Constants.FAT_KEY])
        }
    }

    private fun removeInputTextData() = viewModelScope.launch {
        savableState = false   // Gon [22.03.22] : 비동기 처리로 인해 saveFoodNtrIrdntData() 호출 전 false를 먼저 저장해두는 것이 좋음
        preferencesDataStoreManager.removeInputTextData()
    }

    fun saveInputTextData() = viewModelScope.launch {

        if (savableState) {
            preferencesDataStoreManager.saveInputTextData(getInputTextMap())
        }
    }

    fun insertCustomFoodNtrIrdnt() = viewModelScope.launch {
        val inputTextMap = getInputTextMap()

        inputTextMap.keys.forEach { key ->
            if (key != Constants.COMPANY_KEY && inputTextMap[key].isNullOrEmpty()) {
                _uiState.postValue(UIState.Failure)
                return@launch
            }
        }

        val newFoodNtrIrdntModel = createFoodNtrIrdntModel()
        insertCustomFoodNtrIrdntUseCase.invoke(newFoodNtrIrdntModel)

        removeInputTextData()
        _event.emit(Event.SetResult(newFoodNtrIrdntModel))
    }

    private fun getInputTextMap() =
        mutableMapOf(
            Constants.SERVING_WEIGHT_KEY to servingWeight.value!!,
            Constants.DESCRIPTION_KOR_KEY to  descriptionKor.value!!,
            Constants.COMPANY_KEY to  company.value!!,
            Constants.CALORIE_KEY to  calorie.value!!,
            Constants.CARBOHYDRATE_KEY to  carbohydrate.value!!,
            Constants.PROTEIN_KEY to protein.value!!,
            Constants.FAT_KEY to  fat.value!!
        )

    private fun createFoodNtrIrdntModel() =
        FoodNtrIrdntModel(
            id = hashCode().toLong(),
            type = ViewType.CALCULATOR,
            servingWeight = servingWeight.value!!.toInt(),
            descriptionKOR = descriptionKor.value!!,
            company =  company.value!!,
            calorie = calorie.value!!.toDouble(),
            carbohydrate = carbohydrate.value!!.toDouble(),
            protein = protein.value!!.toDouble(),
            fat = fat.value!!.toDouble(),
            beginYear = Calendar.YEAR.toString(),
            sugar = 0.00,
            salt = 0.00,
            cholesterol = 0.00,
            saturatedFattyAcid = 0.00,
            transFat = 0.00,
            servingCount = 1
        )

    sealed class Event {
        data class SetResult(val data: FoodNtrIrdntModel) : Event()
    }
}