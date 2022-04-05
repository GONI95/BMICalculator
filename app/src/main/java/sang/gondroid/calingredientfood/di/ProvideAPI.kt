package sang.gondroid.calingredientfood.di

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import sang.gondroid.calingredientfood.BuildConfig
import sang.gondroid.calingredientfood.data.data_source.FoodNtrIrdntService
import sang.gondroid.calingredientfood.data.util.API
import java.net.URLDecoder
import java.util.concurrent.TimeUnit

/**
 * Gon [22.01.11] : Retrofi2 API 서비스를 반환하는 메서드
 * FoodNtrIrdnt(Food nutrition ingredient)
 */
fun provideFoodNtrIrdntService(retrofit: Retrofit): FoodNtrIrdntService {
    return retrofit.create(FoodNtrIrdntService::class.java)
}

/**
 * Gon [22.01.11] : Retrofit.Build를 통해 Retrofit 인스턴스를 생성하는 메서드
 *                  Retrofit의 Parameter/Query 매핑작업, 처리작업 이점
 * FoodNtrIrdnt(Food nutrition ingredient)
 */
fun provideFoodNtrIrdntRetrofit(
    okHttpClient: OkHttpClient,
    gsonConverterFactory: GsonConverterFactory
): Retrofit {

    return Retrofit.Builder()
        .baseUrl(API.FNI_URL)
        .client(okHttpClient)
        .addConverterFactory(gsonConverterFactory)
        .build()
}

/**
 * Gon [22.01.11] : JSON을 직렬화 하기 위해 Gson converter가 필요
 *                  직렬화 : 객체를 바이트 단위로 변환하여 파일 또는 네트워크 통해 Stream(송수신)이 가능하도록 하는 것
 */
fun provideGsonConverterFactory(): GsonConverterFactory {
    return GsonConverterFactory.create()
}

/**
 * Gon [22.01.12] : OkHttp.Build를 통해 OkHttp 인스턴스를 생성하는 메서드
 *                  OkHttp Client의 Intercepter를 통한 활동 모니터링, 커넥션 타임아웃 등의 이점
 */
fun buildOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    }

    val interceptor = Interceptor { chain ->
        with(chain.request()) {
            /** Gon [22.01.12] : inctercept()의 응답으로 온 chain 객체를 이용해 공통 파라미터 추가 */
            val addClientIdUrl = url.newBuilder()
                .addQueryParameter("serviceKey", URLDecoder.decode(API.FNI_KEY, "UTF-8"))
                .addQueryParameter("bgn_year", API.FNI_BGN_YEAR)
                .addQueryParameter("type", API.FNI_TYPE)
                .build()

            /** Gon [22.01.12] : Request 재정의(공통 헤더 추가 가능) */
            val finalRequest = newBuilder()
                .url(addClientIdUrl)
                .method(method, body)
                .build()

            /** Gon [22.01.12] : proceed() 메서드를 이용해 서버와 통신 후 응답받기 */
            chain.proceed(finalRequest)
        }
    }

    return OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .addInterceptor(interceptor)
        .addInterceptor(httpLoggingInterceptor)
        .build()
}
