package sang.gondroid.calingredientfood.presentation.widget.viewholder

import sang.gondroid.calingredientfood.databinding.LayoutFoodNtrIrdntDetailItemBinding
import sang.gondroid.calingredientfood.domain.model.FoodNtrIrdntModel
import sang.gondroid.calingredientfood.presentation.widget.listener.AdapterListener

class FoodNtrIrdntDetailViewHolder(
    private val binding: LayoutFoodNtrIrdntDetailItemBinding
) : BaseViewHolder<FoodNtrIrdntModel>(binding) {

    override fun bindData(model: FoodNtrIrdntModel) {
        binding.item = model
    }

    override fun bindViews(model: FoodNtrIrdntModel, adapterListener: AdapterListener) {
        binding.root.setOnClickListener {
            adapterListener.onClickItem(model)
        }
    }
}
