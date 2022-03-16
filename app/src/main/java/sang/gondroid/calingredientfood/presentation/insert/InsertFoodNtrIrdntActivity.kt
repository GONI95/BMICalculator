package sang.gondroid.calingredientfood.presentation.insert

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import org.koin.android.ext.android.inject
import sang.gondroid.calingredientfood.R
import sang.gondroid.calingredientfood.databinding.ActivityInsertFoodNtrIrdntBinding
import sang.gondroid.calingredientfood.presentation.base.BaseActivity

internal class InsertFoodNtrIrdntActivity : BaseActivity<ActivityInsertFoodNtrIrdntBinding, InsertFoodNtrIrdntViewModel>() {

    override val viewModel: InsertFoodNtrIrdntViewModel by inject()

    override fun getDataBinding(): ActivityInsertFoodNtrIrdntBinding =
        DataBindingUtil.setContentView(this, R.layout.activity_insert_food_ntr_irdnt)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getDataBinding()
    }

    override fun initViews() { }
}