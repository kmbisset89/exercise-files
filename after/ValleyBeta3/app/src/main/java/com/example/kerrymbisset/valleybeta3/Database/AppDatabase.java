package com.example.kerrymbisset.valleybeta3.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.kerrymbisset.valleybeta3.AllConstants;
import com.example.kerrymbisset.valleybeta3.EventFilter;
import com.example.kerrymbisset.valleybeta3.MemberFilter;

import com.example.kerrymbisset.valleybeta3.SGFilter;
import com.example.kerrymbisset.valleybeta3.Small_Group_Info;

import java.lang.reflect.Member;

/**
 * Created by danielmalone on 10/28/17.
 */

@Database(entities = {EventFilter.class, SGFilter.class, MemberFilter.class, Small_Group_Info.class}, version = 13)

public abstract class AppDatabase extends RoomDatabase implements AllConstants {

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

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void>  implements AllConstants {

        private final ValleyDao mDao;


        private static Small_Group_Info[] small_group_infos =
                {
                        new Small_Group_Info("Coffee and Christ", "Raeley Stevenson",
                                "Come join us for great coffee and even better fellowship",
                                "Grounded Coffee on County Line Rd", WOMENS,"YES"
                                ),
                        new Small_Group_Info("Young Adults Small Group", "William Plott",
                                "We go through books and stuff",
                                "1100", ADULT,"YES"
                        ),
                        new Small_Group_Info("Youth Night Fellowship", "Thomas Newman",
                                "Come join us for great coffee and even better fellowship",
                                "Valley Presybetrian", YOUTH,"NO"
                        ),
                        new Small_Group_Info("Men's Small group", "Jim Radford",
                                "Come join us for great coffee and even better fellowship",
                                "Panera Bread at Bridgestreet", MENS,"YES"
                        ),
                };


        /*
        Order matters with filters if you adjust this list be sure to add/update the EventsConstants Interface
        */
        private static EventFilter [] eventFilters=
                { new EventFilter("All"), new EventFilter("CHURCH"),
                        new EventFilter("SMALL GROUP"), new EventFilter("YOUTH"), new EventFilter("ADULT")

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


            return null;
        }
    }

}
