package sang.gondroid.calingredientfood.presentation.util

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Spinner
import android.widget.EditText
import android.widget.TextView
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.appbar.AppBarLayout
import com.prolificinteractive.materialcalendarview.CalendarDay
import androidx.appcompat.widget.Toolbar
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.paging.LoadState
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.Entry
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import sang.gondroid.calingredientfood.R
import sang.gondroid.calingredientfood.domain.model.Model
import sang.gondroid.calingredientfood.presentation.widget.adapter.BaseRecyclerViewAdapter
import sang.gondroid.calingredientfood.presentation.widget.adapter.SearchModeSpinnerAdapter
import sang.gondroid.calingredientfood.presentation.widget.decorator.CalendarMinMaxDateDecorator
import sang.gondroid.calingredientfood.presentation.widget.decorator.LinearDividerDecoration
import sang.gondroid.calingredientfood.presentation.widget.decorator.SelectDateDecorator
import com.github.mikephil.charting.formatter.ValueFormatter
import kotlin.collections.ArrayList
import com.github.mikephil.charting.components.Legend
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import sang.gondroid.calingredientfood.databinding.LayoutEmptyViewBinding
import sang.gondroid.calingredientfood.presentation.widget.adapter.BasePagingDataAdapter
import sang.gondroid.calingredientfood.presentation.widget.custom.LineChartMarkerView
import sang.gondroid.calingredientfood.presentation.widget.custom.PieChartRenderer
import java.text.NumberFormat
import java.util.Calendar
import android.graphics.Color
import android.graphics.Typeface
import androidx.core.content.ContextCompat.getColor
import androidx.core.content.ContextCompat.getDrawable
import sang.gondroid.calingredientfood.presentation.util.Extensions.getRealPathUri

internal object BindingAdapters {

    /**
     * Gon [22.01.25] : Spinner와 관련된 설정을 해주는 메서드
     *                  SpinnerAdapter : Data 목록을 AdapterView에 출력할 수 있는 형태로 반환하는 Adapter 객체
     *                  Spinner.setAdapter() : AdapterView에 Adapter를 설정
     *                  Spinner.setSelection(int, boolean) : Spinner에서 기본값으로 선택될 항목을 설정하되, onItemSelected()를 호출하지 않음
     */
    @JvmStatic
    @BindingAdapter("setSpinner")
    fun Spinner.setAdapterAndSelection(searchMode: SearchMode) {
        val arrayAdapter =
            SearchModeSpinnerAdapter(context, R.layout.layout_search_mode_item, SearchMode.values())
        adapter = arrayAdapter
        setSelection(searchMode.ordinal, false)
    }

    /**
     * Gon [22.03.02] : AppBarLayout의 verticalOffset이 변경될 때 호출되는 OnOffsetChangedListener를 사용
     *                  전체 범위의 값으로 해당 범위 내의 변경된 값을 나누어 0부터 1까지의 실수를 통해 setProgress()로 전환 위치 설정
     *
     *                  verticalOffset : 상위 AppBarLayout에 대한 띄어져있는 수직 간격(px) | 펼쳐진 0부터 위로 가려진 위치의 음수값 반환
     *                  totalScrollRange : 하위 항목의 스크롤 범위를 반환
     */
    @JvmStatic
    @BindingAdapter("onOffsetChanged")
    fun AppBarLayout.onOffsetChanged(toolbarMotionLayout: MotionLayout) {
        addOnOffsetChangedListener(
            AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
                val seekPosition = -verticalOffset / appBarLayout.totalScrollRange.toFloat()
                toolbarMotionLayout.progress = seekPosition
            }
        )
    }

    /**
     * Gon [22.03.02] : Generics Fun 정의 (out 키워드를 통해 슈퍼클래스에 서브클래스를 대입할 수 있게 허용)
     *                  UIState가 Success인 경우 submitList 호출과 View VISIBLE 아닌 경우 View INVISIBLE
     */
    @JvmStatic
    @BindingAdapter("onEditorEnterAction")
    fun EditText.onEditorEnterAction(searchFunc: Function1<String, Unit>) {

        setOnEditorActionListener { v, actionId, _ ->

            // Gon [22.01.11] : InputMethodManager를 얻어와 키보드를 숨김
            (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                .hideSoftInputFromWindow(v.windowToken, 0)

            with(v.editableText) {
                val replaceText = this.toString().replace(" ", "")
                val imeAction = actionId == EditorInfo.IME_ACTION_SEARCH
                val imeText = replaceText.isNotBlank() or replaceText.isNotEmpty()

                /* Gon [22.03.02] : 입력값이 존재하고, 작업 식별자가 IME_ACTION_SEARCH를 만족하면,
                                    motionLayout의 TransitionToEnd() 호출로 애니메이션 실행
                                    입력값에서 공백을 제거하고 SearchViewModel search() 고차함수 호출
                 */
                if (imeText && imeAction) {
                    true.also {
                        searchFunc(replaceText)
                    }
                }
                // Gon [22.01.11] : 입력값이 비어있는 경우 EditText에 Error message 표시
                else false.also {
                    error = resources.getString(R.string.please_enter_a_search_term)
                }
            }
        }
    }

    /**
     * Gon [22.02.22] : Generics Fun 정의 (out 키워드를 통해 슈퍼클래스에 서브클래스를 대입할 수 있게 허용)
     *                 UIState가 Success인 경우 submitList 호출과 View VISIBLE 아닌 경우 View INVISIBLE
     */
    @Suppress("UNCHECKED_CAST")
    @BindingAdapter("adapter", "submitList")
    @JvmStatic
    fun <T : Model> RecyclerView.setAdapterAndSubmitList(
        foodNtrIrdntAdapter: BaseRecyclerViewAdapter<out T>,
        uiState: UIState
    ) {
        if (adapter == null) {
            addItemDecoration(LinearDividerDecoration(context, R.drawable.bg_divider_design))
            adapter = foodNtrIrdntAdapter
        }

        visibility = when (uiState) {
            is UIState.Success<*> -> {

                val items = (uiState as UIState.Success<List<T>>).data
                DebugLog.d(items.toString())
                (adapter as BaseRecyclerViewAdapter<T>).submitList(items)

                View.VISIBLE
            }
            else -> {
                View.INVISIBLE
            }
        }
    }

    @BindingAdapter("emptyViewVisibility", "progressBarVisibility")
    @JvmStatic
    fun RecyclerView.setPagingDataAdapter(
        emptyView: LayoutEmptyViewBinding,
        lottieAnimationView: LottieAnimationView
    ) {
        var refreshCount = 0
        val basePagingDataAdapter = adapter as BasePagingDataAdapter<*>

        basePagingDataAdapter.addLoadStateListener {

            if (it.refresh is LoadState.NotLoading) {
                suppressLayout(false)
                lottieAnimationView.visibility = View.INVISIBLE

                if (refreshCount == 0)
                    smoothScrollToPosition(0)

                if (basePagingDataAdapter.itemCount != 0) {
                    DebugLog.d("setPagingDataAdapter() : Loading 완료 (Not Empty) $refreshCount")
                    refreshCount++
                    emptyView.root.visibility = View.INVISIBLE
                    visibility = View.VISIBLE
                } else {
                    DebugLog.d("setPagingDataAdapter() : Loading 완료 (Empty)")
                    emptyView.root.visibility = View.VISIBLE
                }
            } else {
                DebugLog.d("setPagingDataAdapter() : Loading 진행 중")
                refreshCount = 0
                lottieAnimationView.visibility = View.VISIBLE
                lottieAnimationView.playAnimation()
                suppressLayout(true)
            }
        }
    }

    @BindingAdapter("showLoading")
    @JvmStatic
    fun LottieAnimationView.showLoading(uiState: UIState) {
        visibility = if (uiState is UIState.Loading)
            View.VISIBLE
        else
            View.INVISIBLE
    }

    @BindingAdapter("showMessage")
    @JvmStatic
    fun TextView.showMessage(uiState: UIState) {
        visibility = when (uiState) {
            is UIState.Empty -> {
                text = resources.getString(uiState.message, uiState.value)
                View.VISIBLE
            }
            is UIState.Error -> {
                text = resources.getString(uiState.message)
                View.VISIBLE
            }
            else -> {
                View.INVISIBLE
            }
        }
    }

    @BindingAdapter("setCalendarView")
    @JvmStatic
    fun MaterialCalendarView.setCalendarView(todayCalendarDay: CalendarDay) {
        val minDay = CalendarDay.from(
            todayCalendarDay.year,
            todayCalendarDay.month,
            todayCalendarDay.day - 6
        )

        val startCalendarDay = CalendarDay.from(
            minDay.year,
            if (todayCalendarDay.day > 6) minDay.month else minDay.month - 1,
            1
        )
        val endCalendarDay = CalendarDay.from(todayCalendarDay.year, todayCalendarDay.month, 31)

        this.state().edit()
            .setMinimumDate(startCalendarDay)
            .setMaximumDate(endCalendarDay)
            .commit()

        val calendarMinMaxDateDecorator =
            CalendarMinMaxDateDecorator(minDay, todayCalendarDay, context)
        val selectDateDecorator = SelectDateDecorator(context, todayCalendarDay)
        addDecorators(calendarMinMaxDateDecorator, selectDateDecorator)
    }

    @BindingAdapter("setCalendarDay")
    @JvmStatic
    fun MaterialCalendarView.setCalendarDay(currentCalendarDay: CalendarDay) {

        val selectDateDecorator = SelectDateDecorator(context, currentCalendarDay)
        addDecorator(selectDateDecorator)

        selectedDate = currentCalendarDay
    }

    @InverseBindingAdapter(attribute = "setCalendarDay", event = "onDateChanged")
    @JvmStatic
    fun MaterialCalendarView.getCalendarDay(): CalendarDay {
        return selectedDate
    }

    @BindingAdapter("onDateChanged")
    @JvmStatic
    fun MaterialCalendarView.setOnDateChangedListener(listener: InverseBindingListener?) {
        setOnDateChangedListener { _, _, _ ->
            listener?.onChange()
        }
    }

    @BindingAdapter("onMenuItemClick")
    @JvmStatic
    fun Toolbar.onMenuItemClick(onMenuItemClick: Function1<Int, Boolean>) {
        setOnMenuItemClickListener {
            onMenuItemClick(it.itemId)
        }
    }

    @BindingAdapter("imageDrawable")
    @JvmStatic
    fun ImageView.imageDrawable(value: BitmapDrawable?) {
        setImageDrawable(
            value ?: getDrawable(context, R.drawable.ic_icon_image)
        )
    }

    @BindingAdapter("clipToOutline")
    @JvmStatic
    fun ImageView.clipToOutline(value: Boolean) {
        clipToOutline = value
    }

    @BindingAdapter("setThreeMajorNtrChart")
    @JvmStatic
    fun PieChart.setThreeMajorNtrChart(threeMajorNtrPercent: List<Float>?) {

        if (threeMajorNtrPercent != null && threeMajorNtrPercent.isNotEmpty()) {
            val extraVerticalOffset = 15f
            val formSize = 10f
            val smallTextSize = 12f
            val textSize = 14f

            /**
             * Gon [22.04.06] : PieChart 디자인 설정
             *                  setDrawEntryLabels : Label(주제) 표시 유무
             *                  setUsePercentValues : 데이터와 상관없이 % 값으로 valueText 표시 유무
             *                  holeRadius : Chart 중심의 원을 최대 반지름의 백분율로 설정 (기본값 50%)
             *                  animateY : Chart가 겹쳐지지 않은 상태에서 펼쳐지는 애니메이션
             *                  invalidate : Chart 새로고침
             */
            setExtraOffsets(0f, extraVerticalOffset, 0f, extraVerticalOffset)
            renderer = PieChartRenderer(this, formSize)
            centerText = resources.getString(R.string.three_major_ntr_chart_center)
            setCenterTextSize(textSize)
            setDrawEntryLabels(false)
            setUsePercentValues(true)
            description.isEnabled = false
            holeRadius = 50f
            animateY(1000)

            with(legend) {
                form = Legend.LegendForm.CIRCLE
                horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
                this.formSize = formSize
                this.textSize = smallTextSize
                textColor = getColor(context, R.color.text_color) // 레전드 컬러 설정
            }

            val pieEntryList = ArrayList<PieEntry>()
            val labelArray = arrayOf(
                resources.getString(R.string.carbohydrate),
                resources.getString(R.string.protein),
                resources.getString(R.string.fat)
            )

            val pieDataSetColorArray = intArrayOf(
                R.color.green,
                R.color.blue,
                R.color.yellow
            )

            val valueTextColorList = listOf(
                getColor(context, R.color.green),
                getColor(context, R.color.blue),
                getColor(context, R.color.yellow)
            )

            repeat(threeMajorNtrPercent.size) {
                pieEntryList.add(
                    PieEntry(
                        threeMajorNtrPercent[it],
                        labelArray[it]
                    )
                )
            }

            val pieDataSet = PieDataSet(pieEntryList, null)

            /**
             * Gon [22.04.06] : PieChart 데이터와 관련된 디자인 설정
             *                  valueTextSize : slice에 위치하는 value textSzie 설정
             *                  valueTextColor : slice에 위치하는 value color 설정
             *                  formSize : Chart 하단 slice 설명란 textSize 설정
             *                  sliceSpace : slice 마다 공백 크기 설정
             */
            with(pieDataSet) {
                setColors()
                setColors(pieDataSetColorArray, context)
                valueTextSize = textSize
                setValueTextColors(valueTextColorList)
                sliceSpace = 3f
                selectionShift = 3f
                valueLineWidth = 2f
                valueLinePart1Length = 0.6f
                valueLinePart2Length = 0.3f
                valueLinePart1OffsetPercentage = 120f // Line starts outside of chart
                isUsingSliceColorAsValueLineColor = true
                yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
                valueTypeface = Typeface.DEFAULT_BOLD
                valueFormatter = object : ValueFormatter() {
                    private val formatter = NumberFormat.getPercentInstance()

                    override fun getFormattedValue(value: Float): String =
                        formatter.format(value / 100f)
                }
            }

            // Gon [22.04.06] : Chart에 사용될 정보 설정
            data = PieData(pieDataSet)
            invalidate()
        }
    }

    @BindingAdapter("setAverageCalorieChart")
    @JvmStatic
    fun LineChart.setAverageCalorie(calorieList: List<Float>?) {

        val dateList = mutableListOf<String>()
        val startDay = CalendarDay.today()
        startDay.calendar.add(Calendar.DATE, -7)

        repeat(7) {
            startDay.calendar.add(Calendar.DATE, +1)

            val month = startDay.calendar.get(Calendar.MONTH) + 1
            val day = startDay.calendar.get(Calendar.DATE)

            dateList.add("$month.$day")
        }

        if (calorieList != null && calorieList.isNotEmpty()) {
            val extraTopOffset = 22f
            val extraBottomOffset = 10f
            val formSize = 10f
            val smallTextSize = 12f

            setExtraOffsets(10f, extraTopOffset, 10f, extraBottomOffset)
            marker = LineChartMarkerView(context, R.layout.line_chart_marker)
            description.isEnabled = false
            axisRight.isEnabled = false
            legend.textColor = Color.WHITE
            animateXY(1000, 1000)

            with(legend) {
                form = Legend.LegendForm.CIRCLE
                horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
                this.formSize = formSize
                this.textSize = smallTextSize
                textColor = getColor(context, R.color.text_color)
            }

            // XAxis (아래쪽) - 선 유무, 사이즈, 색상, 축 위치 설정
            with(xAxis) {
                setDrawAxisLine(false)
                setDrawGridLines(false)
                position = XAxis.XAxisPosition.BOTTOM
                granularity = 1f
                this.textSize = smallTextSize
                textColor = getColor(context, R.color.light_text_color)
                position = XAxis.XAxisPosition.BOTTOM
            }

            // YAxis(Right) (왼쪽) - 선 유무, 데이터 최솟값/최댓값, 색상
            axisLeft.setDrawLabels(false)

            // XAxis에 원하는 String 설정하기 (날짜)
            xAxis.valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return dateList[value.toInt()]
                }
            }

            val lineEntryList = ArrayList<Entry>()

            repeat(calorieList.size) {
                lineEntryList.add(
                    Entry(
                        it.toFloat(),
                        calorieList[it]
                    )
                )
            }

            val lineDataSet = LineDataSet(lineEntryList, resources.getString(R.string.calorie))

            with(lineDataSet) {
                fillAlpha = 50
                fillColor = getColor(context, R.color.blue)
                setDrawFilled(true)
                color = getColor(context, R.color.blue)
                setCircleColor(getColor(context, R.color.blue))
                lineWidth = 2f
                circleRadius = 5f
                setDrawValues(false)
                setDrawHorizontalHighlightIndicator(false)
                highLightColor = getColor(context, R.color.hint_color)
            }

            data = LineData(lineDataSet)
            invalidate()
        }
    }

    /**
     * Gone [22.04.28] : Chip 설정 및 클릭된 Chip에 대한 MealNtrIrdntSort 상수를 인자로 가지는 고차함수를 호출하는 메서드
     *                   ChangeListener이기 때문에 check()를 통한 초기값 설정에 대한 호출이 이루어지지 않아
     *                   MonthCategoryViewModel에서 mealNtrIrdntSort 프로퍼티를 MealNtrIrdntSort.INITIALIZE로 초기화
     */
    @BindingAdapter("onCheckedChange")
    @JvmStatic
    fun ChipGroup.onCheckedChange(changeMealNtrIrdntSort: Function1<MealNtrIrdntSort, Unit>) {
        check(R.id.initializeChip)

        setOnCheckedChangeListener { _, checkedId ->
            val initializeChip = findViewById<Chip>(R.id.initializeChip)

            when (checkedId) {
                R.id.initializeChip -> {
                    initializeChip.chipIcon = null
                    changeMealNtrIrdntSort(MealNtrIrdntSort.INITIALIZE)
                }
                R.id.calorieChip -> {
                    initializeChip.chipIcon = getDrawable(context, R.drawable.ic_undo)
                    changeMealNtrIrdntSort(MealNtrIrdntSort.CALORIE)
                }
                R.id.carbohydrateChip -> {
                    initializeChip.chipIcon = getDrawable(context, R.drawable.ic_undo)
                    changeMealNtrIrdntSort(MealNtrIrdntSort.CARBOHYDRATE)
                }
                R.id.proteinChip -> {
                    initializeChip.chipIcon = getDrawable(context, R.drawable.ic_undo)
                    changeMealNtrIrdntSort(MealNtrIrdntSort.PROTEIN)
                }
                R.id.fatChip -> {
                    initializeChip.chipIcon = getDrawable(context, R.drawable.ic_undo)
                    changeMealNtrIrdntSort(MealNtrIrdntSort.FAT)
                }
            }
        }
    }

    @BindingAdapter("srcFromUri")
    @JvmStatic
    fun AppCompatImageView.srcFromUri(uri: Uri?) {

        if (uri != null) {
            val newUri = uri.getRealPathUri(context)
            setImageURI(newUri)
        } else {
            setImageDrawable(getDrawable(context, R.drawable.ic_icon_image))
        }
    }
}
