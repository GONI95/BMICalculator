package sang.gondroid.calingredientfood.domain.mapper

import kotlinx.coroutines.flow.map
import sang.gondroid.calingredientfood.data.dto.entity.MealNtrIrdntEntity
import sang.gondroid.calingredientfood.domain.model.MealNtrIrdntModel
import sang.gondroid.calingredientfood.domain.util.ViewType

class MealNtrIrdntMapper(
    private val foodNtrIrdntMapper: FoodNtrIrdntMapper
) : Mapper<MealNtrIrdntEntity, MealNtrIrdntModel> {
    override fun toModel(input: MealNtrIrdntEntity, viewType: ViewType): MealNtrIrdntModel {
        return with(input) {
            MealNtrIrdntModel(
                id ?: 0,
                viewType,
                mealImage,
                currentDate,
                foodNtrIrdntList.map {
                    foodNtrIrdntMapper.toModel(it, viewType)
                },
                totalCalorie,
                totalCarbohydrate,
                totalProtein,
                totalFat,
                totalSugar,
                totalSalt,
                totalCholesterol,
                totalSaturatedFattyAcid,
                totalTransFat
            )
        }
    }

    override fun toEntity(input: MealNtrIrdntModel): MealNtrIrdntEntity {
        return with(input) {
            MealNtrIrdntEntity(
                null,
                mealImage,
                currentDate,
                foodNtrIrdntList.map {
                    foodNtrIrdntMapper.toEntity(it)
                },
                totalCalorie,
                totalCarbohydrate,
                totalProtein,
                totalFat,
                totalSugar,
                totalSalt,
                totalCholesterol,
                totalSaturatedFattyAcid,
                totalTransFat
            )
        }
    }
}
