package sang.gondroid.calingredientfood.domain.model

import sang.gondroid.calingredientfood.domain.util.ViewType

data class FoodNtrIrdntModel(
    override val id: Long,
    override val type: ViewType,
    val company: String,
    val beginYear: String,
    val descriptionKOR: String,
    val calorie: Double,
    val carbohydrate: Double,
    val protein: Double,
    val fat: Double,
    val sugar: Double,
    val salt: Double,
    val cholesterol: Double,
    val saturatedFattyAcid: Double,
    val transFat: Double,
    val servingWeight: Int,
    val servingCount: Int
) : Model(id, type)