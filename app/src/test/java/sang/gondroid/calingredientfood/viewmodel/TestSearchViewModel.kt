package sang.gondroid.calingredientfood.viewmodel

import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.koin.test.inject
import sang.gondroid.calingredientfood.presentation.search.SearchViewModel

/**
 * Gon [22.02.22] : SearchViewModel을 주입받아 테스트
 *                  @ExperimentalCoroutinesApi : Coroutine Test가 실험용 라이브러리이기 때문에 추가
 */
class TestSearchViewModel : ViewModelTest() {
    private val viewModel: SearchViewModel by inject()

    @Test
    fun `test viewModel searchFunc Success`() : Unit = runBlockingTest {
        val searchFunc = viewModel.searchFunc
        searchFunc("Success")
        println(viewModel.foodNtrIrdnrUIStateLiveData.getOrAwaitValue())
    }

    @Test
    fun `test viewModel searchFunc Fail`() : Unit = runBlockingTest {
        val searchFunc = viewModel.searchFunc
        searchFunc("Empty")
        println(viewModel.foodNtrIrdnrUIStateLiveData.getOrAwaitValue())
    }

    @Test
    fun `test viewModel searchFunc Error`() : Unit = runBlockingTest {
        val searchFunc = viewModel.searchFunc
        searchFunc("Exception")
        println(viewModel.foodNtrIrdnrUIStateLiveData.getOrAwaitValue())
    }
}