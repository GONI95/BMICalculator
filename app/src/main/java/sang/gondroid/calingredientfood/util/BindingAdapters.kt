package sang.gondroid.calingredientfood.util

import androidx.databinding.BindingAdapter
import androidx.viewpager2.widget.ViewPager2
import me.relex.circleindicator.CircleIndicator3
import android.widget.Spinner
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
}