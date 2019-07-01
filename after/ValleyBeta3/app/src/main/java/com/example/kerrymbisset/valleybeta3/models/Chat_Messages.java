package com.example.kerrymbisset.valleybeta3.models;

public class Chat_Messages {

    private String message;
    private long time_stamp;
    private String created_by;


    public Chat_Messages(String message, long time_stamp, String created_by) {
        this.message = message;
        this.time_stamp = time_stamp;
        this.created_by = created_by;
    }

    public Chat_Messages() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(long time_stamp) {
        this.time_stamp = time_stamp;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    @Override
    public String toString() {
        return "Chat_Messages{" +
                "message='" + message + '\'' +
                ", time_stamp=" + time_stamp +
                ", created_by='" + created_by + '\'' +
                '}';
    }
}
