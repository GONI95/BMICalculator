package sang.gondroid.calingredientfood.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import sang.gondroid.calingredientfood.data.repository.TestFoodNtrIrdntRepositoryImpl
import sang.gondroid.calingredientfood.data.repository.TestMealNtrIrdntRepositoryImpl
import sang.gondroid.calingredientfood.domain.mapper.FoodNtrIrdntMapper
import sang.gondroid.calingredientfood.domain.mapper.MealNtrIrdntMapper
import sang.gondroid.calingredientfood.domain.repository.FoodNtrIrdntRepository
import sang.gondroid.calingredientfood.domain.repository.MealNtrIrdntRepository
import sang.gondroid.calingredientfood.domain.use_case.GetCustomFoodNtrIrdntListUseCase
import sang.gondroid.calingredientfood.domain.use_case.GetFoodNtrIrdntListUseCase
import sang.gondroid.calingredientfood.domain.use_case.GetMealNtrIrdntListUseCase
import sang.gondroid.calingredientfood.domain.use_case.InsertMealNtrIrdntUseCase
import sang.gondroid.calingredientfood.presentation.meal.MealViewModel
import sang.gondroid.calingredientfood.presentation.search.SearchViewModel

internal val appTestModule = module {

    // ViewMdoel
    viewModel { SearchViewModel(get(), get()) }
    viewModel { MealViewModel(get()) }

    // UseCase
    factory { GetFoodNtrIrdntListUseCase(get<FoodNtrIrdntRepository>()) }
    factory { GetCustomFoodNtrIrdntListUseCase(get<FoodNtrIrdntRepository>()) }

    factory { GetMealNtrIrdntListUseCase(get<MealNtrIrdntRepository>()) }
    factory { InsertMealNtrIrdntUseCase(get<MealNtrIrdntRepository>()) }

    // Mapper
    single { MealNtrIrdntMapper(get()) }
    single { FoodNtrIrdntMapper() }

    // Repository
    single<FoodNtrIrdntRepository> { TestFoodNtrIrdntRepositoryImpl() }
    single<MealNtrIrdntRepository> { TestMealNtrIrdntRepositoryImpl(get()) }
}
