package sang.gondroid.calingredientfood.presentation.management

import org.koin.android.ext.android.inject
import sang.gondroid.calingredientfood.databinding.FragmentManagementBinding
import sang.gondroid.calingredientfood.presentation.base.BaseFragment

internal class ManagementFragment : BaseFragment<FragmentManagementBinding, ManagementViewModel>() {

    companion object {
        const val TAG = "ManagementFragment"

        fun newInstance() = ManagementFragment()
    }

    override val viewModel: ManagementViewModel by inject()

    override fun getDataBinding(): FragmentManagementBinding =
        FragmentManagementBinding.inflate(layoutInflater)

    override fun initViews() { }

    override fun observeData() { }

}