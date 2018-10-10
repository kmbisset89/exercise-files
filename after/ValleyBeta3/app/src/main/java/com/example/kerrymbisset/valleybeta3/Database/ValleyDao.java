package com.example.kerrymbisset.valleybeta3.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.kerrymbisset.valleybeta3.EventFilter;
import com.example.kerrymbisset.valleybeta3.Events;
import com.example.kerrymbisset.valleybeta3.MemberFilter;
import com.example.kerrymbisset.valleybeta3.MemberInfo;
import com.example.kerrymbisset.valleybeta3.SGFilter;

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

    @Query("SELECT * FROM events ORDER BY event_millis ASC")
    LiveData<List<Events>> getAllEvents();

    @Query("SELECT * FROM events WHERE event_filter = :event_id")
    LiveData<List<Events>> findSpecificEvent(long event_id);

    @Query("SELECT * FROM events LIMIT 1")
    Events[] getAnyEvent();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertNewEvents(Events events);

    @Delete
    void deleteEvent(Events events);

    @Query("DELETE FROM events")
    void deleteAllEvents();

    @Update
    void update(Events... events);

    /////////Members//////////
    @Query("SELECT * FROM member ORDER BY member_name ASC")
    LiveData<List<MemberInfo>> getAllMembers();

    @Query("SELECT * FROM member WHERE member_sort = :member_sort")
    LiveData<List<MemberInfo>> findSpecificMembers(long member_sort);

    @Query("SELECT * FROM member WHERE member_email = :member_email")
    LiveData<List<MemberInfo>> checkEmail(String member_email);

    @Query("SELECT * FROM member LIMIT 1")
    MemberInfo[] getAnyMember();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertNewMember(MemberInfo member);

    @Delete
    void deleteEvent(MemberInfo member);

}
