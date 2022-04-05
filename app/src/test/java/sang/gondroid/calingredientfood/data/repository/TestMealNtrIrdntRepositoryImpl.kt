package sang.gondroid.calingredientfood.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import sang.gondroid.calingredientfood.data.dto.entity.MealNtrIrdntEntity
import sang.gondroid.calingredientfood.domain.mapper.MealNtrIrdntMapper
import sang.gondroid.calingredientfood.domain.model.MealNtrIrdntModel
import sang.gondroid.calingredientfood.domain.repository.MealNtrIrdntRepository
import sang.gondroid.calingredientfood.domain.util.ViewType

class TestMealNtrIrdntRepositoryImpl(
    private val mealNtrIrdntMapper: MealNtrIrdntMapper
) : MealNtrIrdntRepository {

    private val mealNtrIrdntList: MutableList<MealNtrIrdntEntity> = mutableListOf()

    override fun getMealNtrIrdntList(): Flow<List<MealNtrIrdntModel>> = flow {
        val result = mealNtrIrdntList.map {
            mealNtrIrdntMapper.toModel(it, ViewType.MEAL_NTR_IRDNT)
        }

        emit(result)
    }

    override suspend fun insertMealNtrIrdnt(mealNtrIrdntModel: MealNtrIrdntModel) {
        mealNtrIrdntMapper.toEntity(mealNtrIrdntModel).also {
            mealNtrIrdntList.add(it)
        }
    }
}
