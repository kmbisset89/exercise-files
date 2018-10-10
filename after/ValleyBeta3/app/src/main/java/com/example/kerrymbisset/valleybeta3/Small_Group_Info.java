package com.example.kerrymbisset.valleybeta3;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity (tableName = "small_group")
public class Small_Group_Info {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    @ColumnInfo (name = "small_group_title")
    private String sgTitle =null;

    @ColumnInfo(name ="small_group_point_of_contact")
    private String sgPOC =null;

    @ColumnInfo(name = "small_group_description")
    private String sgDesc =null;

    @ColumnInfo(name = "small_group_meeting_place")
    private String sgMP = null;

    @ColumnInfo(name= "small_group_filter")
    private int sgFilter = 0;

    @ColumnInfo(name ="small_group_subscription")
    private String sgSub = null;




    public Small_Group_Info( String sgTitle, String sgPOC, String sgDesc, String sgMP, int sgFilter, String sgSub) {
        this.sgTitle = sgTitle;
        this.sgPOC = sgPOC;
        this.sgDesc = sgDesc;
        this.sgMP = sgMP;
        this.sgFilter =sgFilter;
        this.sgSub = sgSub;

    }

    @Ignore
    public Small_Group_Info(int id, String sgTitle, String sgPOC, String sgDesc, String sgMP, int sgFilter, String sgSub) {
        this.id = id;
        this.sgTitle = sgTitle;
        this.sgPOC = sgPOC;
        this.sgDesc = sgDesc;
        this.sgMP = sgMP;
        this.sgFilter =sgFilter;
        this.sgSub = sgSub;

    }


    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getSgTitle() {
        return sgTitle;
    }

    public void setSgTitle(String sgTitle) {
        this.sgTitle = sgTitle;
    }

    public String getSgPOC() {
        return sgPOC;
    }

    public void setSgPOC(String sgPOC) {
        this.sgPOC = sgPOC;
    }

    public String getSgDesc() {
        return sgDesc;
    }

    public void setSgDesc(String sgDesc) {
        this.sgDesc = sgDesc;
    }

    public String getSgMP() {
        return sgMP;
    }

    public void setSgMP(String sgMP) {
        this.sgMP = sgMP;
    }

    public int getSgFilter() {
        return sgFilter;
    }

    public void setSgFilter(int sgFilter) {
        this.sgFilter = sgFilter;
    }

    public String getSgSub() {
        return sgSub;
    }

    public void setSgSub(String sgSub) {
        this.sgSub = sgSub;
    }
}
