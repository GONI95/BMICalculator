package sang.gondroid.calingredientfood.presentation.widget.custom

import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import sang.gondroid.calingredientfood.R
import sang.gondroid.calingredientfood.databinding.SnackBarNotificationBinding

/**
 * Gon [22.02.26] : SnackBar는 final class 이므로 상속이 불가능, SnackBar의 getView()를 통해 View에 액세스해 커스텀하는 방법을 선택
 */
class NotificationSnackBar(view: View, private val message: String) {

    // Gon [22.02.26] : 여러 instance로 메모리가 소비되지 않도록 SingleTon Pattern으로 구현
    companion object {
        fun make(view: View, message: String) = NotificationSnackBar(view, message)
    }

    private val context = view.context
    // Gon [22.02.26] : LENGTH_INDEFINITE를 통해 다른 SnackBar가 표시되거나 dismiss하기 전까지 노출
    private val snackBar = Snackbar.make(view, "", Snackbar.LENGTH_INDEFINITE)
    // Gon [22.02.26] : getView()를 통해 SnackBar의 View에 접근
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
        // Gon [22.02.26] : SnackBar를 표시
        snackBar.show()
    }
}
