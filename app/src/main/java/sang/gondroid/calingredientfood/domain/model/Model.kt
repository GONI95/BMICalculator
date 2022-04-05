package sang.gondroid.calingredientfood.domain.model

import android.annotation.SuppressLint
import androidx.annotation.NonNull
import androidx.recyclerview.widget.DiffUtil
import sang.gondroid.calingredientfood.domain.util.ViewType
import sang.gondroid.calingredientfood.presentation.util.DebugLog

/**
 * Gon [22.01.20] : Domain Model에서 공통적으로 사용될 id, type 프로퍼티 정의
 *                  DiffUtil : RecyclerView의 성능을 개선할 수 있게 해주는 유틸리티 클래스, 기존의 List와 교체할,
 *                  List를 비교해 실질적으로 업데이트가 필요한 Item들을 필터링함
 */
abstract class Model(
    open val id: Long,
    open val type: ViewType
) {

    /**
     * Gon [22.01.20] : DiffUtil 사용을 위해 ItemCallback 추상클래스를 구현
     *                  DiffUtil : RecyclerView의 성능을 개선할 수 있게 해주는 유틸리티 클래스, 기존의 List와 교체할,
     *                  List를 비교해 실질적으로 업데이트가 필요한 Item들을 필터링함
     */
    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Model> = object : DiffUtil.ItemCallback<Model>() {
            override fun areItemsTheSame(@NonNull oldItem: Model, @NonNull newItem: Model): Boolean {
                DebugLog.d("Model areItemsTheSame() called : ${oldItem.id} ${newItem.id}")
                return oldItem.id == newItem.id
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(@NonNull oldItem: Model, @NonNull newItem: Model): Boolean {
                DebugLog.d("BaseModel areContentsTheSame() called : ${oldItem.id} ${newItem.id}")
                return oldItem === newItem
            }
        }
    }
}
