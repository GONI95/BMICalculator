package sang.gondroid.calingredientfood.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import sang.gondroid.calingredientfood.domain.model.MealNtrIrdntModel
import sang.gondroid.calingredientfood.presentation.util.MealNtrIrdntSort

interface MealNtrIrdntRepository {
    fun getMealNtrIrdntList(): Flow<List<MealNtrIrdntModel>>

    suspend fun insertMealNtrIrdnt(mealNtrIrdntModel: MealNtrIrdntModel)

    fun getLastSevenDaysMealNtrIrdntList(): Flow<List<MealNtrIrdntModel>>

    fun getMealNtrIrdntListForMonth(
        firstDay: String,
        lastDay: String,
        mealNtrIrdntSort: MealNtrIrdntSort
    ): Flow<PagingData<MealNtrIrdntModel>>
}
