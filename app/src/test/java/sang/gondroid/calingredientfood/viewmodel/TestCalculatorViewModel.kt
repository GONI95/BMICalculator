package sang.gondroid.calingredientfood.viewmodel

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.koin.test.inject
import sang.gondroid.calingredientfood.R
import sang.gondroid.calingredientfood.domain.use_case.GetMealNtrIrdntListUseCase
import sang.gondroid.calingredientfood.presentation.meal.MealViewModel

@ExperimentalCoroutinesApi
class TestCalculatorViewModel : ViewModelTest() {
    private val viewModel: MealViewModel by inject()
    private val getMealListIrdntUseCase: GetMealNtrIrdntListUseCase by inject()

    @Before
    fun `test viewModel insertMeal`() = runBlockingTest {
        val onMenuItemClick = viewModel.onMenuItemClick
        onMenuItemClick(R.id.save_menu_item)
    }

    @Test
    fun `test getMealListIrdntUseCase Result`() = runBlockingTest {
        getMealListIrdntUseCase.invoke().collect { mealNtrIrdntModelList ->
            assert(mealNtrIrdntModelList.isNotEmpty())

            mealNtrIrdntModelList.forEach {
                println("id : ${it.id}")
            }
        }
    }
}