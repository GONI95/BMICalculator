package sang.gondroid.calingredientfood.domain.mapper

import sang.gondroid.calingredientfood.data.dto.network.NetworkItem
import sang.gondroid.calingredientfood.data.util.toDoubleOrZero
import sang.gondroid.calingredientfood.domain.model.FoodNtrIrdntModel
import sang.gondroid.calingredientfood.domain.util.ViewType

class ToFoodNtrIrdntModelMapper : Mapper<NetworkItem, FoodNtrIrdntModel> {

    override fun map(input: NetworkItem): FoodNtrIrdntModel = with(input) {
        FoodNtrIrdntModel(
            hashCode().toLong(),
            ViewType.FOOD_NTR_IRDNT,
            company,
            beginYear,
            descriptionKOR,
            calorie.toDoubleOrZero(),
            carbohydrate.toDoubleOrZero(),
            protein.toDoubleOrZero(),
            fat.toDoubleOrZero(),
            sugar.toDoubleOrZero(),
            salt.toDoubleOrZero(),
            cholesterol.toDoubleOrZero(),
            saturatedFattyAcid.toDoubleOrZero(),
            transFat.toDoubleOrZero(),
            servingWeight.toInt(),
            1
        )
    }
}