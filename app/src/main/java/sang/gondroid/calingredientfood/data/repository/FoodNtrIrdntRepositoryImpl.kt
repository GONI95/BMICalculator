package sang.gondroid.calingredientfood.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import sang.gondroid.calingredientfood.data.data_source.FoodNtrIrdntService
import sang.gondroid.calingredientfood.data.db.FoodNtrIrdntDAO
import sang.gondroid.calingredientfood.data.dto.network.NetworkItem
import sang.gondroid.calingredientfood.data.util.*
import sang.gondroid.calingredientfood.domain.mapper.FoodNtrIrdntMapper
import sang.gondroid.calingredientfood.domain.model.FoodNtrIrdntModel
import sang.gondroid.calingredientfood.domain.repository.FoodNtrIrdntRepository
import sang.gondroid.calingredientfood.domain.util.ViewType
import java.lang.Exception

/**
 * Gon [22.01.12] : Domain Layer의 FoodNtrIrdntRepository 구현체
 */
class FoodNtrIrdntRepositoryImpl(
    private val ioDispatcher: CoroutineDispatcher,
    private val foodNtrIrdntService: FoodNtrIrdntService,
    private val foodNtrIrdntDAO: FoodNtrIrdntDAO,
    private val foodNtrIrdntMapper: FoodNtrIrdntMapper,
) : FoodNtrIrdntRepository {

    /**
     * Gon [22.02.22] : FoodNtrIrdntInfoService API에 매개변수에 해당하는 식품 영양성분 요청
     */
    override suspend fun getFoodNtrIrdntList(value: String): List<FoodNtrIrdntModel>? =
        withContext(ioDispatcher) {
            try {
                val result = foodNtrIrdntService.getSearchList(value)
                result.body()?.body?.networkItems?.let { networkItems ->
                    toDomainModelList(networkItems)
                } ?: emptyList()

            } catch (e: Exception) {
                null
            }
        }

    override suspend fun getCustomFoodNtrIrdntList(value: String): List<FoodNtrIrdntModel> =
        withContext(ioDispatcher) {
            val result = foodNtrIrdntDAO.getSearchList(value)

            result.map { entity ->
                foodNtrIrdntMapper.toModel(entity, ViewType.FOOD_NTR_IRDNT)
            }
        }

    override suspend fun insertCustomFoodNtrIrdnt(foodNtrIrdntModel: FoodNtrIrdntModel) =
        withContext(ioDispatcher) {
            val foodNtrIrdntEntity = foodNtrIrdntMapper.toEntity(foodNtrIrdntModel)
            foodNtrIrdntDAO.insert(foodNtrIrdntEntity)
        }

    /**
     * Gon [22.01.17] : DTO(NetworkItem) -> Domain Model(FoodNtrIrdntModel)
     */
    private fun toDomainModelList(networkItemList: List<NetworkItem>): List<FoodNtrIrdntModel> =
        networkItemList.map { networkItem ->
            FoodNtrIrdntModel(
                hashCode().toLong(),
                ViewType.FOOD_NTR_IRDNT,
                networkItem.company,
                networkItem.beginYear,
                networkItem.descriptionKOR,
                networkItem.calorie.toDoubleOrZero(),
                networkItem.carbohydrate.toDoubleOrZero(),
                networkItem.protein.toDoubleOrZero(),
                networkItem.fat.toDoubleOrZero(),
                networkItem.sugar.toDoubleOrZero(),
                networkItem.salt.toDoubleOrZero(),
                networkItem.cholesterol.toDoubleOrZero(),
                networkItem.saturatedFattyAcid.toDoubleOrZero(),
                networkItem.transFat.toDoubleOrZero(),
                networkItem.servingWeight.toInt(),
                1
            )
        }
}