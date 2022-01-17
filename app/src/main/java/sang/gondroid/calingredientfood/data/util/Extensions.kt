package sang.gondroid.calingredientfood.data.util

import retrofit2.Response

/**
 * Gon [22.01.17] : Response.Body 유무에 따라 고차함수 getData를 이용해 TaskResult를 반환하는 Response<Type> 확장 함수
 */
fun <T, R> Response<T>.toTaskResult(getData: (T) -> R) : TaskResult<R> {
    return body()?.let { TaskResult.Success(getData(it)) } ?: TaskResult.Fail
}
