package sang.gondroid.calingredientfood.presentation.diet

import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import sang.gondroid.calingredientfood.databinding.FragmentDietBinding
import sang.gondroid.calingredientfood.presentation.base.BaseFragment

class DietFragment : BaseFragment<FragmentDietBinding, DietViewModel>() {
    override val viewModel: DietViewModel by viewModel()

    override fun getDataBinding(): FragmentDietBinding = FragmentDietBinding.inflate(layoutInflater)

    override fun observeData() { }
}