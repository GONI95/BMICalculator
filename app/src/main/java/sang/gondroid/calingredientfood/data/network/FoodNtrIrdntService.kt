package sang.gondroid.calingredientfood.data.network

import com.google.gson.JsonElement
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import sang.gondroid.calingredientfood.data.url.API

interface FoodNtrIrdntService {
    @GET(API.GET_FNI_LIST)
    suspend fun getFoodNtrItdntList(
        @Query("desc_kor") descKor : String,
        @Query("bgn_year") bgnYear : String,
        @Query("ServiceKey") serviceKey : String,
        @Query("type") type : String
    ) : Response<JsonElement>
}