package sang.gondroid.calingredientfood.presentation.management.meal

import androidx.databinding.DataBindingUtil
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.android.ext.android.inject
import sang.gondroid.calingredientfood.R
import sang.gondroid.calingredientfood.databinding.ActivityMealManagementBinding
import sang.gondroid.calingredientfood.presentation.base.BaseActivity
import sang.gondroid.calingredientfood.presentation.management.meal.month_category.MonthCategoryFragment
import sang.gondroid.calingredientfood.presentation.util.MonthCategory
import sang.gondroid.calingredientfood.presentation.widget.adapter.FragmentViewPagerAdapter

internal class MealManagementActivity :
    BaseActivity<ActivityMealManagementBinding, MealManagementViewModel>() {

    override val viewModel: MealManagementViewModel by inject()

    // Fragment에 하위 뷰를 삽입하기위해 FragmentStateAdapter를 상속받는 Adapter 선언
    private lateinit var viewPagerAdapter: FragmentViewPagerAdapter

    override fun getDataBinding(): ActivityMealManagementBinding =
        DataBindingUtil.setContentView(this, R.layout.activity_meal_management)

    override fun initState() {}

    override fun initViews() {
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

    override fun observeData() {}
}
