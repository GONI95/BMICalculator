package sang.gondroid.calingredientfood.presentation.widget

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import org.jetbrains.annotations.NotNull
import sang.gondroid.calingredientfood.R
import sang.gondroid.calingredientfood.databinding.ItemSearchModeBinding
import sang.gondroid.calingredientfood.domain.util.SearchMode

/**
 * Gon [22.01.10] : 데이터 목록을 받아, AdapterView에 출력할 수 있는 형태의 Data를 제공하기 위한 Adapter class
 *                  getCount() : Adapter가 나타내는 Data 목록의 항목 수를 반환
 *                  getItem() : Data 목록에서 지정된 위치와 연관된 데이터 항목을 반환
 *                  getView() : Data 목록에서 지정된 위치의 Data를 표시하는 View를 반환
 *                  getDropDownView() : DropDown 팝업에 표시할 View를 반환
 */
class SpinnerAdapter(
    context: Context,
    @NotNull @LayoutRes private val resId: Int,
    @NotNull private val values: Array<SearchMode>
) : ArrayAdapter<SearchMode>(context, resId, values) {

    // ItemSearchModeBinding을 반환하는 함수(고차함수 람다식)
    private val binding = { parent: ViewGroup ->
        ItemSearchModeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun getCount() = values.size

    override fun getItem(position: Int) = values[position]

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        with(binding(parent)) {
            val model = values[position]

            try {
                itemSearchModeImageView.contentDescription = context.getString(model.modelName)
                itemSearchModeImageView.setImageResource(model.modelImage)
                itemSearchModeImageView.setColorFilter(ContextCompat.getColor(context,
                    R.color.color_on_secondary
                ))
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return root
        }
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        with(binding(parent)) {
            val model = values[position]
            try {
                itemSearchModeImageView.setImageResource(model.modelImage)
                itemSearchModeTextView.text = context.getString(model.modelName)

            } catch (e: Exception) {
                e.printStackTrace()
            }
            return root
        }
    }
}