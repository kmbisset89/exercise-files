package com.example.kerrymbisset.valleybeta3.models;

public class Chatroom {
    String chatroom_name;
    String chatroom_subname;
    String chatroom_created_by;
    String chatroom_key;
    String chatroom_group_key;

    public Chatroom(String chatroom_name, String chatroom_subname, String chatroom_created_by, String chatroom_key, String chatroom_group_key) {
        this.chatroom_name = chatroom_name;
        this.chatroom_subname = chatroom_subname;
        this.chatroom_created_by = chatroom_created_by;
        this.chatroom_key = chatroom_key;
        this.chatroom_group_key = chatroom_group_key;
    }

    public String getChatroom_name() {
        return chatroom_name;
    }

    public void setChatroom_name(String chatroom_name) {
        this.chatroom_name = chatroom_name;
    }

    public String getChatroom_subname() {
        return chatroom_subname;
    }

    public void setChatroom_subname(String chatroom_subname) {
        this.chatroom_subname = chatroom_subname;
    }

    public String getChatroom_created_by() {
        return chatroom_created_by;
    }

    public void setChatroom_created_by(String chatroom_created_by) {
        this.chatroom_created_by = chatroom_created_by;
    }

    public String getChatroom_key() {
        return chatroom_key;
    }

    public void setChatroom_key(String chatroom_key) {
        this.chatroom_key = chatroom_key;
    }

    public String getChatroom_group_key() {
        return chatroom_group_key;
    }

    public void setChatroom_group_key(String chatroom_group_key) {
        this.chatroom_group_key = chatroom_group_key;
    }

    @Override
    public String toString() {
        return "Chatroom{" +
                "chatroom_name='" + chatroom_name + '\'' +
                ", chatroom_subname='" + chatroom_subname + '\'' +
                ", chatroom_created_by='" + chatroom_created_by + '\'' +
                ", chatroom_key='" + chatroom_key + '\'' +
                ", chatroom_group_key='" + chatroom_group_key + '\'' +
                '}';
    }
}

