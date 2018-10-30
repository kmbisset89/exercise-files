package com.example.kerrymbisset.valleybeta3;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity (tableName = "member")
public class MemberInfo implements AllConstants {


    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    @ColumnInfo(name = "member_name")
    private String memberName =null;

    @ColumnInfo(name = "member_email")
    private String memberEmail=null;

    @ColumnInfo(name = "member_phone")
    private String memberPhone=null;

    @ColumnInfo(name = "member_password")
    private String memberPassword=null;

    @ColumnInfo(name = "member_sort")
    private int memberSort= 0;

    @ColumnInfo(name = "member_access_level")
    private int memberAccessLevel=0;

    @ColumnInfo(name = "member_logged_on")
    private int memberLoggedOn =0;

    @ColumnInfo(name = "sub_1")
    private int subscribeToSG1 =NOTSUBSCRIBED;

    @ColumnInfo(name = "sub_2")
    private int subscribeToSG2 =NOTSUBSCRIBED;

    @ColumnInfo(name = "sub_3")
    private int subscribeToSG3 =NOTSUBSCRIBED;

    @ColumnInfo(name = "sub_4")
    private int subscribeToSG4 =NOTSUBSCRIBED;

    @ColumnInfo(name = "sub_5")
    private int subscribeToSG5 =NOTSUBSCRIBED;

    public MemberInfo(String memberName, String memberEmail, String memberPhone,
                      String memberPassword, int memberSort, int memberAccessLevel, int memberLoggedOn,
                      int subscribeToSG1, int subscribeToSG2, int subscribeToSG3, int subscribeToSG4, int subscribeToSG5) {
        this.memberName = memberName;
        this.memberEmail = memberEmail;
        this.memberPhone = memberPhone;
        this.memberPassword = memberPassword;
        this.memberSort = memberSort;
        this.memberAccessLevel = memberAccessLevel;
        this.memberLoggedOn = memberLoggedOn;
        this.subscribeToSG1=subscribeToSG1;
        this.subscribeToSG2=subscribeToSG2;
        this.subscribeToSG3=subscribeToSG3;
        this.subscribeToSG4=subscribeToSG4;
        this.subscribeToSG5=subscribeToSG5;
    }

    @Ignore
    public MemberInfo(@NonNull int id, String memberName, String memberEmail, String memberPhone, String memberPassword,
                      int memberSort, int memberAccessLevel, int memberLoggedOn,
                      int subscribeToSG1, int subscribeToSG2, int subscribeToSG3, int subscribeToSG4, int subscribeToSG5) {
        this.id = id;
        this.memberName = memberName;
        this.memberEmail = memberEmail;
        this.memberPhone = memberPhone;
        this.memberPassword = memberPassword;
        this.memberSort = memberSort;
        this.memberAccessLevel = memberAccessLevel;
        this.memberLoggedOn = memberLoggedOn;
        this.subscribeToSG1=subscribeToSG1;
        this.subscribeToSG2=subscribeToSG2;
        this.subscribeToSG3=subscribeToSG3;
        this.subscribeToSG4=subscribeToSG4;
        this.subscribeToSG5=subscribeToSG5;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }

    public String getMemberPhone() {
        return memberPhone;
    }

    public void setMemberPhone(String memberPhone) {
        this.memberPhone = memberPhone;
    }

    public String getMemberPassword() {
        return memberPassword;
    }

    public void setMemberPassword(String memberPassword) {
        this.memberPassword = memberPassword;
    }

    public int getMemberSort() {
        return memberSort;
    }

    public void setMemberSort(int memberSort) {
        this.memberSort = memberSort;
    }

    public int getMemberAccessLevel() {
        return memberAccessLevel;
    }

    public void setMemberAccessLevel(int memberAccessLevel) {
        this.memberAccessLevel = memberAccessLevel;
    }

    public int getMemberLoggedOn() {
        return memberLoggedOn;
    }

    public void setMemberLoggedOn(int memberLoggedOn) {
        this.memberLoggedOn = memberLoggedOn;
    }

    public int getSubscribeToSG1() {
        return subscribeToSG1;
    }

    public void setSubscribeToSG1(int subscribeToSG1) {
        this.subscribeToSG1 = subscribeToSG1;
    }

    public int getSubscribeToSG2() {
        return subscribeToSG2;
    }

    public void setSubscribeToSG2(int subscribeToSG2) {
        this.subscribeToSG2 = subscribeToSG2;
    }

    public int getSubscribeToSG3() {
        return subscribeToSG3;
    }

    public void setSubscribeToSG3(int subscribeToSG3) {
        this.subscribeToSG3 = subscribeToSG3;
    }

    public int getSubscribeToSG4() {
        return subscribeToSG4;
    }

    public void setSubscribeToSG4(int subscribeToSG4) {
        this.subscribeToSG4 = subscribeToSG4;
    }

    public int getSubscribeToSG5() {
        return subscribeToSG5;
    }

    public void setSubscribeToSG5(int subscribeToSG5) {
        this.subscribeToSG5 = subscribeToSG5;
    }
}
