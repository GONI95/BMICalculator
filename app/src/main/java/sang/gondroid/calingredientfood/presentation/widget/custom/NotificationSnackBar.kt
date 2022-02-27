package sang.gondroid.calingredientfood.presentation.widget.custom

import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import sang.gondroid.calingredientfood.R
import sang.gondroid.calingredientfood.databinding.SnackBarNotificationBinding

class NotificationSnackBar(view: View, private val message: String) {

    companion object {
        fun make(view: View, message: String) = NotificationSnackBar(view, message)
    }

    private val context = view.context
    private val snackBar = Snackbar.make(view, "", Snackbar.LENGTH_INDEFINITE)
    private val snackBarLayout = snackBar.view as Snackbar.SnackbarLayout

    private val inflater = LayoutInflater.from(context)
    private val snackBarBinding: SnackBarNotificationBinding = DataBindingUtil.inflate(inflater, R.layout.snack_bar_notification, null, false)

    init {
        initView()
        initData()
    }

    private fun initView() {
        with(snackBarLayout) {
            removeAllViews()
            setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
            addView(snackBarBinding.root, 0)
        }
    }

    private fun initData() {
        snackBarBinding.notificationInfoTextView.text = message

        snackBarBinding.notificationActionButton.setOnClickListener {
            snackBar.dismiss()
        }
    }

    fun show() {
        snackBar.show()
    }
}