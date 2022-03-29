package sang.gondroid.calingredientfood.domain.use_case

import sang.gondroid.calingredientfood.domain.model.MealNtrIrdntModel
import sang.gondroid.calingredientfood.domain.repository.MealNtrIrdntRepository

class InsertMealNtrIrdntUseCase(
    private val mealNtrIrdntRepository: MealNtrIrdntRepository
) {
    suspend operator fun invoke(mealNtrIrdntModel: MealNtrIrdntModel) {
        mealNtrIrdntRepository.insertMealNtrIrdnt(mealNtrIrdntModel)
    }
}