package com.example.kerrymbisset.valleybeta3;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "events")
public class Events {

    public Events(String eventTitle, String eventDate, String eventTime, String eventDesc, long eventMillis, int eventFilter) {
        this.eventTitle = eventTitle;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.eventDesc = eventDesc;
        this.eventMillis = eventMillis;
        this.eventFilter = eventFilter;
    }

    @Ignore
    public Events(int id, String eventTitle, String eventDate, String eventTime, String eventDesc, long eventMillis, int eventFilter) {
        this.id = id;
        this.eventTitle = eventTitle;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.eventDesc = eventDesc;
        this.eventMillis = eventMillis;
        this.eventFilter = eventFilter;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "event_title")
    private String eventTitle;

    @ColumnInfo(name = "event_date")
    private String eventDate;

    @ColumnInfo(name = "event_time")
    private String eventTime;

    @ColumnInfo(name = "event_desc")
    private String eventDesc;

    @ColumnInfo(name = "event_millis")
    private long eventMillis;

    @ColumnInfo(name = "event_filter")
    private int eventFilter;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }

    public long getEventMillis() {
        return eventMillis;
    }

    public void setEventMillis(long eventMillis) {
        this.eventMillis = eventMillis;
    }

    public int getEventFilter() {
        return eventFilter;
    }

    public void setEventFilter(int eventFilter) {
        this.eventFilter = eventFilter;
    }
}
