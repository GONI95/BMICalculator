package sang.gondroid.calingredientfood.domain.use_case

import com.google.gson.JsonElement
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response
import sang.gondroid.calingredientfood.domain.repository.FoodNtrIrdntRepository

/**
 * Gon [22.01.12] : FoodNtrIrdntInfoService API에 매개변수에 해당하는 식품 영양성분 요청
 *                  FoodNtrIrdntRepository를 받아 개별 비즈니스 로직을 담당하는 UseCase
 */
class GetFoodNtrIrdntUseCase(
    private val foodNtrIrdntRepository: FoodNtrIrdntRepository,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(value: String): Response<JsonElement> = withContext(ioDispatcher) {
        foodNtrIrdntRepository.getFoodNtrIrdnt(value)
    }
    //매퍼
}