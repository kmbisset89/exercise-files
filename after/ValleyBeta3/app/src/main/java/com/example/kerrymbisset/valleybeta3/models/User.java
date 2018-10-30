package com.example.kerrymbisset.valleybeta3.models;

import java.net.URI;

public class User {

    String city;
    String member_filter;
    String name;
    String phone;
    String email;
    String profile_image;
    String security_level;
    String small_group_subscription1;
    String small_group_subscription2;
    String small_group_subscription3;
    String small_group_subscription4;
    String small_group_subscription5;
    String state;
    String street_address;
    String user_id;
    String zip;
    String show_phone;
    String show_email;
    String show_address;


    public User(String city, String member_filter, String name, String phone, String email,
                String profile_image, String security_level, String small_group_subscription1,
                String small_group_subscription2, String small_group_subscription3, String small_group_subscription4,
                String small_group_subscription5, String state, String street_address, String user_id,
                String zip, String show_phone, String show_email, String show_address) {
        this.email = email;
        this.city = city;
        this.member_filter = member_filter;
        this.name = name;
        this.phone = phone;
        this.profile_image = profile_image;
        this.security_level = security_level;
        this.small_group_subscription1 = small_group_subscription1;
        this.small_group_subscription2 = small_group_subscription2;
        this.small_group_subscription3 = small_group_subscription3;
        this.small_group_subscription4 = small_group_subscription4;
        this.small_group_subscription5 = small_group_subscription5;
        this.state = state;
        this.street_address = street_address;
        this.user_id = user_id;
        this.zip = zip;
        this.show_phone = show_phone;
        this.show_email = show_email;
        this.show_address = show_address;
    }

    public User() {
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMember_filter() {
        return member_filter;
    }

    public void setMember_filter(String member_filter) {
        this.member_filter = member_filter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getSecurity_level() {
        return security_level;
    }

    public void setSecurity_level(String security_level) {
        this.security_level = security_level;
    }

    public String getSmall_group_subscription1() {
        return small_group_subscription1;
    }

    public void setSmall_group_subscription1(String small_group_subscription1) {
        this.small_group_subscription1 = small_group_subscription1;
    }

    public String getSmall_group_subscription2() {
        return small_group_subscription2;
    }

    public void setSmall_group_subscription2(String small_group_subscription2) {
        this.small_group_subscription2 = small_group_subscription2;
    }

    public String getSmall_group_subscription3() {
        return small_group_subscription3;
    }

    public void setSmall_group_subscription3(String small_group_subscription3) {
        this.small_group_subscription3 = small_group_subscription3;
    }

    public String getSmall_group_subscription4() {
        return small_group_subscription4;
    }

    public void setSmall_group_subscription4(String small_group_subscription4) {
        this.small_group_subscription4 = small_group_subscription4;
    }

    public String getSmall_group_subscription5() {
        return small_group_subscription5;
    }

    public void setSmall_group_subscription5(String small_group_subscription5) {
        this.small_group_subscription5 = small_group_subscription5;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStreet_address() {
        return street_address;
    }

    public void setStreet_address(String street_address) {
        this.street_address = street_address;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getShow_phone() {
        return show_phone;
    }

    public void setShow_phone(String show_phone) {
        this.show_phone = show_phone;
    }

    public String getShow_email() {
        return show_email;
    }

    public void setShow_email(String show_email) {
        this.show_email = show_email;
    }

    public String getShow_address() {
        return show_address;
    }

    public void setShow_address(String show_address) {
        this.show_address = show_address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "city='" + city + '\'' +
                ", member_filter='" + member_filter + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", profile_image='" + profile_image + '\'' +
                ", security_level='" + security_level + '\'' +
                ", small_group_subscription1='" + small_group_subscription1 + '\'' +
                ", small_group_subscription2='" + small_group_subscription2 + '\'' +
                ", small_group_subscription3='" + small_group_subscription3 + '\'' +
                ", small_group_subscription4='" + small_group_subscription4 + '\'' +
                ", small_group_subscription5='" + small_group_subscription5 + '\'' +
                ", state='" + state + '\'' +
                ", street_address='" + street_address + '\'' +
                ", user_id='" + user_id + '\'' +
                ", zip='" + zip + '\'' +
                ", show_phone='" + show_phone + '\'' +
                ", show_email='" + show_email + '\'' +
                ", show_address='" + show_address + '\'' +
                '}';
    }
}