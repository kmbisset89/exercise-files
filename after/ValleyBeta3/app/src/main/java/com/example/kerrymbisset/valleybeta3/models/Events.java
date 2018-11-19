package com.example.kerrymbisset.valleybeta3.models;

public class Events {
    String event_key;
    String event_title;
    String event_date;
    String event_time;
    String event_millis;
    String event_desc;
    String event_filter;
    String group_number;
    String event_location;
    String event_create_by;

    public Events(String event_key, String event_title, String event_date, String event_time, String event_millis,
                  String event_desc, String event_location, String event_filter, String group_number, String event_create_by) {
        this.event_key = event_key;
        this.event_title = event_title;
        this.event_date = event_date;
        this.event_time = event_time;
        this.event_millis = event_millis;
        this.event_desc = event_desc;
        this.event_filter = event_filter;
        this.group_number = group_number;
        this.event_location = event_location;
        this.event_create_by = event_create_by;
    }

    public Events() {

    }

    public String getEvent_title() {
        return event_title;
    }

    public void setEvent_title(String event_title) {
        this.event_title = event_title;
    }

    public String getEvent_date() {
        return event_date;
    }

    public void setEvent_date(String event_date) {
        this.event_date = event_date;
    }

    public String getEvent_time() {
        return event_time;
    }

    public void setEvent_time(String event_time) {
        this.event_time = event_time;
    }

    public String getEvent_millis() {
        return event_millis;
    }

    public void setEvent_millis(String event_millis) {
        this.event_millis = event_millis;
    }

    public String getEvent_desc() {
        return event_desc;
    }

    public void setEvent_desc(String event_desc) {
        this.event_desc = event_desc;
    }

    public String getEvent_filter() {
        return event_filter;
    }

    public void setEvent_filter(String event_filter) {
        this.event_filter = event_filter;
    }

    public String getGroup_number() {
        return group_number;
    }

    public void setGroup_number(String group_number) {
        this.group_number = group_number;
    }

    public String getEvent_key() {
        return event_key;
    }

    public void setEvent_key(String event_key) {
        this.event_key = event_key;
    }

    public String getEvent_location() {
        return event_location;
    }

    public void setEvent_location(String event_location) {
        this.event_location = event_location;
    }

    public String getEvent_create_by() {
        return event_create_by;
    }

    public void setEvent_create_by(String event_create_by) {
        this.event_create_by = event_create_by;
    }

    @Override
    public String toString() {
        return "Events{" +
                "event_key='" + event_key + '\'' +
                ", event_title='" + event_title + '\'' +
                ", event_date='" + event_date + '\'' +
                ", event_time='" + event_time + '\'' +
                ", event_millis='" + event_millis + '\'' +
                ", event_desc='" + event_desc + '\'' +
                ", event_filter='" + event_filter + '\'' +
                ", group_number='" + group_number + '\'' +
                ", event_location='" + event_location + '\'' +
                ", event_create_by='" + event_create_by + '\'' +
                '}';
    }
}


