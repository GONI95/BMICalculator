package sang.gondroid.calingredientfood.presentation.widget.custom.dialog

import sang.gondroid.calingredientfood.databinding.DialogFragmentMealNtrIrdntBinding
import sang.gondroid.calingredientfood.domain.model.MealNtrIrdntModel

class MealNtrIrdntDialogFragment(private val model: MealNtrIrdntModel) :
    BaseDialogFragment<DialogFragmentMealNtrIrdntBinding>() {

    override fun getDataBinding(): DialogFragmentMealNtrIrdntBinding =
        DialogFragmentMealNtrIrdntBinding.inflate(layoutInflater)

    override fun onResume() {
        super.onResume()
        requireContext().dialogFragmentResize(0.9f)
    }

    override fun initViews() {
        binding.item = model

        binding.dismissButton.setOnClickListener {
            dismiss()
        }
    }
}
