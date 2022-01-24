package sang.gondroid.calingredientfood.domain.mapper

import sang.gondroid.calingredientfood.data.dto.network.NetworkItem
import sang.gondroid.calingredientfood.domain.model.FoodNtrIrdntModel
import sang.gondroid.calingredientfood.domain.util.ViewType

class ToFoodNtrIrdntModelMapper : Mapper<NetworkItem, FoodNtrIrdntModel> {
    override fun map(input: NetworkItem): FoodNtrIrdntModel {
        return with(input) {
            FoodNtrIrdntModel(hashCode().toLong(), ViewType.FOOD_NTR_IRDNT, company, beginYear, descriptionKOR, calorie, carbohydrate, protein, fat, sugar,
                salt, cholesterol, saturatedFattyAcid, transFat, sERVINGWT)
        }
    }
}