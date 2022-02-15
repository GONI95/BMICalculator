package sang.gondroid.calingredientfood.presentation.widget.custom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import sang.gondroid.calingredientfood.R
import sang.gondroid.calingredientfood.databinding.BottomSheetFoodNtrIrdntBinding
import sang.gondroid.calingredientfood.domain.model.FoodNtrIrdntModel
import sang.gondroid.calingredientfood.presentation.util.DebugLog

/**
 * Gon [22.01.24] : Google Material Design에서 제공하는 BottomSheetDialogFragment를 상속받아 구현한 FoodNtrIrdntBottomSheet
 *                  internal constructor() : 내부 생성자가 있는 class에서 접근제한자를 internal로 하여 외부에선 개체를 만들지 못하게하는 효과
 */
class FoodNtrIrdntBottomSheet internal constructor(private val model: FoodNtrIrdntModel) : BottomSheetDialogFragment() {

    companion object {
        /**
         * Gon [22.01.24] : 여러 번 표시되지 않도록 SingleTon Pattern으로 구현
         */
        fun newInstance(model: FoodNtrIrdntModel) : FoodNtrIrdntBottomSheet =
            FoodNtrIrdntBottomSheet(model).also {
                DebugLog.d("called : ${hashCode()}")
            }
    }

    /**
     * Gon [22.01.24] : BottomSheetDialogFragment의 테마 설정
     */
    override fun getTheme(): Int = R.style.Theme_CalIngredientFood_BottomSheetDialog

    private lateinit var binding : BottomSheetFoodNtrIrdntBinding

    private fun getDataBinding(): BottomSheetFoodNtrIrdntBinding =
        BottomSheetFoodNtrIrdntBinding.inflate(layoutInflater)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = getDataBinding()
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.model = this.model
    }
}