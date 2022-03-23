package sang.gondroid.calingredientfood.presentation.insert

import android.content.Intent
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import org.koin.android.ext.android.inject
import sang.gondroid.calingredientfood.R
import sang.gondroid.calingredientfood.databinding.ActivityInsertFoodNtrIrdntBinding
import sang.gondroid.calingredientfood.domain.model.FoodNtrIrdntModel
import sang.gondroid.calingredientfood.domain.util.ViewType
import sang.gondroid.calingredientfood.presentation.base.BaseActivity
import sang.gondroid.calingredientfood.presentation.calculator.CalculatorFragment
import sang.gondroid.calingredientfood.presentation.util.Constants
import java.util.*

internal class InsertFoodNtrIrdntActivity : BaseActivity<ActivityInsertFoodNtrIrdntBinding, InsertFoodNtrIrdntViewModel>() {

    override val viewModel: InsertFoodNtrIrdntViewModel by inject()

    override fun getDataBinding(): ActivityInsertFoodNtrIrdntBinding =
        DataBindingUtil.setContentView(this, R.layout.activity_insert_food_ntr_irdnt)

    override fun initState() {
        super.initState()

        binding.handler = this
    }

    override fun initViews() {
        viewModel.foodNtrIrdntDataLiveData.observe(this, Observer { map ->
            binding.servingWeightEditText.setText(map[Constants.SERVING_WEIGHT_KEY])
            binding.descriptionKorEditText.setText(map[Constants.DESCRIPTION_KOR_KEY])
            binding.companyEditText.setText(map[Constants.COMPANY_KEY])
            binding.calorieEditText.setText(map[Constants.CALORIE_KEY])
            binding.carbohydrateEditText.setText(map[Constants.CARBOHYDRATE_KEY])
            binding.proteinEditText.setText(map[Constants.PROTEIN_KEY])
            binding.fatEditText.setText(map[Constants.FAT_KEY])
        })
    }

    fun addFoodNtrIrdnt() {
        val inputTextArray = getInputTextArray()

        val intent = Intent(this, CalculatorFragment::class.java)
        intent.putExtra(
            Constants.FOOD_NTR_IRDNT_MODEL_KEY,
            FoodNtrIrdntModel(
                id = hashCode().toLong(),
                type = ViewType.CALCULATOR,
                servingWeight = inputTextArray[0].toInt(),
                descriptionKOR = inputTextArray[1],
                company = inputTextArray[2],
                calorie = inputTextArray[3].toDouble(),
                carbohydrate = inputTextArray[4].toDouble(),
                protein = inputTextArray[5].toDouble(),
                fat = inputTextArray[6].toDouble(),
                beginYear = Calendar.YEAR.toString(),
                sugar = 0.00,
                salt = 0.00,
                cholesterol = 0.00,
                saturatedFattyAcid = 0.00,
                transFat = 0.00,
                servingCount = 1
                )
        )
        setResult(RESULT_OK, intent)

        viewModel.removeFoodNtrIrdntData()
        finish()
    }

    private fun getInputTextArray() = with(binding) {
        arrayOf(
            servingWeightEditText.getText(),
            descriptionKorEditText.getText(),
            companyEditText.getText(),
            calorieEditText.getText(),
            carbohydrateEditText.getText(),
            proteinEditText.getText(),
            fatEditText.getText()
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.saveFoodNtrIrdntData(getInputTextArray())
    }
}