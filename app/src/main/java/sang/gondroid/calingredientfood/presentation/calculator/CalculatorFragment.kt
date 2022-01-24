package sang.gondroid.calingredientfood.presentation.calculator

import org.koin.android.viewmodel.ext.android.viewModel
import sang.gondroid.calingredientfood.databinding.FragmentCalculatorBinding
import sang.gondroid.calingredientfood.presentation.base.BaseFragment

class CalculatorFragment : BaseFragment<FragmentCalculatorBinding, CalculatorViewModel>() {
    override val viewModel: CalculatorViewModel by viewModel()

    override fun getDataBinding(): FragmentCalculatorBinding = FragmentCalculatorBinding.inflate(layoutInflater)

    override fun initViews() = with(binding) {
        calculatorViewModel = viewModel
        handler = this@CalculatorFragment
    }

    override fun observeData() { }
}