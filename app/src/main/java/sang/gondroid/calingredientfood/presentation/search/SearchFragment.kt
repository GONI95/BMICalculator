package sang.gondroid.calingredientfood.presentation.search

import org.koin.android.viewmodel.ext.android.viewModel
import sang.gondroid.calingredientfood.R
import sang.gondroid.calingredientfood.databinding.FragmentSearchBinding
import sang.gondroid.calingredientfood.domain.model.FoodNtrIrdntModel
import sang.gondroid.calingredientfood.presentation.base.BaseFragment
import sang.gondroid.calingredientfood.presentation.util.Constants
import sang.gondroid.calingredientfood.presentation.widget.adapter.BaseRecyclerViewAdapter
import sang.gondroid.calingredientfood.presentation.widget.custom.FoodNtrIrdntBottomSheet
import sang.gondroid.calingredientfood.presentation.widget.custom.NotificationSnackBar
import sang.gondroid.calingredientfood.presentation.widget.listener.CalculatorListener
import sang.gondroid.calingredientfood.presentation.widget.listener.FoodNtrIrdntListener

internal class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>() {
    override val viewModel: SearchViewModel by viewModel()

    override fun getDataBinding(): FragmentSearchBinding =
        FragmentSearchBinding.inflate(layoutInflater)


    /**
     * Gon [22.02.04] : BaseRecyclerViewAdapter 객체, FoodNtrIrdntViewHolder의 Event가 발생되면 호출되는 FoodNtrIrdntListener 구현체
     *                  by lazy : 사용되는 시점에서 객체 생성과 동시에 값을 초기화
     */
    private val foodNtrIrdntAdapter by lazy {
        BaseRecyclerViewAdapter<FoodNtrIrdntModel>(listOf(), object : FoodNtrIrdntListener {

            override fun onClickItem(model: FoodNtrIrdntModel) {
                FoodNtrIrdntBottomSheet.newInstance(model).show(requireActivity().supportFragmentManager, Constants.BOTTOM_SHEET_TAG)
            }

            override fun onClickAddButton(model: FoodNtrIrdntModel) {
                viewModel.addCalculatorItem(model).let {
                    if (!it)
                        NotificationSnackBar.make(requireView(), resources.getString(R.string.same_value_exists)).show()
                }
            }
        })
    }

    /**
     * Gon [22.02.10] : BaseRecyclerViewAdapter 객체, CalculatorViewHolder의 Event가 발생되면 호출되는 CalculatorListener 구현체
     *                  by lazy : 사용되는 시점에서 객체 생성과 동시에 값을 초기화
     */
    private val calculatorAdapter by lazy {
        BaseRecyclerViewAdapter<FoodNtrIrdntModel>(listOf(), object : CalculatorListener {

            override fun onClickItem(model: FoodNtrIrdntModel) {
                FoodNtrIrdntBottomSheet.newInstance(model).show(requireActivity().supportFragmentManager, Constants.BOTTOM_SHEET_TAG)
            }

            override fun onClickRemoveButton(model: FoodNtrIrdntModel) {
                viewModel.removeCalculatorItem(model)
            }

            override fun onClickCountUpdateButton(servingCount: Int, position: Int) {
                viewModel.countUpdateCalculatorItem(servingCount, position)
            }
        })
    }

    override fun initViews() = with(binding) {
        searchViewModel = viewModel

        foodNtrIrdntAdapter = this@SearchFragment.foodNtrIrdntAdapter
        calculatorAdapter = this@SearchFragment.calculatorAdapter
    }

    override fun observeData() { }
}