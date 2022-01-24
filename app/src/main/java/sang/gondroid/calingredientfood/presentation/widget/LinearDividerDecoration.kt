package sang.gondroid.calingredientfood.presentation.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

/**
 * Gon [22.01.24] : RecyclerView의 ItemDecoration 추상클래스를 상속받아 구현하여, 구분선을 그리는 LinearDividerDecoration
 *                  internal constructor() : 내부 생성자가 있는 class에서 접근제한자를 internal로 하여 외부에선 개체를 만들지 못하게하는 효과
 */
class LinearDividerDecoration internal constructor(context: Context, @DrawableRes layout: Int) : RecyclerView.ItemDecoration() {

    private var divider: Drawable? = null

    init {
        divider = ContextCompat.getDrawable(context, layout)
    }

    /**
     * Gon [22.01.24] : 각 예상 ItemView 간격에 구분선을 그립니다.
     *                  parent : fragment_calculator.xml의 recyclerView를 의미
     *                  parent.getChildAt(0) : 첫 ItemView
     *                  child.layoutParams : ItemView의 layout에 대한 추가적인 정보를 가짐
     */
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (parent.childCount == 0) return
        val child = parent.getChildAt(0)

        if (child.height == 0) return
        val params = child.layoutParams as RecyclerView.LayoutParams
        val dividerHeight = divider?.intrinsicHeight ?: 0

        val left = params.leftMargin
        val right = parent.width - params.leftMargin
        var top = child.bottom + params.bottomMargin
        var bottom = top + dividerHeight
        val parentBottom = parent.height - parent.paddingBottom

        while (bottom < parentBottom) {
            divider?.setBounds(left, top, right, bottom)
            divider?.draw(c)
            top += child.height + params.bottomMargin
            bottom = top + dividerHeight
        }
    }
}