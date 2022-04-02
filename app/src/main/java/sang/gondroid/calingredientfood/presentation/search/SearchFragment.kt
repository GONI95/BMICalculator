package sang.gondroid.calingredientfood.presentation.search

import android.content.Context
import org.koin.android.viewmodel.ext.android.viewModel
import sang.gondroid.calingredientfood.R
import sang.gondroid.calingredientfood.databinding.FragmentSearchBinding
import sang.gondroid.calingredientfood.domain.model.FoodNtrIrdntModel
import sang.gondroid.calingredientfood.presentation.base.BaseFragment
import sang.gondroid.calingredientfood.presentation.base.FragmentListener
import sang.gondroid.calingredientfood.presentation.util.Constants
import sang.gondroid.calingredientfood.presentation.util.DebugLog
import sang.gondroid.calingredientfood.presentation.widget.adapter.BaseRecyclerViewAdapter
import sang.gondroid.calingredientfood.presentation.widget.custom.FoodNtrIrdntBottomSheet
import sang.gondroid.calingredientfood.presentation.widget.custom.NotificationSnackBar
import sang.gondroid.calingredientfood.presentation.widget.listener.FoodNtrIrdntListener

internal class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>() {

    companion object {
        const val TAG = "SearchFragment"

        fun newInstance() = SearchFragment()
    }

    override val viewModel: SearchViewModel by viewModel()

    override fun getDataBinding(): FragmentSearchBinding =
        FragmentSearchBinding.inflate(layoutInflater)

    private var fragmentListener: FragmentListener? = null

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
                // Gon [22.03.03] : FragmentListener sendCalculatorItem() 호출 / MainActivity의 구현체가 호출됨
                val result = fragmentListener?.sendCalculatorItem(model)

                result?.let {
                    if (result)
                        NotificationSnackBar.make(requireView(), resources.getString(R.string.same_value_exists)).show()
                }
            }
        })
    }

    override fun initViews() = with(binding) {
        searchViewModel = viewModel
        foodNtrIrdntAdapter = this@SearchFragment.foodNtrIrdntAdapter
    }

    override fun observeData() { }

    // Gon [22.03.03] : FragmentListener 등록
    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is FragmentListener)
            fragmentListener = context
    }

    // Gon [22.03.03] : FragmentListener 해제
    override fun onDetach() {
        super.onDetach()

        if (fragmentListener != null)
            fragmentListener = null
    }
}