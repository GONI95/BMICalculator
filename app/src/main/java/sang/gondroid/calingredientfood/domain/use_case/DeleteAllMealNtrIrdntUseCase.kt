package sang.gondroid.calingredientfood.domain.use_case

import sang.gondroid.calingredientfood.domain.repository.MealNtrIrdntRepository

class DeleteAllMealNtrIrdntUseCase(
    private val mealNtrIrdntRepository: MealNtrIrdntRepository
) {
    suspend fun invoke() {
        mealNtrIrdntRepository.deleteAllMealNtrIrdnt()
    }
}
