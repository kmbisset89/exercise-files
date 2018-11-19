package com.example.kerrymbisset.valleybeta3.Database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.kerrymbisset.valleybeta3.EventFilter;

import com.example.kerrymbisset.valleybeta3.MemberFilter;

import com.example.kerrymbisset.valleybeta3.SGFilter;
import com.example.kerrymbisset.valleybeta3.Small_Group_Info;

import java.util.List;

public class DataRepository {

    private ValleyDao mDao;

    private LiveData<List<EventFilter>> mAllEventFilters;
    private LiveData<List<SGFilter>> mAllSGFilters;
    private LiveData<List<MemberFilter>> mAllMemberFilters;

    private LiveData<List<Small_Group_Info>> mAllSmallGroups;
    public static volatile DataRepository sInstance = null;



    public DataRepository(Application application) {
        if (sInstance==null) {
            synchronized (DataRepository.class){
                if (sInstance== null){
                    AppDatabase db = AppDatabase.getInstance(application);
                    mDao = db.valleyDao();

                    mAllEventFilters =mDao.getAllEventsFilters();
                    mAllSGFilters = mDao.getAllSGFilters();
                    mAllMemberFilters = mDao.getAllMemberFilters();

                    mAllSmallGroups = mDao.getAllSmallGroups();
                }
            }
        }

    }

    //////ALL



    public LiveData<List<EventFilter>> getmAllEventFilters() {
        return mAllEventFilters;
    }

    public LiveData<List<SGFilter>> getmAllSGFilters() {return  mAllSGFilters;}

    public LiveData<List<MemberFilter>> getmAllMemberFilters() {
        return mAllMemberFilters;
    }


    public LiveData<List<Small_Group_Info>> getmAllSmallGroups() {return  mAllSmallGroups;}

    ///////Certain



    // Static inner classes below here to run database interactions in the background.

    /**
     * Inserts a word into the database.
     */


    /**
     * Deletes all words from the database (does not delete the table).
     */

    /**
     *  Deletes a single word from the database.
     */


    /**
     *  Updates a word in the database.


    /**
     *  Updates a word in the database.
     */


}
