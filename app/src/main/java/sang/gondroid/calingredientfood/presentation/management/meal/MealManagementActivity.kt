package sang.gondroid.calingredientfood.presentation.management.meal

import androidx.databinding.DataBindingUtil
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.android.ext.android.inject
import sang.gondroid.calingredientfood.R
import sang.gondroid.calingredientfood.databinding.ActivityMealManagementBinding
import sang.gondroid.calingredientfood.presentation.base.BaseActivity
import sang.gondroid.calingredientfood.presentation.management.meal.month_category.MonthCategoryFragment
import sang.gondroid.calingredientfood.presentation.util.MealNtrIrdntSort
import sang.gondroid.calingredientfood.presentation.util.MonthCategory
import sang.gondroid.calingredientfood.presentation.widget.adapter.FragmentViewPagerAdapter

internal class MealManagementActivity :
    BaseActivity<ActivityMealManagementBinding, MealManagementViewModel>() {

    val changeMealNtrIrdntSort: (MealNtrIrdntSort) -> Unit = this::changeMealNtrIrdntSort

    override val viewModel: MealManagementViewModel by inject()

    // Fragment에 하위 뷰를 삽입하기위해 FragmentStateAdapter를 상속받는 Adapter 선언
    private lateinit var viewPagerAdapter: FragmentViewPagerAdapter

    override fun getDataBinding(): ActivityMealManagementBinding =
        DataBindingUtil.setContentView(this, R.layout.activity_meal_management)

    override fun initViews() {
        binding.handler = this

        initViewPager()
    }

    private val monthCategories = MonthCategory.values()

    private val fragmentList by lazy {
        monthCategories.map {
            MonthCategoryFragment.newInstance(it, viewModel.monthRangeMap.getValue(it))
        }
    }

    private fun initViewPager() = with(binding) {

        if (::viewPagerAdapter.isInitialized.not()) {
            viewPagerAdapter = FragmentViewPagerAdapter(this@MealManagementActivity, fragmentList)
            viewPager.adapter = viewPagerAdapter
        }

        viewPager.offscreenPageLimit = monthCategories.size // 1로하면 오류남!!

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = getString(monthCategories[position].stringId)
        }.attach()
    }

    /**
     * Gon [22.04.28] : MealNtrIrdntSort 상수를 통해 각 MonthCategoryViewModel의 setMealNtrIrdntSort() 호출
     */
    private fun changeMealNtrIrdntSort(mealNtrIrdntSort: MealNtrIrdntSort) {
        fragmentList.forEach {
            it.viewModel.setMealNtrIrdntSort(mealNtrIrdntSort)
        }
    }

    override fun observeData() {}
}
