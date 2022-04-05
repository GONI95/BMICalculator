package sang.gondroid.calingredientfood.data.dto.network

import com.google.gson.annotations.SerializedName

data class NetworkItem(
    @SerializedName("ANIMAL_PLANT")
    val company: String,
    @SerializedName("BGN_YEAR")
    val beginYear: String,
    @SerializedName("DESC_KOR")
    val descriptionKOR: String,
    @SerializedName("NUTR_CONT1")
    val calorie: String,
    @SerializedName("NUTR_CONT2")
    val carbohydrate: String,
    @SerializedName("NUTR_CONT3")
    val protein: String,
    @SerializedName("NUTR_CONT4")
    val fat: String,
    @SerializedName("NUTR_CONT5")
    val sugar: String,
    @SerializedName("NUTR_CONT6")
    val salt: String,
    @SerializedName("NUTR_CONT7")
    val cholesterol: String,
    @SerializedName("NUTR_CONT8")
    val saturatedFattyAcid: String,
    @SerializedName("NUTR_CONT9")
    val transFat: String,
    @SerializedName("SERVING_WT")
    val servingWeight: String
)
