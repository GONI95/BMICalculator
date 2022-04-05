package sang.gondroid.calingredientfood.presentation

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationBarView
import sang.gondroid.calingredientfood.R
import sang.gondroid.calingredientfood.databinding.ActivityMainBinding
import sang.gondroid.calingredientfood.domain.model.FoodNtrIrdntModel
import sang.gondroid.calingredientfood.presentation.base.FragmentListener
import sang.gondroid.calingredientfood.presentation.management.ManagementFragment
import sang.gondroid.calingredientfood.presentation.meal.MealFragment
import sang.gondroid.calingredientfood.presentation.search.SearchFragment
import sang.gondroid.calingredientfood.presentation.util.DebugLog

internal class MainActivity : AppCompatActivity(), FragmentListener, NavigationBarView.OnItemSelectedListener {
    private lateinit var binding: ActivityMainBinding

    private val mealFragment = MealFragment.newInstance()
    private val searchFragment = SearchFragment.newInstance()
    private val managementFragment = ManagementFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DebugLog.v("called")

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.handler = this

        initView()
    }

    private fun initView() {
        showFragment(searchFragment, SearchFragment.TAG)
        binding.bottomNav.setOnItemSelectedListener(this)
    }

    /**
     * Gon [22.04.01] : BottomNavigationView에서 선택한 bottom_navigation_menu의 item 값에 따라 해당하는 Framgent를 표시
     */
    override fun onNavigationItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.menu_search -> {
                showFragment(searchFragment, SearchFragment.TAG)
                true
            }
            R.id.menu_meal -> {
                showFragment(mealFragment, MealFragment.TAG)
                true
            }
            R.id.menu_management -> {
                showFragment(managementFragment, ManagementFragment.TAG)
                true
            }
            else -> false
        }

    private fun showFragment(fragment: Fragment, tag: String) = with(supportFragmentManager) {
        // Gon [22.04.01] : FragmentManager에 추가된 Fragment들 중 동일한 tag를 가지는 Fragment를 찾음
        val findFragment = findFragmentByTag(tag)

        // Gon [22.04.01] : FragmentManager에 추가된 모든 Fragment 리스트를 가져와 화면에서 숨김
        fragments.forEach { fm ->
            this.beginTransaction().hide(fm).commitAllowingStateLoss()
        }

        // Gon [22.04.01] : findFragment의 값이 null이면 프래그먼트를 추가, null이 아니면 숨겨두었던 Fragment를 표시
        findFragment?.let {
            beginTransaction().show(it).commitAllowingStateLoss()
        } ?: kotlin.run {
            beginTransaction()
                .add(R.id.fragmentContainer, fragment, tag)
                .commitAllowingStateLoss()
        }
    }

    /**
     * Gon [22.03.03] : FragmentListener sendCalculatorItem() 구현체
     *                  calculatorFragment.receiveCalculatorItem() 호출
     */
    override fun sendCalculatorItem(model: FoodNtrIrdntModel): Boolean =
        mealFragment.receiveCalculatorItem(model)
}
