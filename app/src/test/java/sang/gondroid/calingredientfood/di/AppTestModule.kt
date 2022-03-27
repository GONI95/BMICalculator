package sang.gondroid.calingredientfood.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import sang.gondroid.calingredientfood.data.repository.TestFoodNtrIrdntRepositoryImpl
import sang.gondroid.calingredientfood.domain.repository.FoodNtrIrdntRepository
import sang.gondroid.calingredientfood.domain.use_case.GetCustomFoodNtrIrdntListUseCase
import sang.gondroid.calingredientfood.domain.use_case.GetFoodNtrIrdntListUseCase
import sang.gondroid.calingredientfood.presentation.search.SearchViewModel

internal val appTestModule = module {

    // ViewMdoel
    viewModel { SearchViewModel(get(), get()) }

    // UseCase
    factory { GetFoodNtrIrdntListUseCase(get<FoodNtrIrdntRepository>()) }
    factory { GetCustomFoodNtrIrdntListUseCase(get<FoodNtrIrdntRepository>()) }

    // Repository
    single<FoodNtrIrdntRepository> { TestFoodNtrIrdntRepositoryImpl() }
}