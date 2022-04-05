package sang.gondroid.calingredientfood.presentation.widget.viewholder

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import sang.gondroid.calingredientfood.domain.model.Model
import sang.gondroid.calingredientfood.presentation.widget.listener.AdapterListener

/**
 * Gon [22.01.20] : ViewHolder에서 공통적으로 사용될 메서드를 정의
 *                  화면에 표시될 ItemView를 저장하는 객체, Adapter에 의해 관리되며, 미리 생성된 ViewHolder 객체가
 *                  있는 경우, 새로 생성하지 않고 생성되어있는 ViewHolder의 ItemView에 데이터를 Binding 하여 재사용
 */
abstract class BaseViewHolder<T : Model> (
    binding: ViewDataBinding
) : RecyclerView.ViewHolder(binding.root) {

    abstract fun bindData(model: T)

    abstract fun bindViews(model: T, adapterListener: AdapterListener)
}
