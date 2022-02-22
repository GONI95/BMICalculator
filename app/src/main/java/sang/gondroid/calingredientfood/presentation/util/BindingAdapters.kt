package sang.gondroid.calingredientfood.presentation.util

import android.content.Context
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.viewpager2.widget.ViewPager2
import me.relex.circleindicator.CircleIndicator3
import android.widget.Spinner
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import sang.gondroid.calingredientfood.R
import sang.gondroid.calingredientfood.domain.model.Model
import sang.gondroid.calingredientfood.presentation.widget.*
import sang.gondroid.calingredientfood.presentation.widget.adapter.BaseRecyclerViewAdapter
import sang.gondroid.calingredientfood.presentation.widget.adapter.MainViewPagerAdapter
import sang.gondroid.calingredientfood.presentation.widget.adapter.SearchModeSpinnerAdapter


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
     * Gon [22.01.11] : Soft Keyboard 완료 버튼 클릭 시 호출되는 메서드
     *                  매개변수 : CalculatorViewModel의 고차함수 search
     */
    @JvmStatic
    @BindingAdapter("onEditorEnterAction")
    fun EditText.onEditorEnterAction(searchFunc: Function1<String, Unit>) {

        setOnEditorActionListener { v, actionId, _ ->

            // Gon [22.01.11] : InputMethodManager를 얻어와 키보드를 숨김
            (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                .hideSoftInputFromWindow(v.windowToken, 0)

            with(v.editableText) {
                val replaceText = this.toString().replace(" ","")
                val imeAction = actionId == EditorInfo.IME_ACTION_SEARCH
                val imeText = replaceText.isNotBlank() or replaceText.isNotEmpty()

                /* Gon [22.01.11] : 입력값이 존재하고, 작업 식별자가 IME_ACTION_SEARCH를 만족하면, 입력값에서 공백을 제거하고
                                    CalculatorViewModel search() 고차함수 호출
                 */
                if (imeText && imeAction) {
                    true.also { searchFunc(replaceText) }
                }
                // Gon [22.01.11] : 입력값이 비어있는 경우 EditText에 Error message 표시
                else false.also { error = resources.getString(R.string.please_enter_a_search_term) }
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
}