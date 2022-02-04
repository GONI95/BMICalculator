package sang.gondroid.calingredientfood.presentation.widget.viewholder

import sang.gondroid.calingredientfood.databinding.LayoutCalculatorItemBinding
import sang.gondroid.calingredientfood.domain.model.FoodNtrIrdntModel
import sang.gondroid.calingredientfood.presentation.widget.listener.AdapterListener
import sang.gondroid.calingredientfood.presentation.widget.listener.CalculatorListener

class CalculatorViewHolder(
    private val binding: LayoutCalculatorItemBinding
) : BaseViewHolder<FoodNtrIrdntModel>(binding) {

    override fun bindData(model: FoodNtrIrdntModel) {
        binding.item = model
    }

    override fun bindViews(model: FoodNtrIrdntModel, adapterListener: AdapterListener) {
        if (adapterListener is CalculatorListener) {
            binding.root.setOnClickListener {
                adapterListener.onClickItem(model)
            }
        }
    }
}