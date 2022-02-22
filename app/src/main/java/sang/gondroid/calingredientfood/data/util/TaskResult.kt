package sang.gondroid.calingredientfood.data.util

/**
 * Gon [22.01.17] : Task 처리에 대한 반환 Type으로 사용될 TaskResult
 *                  sealed class : 상위 클래스를 상속하는 하위 클래스 정의를 제한
 *                  generic : Data Type에 의존하지 않고, 하나의 값이 여러 다른 Data Type을 가질 수 있게하는 방법
 */
sealed class TaskResult<out T> {
    object Loading : TaskResult<Nothing>()
    data class Success<T>(val data : T) : TaskResult<T>()
    object Fail : TaskResult<Nothing>()
    data class Exception(val throwable: Throwable) : TaskResult<Nothing>()
}