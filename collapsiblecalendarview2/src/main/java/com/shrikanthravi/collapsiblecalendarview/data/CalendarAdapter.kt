package com.shrikanthravi.collapsiblecalendarview.data

import android.content.Context
import android.content.res.Resources
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.text.TextUtils
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.shrikanthravi.collapsiblecalendarview.R
import java.util.*

/**
 * Created by shrikanthravi on 06/03/18.
 */

class CalendarAdapter(private val context: Context, cal: Calendar) {
    private var mFirstDayOfWeek = 0
    private var mNumberOfWeeks = 0
    var calendar: Calendar
    private val mInflater: LayoutInflater

    private val mItemList = ArrayList<Day>()
    private val mViewList = ArrayList<View>()
    var mEventList = ArrayList<Event>()
    var mScheduleList = ArrayList<Schedule>()

    private var mListener: ScheduleListener? = null

    // callback
    fun setCalendarListener(listener: ScheduleListener) {
        mListener = listener
    }

    // public methods
    val count: Int
        get() = mItemList.size

    init {
        this.calendar = cal.clone() as Calendar
        this.calendar.set(Calendar.DAY_OF_MONTH, 1)

        mInflater = LayoutInflater.from(context)

        refresh()
    }

    fun getItem(position: Int): Day {
        return mItemList[position]
    }

    fun getItems(): ArrayList<Day> {
        return mItemList
    }

    fun getView(position: Int): View {
        return mViewList[position]
    }

    fun setFirstDayOfWeek(firstDayOfWeek: Int) {
        mFirstDayOfWeek = firstDayOfWeek
    }

    fun setNumberOfWeeks(numberOfWeeks: Int) {
        mNumberOfWeeks = numberOfWeeks
    }

    fun addEvent(event: Event) {
        mEventList.add(event)
    }

    fun removeEvents () {
        mEventList.clear()
    }

    fun addEvents(events: List<Event>) {
        mEventList.clear()
        mEventList.addAll(events)
    }

    fun addSchedules(schedule: List<Schedule>) {
        mScheduleList.clear()
        mScheduleList.addAll(schedule)
    }

    fun removeSchedules () {
        mScheduleList.clear()
    }

    fun refresh() {
        // clear data
        mItemList.clear()
        mViewList.clear()

        // set calendar
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)

        calendar.set(year, month, 1)

        val lastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1

        // generate day list
        val offset = 0 - (firstDayOfWeek - mFirstDayOfWeek) + 1
        val length = if (mNumberOfWeeks > 0) mNumberOfWeeks * 7 else Math.ceil(((lastDayOfMonth - offset + 1).toFloat() / 7).toDouble()).toInt() * 7
        for (i in offset until length + offset) {
            val numYear: Int
            val numMonth: Int
            val numDay: Int

            val tempCal = Calendar.getInstance()
            if (i <= 0) { // prev month
                if (month == 0) {
                    numYear = year - 1
                    numMonth = 11
                } else {
                    numYear = year
                    numMonth = month - 1
                }
                tempCal.set(numYear, numMonth, 1)
                numDay = tempCal.getActualMaximum(Calendar.DAY_OF_MONTH) + i
            } else if (i > lastDayOfMonth) { // next month
                if (month == 11) {
                    numYear = year + 1
                    numMonth = 0
                } else {
                    numYear = year
                    numMonth = month + 1
                }
                tempCal.set(numYear, numMonth, 1)
                numDay = i - lastDayOfMonth
            } else {
                numYear = year
                numMonth = month
                numDay = i
            }

            val day = Day(numYear, numMonth, numDay)

            val view = mInflater.inflate(R.layout.day_layout, null)
            val txtDay = view.findViewById<View>(R.id.txt_day) as TextView
            val imgEventTag = view.findViewById<View>(R.id.img_event_tag) as ImageView
            val holderView = view.findViewById<View>(R.id.holder_view) as LinearLayout

            val numMonthReal = numMonth + 1
            var numMonthString = numMonthReal.toString()
            var numDayString = numDay.toString()
            if (numMonthReal < 10) {
                numMonthString = "0$numMonthReal"
            }
            if (numDay < 10) {
                numDayString = "0$numDay"
            }
            view.contentDescription = "ScheduleDayContainer_$numYear-$numMonthString-$numDayString"

            txtDay.text = day.day.toString()
            if (day.month != calendar.get(Calendar.MONTH)) {
                txtDay.alpha = 0.3f
            }

            for (j in mEventList.indices) {
                val event = mEventList[j]
                if (day.year == event.year
                        && day.month == event.month
                        && day.day == event.day) {
                    imgEventTag.visibility = View.VISIBLE
                    imgEventTag.setColorFilter(event.color, PorterDuff.Mode.SRC_ATOP)
                }
            }

            val height =  Resources.getSystem().displayMetrics.heightPixels
            val otherViewHeight = ((65) * context.resources.displayMetrics.density).toInt()

            val bottomLineView = view.findViewById<View>(R.id.bottom_line) as View
            val endLineView = view.findViewById<View>(R.id.end_line) as View

            var holderViewHeight = 0
            if (mNumberOfWeeks > 0) {
                holderViewHeight = (height / 6) - otherViewHeight
                holderView.layoutParams.height = holderViewHeight
                holderView.visibility = View.VISIBLE
                endLineView.visibility = View.VISIBLE
                if (i < length + offset - 7) {
                    bottomLineView.visibility = View.VISIBLE
                } else {
                    bottomLineView.visibility = View.GONE
                }
            } else {
                holderView.visibility = View.GONE
                bottomLineView.visibility = View.GONE
                endLineView.visibility = View.GONE
            }
            val density = context.resources.displayMetrics.density
            val oneItemHeight = (16 * density).toInt()
            val numberOfItemsThatCanBeAdded: Int = holderViewHeight / oneItemHeight
            val numberOfItemsOnADay = mScheduleList.filter { day.year == it.year && day.month == it.month && day.day == it.day }

            if (holderViewHeight > 0) {
                var addedItems = 0

                for (item in numberOfItemsOnADay) {
                    if (numberOfItemsThatCanBeAdded == numberOfItemsOnADay.count() || numberOfItemsThatCanBeAdded - 1 > addedItems) {
                        addedItems++
                        addScheduleView(
                            item.placeName,
                            item,
                            item.type.color,
                            holderView,
                            density,
                            item.contentDescription
                        )
                    } else {
                        break
                    }
                }

                if (addedItems < numberOfItemsOnADay.count()) {
                    addScheduleView(
                        "+ ${numberOfItemsOnADay.count() - addedItems}",
                        day,
                        R.color.white,
                        holderView,
                        density,
                        "groupedVisitsExpandPlusLabel"
                    )
                }
            }


            mItemList.add(day)
            mViewList.add(view)
        }
    }

    private fun addScheduleView(
        placeName: String,
        tag: Any,
        color: Int,
        holderView: LinearLayout,
        density: Float,
        contentDescription: String
    ) {

        val scheduleView = TextView(context)
        scheduleView.contentDescription = contentDescription
        scheduleView.text = placeName
        scheduleView.tag = tag
        scheduleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10f)
        scheduleView.setTextColor(ContextCompat.getColor(context, R.color.gray_button_text))
        scheduleView.setTypeface(null, Typeface.BOLD)
        scheduleView.ellipsize = TextUtils.TruncateAt.END
        scheduleView.isSingleLine = true
        scheduleView.setOnClickListener {
            mListener?.onScheduleClicked(it.tag)
        }

        val shapeDrawable = GradientDrawable()
        shapeDrawable.shape = GradientDrawable.RECTANGLE
        shapeDrawable.cornerRadius = 12f
        shapeDrawable.setColor(ContextCompat.getColor(context, color))
        scheduleView.background = shapeDrawable

        val density4 = (4 * density).toInt()
        val density2 = (2 * density).toInt()
        scheduleView.setPadding(density4, 0, density2, 0)

        val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (14 * density).toInt())
        layoutParams.setMargins(density2, density2, density2, 0)
        scheduleView.layoutParams = layoutParams

        holderView.addView(scheduleView)
    }

    interface ScheduleListener {
        fun onScheduleClicked(tag: Any)
    }
}
