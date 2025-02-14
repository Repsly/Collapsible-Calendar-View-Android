package com.shrikanthravi.collapsiblecalendarview.data;

/**
 * Created by shrikanthravi on 06/03/18.
 */

public class Schedule {
    private int mYear;
    private int mMonth;
    private int mDay;
    private ScheduleType type;
    private String tag;
    private String placeName;
    private String contentDescription;

    public Schedule(int year, int month, int day, ScheduleType type, String placeName, String tag, String contentDescription){
        this.mYear = year;
        this.mMonth = month;
        this.mDay = day;
        this.type = type;
        this.placeName = placeName;
        this.tag = tag;
        this.contentDescription = contentDescription;
    }

    public int getMonth(){
        return mMonth;
    }

    public int getYear(){
        return mYear;
    }

    public int getDay(){
        return mDay;
    }

    public ScheduleType getType() {
        return type;
    }

    public String getTag() {return tag;}

    public String getPlaceName() {return placeName;}

    public String getContentDescription() {return contentDescription;}


}
