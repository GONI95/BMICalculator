package sang.gondroid.calingredientfood.domain.model

import sang.gondroid.calingredientfood.domain.util.ViewType

data class FoodNtrIrdntModel(
    override val id: Long,
    override val type: ViewType,
    val company: String,
    val beginYear: String,
    val descriptionKOR: String,
    val calorie: String,
    val carbohydrate: String,
    val protein: String,
    val fat: String,
    val sugar: String,
    val salt: String,
    val cholesterol: String,
    val saturatedFattyAcid: String,
    val transFat: String,
    val servingWeight: String
) : Model(id, type)