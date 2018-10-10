package com.example.kerrymbisset.valleybeta3;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity (tableName = "member")
public class MemberInfo {
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

    public MemberInfo(String memberName, String memberEmail, String memberPhone, String memberPassword, int memberSort, int memberAccessLevel, int memberLoggedOn) {
        this.memberName = memberName;
        this.memberEmail = memberEmail;
        this.memberPhone = memberPhone;
        this.memberPassword = memberPassword;
        this.memberSort = memberSort;
        this.memberAccessLevel = memberAccessLevel;
        this.memberLoggedOn = memberLoggedOn;
    }

    @Ignore
    public MemberInfo(@NonNull int id, String memberName, String memberEmail, String memberPhone, String memberPassword, int memberSort, int memberAccessLevel, int memberLoggedOn) {
        this.id = id;
        this.memberName = memberName;
        this.memberEmail = memberEmail;
        this.memberPhone = memberPhone;
        this.memberPassword = memberPassword;
        this.memberSort = memberSort;
        this.memberAccessLevel = memberAccessLevel;
        this.memberLoggedOn = memberLoggedOn;
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
}
