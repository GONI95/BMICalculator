package sang.gondroid.calingredientfood.presentation.util

import androidx.core.widget.addTextChangedListener
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import sang.gondroid.calingredientfood.R
import sang.gondroid.calingredientfood.presentation.widget.custom.AnimationEditText
import sang.gondroid.calingredientfood.presentation.widget.custom.HorizontalNumberCounter

object CustomViewBindingAdapters {

    /**
     * Gon [22.02.26] : Setter 역할을 하는 메서드
     */
    @JvmStatic
    @BindingAdapter("nc_text")
    fun HorizontalNumberCounter.setText(newCount: CharSequence) {

        val oldCount = countInfoTextView.text.toString()

        if (oldCount != newCount) countInfoTextView.text = newCount
    }

    /**
     * Gon [22.02.15] : Setter 역할을 하는 메서드
     */
    @JvmStatic
    @BindingAdapter("ie_text")
    fun AnimationEditText.ieSetText(newCount: String) {

        val oldCount = infoEditText.text.toString()

        if (oldCount != newCount) infoEditText.setText(newCount)
    }

    @JvmStatic
    @BindingAdapter("textAttrChanged")
    fun AnimationEditText.setTextWatcher(textAttrChagned: InverseBindingListener?) {
        infoEditText.addTextChangedListener { editable ->
            editable?.let {
                if (it.isNotEmpty()) {
                    hintUpAnimation()
                    clearError()
                } else {
                    hintDownAnimation()
                }
            }

            textAttrChagned?.onChange()
        }
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "ie_text", event = "textAttrChanged")
    fun AnimationEditText.ieGetText(): String {
        return infoEditText.text.toString()
    }

    @BindingAdapter("mustNotEmpty")
    @JvmStatic
    fun AnimationEditText.mustNotEmpty(message: String) {
        infoEditText.setOnFocusChangeListener { _, focusState ->
            if (!focusState && infoEditText.text.isEmpty()) {
                setError(message)
            }
        }
    }

    @BindingAdapter("uiState")
    @JvmStatic
    fun AnimationEditText.setUiState(uiState: UIState) {
        when (uiState) {
            is UIState.Failure -> {
                if (this.infoEditText.text.isNullOrEmpty())
                    setError(resources.getString(R.string.must_not_be_empty))
            }
            else -> { }
        }
    }
}
