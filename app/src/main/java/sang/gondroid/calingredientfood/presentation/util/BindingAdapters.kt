package sang.gondroid.calingredientfood.presentation.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.provider.MediaStore
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.BindingAdapter
import androidx.viewpager2.widget.ViewPager2
import me.relex.circleindicator.CircleIndicator3
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.appbar.AppBarLayout
import com.prolificinteractive.materialcalendarview.CalendarDay
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.lifecycle.Lifecycle
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import sang.gondroid.calingredientfood.R
import sang.gondroid.calingredientfood.domain.model.Model
import sang.gondroid.calingredientfood.presentation.calculator.CalculatorFragment
import sang.gondroid.calingredientfood.presentation.widget.adapter.BaseRecyclerViewAdapter
import sang.gondroid.calingredientfood.presentation.widget.adapter.MainViewPagerAdapter
import sang.gondroid.calingredientfood.presentation.widget.adapter.SearchModeSpinnerAdapter
import sang.gondroid.calingredientfood.presentation.widget.decorator.CalendarMinMaxDateDecorator
import sang.gondroid.calingredientfood.presentation.widget.decorator.LinearDividerDecoration
import sang.gondroid.calingredientfood.presentation.widget.decorator.SelectDateDecorator
import java.util.*


internal object BindingAdapters {

    @JvmStatic
    @BindingAdapter("setAdapter","setViewPager")
    fun bindAdapterAndViewPager(
        circleIndicator: CircleIndicator3,
        viewPagerAdapter: MainViewPagerAdapter,
        viewPager: ViewPager2
    ) {
        viewPager.adapter = viewPagerAdapter
        circleIndicator.setViewPager(viewPager)
    }

    /**
     * Gon [22.01.25] : Spinner와 관련된 설정을 해주는 메서드
     *                  SpinnerAdapter : Data 목록을 AdapterView에 출력할 수 있는 형태로 반환하는 Adapter 객체
     *                  Spinner.setAdapter() : AdapterView에 Adapter를 설정
     *                  Spinner.setSelection(int, boolean) : Spinner에서 기본값으로 선택될 항목을 설정하되, onItemSelected()를 호출하지 않음
     */
    @JvmStatic
    @BindingAdapter("setSpinner")
    fun Spinner.setAdapterAndSelection(searchMode : SearchMode) {
        val arrayAdapter = SearchModeSpinnerAdapter(context, R.layout.layout_search_mode_item, SearchMode.values())
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
        addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            val seekPosition = -verticalOffset / appBarLayout.totalScrollRange.toFloat()
            toolbarMotionLayout.progress = seekPosition
        })
    }

    /**
     * Gon [22.03.02] : Generics Fun 정의 (out 키워드를 통해 슈퍼클래스에 서브클래스를 대입할 수 있게 허용)
     *                  UIState가 Success인 경우 submitList 호출과 View VISIBLE 아닌 경우 View INVISIBLE
     */
    @JvmStatic
    @BindingAdapter("onEditorEnterAction", "targetMotionLayout")
    fun EditText.onEditorEnterAction(searchFunc: Function1<String, Unit>, motionLayout: MotionLayout) {

        setOnEditorActionListener { v, actionId, _ ->

            // Gon [22.01.11] : InputMethodManager를 얻어와 키보드를 숨김
            (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                .hideSoftInputFromWindow(v.windowToken, 0)

            with(v.editableText) {
                val replaceText = this.toString().replace(" ","")
                val imeAction = actionId == EditorInfo.IME_ACTION_SEARCH
                val imeText = replaceText.isNotBlank() or replaceText.isNotEmpty()

                /* Gon [22.03.02] : 입력값이 존재하고, 작업 식별자가 IME_ACTION_SEARCH를 만족하면,
                                    motionLayout의 TransitionToEnd() 호출로 애니메이션 실행
                                    입력값에서 공백을 제거하고 SearchViewModel search() 고차함수 호출
                 */
                if (imeText && imeAction) {
                    true.also {
                        motionLayout.transitionToEnd()
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
    fun <T: Model> RecyclerView.setAdapterAndSubmitList(
        foodNtrIrdntAdapter: BaseRecyclerViewAdapter<out T>, uiState: UIState
    ) {
        if(adapter == null) {
            addItemDecoration(LinearDividerDecoration(context, R.drawable.bg_divider_design))
            adapter  = foodNtrIrdntAdapter
        }

        visibility = when(uiState) {
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
        visibility = when(uiState) {
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
        val minDay = CalendarDay.from(todayCalendarDay.year, todayCalendarDay.month, todayCalendarDay.day - 6)

        val stCalendarDay = CalendarDay.from(minDay.year, if (todayCalendarDay.day > 6) minDay.month else minDay.month - 1, 1)
        val enCalendarDay = CalendarDay.from(todayCalendarDay.year, todayCalendarDay.month, 31)

        this.state().edit()
            .setMinimumDate(stCalendarDay)
            .setMaximumDate(enCalendarDay)
            .commit()

        val calendarMinMaxDateDecorator = CalendarMinMaxDateDecorator(minDay, todayCalendarDay, context)
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
            value ?: ContextCompat.getDrawable(context, R.drawable.ic_icon_image)
        )
    }

    @BindingAdapter("clipToOutline")
    @JvmStatic
    fun ImageView.clipToOutline(value: Boolean) {
        clipToOutline = value
    }
}