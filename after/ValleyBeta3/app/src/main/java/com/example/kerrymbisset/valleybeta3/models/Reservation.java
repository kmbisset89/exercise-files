package com.example.kerrymbisset.valleybeta3.models;

public class Reservation {
    String event_key;
    String member_number;
    String member_name;
    String reply;

    public Reservation(String event_key, String member_number, String member_name, String reply) {
        this.event_key = event_key;
        this.member_number = member_number;
        this.member_name = member_name;
        this.reply = reply;
    }

    public Reservation () {}

    public String getEvent_key() {
        return event_key;
    }

    public void setEvent_key(String event_key) {
        this.event_key = event_key;
    }

    public String getMember_number() {
        return member_number;
    }

    public void setMember_number(String member_number) {
        this.member_number = member_number;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "event_key='" + event_key + '\'' +
                ", member_number='" + member_number + '\'' +
                ", member_name='" + member_name + '\'' +
                ", reply='" + reply + '\'' +
                '}';
    }
}
