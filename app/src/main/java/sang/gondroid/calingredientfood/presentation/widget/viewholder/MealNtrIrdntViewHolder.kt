package sang.gondroid.calingredientfood.presentation.widget.viewholder

import sang.gondroid.calingredientfood.databinding.LayoutMealNtrIrdntItemBinding
import sang.gondroid.calingredientfood.domain.model.MealNtrIrdntModel
import sang.gondroid.calingredientfood.presentation.widget.listener.AdapterListener
import sang.gondroid.calingredientfood.presentation.widget.listener.MealNtrIrdntListener

class MealNtrIrdntViewHolder(
    private val binding: LayoutMealNtrIrdntItemBinding
) : BaseViewHolder<MealNtrIrdntModel>(binding) {

    override fun bindData(model: MealNtrIrdntModel) {
        binding.item = model
    }

    override fun bindViews(model: MealNtrIrdntModel, adapterListener: AdapterListener) {
        if (adapterListener is MealNtrIrdntListener) {
            binding.root.setOnClickListener {
                adapterListener.onClickItem(model)
            }
        }
    }
}
