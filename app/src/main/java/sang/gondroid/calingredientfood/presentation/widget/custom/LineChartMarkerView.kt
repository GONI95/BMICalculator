package sang.gondroid.calingredientfood.presentation.widget.custom

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.widget.TextView
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import sang.gondroid.calingredientfood.R
import sang.gondroid.calingredientfood.presentation.util.DebugLog

@SuppressLint("ViewConstructor")
class LineChartMarkerView constructor(context: Context, layoutResource: Int) :
    MarkerView(context, layoutResource) {

    private val averageCalorieTextView: TextView = findViewById(R.id.averageCalorieInfoTextView)

    override fun refreshContent(entry: Entry?, highlight: Highlight?) {
        entry?.let {
            averageCalorieTextView.text = resources.getString(R.string.calorie_info, it.y)
        }

        super.refreshContent(entry, highlight)
    }

    override fun draw(canvas: Canvas, posX: Float, posY: Float) {
        val lineChartWidth = canvas.width
        val markerWidth = width.toFloat()
        val newPosY = posY - (height + 20f)
        var newPosX = posX

        when {
            posX < markerWidth / 2 -> {
                DebugLog.d("범위 보다 작은 경우")
                newPosX = 0f
            }
            posX + markerWidth / 2 > lineChartWidth -> {
                DebugLog.d("범위를 만족하는 경우")
                newPosX = lineChartWidth - markerWidth
            }
            else -> {
                DebugLog.d("범위 보다 큰 경우")
                newPosX -= markerWidth / 2
            }
        }

        canvas.translate(newPosX, newPosY)
        draw(canvas)
    }
}
