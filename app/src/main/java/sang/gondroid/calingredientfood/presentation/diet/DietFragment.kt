package sang.gondroid.calingredientfood.presentation.diet

import org.koin.android.ext.android.inject
import sang.gondroid.calingredientfood.databinding.FragmentDietBinding
import sang.gondroid.calingredientfood.presentation.base.BaseFragment

class DietFragment : BaseFragment<FragmentDietBinding, DietViewModel>() {
    override val viewModel: DietViewModel by inject()

    override fun getDataBinding(): FragmentDietBinding = FragmentDietBinding.inflate(layoutInflater)

    override fun observeData() { }
}