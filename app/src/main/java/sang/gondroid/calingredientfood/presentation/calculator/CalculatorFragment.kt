package sang.gondroid.calingredientfood.presentation.calculator

import org.koin.android.ext.android.inject
import sang.gondroid.calingredientfood.databinding.FragmentCalculatorBinding
import sang.gondroid.calingredientfood.presentation.base.BaseFragment

class CalculatorFragment : BaseFragment<FragmentCalculatorBinding, CalculatorViewModel>() {
    override val viewModel: CalculatorViewModel by inject()

    override fun getDataBinding(): FragmentCalculatorBinding = FragmentCalculatorBinding.inflate(layoutInflater)

    override fun observeData() {
        TODO("Not yet implemented")
    }
}