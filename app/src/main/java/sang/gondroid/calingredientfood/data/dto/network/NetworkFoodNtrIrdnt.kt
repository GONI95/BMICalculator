package sang.gondroid.calingredientfood.data.dto.network


import com.google.gson.annotations.SerializedName

/**
 * Gon [22.01.17] : API 응답인 JSON Element를 Kotlin Object로 변환한 NetworkFoodNtrIrdnt data class
 */
data class NetworkFoodNtrIrdnt(
    @SerializedName("body")
    val body: Body,
    @SerializedName("header")
    val header: Header
)