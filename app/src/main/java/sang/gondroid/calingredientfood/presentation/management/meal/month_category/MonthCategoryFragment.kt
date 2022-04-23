package sang.gondroid.calingredientfood.presentation.management.meal.month_category

import androidx.core.os.bundleOf
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import sang.gondroid.calingredientfood.databinding.FragmentMonthCategoryBinding
import sang.gondroid.calingredientfood.presentation.base.BaseFragment
import sang.gondroid.calingredientfood.presentation.util.DebugLog
import sang.gondroid.calingredientfood.presentation.util.MonthCategory

internal class MonthCategoryFragment :
    BaseFragment<FragmentMonthCategoryBinding, MonthCategoryViewModel>() {

    override val viewModel: MonthCategoryViewModel by viewModel {
        parametersOf(
            todoCategory,
            monthRange
        )
    }

    override fun getDataBinding(): FragmentMonthCategoryBinding =
        FragmentMonthCategoryBinding.inflate(layoutInflater)

    // Gon [22.04.22] : MealManagementActivity에서 newInstance()를 통해 전달한 category값을 Bundle로부터 가져옴
    private val todoCategory by lazy { arguments?.getSerializable(MONTH_CATEGORY_KEY) as MonthCategory }
    private val monthRange by lazy { arguments?.getSerializable(MONTH_RANGE_KEY) as Pair<String, String> }

    override fun initViews() {
        binding.viewModel = viewModel
    }

    override fun observeData() {}

    companion object {
        const val MONTH_CATEGORY_KEY = "MONTHCATEGORY"
        const val MONTH_RANGE_KEY = "MONTHRANGE"

        // Gon [22.04.22] : MonthCategoryFragment 인스턴스를 생성하면서 arguments에 데이터를 넘겨주는 코드
        fun newInstance(
            monthCategory: MonthCategory,
            monthRange: Pair<String, String>
        ) = MonthCategoryFragment().apply {
            arguments = bundleOf(
                MONTH_CATEGORY_KEY to monthCategory,
                MONTH_RANGE_KEY to monthRange
            )

            DebugLog.i("$arguments, $MONTH_CATEGORY_KEY, ${hashCode()}")
        }
    }
}
