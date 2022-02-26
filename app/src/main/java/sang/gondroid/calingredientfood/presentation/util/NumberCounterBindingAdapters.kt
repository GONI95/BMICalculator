package sang.gondroid.calingredientfood.presentation.util

import androidx.databinding.BindingAdapter
import sang.gondroid.calingredientfood.presentation.widget.custom.HorizontalNumberCounter

object NumberCounterBindingAdapters {

    /**
     * Gon [22.02.26] : Setter 역할을 하는 메서드
     */
    @JvmStatic
    @BindingAdapter("nc_text")
    fun HorizontalNumberCounter.setText(newCount: CharSequence) {

        val oldCount = countInfoTextView.text.toString()

        if (oldCount != newCount) countInfoTextView.text = newCount
    }
}