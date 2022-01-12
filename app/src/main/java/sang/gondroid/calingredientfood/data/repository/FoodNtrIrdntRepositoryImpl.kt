package sang.gondroid.calingredientfood.data.repository

import com.google.gson.JsonElement
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response
import sang.gondroid.calingredientfood.data.data_source.FoodNtrIrdntService
import sang.gondroid.calingredientfood.domain.repository.FoodNtrIrdntRepository

/**
 * Gon [22.01.12] : FoodNtrIrdntInfoService API에 매개변수에 해당하는 식품 영양성분 요청
 *                  Domain Layer의 FoodNtrIrdntRepository 구현체
 */
class FoodNtrIrdntRepositoryImpl(
    private val foodNtrIrdntService: FoodNtrIrdntService,
    private val ioDispatcher : CoroutineDispatcher
) : FoodNtrIrdntRepository {
    override suspend fun getFoodNtrIrdnt(value: String): Response<JsonElement> = withContext(ioDispatcher) {
        foodNtrIrdntService.getFoodNtrItdntList(value)
    }
}