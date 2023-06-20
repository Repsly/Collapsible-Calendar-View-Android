package com.shrikanthravi.collapsiblecalendarview.data;

/**
 * Created by shrikanthravi on 06/03/18.
 */

public class Schedule {
    private int mYear;
    private int mMonth;
    private int mDay;
    private String mColor;
    private String tag;
    private String placeName;

    public Schedule(int year, int month, int day, String color, String placeName, String tag){
        this.mYear = year;
        this.mMonth = month;
        this.mDay = day;
        this.mColor = color;
        this.placeName = placeName;
        this.tag = tag;
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

    public String getColor() {
        return mColor;
    }

    public String getTag() {return tag;}

    public String getPlaceName() {return placeName;}
}
