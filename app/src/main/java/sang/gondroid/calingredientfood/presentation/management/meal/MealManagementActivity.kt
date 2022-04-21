package sang.gondroid.calingredientfood.presentation.management.meal

import androidx.databinding.DataBindingUtil
import org.koin.android.ext.android.inject
import sang.gondroid.calingredientfood.R
import sang.gondroid.calingredientfood.databinding.ActivityMealManagementBinding
import sang.gondroid.calingredientfood.presentation.base.BaseActivity

internal class MealManagementActivity :
    BaseActivity<ActivityMealManagementBinding, MealManagementViewModel>() {

    override val viewModel: MealManagementViewModel by inject()

    override fun getDataBinding(): ActivityMealManagementBinding =
        DataBindingUtil.setContentView(this, R.layout.activity_meal_management)

    override fun initState() {}

    override fun observeData() {}
}
