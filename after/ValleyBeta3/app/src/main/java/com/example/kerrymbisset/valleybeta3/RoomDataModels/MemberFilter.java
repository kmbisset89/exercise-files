package com.example.kerrymbisset.valleybeta3.RoomDataModels;



import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Random;


@Entity(tableName = "member_filter")
public class MemberFilter {

    public MemberFilter(String filterName) {
        this.filterName = filterName;
    }

    @Ignore
    public MemberFilter(int id, String eventTitle) {
        this.id = id;
        this.filterName = eventTitle; }

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "filter_name")
    private String filterName;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String eventTitle) {
        this.filterName = eventTitle;
    }


}
