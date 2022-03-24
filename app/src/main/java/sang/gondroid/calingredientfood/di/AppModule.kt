package sang.gondroid.calingredientfood.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import sang.gondroid.calingredientfood.CalIngredientFoodApplication.Companion.dataStore
import sang.gondroid.calingredientfood.data.datastore.PreferencesDataStoreManager
import sang.gondroid.calingredientfood.data.repository.FoodNtrIrdntRepositoryImpl
import sang.gondroid.calingredientfood.domain.mapper.ToFoodNtrIrdntModelMapper
import sang.gondroid.calingredientfood.domain.repository.FoodNtrIrdntRepository
import sang.gondroid.calingredientfood.domain.use_case.GetFoodNtrIrdntListUseCase
import sang.gondroid.calingredientfood.presentation.search.SearchViewModel
import sang.gondroid.calingredientfood.presentation.calculator.CalculatorViewModel
import sang.gondroid.calingredientfood.presentation.insert.InsertFoodNtrIrdntViewModel
import sang.gondroid.calingredientfood.presentation.widget.adapter.MainViewPagerAdapter

internal val appModule = module {

    /**
     * viewModel
     */
    viewModel { SearchViewModel(get()) }
    viewModel { CalculatorViewModel() }
    viewModel { InsertFoodNtrIrdntViewModel(get()) }

    /**
     * UseCase : Repository를 받아 비즈니스 로직을 처리하는 부분, Interface 구현체
     */
    single { GetFoodNtrIrdntListUseCase(get()) }

    /**
     * Repository : Domain과 Data Layer 사이를 중재해주는 객체
     */
    single<FoodNtrIrdntRepository> { FoodNtrIrdntRepositoryImpl(get(), get(), get(named("toItemModelMapper"))) }

    /**
     * Mapper : Model <-> Dto
     */
    single(named("toItemModelMapper")) { ToFoodNtrIrdntModelMapper() }

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
     * Preferences DataStore : ProvideDataStore.kt
     */
    single { PreferencesDataStoreManager(getPreferencesDataStore(androidApplication())) }

    /**
     * Coroutine Dispatchers
     * Gon [22.01.12] : Coroutine을 Dispatcher에 전달하면 dispatcher가 자신이 관리하는 Thread Pool 내의 Thread에 분배
     */
    single { Dispatchers.IO }
    single { Dispatchers.Main }
}

private fun getPreferencesDataStore(context: Context): DataStore<Preferences> = context.dataStore