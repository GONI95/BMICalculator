package sang.gondroid.calingredientfood.presentation.widget.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import sang.gondroid.calingredientfood.databinding.LayoutFoodNtrIrdntItemBinding
import sang.gondroid.calingredientfood.domain.model.Model
import sang.gondroid.calingredientfood.domain.util.ViewType
import sang.gondroid.calingredientfood.presentation.util.BaseViewHolderMapper
import sang.gondroid.calingredientfood.presentation.widget.listener.AdapterListener
import sang.gondroid.calingredientfood.presentation.widget.viewholder.BaseViewHolder
import sang.gondroid.calingredientfood.presentation.widget.viewholder.FoodNtrIrdntViewHolder

/**
 * Gon [22.01.20] : Adapter에서 공통적으로 사용될 메서드 정의
 *                  App에서 관리되는 데이터 목록을 이용해 ItemView를 생성하고 관리하는 역할
 */
class BaseRecyclerViewAdapter<T : Model>(
    private var modelList: List<Model>,
    private val adapterListener: AdapterListener
) : ListAdapter<Model, BaseViewHolder<T>>(Model.DIFF_CALLBACK) {

    override fun getItemCount(): Int = modelList.size

    /**
     * Gon [22.01.20] : ItemView의 재활용을 위해서 해당 위치에 있는 Data의 viewType을 반환
     *                  Model을 상속받아 구현한 Domain Model이 가지는 type의 값으로 반환
     */
    override fun getItemViewType(position: Int) = modelList[position].type.ordinal

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        return BaseViewHolderMapper.viewHolderMapper(parent, ViewType.values()[viewType])
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.bindData(modelList[position] as T)
        holder.bindViews(modelList[position] as T, adapterListener)
    }

    override fun submitList(list: List<Model>?) {
        list?.let { modelList = it }
        super.submitList(list)
    }
}