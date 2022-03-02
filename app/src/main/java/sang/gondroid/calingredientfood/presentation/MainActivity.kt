package sang.gondroid.calingredientfood.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import sang.gondroid.calingredientfood.R
import sang.gondroid.calingredientfood.databinding.ActivityMainBinding
import sang.gondroid.calingredientfood.domain.model.FoodNtrIrdntModel
import sang.gondroid.calingredientfood.presentation.base.FragmentListener
import sang.gondroid.calingredientfood.presentation.search.SearchFragment
import sang.gondroid.calingredientfood.presentation.calculator.CalculatorFragment
import sang.gondroid.calingredientfood.presentation.widget.adapter.MainViewPagerAdapter
import sang.gondroid.calingredientfood.presentation.util.DebugLog

internal class MainActivity : AppCompatActivity(), FragmentListener {
    private lateinit var binding: ActivityMainBinding

    /**
     * Gon [21.12.29] : MainViewPagerAdapter을 주입 받으며, 파라미터로 MainActivity, Fragment를 담은 List를 전달
     */
    private val searchFragment = SearchFragment()
    private val calculatorFragment = CalculatorFragment()

    private val mainViewPagerAdapter : MainViewPagerAdapter by inject() {
        parametersOf(this@MainActivity, listOf(searchFragment, calculatorFragment))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        DebugLog.v("called")

        initView()
    }

    private fun initView() {
        binding.viewPagerAdapter = mainViewPagerAdapter
    }

    /**
     * Gon [22.03.03] : FragmentListener sendCalculatorItem() 구현체
     *                  calculatorFragment.receiveCalculatorItem() 호출
     */
    override fun sendCalculatorItem(model: FoodNtrIrdntModel): Boolean {
        return calculatorFragment.receiveCalculatorItem(model)
    }

}