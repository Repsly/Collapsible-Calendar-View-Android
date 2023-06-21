package com.shrikanthravi.collapsiblecalendarview.data

import com.shrikanthravi.collapsiblecalendarview.R

enum class ScheduleType(val color: Int) {
    MISSED(R.color.schedule_missed), DONE(R.color.schedule_done),
    UNPLANNED(R.color.schedule_unplanned), SCHEDULED(R.color.schedule_scheduled)
}