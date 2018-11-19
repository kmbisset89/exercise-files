package com.example.kerrymbisset.valleybeta3.models;

public class Small_Groups {

    String sg_title;
    String sg_point_of_contact;
    String sg_desc;
    String sg_meeting_place;
    String sg_filter;
    String sg_created_by;
    String sg_group_number;
    String sg_key;

    public Small_Groups(String sg_title, String sg_point_of_contact, String sg_desc,
                        String sg_meeting_place, String sg_filter, String sg_created_by, String sg_group_number, String sg_key) {
        this.sg_title = sg_title;
        this.sg_point_of_contact = sg_point_of_contact;
        this.sg_desc = sg_desc;
        this.sg_meeting_place = sg_meeting_place;
        this.sg_filter = sg_filter;
        this.sg_created_by = sg_created_by;
        this.sg_group_number = sg_group_number;
        this.sg_key = sg_key;
    }

    public Small_Groups () {}

    public String getSg_title() {
        return sg_title;
    }

    public void setSg_title(String sg_title) {
        this.sg_title = sg_title;
    }

    public String getSg_point_of_contact() {
        return sg_point_of_contact;
    }

    public void setSg_point_of_contact(String sg_point_of_contact) {
        this.sg_point_of_contact = sg_point_of_contact;
    }

    public String getSg_desc() {
        return sg_desc;
    }

    public void setSg_desc(String sg_desc) {
        this.sg_desc = sg_desc;
    }

    public String getSg_meeting_place() {
        return sg_meeting_place;
    }

    public void setSg_meeting_place(String sg_meeting_place) {
        this.sg_meeting_place = sg_meeting_place;
    }

    public String getSg_filter() {
        return sg_filter;
    }

    public void setSg_filter(String sg_filter) {
        this.sg_filter = sg_filter;
    }

    public String getSg_created_by() {
        return sg_created_by;
    }

    public void setSg_created_by(String sg_created_by) {
        this.sg_created_by = sg_created_by;
    }

    public String getSg_group_number() {
        return sg_group_number;
    }

    public void setSg_group_number(String sg_group_number) {
        this.sg_group_number = sg_group_number;
    }

    public String getSg_key() {
        return sg_key;
    }

    public void setSg_key(String sg_key) {
        this.sg_key = sg_key;
    }

    @Override
    public String toString() {
        return "Small_Groups{" +
                "sg_title='" + sg_title + '\'' +
                ", sg_point_of_contact='" + sg_point_of_contact + '\'' +
                ", sg_desc='" + sg_desc + '\'' +
                ", sg_meeting_place='" + sg_meeting_place + '\'' +
                ", sg_filter='" + sg_filter + '\'' +
                ", sg_created_by='" + sg_created_by + '\'' +
                ", sg_group_number='" + sg_group_number + '\'' +
                ", sg_key='" + sg_key + '\'' +
                '}';
    }
}
