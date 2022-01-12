package sang.gondroid.calingredientfood.data.data_source

import com.google.gson.JsonElement
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import sang.gondroid.calingredientfood.data.util.API

/**
 * Gon [22.01.12] : HTTP 작업을 정의하는 API Interface [식품 영양성분 DB 서비스]
 */
interface FoodNtrIrdntService {
    @GET(API.GET_FNI_LIST)
    suspend fun getFoodNtrItdntList(
        @Query("desc_kor") descKor : String
    ) : Response<JsonElement>
}