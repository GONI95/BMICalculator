package sang.gondroid.calingredientfood.domain.use_case

import sang.gondroid.calingredientfood.domain.model.FoodNtrIrdntModel
import sang.gondroid.calingredientfood.domain.repository.FoodNtrIrdntRepository

/**
 * Gon [22.01.12] : FoodNtrIrdntInfoService API에 매개변수에 해당하는 식품 영양성분 요청
 *                  FoodNtrIrdntRepository를 받아 개별 비즈니스 로직을 담당하는 UseCase
 */
class GetFoodNtrIrdntListUseCase(
    private val foodNtrIrdntRepository: FoodNtrIrdntRepository
) {
    suspend fun invoke(value: String): List<FoodNtrIrdntModel>? =
        foodNtrIrdntRepository.getFoodNtrIrdntList(value)
}
