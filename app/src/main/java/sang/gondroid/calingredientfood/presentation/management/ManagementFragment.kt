package sang.gondroid.calingredientfood.presentation.management

import android.content.Intent
import org.koin.android.ext.android.inject
import sang.gondroid.calingredientfood.databinding.FragmentManagementBinding
import sang.gondroid.calingredientfood.presentation.base.BaseFragment
import sang.gondroid.calingredientfood.presentation.management.meal.MealManagementActivity

internal class ManagementFragment : BaseFragment<FragmentManagementBinding, ManagementViewModel>() {

    companion object {
        const val TAG = "ManagementFragment"

        fun newInstance() = ManagementFragment()
    }

    override val viewModel: ManagementViewModel by inject()

    override fun getDataBinding(): FragmentManagementBinding =
        FragmentManagementBinding.inflate(layoutInflater)

    override fun initViews() {
        binding.handler = this
        binding.viewModel = viewModel
    }

    fun startMealManagementActivity() {
        val intent = Intent(requireContext(), MealManagementActivity::class.java)
        startActivity(intent)
    }

    override fun observeData() {}
}
