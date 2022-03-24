package sang.gondroid.calingredientfood.presentation.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import sang.gondroid.calingredientfood.presentation.util.DebugLog

internal abstract class BaseActivity<VDB : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity() {

    // Gon [22.03.16] : viewModel을 외부에서 받는 Generic 타입으로 선언했습니다.
    abstract val viewModel : VM

    // Gon [22.03.16] : binding을 외부에서 받는 Generic 타입으로 선언했습니다.
    protected lateinit var binding : VDB

    // Gon [22.03.16] : DataBinding 초기화를 위한 getDataBinding()
    abstract fun getDataBinding() : VDB

    // Gon [22.03.24] : LiveData를 관찰하는 observeData()
    abstract fun observeData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = getDataBinding()
        binding.lifecycleOwner = this
        initState()
        observeData()
    }

    open fun initState() = Unit

    override fun onResume() {
        super.onResume()

        viewModel.fetchData()
    }

    // Gon [22.03.16] : Fragment 제거 시 viewModel의 fetchData()에서 사용하는 coroutine이 살아있는 경우 중지시킵니다.
    override fun onDestroy() {
        DebugLog.d("called : ${hashCode()}")

        if (viewModel.fetchData().isActive) viewModel.fetchData().cancel()
        super.onDestroy()
    }
}