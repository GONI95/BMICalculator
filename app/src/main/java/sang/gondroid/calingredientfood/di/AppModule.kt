package sang.gondroid.calingredientfood.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import sang.gondroid.calingredientfood.presentation.calculator.CalculatorViewModel
import sang.gondroid.calingredientfood.presentation.diet.DietViewModel

val appModule = module {

    viewModel { CalculatorViewModel() }
    viewModel { DietViewModel() }
}