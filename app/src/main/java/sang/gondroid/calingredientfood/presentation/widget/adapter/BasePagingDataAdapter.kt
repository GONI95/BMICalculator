package sang.gondroid.calingredientfood.presentation.widget.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import sang.gondroid.calingredientfood.domain.model.Model
import sang.gondroid.calingredientfood.domain.util.ViewType
import sang.gondroid.calingredientfood.presentation.util.BaseViewHolderMapper
import sang.gondroid.calingredientfood.presentation.util.DebugLog
import sang.gondroid.calingredientfood.presentation.widget.listener.AdapterListener
import sang.gondroid.calingredientfood.presentation.widget.viewholder.BaseViewHolder

class BasePagingDataAdapter<T : Model>(
    private val adapterListener: AdapterListener
) : PagingDataAdapter<Model, BaseViewHolder<T>>(Model.DIFF_CALLBACK) {

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        getItem(position)?.let {
            val model = it as T

            holder.bindData(model)
            holder.bindViews(model, adapterListener)
        }
    }

    override fun getItemViewType(position: Int): Int = getItem(position)?.let {
        it.type.ordinal
    } ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        DebugLog.i("$viewType, ${ViewType.values()[viewType]}")
        return BaseViewHolderMapper.viewHolderMapper(parent, ViewType.values()[viewType])
    }
}
