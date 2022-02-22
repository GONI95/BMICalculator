package sang.gondroid.calingredientfood.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import sang.gondroid.calingredientfood.data.data_source.FoodNtrIrdntService
import sang.gondroid.calingredientfood.data.dto.network.NetworkItem
import sang.gondroid.calingredientfood.data.util.*
import sang.gondroid.calingredientfood.domain.mapper.ToFoodNtrIrdntModelMapper
import sang.gondroid.calingredientfood.domain.model.FoodNtrIrdntModel
import sang.gondroid.calingredientfood.domain.repository.FoodNtrIrdntRepository
import sang.gondroid.calingredientfood.presentation.util.DebugLog
import java.lang.Exception

/**
 * Gon [22.01.12] : Domain Layer의 FoodNtrIrdntRepository 구현체
 */
class FoodNtrIrdntRepositoryImpl(
    private val foodNtrIrdntService: FoodNtrIrdntService,
    private val ioDispatcher: CoroutineDispatcher,
    private val mapper: ToFoodNtrIrdntModelMapper
) : FoodNtrIrdntRepository {

    /**
     * Gon [22.02.22] : FoodNtrIrdntInfoService API에 매개변수에 해당하는 식품 영양성분 요청
     */
    override suspend fun getFoodNtrIrdnt(value: String): List<FoodNtrIrdntModel>? = withContext(ioDispatcher) {
        try {
            val result = foodNtrIrdntService.getFoodNtrItdnt(value)
            result.body()?.let { toDomainModelList(it.body.networkItems) }

        } catch (e : Exception) {
            null
        }
    }

    /**
     * Gon [22.01.17] : DTO(NetworkItem) -> Domain Model(FoodNtrIrdntModel)
     */
    private fun toDomainModelList(networkItemList: List<NetworkItem>?) : List<FoodNtrIrdntModel>
        = networkItemList?.map { mapper.map(it) } ?: emptyList()
}