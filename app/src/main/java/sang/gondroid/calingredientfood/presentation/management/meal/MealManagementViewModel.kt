package sang.gondroid.calingredientfood.presentation.management.meal

import android.annotation.SuppressLint
import sang.gondroid.calingredientfood.presentation.base.BaseViewModel
import sang.gondroid.calingredientfood.presentation.util.MonthCategory
import java.text.SimpleDateFormat
import java.util.TimeZone
import java.util.Calendar
import java.util.Locale

@SuppressLint("SimpleDateFormat")
internal class MealManagementViewModel : BaseViewModel() {
    private val monthCategories = MonthCategory.values()

    private var _monthRangeMap = mapOf<MonthCategory, Pair<String, String>>()
    val monthRangeMap by lazy {
        val calendar = Calendar.getInstance()
        val tz = TimeZone.getTimeZone("Asia/Seoul")
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).apply {
            timeZone = tz
        }

        monthCategories.mapIndexed { index, monthCategory ->

            _monthRangeMap += if (monthCategory == MonthCategory.ALL) {

                monthCategory to ("" to dateFormat.format(calendar.time))
            } else {

                calendar.set(Calendar.MONTH, index - 1)

                calendar.set(Calendar.DATE, 1)
                val startDay = dateFormat.format(calendar.time)

                calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
                val lastDay = dateFormat.format(calendar.time)

                monthCategory to (startDay to lastDay)
            }
        }

        _monthRangeMap
    }.also {
        require(it.value.size == 13)
    }
}
