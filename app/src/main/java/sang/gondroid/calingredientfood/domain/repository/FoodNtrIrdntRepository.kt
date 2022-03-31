package sang.gondroid.calingredientfood.domain.repository

import sang.gondroid.calingredientfood.domain.model.FoodNtrIrdntModel

/**
 * Gon [22.01.12] : UseCase와 FoodNtrIrdntRepositoryImpl 사이를 중재해주는 Interface
 *                  GetFoodNtrIrdntListUseCase, InsertFoodNtrIrdntUseCase, GetCustomFoodNtrIrdntListUseCase
 *                  Domain / Data Layer 사이를 중재해주는 매개체 역할을 하는 Repository
 */
interface FoodNtrIrdntRepository {
    suspend fun getFoodNtrIrdntList(value: String) : List<FoodNtrIrdntModel>?

    suspend fun getCustomFoodNtrIrdntList(value: String): List<FoodNtrIrdntModel>

    suspend fun insertCustomFoodNtrIrdnt(foodNtrIrdntModel: FoodNtrIrdntModel)
}