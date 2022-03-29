package sang.gondroid.calingredientfood.data.repository

import kotlinx.coroutines.flow.Flow
import sang.gondroid.calingredientfood.domain.model.MealNtrIrdntModel
import sang.gondroid.calingredientfood.domain.repository.MealNtrIrdntRepository

class MealNtrIrdntRepositoryImpl: MealNtrIrdntRepository {
    override fun getMealNtrIrdntList(): Flow<List<MealNtrIrdntModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertMealNtrIrdnt(mealNtrIrdntModel: MealNtrIrdntModel) {
        TODO("Not yet implemented")
    }

}