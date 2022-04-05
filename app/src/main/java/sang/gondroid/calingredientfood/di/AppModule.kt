package sang.gondroid.calingredientfood.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import sang.gondroid.calingredientfood.CalIngredientFoodApplication.Companion.dataStore
import sang.gondroid.calingredientfood.data.datastore.PreferencesDataStoreManager
import sang.gondroid.calingredientfood.data.repository.FoodNtrIrdntRepositoryImpl
import sang.gondroid.calingredientfood.data.repository.MealNtrIrdntRepositoryImpl
import sang.gondroid.calingredientfood.domain.mapper.FoodNtrIrdntMapper
import sang.gondroid.calingredientfood.domain.mapper.MealNtrIrdntMapper
import sang.gondroid.calingredientfood.domain.repository.FoodNtrIrdntRepository
import sang.gondroid.calingredientfood.domain.repository.MealNtrIrdntRepository
import sang.gondroid.calingredientfood.domain.use_case.GetMealNtrIrdntListUseCase
import sang.gondroid.calingredientfood.domain.use_case.GetFoodNtrIrdntListUseCase
import sang.gondroid.calingredientfood.domain.use_case.GetCustomFoodNtrIrdntListUseCase
import sang.gondroid.calingredientfood.domain.use_case.InsertMealNtrIrdntUseCase
import sang.gondroid.calingredientfood.domain.use_case.InsertCustomFoodNtrIrdntUseCase
import sang.gondroid.calingredientfood.presentation.search.SearchViewModel
import sang.gondroid.calingredientfood.presentation.meal.MealViewModel
import sang.gondroid.calingredientfood.presentation.insert.InsertFoodNtrIrdntViewModel
import sang.gondroid.calingredientfood.presentation.management.ManagementViewModel

internal val appModule = module {

    /**
     * viewModel
     */
    viewModel { SearchViewModel(get(), get()) }
    viewModel { MealViewModel(get()) }
    viewModel { InsertFoodNtrIrdntViewModel(get(), get()) }
    viewModel { ManagementViewModel() }

    /**
     * UseCase : Repository를 받아 비즈니스 로직을 처리하는 부분, Interface 구현체
     */
    factory { GetFoodNtrIrdntListUseCase(get()) }
    factory { GetCustomFoodNtrIrdntListUseCase(get()) }
    factory { InsertCustomFoodNtrIrdntUseCase(get()) }
    factory { InsertMealNtrIrdntUseCase(get()) }
    factory { GetMealNtrIrdntListUseCase(get()) }

    /**
     * Repository : Domain과 Data Layer 사이를 중재해주는 객체
     */
    single<FoodNtrIrdntRepository> { FoodNtrIrdntRepositoryImpl(get(), get(), get(), get()) }
    single<MealNtrIrdntRepository> { MealNtrIrdntRepositoryImpl(get(), get(), get()) }

    /**
     * Mapper : Model <-> Dto
     */
    single { FoodNtrIrdntMapper() }
    single { MealNtrIrdntMapper(get()) }

    /**
     * Network : ProvideAPI.kt
     */
    single { provideGsonConverterFactory() }
    single { buildOkHttpClient() }
    single { provideFoodNtrIrdntRetrofit(get(), get()) }
    single { provideFoodNtrIrdntService(get()) }

    /**
     * Preferences DataStore : ProvideDataStore.kt
     */
    single { PreferencesDataStoreManager(getPreferencesDataStore(androidApplication())) }

    /**
     * Room
     */
    single { provideDB(androidApplication()) }
    single { provideFoodNtrIrdntDao(get()) }
    single { provideMealNtrIrdntDao(get()) }

    /**
     * Coroutine Dispatchers
     * Gon [22.01.12] : Coroutine을 Dispatcher에 전달하면 dispatcher가 자신이 관리하는 Thread Pool 내의 Thread에 분배
     */
    single { Dispatchers.IO }
    single { Dispatchers.Main }
}

private fun getPreferencesDataStore(context: Context): DataStore<Preferences> = context.dataStore
