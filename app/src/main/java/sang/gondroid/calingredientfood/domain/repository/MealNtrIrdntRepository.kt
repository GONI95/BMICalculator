package sang.gondroid.calingredientfood.domain.repository

import kotlinx.coroutines.flow.Flow
import sang.gondroid.calingredientfood.domain.model.MealNtrIrdntModel

interface MealNtrIrdntRepository {
    fun getMealNtrIrdntList(): Flow<List<MealNtrIrdntModel>>

    suspend fun insertMealNtrIrdnt(mealNtrIrdntModel: MealNtrIrdntModel)
}
