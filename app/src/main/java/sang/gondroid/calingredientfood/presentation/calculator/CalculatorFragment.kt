package sang.gondroid.calingredientfood.presentation.calculator

import org.koin.androidx.viewmodel.ext.android.stateViewModel
import sang.gondroid.calingredientfood.databinding.FragmentCalculatorBinding
import sang.gondroid.calingredientfood.presentation.base.BaseFragment

class CalculatorFragment : BaseFragment<FragmentCalculatorBinding, CalculatorViewModel>() {
    override val viewModel: CalculatorViewModel by stateViewModel()

    override fun getDataBinding(): FragmentCalculatorBinding = FragmentCalculatorBinding.inflate(layoutInflater)

    override fun initViews() = with(binding) {
        calculatorViewModel = viewModel
        handler = this@CalculatorFragment
    }

    override fun observeData() { }
}