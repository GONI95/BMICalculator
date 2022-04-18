package sang.gondroid.calingredientfood.presentation.management

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import sang.gondroid.calingredientfood.domain.model.MealNtrIrdntModel
import sang.gondroid.calingredientfood.domain.use_case.GetLastSevenDaysMealNtrIrdntListUseCase
import sang.gondroid.calingredientfood.presentation.base.BaseViewModel
import sang.gondroid.calingredientfood.presentation.util.DebugLog
import java.text.SimpleDateFormat
import java.util.Calendar
import kotlin.math.roundToInt

internal class ManagementViewModel(
    private val getLastSevenDaysMealNtrIrdntListUseCase: GetLastSevenDaysMealNtrIrdntListUseCase,
    private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel() {

    private val _threeMajorNtrList = MutableLiveData<List<Float>>()
    val threeMajorNtrList: LiveData<List<Float>>
        get() = _threeMajorNtrList

    private val _calorieList = MutableLiveData<List<Float>>()
    val calorieList: LiveData<List<Float>>
        get() = _calorieList

    private val _averageCalorie = MutableLiveData<String>()
    val averageCalorie: LiveData<String>
        get() = _averageCalorie

    override fun fetchData() = viewModelScope.launch(ioDispatcher) {

        getLastSevenDaysMealNtrIrdntListUseCase.invoke()
            .collect { list ->

                launch {
                    DebugLog.d("최근 7일 칼로리 목록을 처리하는 코루틴 : ${Thread.currentThread().name}")

                    val lastSevenDaysCalorieList = getLastSevenDaysCalorieList(list)
                    val averageCalorie = getAverageCalorie(list, lastSevenDaysCalorieList.count { it == 0f })

                    _calorieList.postValue(lastSevenDaysCalorieList)
                    _averageCalorie.postValue(averageCalorie)
                }

                launch {
                    DebugLog.d("최근 7일 3대 영양소 목록을 처리하는 코루틴 : ${Thread.currentThread().name}")

                    _threeMajorNtrList.postValue(getAverageThreeMajorNtrList(list))
                }
            }
    }

    /**
     * Gon [22.04.18] : 최근 7일간 칼로리 목록을 가져오는 메서드
     */
    @SuppressLint("SimpleDateFormat")
    private fun getLastSevenDaysCalorieList(mealNtrIrdntModelList: List<MealNtrIrdntModel>): List<Float> {
        var index = 0
        val averageCalorieArray = Array(size = 7) { 0f }
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val today = CalendarDay.today().apply {
            calendar.add(Calendar.DATE, -6)
        }

        mealNtrIrdntModelList.forEach {

            // Gon : 오늘 날짜를 기준으로 createdDate와 비교해 같을 때 까지 index, calendar를 변경해 날짜를 맞춤
            while (dateFormat.format(today.calendar.time) != it.createdDate) {
                index += 1
                today.calendar.add(Calendar.DATE, +1)
            }

            averageCalorieArray[index] += it.totalCalorie.toFloat()
        }

        return averageCalorieArray.toList()
    }

    /**
     * Gon [22.04.18] : 최근 7일간 칼로리의 평균을 구하는 메서드
     *                  count : getLastSevenDaysCalorieList() 메서드 결과에서 0f을 가지는 갯수
     */
    private fun getAverageCalorie(mealNtrIrdntModelList: List<MealNtrIrdntModel>, count: Int): String {
        var averageCalorie = 0f
        val divisor = 7 - count

        mealNtrIrdntModelList.forEach {
            averageCalorie += it.totalCalorie.toFloat() / divisor
        }

        return averageCalorie.roundToInt().toString()
    }

    /**
     * Gon [22.04.18] : 최근 7일간 3대 영양소 총 합을 구하는 메서드
     */
    private fun getAverageThreeMajorNtrList(mealNtrIrdntModelList: List<MealNtrIrdntModel>): List<Float> {
        val threeMajorNtrArray = Array(size = 3) { 0f }

        mealNtrIrdntModelList.forEach {
            threeMajorNtrArray[0] += it.totalCarbohydrate.toFloat()
            threeMajorNtrArray[1] += it.totalProtein.toFloat()
            threeMajorNtrArray[2] += it.totalFat.toFloat()
        }

        return threeMajorNtrArray.toList()
    }
}
