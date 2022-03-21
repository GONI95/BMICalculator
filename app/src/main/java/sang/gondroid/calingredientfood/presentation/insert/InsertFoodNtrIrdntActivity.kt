package sang.gondroid.calingredientfood.presentation.insert

import android.content.Intent
import androidx.databinding.DataBindingUtil
import org.koin.android.ext.android.inject
import sang.gondroid.calingredientfood.R
import sang.gondroid.calingredientfood.databinding.ActivityInsertFoodNtrIrdntBinding
import sang.gondroid.calingredientfood.domain.model.FoodNtrIrdntModel
import sang.gondroid.calingredientfood.domain.util.ViewType
import sang.gondroid.calingredientfood.presentation.base.BaseActivity
import sang.gondroid.calingredientfood.presentation.calculator.CalculatorFragment
import sang.gondroid.calingredientfood.presentation.util.Constants

internal class InsertFoodNtrIrdntActivity : BaseActivity<ActivityInsertFoodNtrIrdntBinding, InsertFoodNtrIrdntViewModel>() {

    override val viewModel: InsertFoodNtrIrdntViewModel by inject()

    override fun getDataBinding(): ActivityInsertFoodNtrIrdntBinding =
        DataBindingUtil.setContentView(this, R.layout.activity_insert_food_ntr_irdnt)

    override fun initState() {
        super.initState()

        binding.handler = this
    }

    override fun initViews() { }

    fun addFoodNtrIrdnt() {
        val intent = Intent(this, CalculatorFragment::class.java)
        intent.putExtra(Constants.FOOD_NTR_IRDNT_MODEL_KEY, FoodNtrIrdntModel(1L,ViewType.FOOD_NTR_IRDNT,"COMPANY", "2017", "FOOD", 1.1, 1.1, 1.1, 1.1, 1.1, 1.1, 1.1, 1.1, 1.1,1 ,1))
        setResult(RESULT_OK, intent)
        finish()
    }
}