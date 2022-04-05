package sang.gondroid.calingredientfood.presentation.widget.decorator

import android.content.Context
import android.text.style.ForegroundColorSpan
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import sang.gondroid.calingredientfood.R
import sang.gondroid.calingredientfood.presentation.util.DebugLog

class SelectDateDecorator(private val context: Context, private val selectDay: CalendarDay) : DayViewDecorator {

    override fun shouldDecorate(day: CalendarDay): Boolean {
        return day.date == selectDay.date
    }

    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(ForegroundColorSpan(context.getColor(R.color.contrasting_color)))
        val drawable = context.getDrawable(R.drawable.bg_selected_date_decorator_design)

        if (drawable != null) {
            view?.setSelectionDrawable(drawable)
        } else {
            DebugLog.d("is null")
        }
    }
}
