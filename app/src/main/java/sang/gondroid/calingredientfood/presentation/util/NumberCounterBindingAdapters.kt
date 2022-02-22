package sang.gondroid.calingredientfood.presentation.util

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import sang.gondroid.calingredientfood.R
import sang.gondroid.calingredientfood.presentation.widget.custom.HorizontalNumberCounter

object NumberCounterBindingAdapters {

    /**
     * Gon [22.02.22] : ImageButton 클릭 시 countInfoTextView의 현재 text를 통해 Count 값 변경
     */
    @JvmStatic
    @BindingAdapter("onClick")
    fun ImageButton.setOnClick(countInfoTextView: TextView) {
        setOnClickListener {
            val currentNumber = countInfoTextView.text.toString().toInt()
            val changeNumber = if (it.id == R.id.count_up_button) currentNumber + 1 else currentNumber - 1

            countInfoTextView.text = changeNumber.toString()
        }
    }
}