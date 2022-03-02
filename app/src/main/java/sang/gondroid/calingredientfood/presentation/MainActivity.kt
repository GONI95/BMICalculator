package sang.gondroid.calingredientfood.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import sang.gondroid.calingredientfood.R
import sang.gondroid.calingredientfood.databinding.ActivityMainBinding
import sang.gondroid.calingredientfood.presentation.search.SearchFragment
import sang.gondroid.calingredientfood.presentation.calculator.CalculatorFragment
import sang.gondroid.calingredientfood.presentation.widget.adapter.MainViewPagerAdapter
import sang.gondroid.calingredientfood.presentation.util.DebugLog

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    /**
     * Gon [21.12.29] : MainViewPagerAdapter을 주입 받으며, 파라미터로 MainActivity, Fragment를 담은 List를 전달
     */
    private val mainViewPagerAdapter : MainViewPagerAdapter by inject() {
        parametersOf(this@MainActivity, listOf(SearchFragment(), CalculatorFragment()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        DebugLog.v("called")

        initView()
    }

    private fun initView() = with(binding) {
        binding.viewPagerAdapter = mainViewPagerAdapter
    }

}