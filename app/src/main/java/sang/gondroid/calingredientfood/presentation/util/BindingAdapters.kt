package sang.gondroid.calingredientfood.presentation.util

import android.content.Context
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.viewpager2.widget.ViewPager2
import me.relex.circleindicator.CircleIndicator3
import android.widget.Spinner
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import sang.gondroid.calingredientfood.R
import sang.gondroid.calingredientfood.domain.model.FoodNtrIrdntModel
import sang.gondroid.calingredientfood.domain.model.Model
import sang.gondroid.calingredientfood.presentation.calculator.CalculatorFragment
import sang.gondroid.calingredientfood.presentation.util.Extensions.checkType
import sang.gondroid.calingredientfood.presentation.widget.*
import sang.gondroid.calingredientfood.presentation.widget.adapter.BaseRecyclerViewAdapter
import sang.gondroid.calingredientfood.presentation.widget.bottom_sheet.DetailFoodNtrIrdntBottomSheet
import sang.gondroid.calingredientfood.presentation.widget.listener.FoodNtrIrdntListener


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
     * Gon [22.01.25] : Spinner와 관련된 설정을 해주는 메서드
     *                  SpinnerAdapter : Data 목록을 AdapterView에 출력할 수 있는 형태로 반환하는 Adapter 객체
     *                  Spinner.setAdapter() : AdapterView에 Adapter를 설정
     *                  Spinner.setSelection(int, boolean) : Spinner에서 기본값으로 선택될 항목을 설정하되, onItemSelected()를 호출하지 않음
     */
    @JvmStatic
    @BindingAdapter("setSpinner")
    fun Spinner.setAdapterAndSelection(searchMode : SearchMode) {
        val arrayAdapter = SpinnerAdapter(context, R.layout.item_search_mode, SearchMode.values())
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
                    true.also { val a = searchFunc(replaceText) }
                }
                // Gon [22.01.11] : 입력값이 비어있는 경우 EditText에 Error message 표시
                else false.also { error = resources.getString(R.string.please_enter_a_search_term) }
            }
        }
    }

    /**
     * Gon [22.01.20] : RecyclerView의 Adapter에 설정되지 않은 경우 Adapter를 설정
     *                  Listener를 통해 Click 메서드가 호출되면 CalculatorViewModel의 고차함수를 호출
     *                  LiveData의 List 타입에 따라 대응하는 RecyclerViewAdapter의 리스트 데이터를 교체하는 메서드
     *
     * Gon [22.01.25] : BindingAdapter 메서드의 보장된 실행 순서를 설정할 수 없어서, submitList() 메서드와 병합
     */
    @Suppress("UNCHECKED_CAST")
    @BindingAdapter("handler","addBtnClick","submitList")
    @JvmStatic
    fun RecyclerView.setAdapter(
        handler: CalculatorFragment,
        addBtnClickFunc: Function1<FoodNtrIrdntModel, Unit>,
        items: LiveData<List<Model>>) {

        if(adapter == null) {
            val foodNtrIrdntAdapter by lazy {
                BaseRecyclerViewAdapter<FoodNtrIrdntModel>(listOf(), object : FoodNtrIrdntListener {
                    override fun onClickAddButton(model: FoodNtrIrdntModel) {
                        addBtnClickFunc(model)
                    }

                    override fun onClickItem(model: FoodNtrIrdntModel) {
                        DetailFoodNtrIrdntBottomSheet.newInstance(model)
                            .show(handler.requireActivity().supportFragmentManager, Constants.BOTTOM_SHEET_TAG)
                    }
                })
            }

            addItemDecoration(LinearDividerDecoration(context, R.drawable.bg_divider_design))
            adapter  = foodNtrIrdntAdapter
        }

        items.value?.let {
            with(it) {
                if (this.checkType<FoodNtrIrdntModel>()) {
                    (adapter as BaseRecyclerViewAdapter<FoodNtrIrdntModel>).submitList(items.value)
                }
            }
        }
    }
}