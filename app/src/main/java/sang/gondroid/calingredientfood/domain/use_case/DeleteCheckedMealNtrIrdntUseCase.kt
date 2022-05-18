package sang.gondroid.calingredientfood.domain.use_case

import sang.gondroid.calingredientfood.domain.repository.MealNtrIrdntRepository

class DeleteCheckedMealNtrIrdntUseCase(
    private val mealNtrIrdntRepository: MealNtrIrdntRepository
) {
    suspend fun invoke(checkedMealNtrIrdntIdSet: Set<Long>) {
        mealNtrIrdntRepository.deleteCheckedMealNtrIrdnt(checkedMealNtrIrdntIdSet)
    }
}
