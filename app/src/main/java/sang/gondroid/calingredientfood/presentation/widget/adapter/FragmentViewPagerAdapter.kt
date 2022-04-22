package sang.gondroid.calingredientfood.presentation.widget.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import sang.gondroid.calingredientfood.presentation.management.meal.month_category.MonthCategoryFragment

internal class FragmentViewPagerAdapter(
    fragmentManager: FragmentActivity,
    private val fragmentList: List<MonthCategoryFragment>
) : FragmentStateAdapter(fragmentManager) {

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment = fragmentList[position]
}
