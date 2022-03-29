package sang.gondroid.calingredientfood.domain.mapper

import sang.gondroid.calingredientfood.data.dto.entity.FoodNtrIrdntEntity
import sang.gondroid.calingredientfood.domain.model.FoodNtrIrdntModel
import sang.gondroid.calingredientfood.domain.util.ViewType

class FoodNtrIrdntMapper: Mapper<FoodNtrIrdntEntity, FoodNtrIrdntModel> {

    override fun toModel(input: FoodNtrIrdntEntity, viewType: ViewType): FoodNtrIrdntModel {
        return with(input) {
            FoodNtrIrdntModel(
                id!!,
                viewType,
                company,
                beginYear,
                descriptionKOR,
                calorie,
                carbohydrate,
                protein,
                fat,
                sugar,
                salt,
                cholesterol,
                saturatedFattyAcid,
                transFat,
                servingWeight,
                servingCount)
        }
    }

    override fun toEntity(input: FoodNtrIrdntModel): FoodNtrIrdntEntity {
        return with(input) {
            FoodNtrIrdntEntity(
                null,
                company,
                beginYear,
                descriptionKOR,
                calorie,
                carbohydrate,
                protein,
                fat,
                sugar,
                salt,
                cholesterol,
                saturatedFattyAcid,
                transFat,
                servingWeight,
                servingCount)
        }
    }
}
