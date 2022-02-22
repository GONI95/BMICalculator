package sang.gondroid.calingredientfood.presentation.widget.adapter

import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import sang.gondroid.calingredientfood.presentation.base.BaseFragment
import sang.gondroid.calingredientfood.presentation.base.BaseViewModel

/**
 * Gon [21.12.29] : Fragment를 이용해 각 page를 관리하는 Adapter 구현
 */
class MainViewPagerAdapter(
    fragmentActivity : FragmentActivity,
    private val fragmentList : List<BaseFragment<out ViewDataBinding, out BaseViewModel>>
) : FragmentStateAdapter(fragmentActivity) {

    // Gon [21.12.29] : ViewPager2에서 노출시키려는 Fragment의 갯수를 정의
    override fun getItemCount(): Int = fragmentList.size
    
    // Gon [21.12.29] : ViewPager2의 각 Page에서 표시할 Fragment 정의
    override fun createFragment(position: Int): Fragment = fragmentList[position]
}