package sang.gondroid.calingredientfood.presentation.widget.listener

import sang.gondroid.calingredientfood.domain.model.MealNtrIrdntModel

interface MealNtrIrdntListener : AdapterListener {
    fun onCheckedChanged(model: MealNtrIrdntModel, isChecked: Boolean)
}
