package sang.gondroid.calingredientfood.domain.model

import android.net.Uri
import sang.gondroid.calingredientfood.domain.util.ViewType

class MealNtrIrdntModel(
    override val id: Long,
    override val type: ViewType,
    val mealImage: Uri?,
    val currentDate: String,
    val foodNtrIrdntList: List<FoodNtrIrdntModel>,
    val totalCalorie: Double,
    val totalCarbohydrate: Double,
    val totalProtein: Double,
    val totalFat: Double,
    val totalSugar: Double,
    val totalSalt: Double,
    val totalCholesterol: Double,
    val totalSaturatedFattyAcid: Double,
    val totalTransFat: Double
) : Model(id, type)
