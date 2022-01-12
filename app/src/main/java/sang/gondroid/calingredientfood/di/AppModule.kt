package sang.gondroid.calingredientfood.di

import kotlinx.coroutines.Dispatchers
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import sang.gondroid.calingredientfood.data.repository.FoodNtrIrdntRepositoryImpl
import sang.gondroid.calingredientfood.domain.repository.FoodNtrIrdntRepository
import sang.gondroid.calingredientfood.domain.use_case.GetFoodNtrIrdntUseCase
import sang.gondroid.calingredientfood.presentation.calculator.CalculatorViewModel
import sang.gondroid.calingredientfood.presentation.diet.DietViewModel
import sang.gondroid.calingredientfood.presentation.widget.MainViewPagerAdapter

val appModule = module {

    /**
     * viewModel
     */
    viewModel { CalculatorViewModel(get()) }
    viewModel { DietViewModel() }

    /**
     * UseCase : Repository를 받아 비즈니스 로직을 처리하는 부분, Interface 구현체
     */
    single { GetFoodNtrIrdntUseCase(get(), get()) }

    /**
     * Repository : Domain과 Data Layer 사이를 중재해주는 객체
     */
    single<FoodNtrIrdntRepository> { FoodNtrIrdntRepositoryImpl(get(), get()) }

    /**
     * Adapter
     */
    factory { MainViewPagerAdapter(it[0], it[1]) }

    /**
     * Network : ProvideAPI.kt
     */
    single { provideGsonConverterFactory() }
    single { buildOkHttpClient() }
    single { provideFoodNtrIrdntRetrofit(get(), get()) }
    single { provideFoodNtrIrdntService(get()) }

    /**
     * Coroutine Dispatchers
     * Gon [22.01.12] : Coroutine을 Dispatcher에 전달하면 dispatcher가 자신이 관리하는 Thread Pool 내의 Thread에 분배
     */
    single { Dispatchers.IO }
    single { Dispatchers.Main }
}