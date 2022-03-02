package sang.gondroid.calingredientfood.presentation.calculator

import androidx.lifecycle.Lifecycle
import org.koin.android.viewmodel.ext.android.viewModel
import sang.gondroid.calingredientfood.databinding.FragmentCalculatorBinding
import sang.gondroid.calingredientfood.domain.model.FoodNtrIrdntModel
import sang.gondroid.calingredientfood.presentation.base.BaseFragment
import sang.gondroid.calingredientfood.presentation.util.Constants
import sang.gondroid.calingredientfood.presentation.widget.adapter.BaseRecyclerViewAdapter
import sang.gondroid.calingredientfood.presentation.widget.custom.FoodNtrIrdntBottomSheet
import sang.gondroid.calingredientfood.presentation.widget.listener.CalculatorListener

internal class CalculatorFragment : BaseFragment<FragmentCalculatorBinding, CalculatorViewModel>() {

    private val calculatorList = ArrayList<FoodNtrIrdntModel>()

    override val viewModel: CalculatorViewModel by viewModel()

    override fun getDataBinding(): FragmentCalculatorBinding = FragmentCalculatorBinding.inflate(layoutInflater)

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
        calculatorViewModel = viewModel
        calculatorAdapter = this@CalculatorFragment.calculatorAdapter
    }

    override fun observeData() { }

    /**
     * Gon [22.03.03] : Lifecycler 생명주기 메서드인 onStart()가 호출되면 calculatorList를 element들로
     *                  viewModel.addCalculatorItem()을 호출
     */
    override fun onStart() {
        calculatorList.forEach {
            viewModel.addCalculatorItem(it)
        }

        super.onStart()
    }

    /**
     * Gon [22.03.03] : MainActivity에서 FragmentListener의 sendCalculatorItem()에 의해 호출됨
     *                  Fragment가 초기화 되지않은 상태에서 ViewModel을 호출할 수 없기 때문에 Lifecycle 상태가
     *                  INITIALIZED인 경우 calculatorList 담아둠
     *                  STARTED로 변경가 호출된 경우부턴 viewModel.addCalculatorItem() 호출
     */
    fun receiveCalculatorItem(model: FoodNtrIrdntModel): Boolean {

        return when(lifecycle.currentState) {
            Lifecycle.State.INITIALIZED -> {
                calculatorList.forEach {
                    if (it.descriptionKOR == model.descriptionKOR)
                        return false
                }

                calculatorList.add(model)
                true
            }

            Lifecycle.State.STARTED ->  viewModel.addCalculatorItem(model)

            else -> false
        }
    }
}