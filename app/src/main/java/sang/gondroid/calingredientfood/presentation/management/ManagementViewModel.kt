package sang.gondroid.calingredientfood.presentation.management

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sang.gondroid.calingredientfood.presentation.base.BaseViewModel

internal class ManagementViewModel : BaseViewModel() {

    private val _threeMajorNtrPercent = MutableLiveData<List<Float>>()
    val threeMajorNtrPercent: LiveData<List<Float>>
        get() = _threeMajorNtrPercent

    private val _calorieList = MutableLiveData<List<Float>>()
    val calorieList: LiveData<List<Float>>
        get() = _calorieList

    private val _averageCalorie = MutableLiveData<String>()
    val averageCalorie: LiveData<String>
        get() = _averageCalorie

    override fun fetchData() = viewModelScope.launch {
        _averageCalorie.postValue(
            "2303"
        )

        _threeMajorNtrPercent.postValue(
            listOf(
                150.03f, 64.06f, 23.00f
            )
        )

        _calorieList.postValue(
            listOf(
                2045.02f, 1930f, 2052.03f, 2543f, 2513f, 2033f, 2312.23f
            )
        )
    }
}
