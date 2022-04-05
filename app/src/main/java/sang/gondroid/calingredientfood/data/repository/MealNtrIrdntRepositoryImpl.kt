package sang.gondroid.calingredientfood.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import sang.gondroid.calingredientfood.data.db.MealNtrIrdntDAO
import sang.gondroid.calingredientfood.domain.mapper.MealNtrIrdntMapper
import sang.gondroid.calingredientfood.domain.model.MealNtrIrdntModel
import sang.gondroid.calingredientfood.domain.repository.MealNtrIrdntRepository
import sang.gondroid.calingredientfood.domain.util.ViewType
import sang.gondroid.calingredientfood.presentation.util.DebugLog

class MealNtrIrdntRepositoryImpl(
    private val ioDispatcher: CoroutineDispatcher,
    private val mealNtrIrdntDAO: MealNtrIrdntDAO,
    private val mealNtrIrdntMapper: MealNtrIrdntMapper
) : MealNtrIrdntRepository {
    override fun getMealNtrIrdntList(): Flow<List<MealNtrIrdntModel>> = flow {
        mealNtrIrdntDAO.getMealNtrIrdntList()
            .map { mealNtrIrdntEntityList ->

                mealNtrIrdntEntityList.map {
                    mealNtrIrdntMapper.toModel(it, ViewType.MEAL_NTR_IRDNT)
                }
            }
            .collect {
                emit(it)
            }
    }

    override suspend fun insertMealNtrIrdnt(mealNtrIrdntModel: MealNtrIrdntModel) =
        withContext(ioDispatcher) {
            val mealNtrIrdntEntity = mealNtrIrdntMapper.toEntity(mealNtrIrdntModel)
                .also {
                    DebugLog.i("$it")
                }

            mealNtrIrdntDAO.insert(mealNtrIrdntEntity)
        }
}
