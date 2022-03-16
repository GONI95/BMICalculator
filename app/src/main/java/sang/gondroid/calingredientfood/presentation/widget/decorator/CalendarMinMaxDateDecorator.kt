package sang.gondroid.calingredientfood.presentation.widget.decorator

import android.content.Context
import android.text.style.ForegroundColorSpan
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import sang.gondroid.calingredientfood.R

class CalendarMinMaxDateDecorator(private val minDay: CalendarDay, private val maxDay: CalendarDay, private val context: Context): DayViewDecorator {

    override fun shouldDecorate(day: CalendarDay): Boolean {
        return (day.date < minDay.date) || (day.date > maxDay.date)
    }

    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(ForegroundColorSpan(context.getColor(R.color.gray)))
        view?.setDaysDisabled(true)
    }
}