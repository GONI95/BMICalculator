package sang.gondroid.calingredientfood.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import sang.gondroid.calingredientfood.presentation.util.DebugLog

internal abstract class BaseFragment<VDB : ViewDataBinding, VM : BaseViewModel> : Fragment() {

    /**
     * Gon [21.12.27] : viewModel을 외부에서 받는 Generic 타입으로 선언했습니다.
     */
    abstract val viewModel : VM

    /**
     * Gon [21.12.27] : binding을 외부에서 받는 Generic 타입으로 선언했습니다.
     */
    protected lateinit var binding : VDB

    /**
     * Gon [21.12.27] : DataBinding 초기화를 위한 getDataBinding()
     */
    abstract fun getDataBinding() : VDB

    /**
     * Gon [21.12.27] : View 초기화를 위한 initViews()
     */
    open fun initViews() = Unit

    /**
     * Gon [21.12.27] : LiveData를 관찰하는 observeData()
     */
    abstract fun observeData()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DebugLog.d("called : ${hashCode()}")
        binding = getDataBinding()
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        observeData()
    }

    override fun onResume() {
        super.onResume()

        viewModel.fetchData()
    }

    /**
     * Gon [21.12.27] : Fragment 제거 시 viewModel의 fetchData()에서 사용하는 coroutine이 살아있는 경우 중지시킵니다.
     */
    override fun onDestroy() {
        DebugLog.d("called : ${hashCode()}")

        if (viewModel.fetchData().isActive) viewModel.fetchData().cancel()
        super.onDestroy()
    }
}