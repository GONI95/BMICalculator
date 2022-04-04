package sang.gondroid.calingredientfood.presentation.insert

import android.content.Intent
import androidx.databinding.DataBindingUtil
import org.koin.android.ext.android.inject
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import sang.gondroid.calingredientfood.R
import sang.gondroid.calingredientfood.databinding.ActivityInsertFoodNtrIrdntBinding
import sang.gondroid.calingredientfood.domain.model.FoodNtrIrdntModel
import sang.gondroid.calingredientfood.presentation.base.BaseActivity
import sang.gondroid.calingredientfood.presentation.meal.MealFragment
import sang.gondroid.calingredientfood.presentation.util.Constants
import sang.gondroid.calingredientfood.presentation.insert.InsertFoodNtrIrdntViewModel.Event

internal class InsertFoodNtrIrdntActivity : BaseActivity<ActivityInsertFoodNtrIrdntBinding, InsertFoodNtrIrdntViewModel>() {

    override val viewModel: InsertFoodNtrIrdntViewModel by inject()

    override fun getDataBinding(): ActivityInsertFoodNtrIrdntBinding =
        DataBindingUtil.setContentView(this, R.layout.activity_insert_food_ntr_irdnt)

    override fun initState() {
        super.initState()

        binding.handler = this
        binding.viewModel = viewModel
    }

    override fun observeData() {
        lifecycleScope.launch {
            viewModel.event.collect { event ->
                when(event) {
                    is Event.SetResult -> {
                        setResult(event.data)
                    }
                }
            }
        }
    }

    private fun setResult(data: FoodNtrIrdntModel) {
        val intent = Intent(this, MealFragment::class.java)
        intent.putExtra(Constants.FOOD_NTR_IRDNT_MODEL_KEY, data)
        setResult(RESULT_OK, intent)

        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.saveInputTextData()
    }
}