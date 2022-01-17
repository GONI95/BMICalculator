package sang.gondroid.calingredientfood.data.dto.network


import com.google.gson.annotations.SerializedName

data class Body(
    @SerializedName("items")
    val networkItems: List<NetworkItem>?,
    @SerializedName("numOfRows")
    val numOfRows: Int,
    @SerializedName("pageNo")
    val pageNo: Int,
    @SerializedName("totalCount")
    val totalCount: Int
)