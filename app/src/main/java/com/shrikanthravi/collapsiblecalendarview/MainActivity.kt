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
        collapsibleCalendar.expand(0)

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

        collapsibleCalendar.addEventTag(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), R.color.cardview_shadow_end_color)
        collapsibleCalendar.addEventTag(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH) + 1, R.color.cardview_shadow_end_color)
        collapsibleCalendar.addEventTag(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH) + 2, R.color.cardview_shadow_end_color)
        collapsibleCalendar.addEventTag(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH) + 3, R.color.cardview_shadow_end_color)
        collapsibleCalendar.addEventTag(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH) + 4, R.color.cardview_shadow_end_color)
        collapsibleCalendar.addEventTag(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH) + 5, R.color.cardview_shadow_end_color)

        //collapsibleCalendar.removeEvents()

        collapsibleCalendar.select(Day(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)))
        //collapsibleCalendar.changeToDay(Day(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)), calendar)
        collapsibleCalendar.changeToDay(null, null)

        val items = collapsibleCalendar.getItems()

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

            }
        })


    }
}
