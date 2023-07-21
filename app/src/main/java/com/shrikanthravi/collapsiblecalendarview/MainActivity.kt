package com.shrikanthravi.collapsiblecalendarview

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.RelativeLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.shrikanthravi.collapsiblecalendarview.data.Day
import com.shrikanthravi.collapsiblecalendarview.data.Event
import com.shrikanthravi.collapsiblecalendarview.data.Schedule
import com.shrikanthravi.collapsiblecalendarview.data.ScheduleType
import com.shrikanthravi.collapsiblecalendarview.view.OnSwipeTouchListener

import com.shrikanthravi.collapsiblecalendarview.widget.CollapsibleCalendar
import com.shrikanthravi.collapsiblecalendarview.widget.DayType
import com.shrikanthravi.collapsiblecalendarview.widget.UICalendar
import java.time.DayOfWeek
import java.time.temporal.WeekFields

import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar


class MainActivity : AppCompatActivity(){
    companion object{
        var TAG = "MainActivity";
    }

    lateinit var collapsibleCalendar:CollapsibleCalendar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.elevation = 0f
        window.statusBarColor = resources.getColor(R.color.google_red)
        var relativeLayout = findViewById<ScrollView>(R.id.scrollView)


        collapsibleCalendar = findViewById(R.id.collapsibleCalendarView)
        //collapsibleCalendar.expand(0)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val wf: WeekFields = WeekFields.of(collapsibleCalendar.getCurrentLocale(this))
            var day: DayOfWeek = wf.firstDayOfWeek

            collapsibleCalendar.firstDayOfWeek = DayType.values().first { it.dayName == day.name }.order
            collapsibleCalendar.mAdapter?.setFirstDayOfWeek(collapsibleCalendar.firstDayOfWeek)
        } else {
            null
        }

        relativeLayout.setOnTouchListener(object:OnSwipeTouchListener(this@MainActivity){
            override fun onSwipeRight() {
                collapsibleCalendar.nextDay()
            }

            override fun onSwipeLeft() {
                collapsibleCalendar.prevDay()
            }

            override fun onSwipeTop() {
//                if(collapsibleCalendar.expanded){
//                    collapsibleCalendar.collapse(400)
//                }
            }

            override fun onSwipeBottom() {
//                if(!collapsibleCalendar.expanded){
//                    collapsibleCalendar.expand(400)
//                }
            }
        });
        //To hide or show expand icon
        collapsibleCalendar.setExpandIconVisible(false)
        val today = GregorianCalendar()
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, 2023)
        calendar.set(Calendar.MONTH, Calendar.JULY)
        calendar.set(Calendar.DAY_OF_MONTH, 11)

        val events = mutableListOf<Event>()

        events.add(Event(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), R.color.cardview_shadow_end_color))
        events.add(Event(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH) + 1, R.color.cardview_shadow_end_color))
        events.add(Event(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH) + 2, R.color.cardview_shadow_end_color))
        events.add(Event(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH) + 3, R.color.cardview_shadow_end_color))
        events.add(Event(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH) + 4, R.color.cardview_shadow_end_color))
        events.add(Event(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH) + 5, R.color.cardview_shadow_end_color))
        events.add(Event(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH) + 6, R.color.cardview_shadow_end_color))

        collapsibleCalendar.addEvents(events)
//        collapsibleCalendar.addEventTag()
//        collapsibleCalendar.addEventTag(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH) + 1, R.color.cardview_shadow_end_color)
//        collapsibleCalendar.addEventTag(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH) + 2, R.color.cardview_shadow_end_color)
//        collapsibleCalendar.addEventTag(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH) + 3, R.color.cardview_shadow_end_color)
//        collapsibleCalendar.addEventTag(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH) + 4, R.color.cardview_shadow_end_color)
//        collapsibleCalendar.addEventTag(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH) + 5, R.color.cardview_shadow_end_color)


        //collapsibleCalendar.removeEvents()

        val schedules = mutableListOf<Schedule>()
        schedules.add(Schedule(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), ScheduleType.MISSED, "Place 1", "1"))
        schedules.add(Schedule(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), ScheduleType.DONE, "Place 2", "2"))
        schedules.add(Schedule(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), ScheduleType.UNPLANNED, "Place 3 12 152152 15 51", "3"))
        schedules.add(Schedule(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), ScheduleType.SCHEDULED, "Place 4", "4"))
        schedules.add(Schedule(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), ScheduleType.SCHEDULED, "Place 5", "5"))
        schedules.add(Schedule(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), ScheduleType.SCHEDULED, "Place 6", "6"))
//        schedules.add(Schedule(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), ScheduleType.SCHEDULED, "Place 7", "7"))

        schedules.add(Schedule(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH) + 1, ScheduleType.MISSED, "Place 11", "1"))
        schedules.add(Schedule(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH) + 1, ScheduleType.DONE, "Place 22", "2"))
        schedules.add(Schedule(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH) + 1, ScheduleType.UNPLANNED, "Place 33 12 152152 15 51", "3"))
        schedules.add(Schedule(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH) + 1, ScheduleType.SCHEDULED, "Place 44", "4"))
        schedules.add(Schedule(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH) + 1, ScheduleType.SCHEDULED, "Place 55", "5"))
        schedules.add(Schedule(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH) + 1, ScheduleType.SCHEDULED, "Place 66", "6"))
        schedules.add(Schedule(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH) + 1, ScheduleType.SCHEDULED, "Place 77", "7"))

        collapsibleCalendar.addSchedules(schedules)

        collapsibleCalendar.select(Day(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)))
        //collapsibleCalendar.changeToDay(Day(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)), calendar)
        collapsibleCalendar.changeToDay(null, null)

        val items = collapsibleCalendar.getItems()


        collapsibleCalendar.weekViewOnly = true

        collapsibleCalendar.setCalendarListener(object : CollapsibleCalendar.CalendarListener {
            override fun onDayChanged() {

            }

            override fun onClickListener() {
                if(collapsibleCalendar.expanded){
                    collapsibleCalendar.collapse(400)
                }
                else{
                    collapsibleCalendar.expand(400)
                }
            }

            override fun onDaySelect() {
                //textView.text = collapsibleCalendar.selectedDay?.toUnixTime().toString()
            }

            override fun onItemClick(v: View) {

            }

            override fun onDataUpdate() {

            }

            override fun onMonthChange() {

            }

            override fun onWeekChange(position: Int) {
                val items = collapsibleCalendar.getWeekItems()
                Log.e("week change", position.toString())

            }

            override fun onScheduleClicked(schedule: Schedule) {
                Log.e("aaa", schedule.tag)
            }
        })


    }
}
