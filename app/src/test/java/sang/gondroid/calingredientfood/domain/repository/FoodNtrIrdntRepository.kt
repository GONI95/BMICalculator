package sang.gondroid.calingredientfood.domain.repository

import sang.gondroid.calingredientfood.domain.model.FoodNtrIrdntModel

interface FoodNtrIrdntRepository {
    suspend fun getFoodNtrIrdnt(value: String) : List<FoodNtrIrdntModel>?
}