package com.example.kerrymbisset.valleybeta3.Database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.kerrymbisset.valleybeta3.EventFilter;
import com.example.kerrymbisset.valleybeta3.Events;
import com.example.kerrymbisset.valleybeta3.MemberFilter;
import com.example.kerrymbisset.valleybeta3.MemberInfo;
import com.example.kerrymbisset.valleybeta3.SGFilter;

import java.util.List;

public class DataRepository {

    private ValleyDao mDao;
    private LiveData<List<Events>> mAllEvents;
    private LiveData<List<EventFilter>> mAllEventFilters;
    private LiveData<List<SGFilter>> mAllSGFilters;
    private LiveData<List<MemberFilter>> mAllMemberFilters;
    private LiveData<List<MemberInfo>> mAllMembers;
    public static volatile DataRepository sInstance = null;



    public DataRepository(Application application) {
        if (sInstance==null) {
            synchronized (DataRepository.class){
                if (sInstance== null){
                    AppDatabase db = AppDatabase.getInstance(application);
                    mDao = db.valleyDao();
                    mAllEvents = mDao.getAllEvents();
                    mAllEventFilters =mDao.getAllEventsFilters();
                    mAllSGFilters = mDao.getAllSGFilters();
                    mAllMemberFilters = mDao.getAllMemberFilters();
                    mAllMembers =mDao.getAllMembers();
                }
            }
        }

    }



    public LiveData<List<Events>> getmAllEvents() {
        return mAllEvents;
    }

    public LiveData<List<EventFilter>> getmAllEventFilters() {
        return mAllEventFilters;
    }

    public LiveData<List<SGFilter>> getmAllSGFilters() {return  mAllSGFilters;}

    public LiveData<List<MemberFilter>> getmAllMemberFilters() {
        return mAllMemberFilters;
    }

    public LiveData<List<MemberInfo>> getmAllMembers() {
        return mAllMembers;
    }

    public LiveData<List<Events>> getmCertainEvents(long eventsId) {
            return mDao.findSpecificEvent(eventsId);

    }

    public LiveData<List<MemberInfo>> getmCertainMember(long memberSort) {
        return mDao.findSpecificMembers(memberSort);

    }

    public LiveData<List<MemberInfo>> checkEmail(String memberEmail) {
        return mDao.checkEmail(memberEmail);

    }

    public void insert(Events events) {
        new insertEventAsyncTask(mDao).execute(events);
    }

    public void update(Events events)  {
        new updateEventAsyncTask(mDao).execute(events);
    }

    public void deleteAll()  {
        new deleteAllEventsAsyncTask(mDao).execute();
    }

    // Must run off main thread
    public void deleteWord(Events events) {
        new deleteEventsAsyncTask(mDao).execute(events);
    }


    // Static inner classes below here to run database interactions in the background.

    /**
     * Inserts a word into the database.
     */
    private static class insertEventAsyncTask extends AsyncTask<Events, Void, Void> {

        private ValleyDao mAsyncTaskDao;

        insertEventAsyncTask(ValleyDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Events... params) {
            mAsyncTaskDao.insertNewEvents(params[0]);
            return null;
        }
    }

    /**
     * Deletes all words from the database (does not delete the table).
     */
    private static class deleteAllEventsAsyncTask extends AsyncTask<Void, Void, Void> {
        private ValleyDao mAsyncTaskDao;

        deleteAllEventsAsyncTask(ValleyDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAllEvents();
            return null;
        }
    }

    /**
     *  Deletes a single word from the database.
     */
    private static class deleteEventsAsyncTask extends AsyncTask<Events, Void, Void> {
        private ValleyDao mAsyncTaskDao;

        deleteEventsAsyncTask(ValleyDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Events... params) {
            mAsyncTaskDao.deleteEvent(params[0]);
            return null;
        }
    }

    /**
     *  Updates a word in the database.
     */
    private static class updateEventAsyncTask extends AsyncTask<Events, Void, Void> {
        private ValleyDao mAsyncTaskDao;

        updateEventAsyncTask(ValleyDao dao) {
            mAsyncTaskDao = dao;
        }


        @Override
        protected Void doInBackground(Events... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }

    /**
     *  Updates a word in the database.
     */


}
