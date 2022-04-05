package sang.gondroid.calingredientfood.presentation.widget.custom

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import sang.gondroid.calingredientfood.R
import sang.gondroid.calingredientfood.databinding.EditTextInputLayoutBinding
import androidx.appcompat.content.res.AppCompatResources

class AnimationEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayoutCompat(context, attrs) {

    val infoEditText: EditText
        get() = binding.infoEditText

    private val binding: EditTextInputLayoutBinding

    private fun getDataBinding(context: Context): EditTextInputLayoutBinding =
        EditTextInputLayoutBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        binding = getDataBinding(context)
        binding.handler = this

        attrs?.let {
            binding.settingMap = setTypedArray(it)
        }
    }

    @SuppressLint("CustomViewStyleable")
    private fun setTypedArray(attrs: AttributeSet): Map<String, Any> {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.AnimationEditText)

        try {
            binding.titleTextView.text =
                typedArray.getText(R.styleable.AnimationEditText_ie_title)
            binding.hintTextView.text =
                typedArray.getText(R.styleable.AnimationEditText_ie_hint)

            return mapOf(
                "inputType" to typedArray.getInt(
                    R.styleable.AnimationEditText_android_inputType,
                    EditorInfo.TYPE_CLASS_TEXT
                ),
                "maxLength" to typedArray.getInteger(
                    R.styleable.AnimationEditText_android_maxLength,
                    10
                ),
                "textSize" to typedArray.getDimension(
                    R.styleable.AnimationEditText_ie_textSize,
                    spToFloat(18)
                ),
                "hintTextSize" to typedArray.getDimension(
                    R.styleable.AnimationEditText_ie_hintTextSize,
                    spToFloat(18)
                ),
                "titleTextSize" to typedArray.getDimension(
                    R.styleable.AnimationEditText_ie_titleTextSize,
                    spToFloat(18)
                ),
                "errorTextSize" to typedArray.getDimension(
                    R.styleable.AnimationEditText_ie_errorTextSize,
                    spToFloat(14)
                ),
                "textColor" to typedArray.getColor(
                    R.styleable.AnimationEditText_ie_textColor,
                    ContextCompat.getColor(context, R.color.text_color)
                ),
                "titleTextColor" to typedArray.getColor(
                    R.styleable.AnimationEditText_ie_titleTextColor,
                    ContextCompat.getColor(context, R.color.contrasting_color)
                ),
                "hintTextColor" to typedArray.getColor(
                    R.styleable.AnimationEditText_ie_hintTextColor,
                    ContextCompat.getColor(context, R.color.hint_color)
                ),
                "errorTextColor" to typedArray.getColor(
                    R.styleable.AnimationEditText_ie_errorTextColor,
                    ContextCompat.getColor(context, R.color.error_color)
                )
            )
        } finally {
            typedArray.recycle()
        }
    }

    private fun spToFloat(value: Int): Float {
        return (value * 2.75).toFloat()
    }

    fun hintUpAnimation() {
        binding.hintTextView.animate().translationY(-50f).setDuration(300).alpha(0f).start()
        binding.textClearImageButton.animate().translationY(0f).setDuration(300).alpha(1f)
            .start()
    }

    fun hintDownAnimation() {
        binding.hintTextView.animate().translationY(0f).setDuration(300).alpha(1f).start()
        binding.textClearImageButton.animate().translationY(-50f).setDuration(300).alpha(0f)
            .start()
    }

    fun setError(errorMessage: String) {
        val errorIcon = AppCompatResources.getDrawable(context, R.drawable.ic_priority_high)
            ?.apply {
                bounds = Rect(0, 0, intrinsicWidth, intrinsicHeight)
            }

        binding.infoEditText.setCompoundDrawables(null, null, errorIcon, null)
        binding.infoEditText.backgroundTintList =
            ContextCompat.getColorStateList(context, R.color.error_color)
        binding.errorTextView.text = errorMessage
    }

    fun clearError() {
        binding.infoEditText.setCompoundDrawables(null, null, null, null)
        binding.infoEditText.backgroundTintList =
            ContextCompat.getColorStateList(context, R.color.light_contrasting_color)
        binding.errorTextView.text = null
    }

    fun clearEditText() {
        binding.infoEditText.text?.clear()
    }
}
