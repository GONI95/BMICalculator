package sang.gondroid.calingredientfood.domain.use_case

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import sang.gondroid.calingredientfood.domain.model.MealNtrIrdntModel
import sang.gondroid.calingredientfood.domain.repository.MealNtrIrdntRepository
import sang.gondroid.calingredientfood.presentation.util.MealNtrIrdntSort

class GetMealNtrIrdntListForMonthUseCase(
    private val mealNtrIrdntRepository: MealNtrIrdntRepository
) {
    operator fun invoke(
        firstDay: String,
        lastDay: String,
        mealNtrIrdntSort: MealNtrIrdntSort
    ): Flow<PagingData<MealNtrIrdntModel>> =
        mealNtrIrdntRepository.getMealNtrIrdntListForMonth(firstDay, lastDay, mealNtrIrdntSort)
}
