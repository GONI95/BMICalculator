package sang.gondroid.calingredientfood.viewmodel

import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import sang.gondroid.calingredientfood.di.appTestModule
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

/**
 * Gon [22.02.22] : JUnit, Koin, Mockito, Arch core, Coroutine을 이용한 ViewModel 테스트
 *                  @ExperimentalCoroutinesApi : Coroutine Test가 실험용 라이브러리이기 때문에 추가
 */
@ExperimentalCoroutinesApi
abstract class ViewModelTest : KoinTest {

    // Gon [22.02.22] : 편리한 Thread 변경을 위해 Dispatcher 정의
    private val dispatcher = TestCoroutineDispatcher()

    // Gon [22.02.22] : Mockito를 시작하는 Rule 인스턴스 생성 / @Rule : Test에 필요한 사전 작업 정의
    @get:Rule
    val mockitoRuule: MockitoRule = MockitoJUnit.rule()

    /**
     * Gon [22.02.22] : 동기화를 신경쓰지 않을 수 있도록 background 작업과 연관된 모든 아키텍처 컴포넌트들을
     *                  하나의 Thread에서 실행해 테스트들이 동기적으로 실행되도록 설정
     *
     *                  LiveData Test 시 필수 / @Rule : Test에 필요한 사전 작업 정의
     */
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    // Gon [22.02.22] : ApplicationContext를 사용하기 위해 정의 / @Mock : Mockito를 이용해 모의 객체 생성
    @Mock
    private lateinit var context: Application

    /**
     * Gon [22.02.22] : 테스트 전 Coroutine Dispatchers, Koin 설정
     *                  @Before : 각 테스트 실행 전 호출, 테스트 환경을 준비하는데 사용
     */
    @Before
    fun setup() {
        startKoin {
            androidContext(context)
            modules(appTestModule)
        }
        Dispatchers.setMain(dispatcher)
    }

    /**
     * Gon [22.02.22] : 테스트 후 Coroutine Dispatchers, Koin 메모리에서 해제 (메모리 누수 방지)
     *                  @After : 각 테스트 실행 후 호출, 테스트 환경을 정리하는데 사용
     */
    @After
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain()
    }

    // Gon [22.02.22] : LiveData를 바로 사용할 수 없음, LiveData를 관찰하도록 구현한 LiveData 확장 함수
    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    protected fun <T> LiveData<T>.getOrAwaitValue(
        time: Long = 1,
        timeUnit: TimeUnit = TimeUnit.SECONDS,
        afterObserve: () -> Unit = {}
    ): T {
        var data: T? = null
        val latch = CountDownLatch(1)
        val observer = object : Observer<T> {
            override fun onChanged(o: T?) {
                data = o
                latch.countDown()
                this@getOrAwaitValue.removeObserver(this)
            }
        }
        this.observeForever(observer)

        try {
            afterObserve.invoke()

            // Don't wait indefinitely if the LiveData is not set.
            if (!latch.await(time, timeUnit)) {
                throw TimeoutException("LiveData value was never set.")
            }
        } finally {
            this.removeObserver(observer)
        }

        @Suppress("UNCHECKED_CAST")
        return data as T
    }
}
