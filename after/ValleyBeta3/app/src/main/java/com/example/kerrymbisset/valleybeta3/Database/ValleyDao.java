package com.example.kerrymbisset.valleybeta3.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.kerrymbisset.valleybeta3.RoomDataModels.EventFilter;

import com.example.kerrymbisset.valleybeta3.RoomDataModels.MemberFilter;
import com.example.kerrymbisset.valleybeta3.RoomDataModels.SGFilter;
import com.example.kerrymbisset.valleybeta3.Small_Group_Info;

import java.util.List;

/**
 * Created by danielmalone on 10/28/17.
 */

@Dao
public interface ValleyDao {


    //////////Filters///////

    @Query("SELECT * FROM event_filter ORDER BY id ASC")
    LiveData<List<EventFilter>> getAllEventsFilters();

    @Query("SELECT * FROM event_filter LIMIT 1")
    EventFilter[] getAnyEventFilter();

    @Query("SELECT * FROM sg_filter ORDER BY id ASC")
    LiveData<List<SGFilter>> getAllSGFilters();

    @Query("SELECT * FROM sg_filter LIMIT 1")
    SGFilter[] getAnySGFilter();

    @Query("SELECT * FROM member_filter ORDER BY id ASC")
    LiveData<List<MemberFilter>> getAllMemberFilters();

    @Query("SELECT * FROM member_filter LIMIT 1")
    MemberFilter[] getAnyMemberFilter();

    //*******Inserts

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertNewEventFilters(EventFilter eventFilter);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertNewSGFilters(SGFilter sgFilter);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertNewMemberFilters(MemberFilter memberFilter);



    ////////Events/////////


    ///////Small Groups///////////

    @Query("SELECT * FROM small_group ORDER BY small_group_title ASC")
    LiveData<List<Small_Group_Info>> getAllSmallGroups();

    @Query("SELECT * FROM small_group WHERE small_group_filter = :small_group_filter")
    LiveData<List<Small_Group_Info>> findSpecificSmallGroups(long small_group_filter);

    @Query("SELECT * FROM small_group LIMIT 1")
    Small_Group_Info[] getAnySmallGroup();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertNewSmallGroup(Small_Group_Info small_group_info);


}
