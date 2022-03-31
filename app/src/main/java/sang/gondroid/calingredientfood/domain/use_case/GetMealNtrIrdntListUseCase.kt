package sang.gondroid.calingredientfood.domain.use_case

import kotlinx.coroutines.flow.Flow
import sang.gondroid.calingredientfood.domain.model.MealNtrIrdntModel
import sang.gondroid.calingredientfood.domain.repository.MealNtrIrdntRepository

class GetMealNtrIrdntListUseCase(
    private val mealNtrIrdntRepository: MealNtrIrdntRepository
) {
    operator fun invoke(): Flow<List<MealNtrIrdntModel>> =
        mealNtrIrdntRepository.getMealNtrIrdntList()
}
