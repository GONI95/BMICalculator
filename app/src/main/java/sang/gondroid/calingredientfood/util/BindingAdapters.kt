package sang.gondroid.calingredientfood.util

import androidx.databinding.BindingAdapter
import androidx.viewpager2.widget.ViewPager2
import me.relex.circleindicator.CircleIndicator3
import sang.gondroid.calingredientfood.presentation.widget.MainViewPagerAdapter

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
}