package sang.gondroid.calingredientfood.data.repository

import sang.gondroid.calingredientfood.domain.model.FoodNtrIrdntModel
import sang.gondroid.calingredientfood.domain.repository.FoodNtrIrdntRepository
import sang.gondroid.calingredientfood.domain.util.ViewType

class TestFoodNtrIrdntRepositoryImpl : FoodNtrIrdntRepository {
    private val foodList = (0 until 3).map {
        FoodNtrIrdntModel(
            it.toLong(),
            ViewType.FOOD_NTR_IRDNT,
            "Company $it",
            "2017",
            "Food $it",
            1.1,
            2.2,
            3.3,
            4.4,
            5.5,
            6.6,
            7.7,
            8.8,
            9.9,
            100,
            1
        )
    }

    override suspend fun getFoodNtrIrdnt(value: String) : List<FoodNtrIrdntModel>? {
        return when(value) {
            "Success" ->{ foodList }
            "Empty" -> { emptyList() }
            else -> { null }
        }
    }
}