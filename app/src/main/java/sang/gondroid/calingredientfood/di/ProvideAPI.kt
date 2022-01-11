package sang.gondroid.calingredientfood.di

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import sang.gondroid.calingredientfood.BuildConfig
import sang.gondroid.calingredientfood.data.network.FoodNtrIrdntService
import sang.gondroid.calingredientfood.data.url.API
import java.util.concurrent.TimeUnit

/**
 * Gon [22.01.11] : JSON을 직렬화 하기 위해 Gson converter가 필요
 *                  직렬화 : 객체를 바이트 단위로 변환하여 파일 또는 네트워크 통해 Stream(송수신)이 가능하도록 하는 것
 */
fun provideGsonConverterFactory(): GsonConverterFactory {
    return GsonConverterFactory.create()
}

/**
 * Gon [22.01.11] : OkHttp.Build를 통해 OkHttp 인스턴스를 생성하는 메서드
 *                  OkHttp Client의 Intercepter를 통한 활동 모니터링, 커넥션 타임아웃 등의 이점
 */
fun buildOkHttpClient(): OkHttpClient {
    val interceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    }

    return OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .addInterceptor(interceptor)
        .build()
}

/**
 * Gon [22.01.11] : Retrofit.Build를 통해 Retrofit 인스턴스를 생성하는 메서드
 *                  Retrofit의 Parameter/Query 매핑작업, 처리작업 이점
 * FoodNtrIrdnt(Food nutrition ingredient)
 */
fun provideFoodNtrIrdntRetrofit(
    gsonConverterFactory: GsonConverterFactory,
    okHttpClient: OkHttpClient
): Retrofit {

    return Retrofit.Builder()
        .baseUrl(API.FNI_URL)
        .addConverterFactory(gsonConverterFactory)
        .client(okHttpClient)
        .build()
}

/**
 * Gon [22.01.11] : Retrofi2 API 서비스를 반환하는 메서드
 * FoodNtrIrdnt(Food nutrition ingredient)
 */
fun provideFoodNtrIrdntService(retrofit: Retrofit) {
    retrofit.create(FoodNtrIrdntService::class.java)
}