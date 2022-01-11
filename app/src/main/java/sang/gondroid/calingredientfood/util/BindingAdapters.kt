package sang.gondroid.calingredientfood.util

import android.content.Context
import android.hardware.input.InputManager
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.viewpager2.widget.ViewPager2
import me.relex.circleindicator.CircleIndicator3
import android.widget.Spinner
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import sang.gondroid.calingredientfood.R
import sang.gondroid.calingredientfood.domain.util.SearchMode
import sang.gondroid.calingredientfood.presentation.widget.*


object BindingAdapters {
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
     * Gon [22.01.10] : Spinner와 관련된 설정을 해주는 메서드
     *                  SpinnerAdapter : Data 목록을 AdapterView에 출력할 수 있는 형태로 반환하는 Adapter 객체
     *                  Spinner.setAdapter() : AdapterView에 Adapter를 설정
     *                  Spinner.setSelection() : Spinner에서 기본값으로 선택될 항목을 설정
     */
    @JvmStatic
    @BindingAdapter("setSpinner")
    fun Spinner.setAdapterAndSelection(position: Int) {
        val arrayAdapter = SpinnerAdapter(context, R.layout.item_search_mode, SearchMode.values())
        adapter = arrayAdapter
        setSelection(position)
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

                val imeAction = actionId == EditorInfo.IME_ACTION_SEARCH
                val imeText = this.isNotBlank() or this.isNotEmpty()

                /* Gon [22.01.11] : 입력값이 존재하고, 작업 식별자가 IME_ACTION_SEARCH를 만족하면, 입력값에서 공백을 제거하고
                                    CalculatorViewModel search() 고차함수 호출
                 */
                if (imeText && imeAction) {
                    true.also { searchFunc(this.toString().replace(" ","")) }
                }
                // Gon [22.01.11] : 입력값이 비어있는 경우 EditText에 Error message 표시
                else false.also { error = resources.getString(R.string.please_enter_a_search_term) }
            }
        }
    }
}