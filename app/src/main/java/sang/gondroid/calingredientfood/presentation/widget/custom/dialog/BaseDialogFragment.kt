package sang.gondroid.calingredientfood.presentation.widget.custom.dialog

import android.content.Context
import android.content.Context.WINDOW_SERVICE
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment

abstract class BaseDialogFragment<VDB : ViewDataBinding> : DialogFragment() {
    protected lateinit var binding: VDB

    abstract fun getDataBinding(): VDB

    protected open fun initViews() = Unit

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = getDataBinding()

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog?.setCanceledOnTouchOutside(false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
    }

    protected open fun Context.dialogFragmentResize(width: Float) {

        val windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            val display = windowManager.defaultDisplay
            val size = Point()
            display.getSize(size)
            params?.width = (size.x * width).toInt()
        } else {
            val rect = windowManager.currentWindowMetrics.bounds
            params?.width = (rect.width() * width).toInt()
        }

        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }
}
