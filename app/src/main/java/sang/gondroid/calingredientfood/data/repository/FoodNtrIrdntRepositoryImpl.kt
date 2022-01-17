package sang.gondroid.calingredientfood.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import sang.gondroid.calingredientfood.data.data_source.FoodNtrIrdntService
import sang.gondroid.calingredientfood.data.dto.network.NetworkItem
import sang.gondroid.calingredientfood.data.util.*
import sang.gondroid.calingredientfood.domain.mapper.ToItemModelMapper
import sang.gondroid.calingredientfood.domain.model.ItemModel
import sang.gondroid.calingredientfood.domain.repository.FoodNtrIrdntRepository
import java.lang.Exception

/**
 * Gon [22.01.12] : Domain Layer의 FoodNtrIrdntRepository 구현체
 */
class FoodNtrIrdntRepositoryImpl(
    private val foodNtrIrdntService: FoodNtrIrdntService,
    private val ioDispatcher: CoroutineDispatcher,
    private val mapper: ToItemModelMapper
) : FoodNtrIrdntRepository {

    /**
     * Gon [22.01.17] : FoodNtrIrdntInfoService API에 매개변수에 해당하는 식품 영양성분 요청
     */
    override suspend fun getFoodNtrIrdnt(value: String): TaskResult<*> = withContext(ioDispatcher) {
        try {
            foodNtrIrdntService.getFoodNtrItdnt(value).toTaskResult { toDomainModelList(it.body.networkItems) }
        } catch (e : Exception) {
            TaskResult.Exception(e)
        }
    }

    /**
     * Gon [22.01.17] : DTO(Item) -> Domain Model(ItemModel)
     */
    private fun toDomainModelList(networkItemList: List<NetworkItem>?) : List<ItemModel>
        = networkItemList?.map { mapper.map(it) } ?: emptyList()
}