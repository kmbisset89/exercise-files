package com.example.kerrymbisset.valleybeta3.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.kerrymbisset.valleybeta3.EventFilter;
import com.example.kerrymbisset.valleybeta3.Events;
import com.example.kerrymbisset.valleybeta3.MemberFilter;
import com.example.kerrymbisset.valleybeta3.MemberInfo;
import com.example.kerrymbisset.valleybeta3.SGFilter;

import java.lang.reflect.Member;

/**
 * Created by danielmalone on 10/28/17.
 */

@Database(entities = {EventFilter.class, Events.class, SGFilter.class, MemberInfo.class, MemberFilter.class}, version = 11)

public abstract class AppDatabase extends RoomDatabase {

    public abstract ValleyDao valleyDao();
    private static volatile  AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null){
            synchronized (AppDatabase.class) {
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "prepop_events")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final ValleyDao mDao;

        // Initial Event data set
        private static Events [] events =
                {  new Events( "Sunday Church", "Sunday October 4th, 2018",
                        "10:45am", "Continuing our journey", 1538667900000l, 2),
                        new Events( "Small Group", "Monday November 5th, 2018",
                                "6:30am", "Finances", 1541464200000l, 3),
                        new Events( "Youth Bonfire", "Sunday October 21st, 2018",
                                "5:00pm", "Continuing our journey", 1540418400000l, 4)
                };


        private static MemberInfo[] memberInfos =
                {  new MemberInfo( "Kerry Bisset", "kerry.bisset@gmail.com",
                        "2059488952", "Password",
                        2, 3, 0),
                        new MemberInfo( "William Plott", "w.plott@valley.com",
                                "2059488952", "Password",
                                5, 1, 0),
                        new MemberInfo( "Kevin Palm", "k.palm@gmail.com",
                                "2059488952", "Password",
                                4, 3, 0),
                        new MemberInfo( "Visitor Jim", "visitor.jim@gmail.com",
                                "2059488952", "Password",
                                1, 1, 0),
                        new MemberInfo( "Daniel Stevenson", "d.stevenson@gmail.com",
                                "2059488952", "Password",2, 2, 0),
                };





        /*
        Order matters with filters if you adjust this list be sure to add/update the EventsConstants Interface
        */
        private static EventFilter [] eventFilters=
                { new EventFilter("All"), new EventFilter("Church"),
                        new EventFilter("Small Group"), new EventFilter("Youth"), new EventFilter("Adults")

                };

        /*
        Order matters with filters if you adjust this list be sure to add/update the SGConstants Interface
        */
        private static SGFilter [] sgFilters=
                { new SGFilter("All"), new SGFilter("Men's"),
                        new SGFilter("Women's"), new SGFilter("Youth"), new SGFilter("Adult")

                };

        private static MemberFilter [] memberFilters=
                { new MemberFilter("All"), new MemberFilter("Church Members"),
                        new MemberFilter("Deacons"), new MemberFilter("Elders"), new MemberFilter("Church Staff")

                };

        PopulateDbAsync(AppDatabase db) {
            mDao = db.valleyDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // If we have no words, then create the initial list of words.
            if (mDao.getAnyEvent().length < 1) {
                for (int i = 0; i <= events.length - 1; i++) {
                    mDao.insertNewEvents(events[i]);
                }
            }

            if (mDao.getAnyEventFilter().length < 1) {
                for (int i = 0; i <= eventFilters.length - 1; i++) {
                    mDao.insertNewEventFilters(eventFilters[i]);
                }
            }

            if (mDao.getAnySGFilter().length < 1) {
                for (int i = 0; i <= sgFilters.length - 1; i++) {
                    mDao.insertNewSGFilters(sgFilters[i]);
                }
            }

            if (mDao.getAnyMemberFilter().length < 1) {
                for (int i = 0; i <= memberFilters.length - 1; i++) {
                    mDao.insertNewMemberFilters(memberFilters[i]);
                }
            }

            if (mDao.getAnyMember().length < 1) {
                for (int i = 0; i <= memberInfos.length - 1; i++) {
                    mDao.insertNewMember(memberInfos[i]);
                }
            }
            return null;
        }
    }

}
