package com.example.kerrymbisset.valleybeta3.Database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.kerrymbisset.valleybeta3.Database.DataRepository;
import com.example.kerrymbisset.valleybeta3.EventFilter;

import com.example.kerrymbisset.valleybeta3.MemberFilter;

import com.example.kerrymbisset.valleybeta3.SGFilter;
import com.example.kerrymbisset.valleybeta3.Small_Group_Info;

import java.util.List;

public class ValleyViewModel extends AndroidViewModel {

    private DataRepository mRepository;


    private LiveData<List<EventFilter>> mAllEventsFilter;

    private LiveData<List<SGFilter>> mAllSGFilter;

    private LiveData<List<MemberFilter>> mAllMemberFilters;


    private LiveData<List<Small_Group_Info>> mAllSmallGroups;


    public ValleyViewModel(@NonNull Application application) {
        super(application);
        mRepository = new DataRepository(application);

        mAllEventsFilter = mRepository.getmAllEventFilters();
        mAllSGFilter = mRepository.getmAllSGFilters();
        mAllMemberFilters = mRepository.getmAllMemberFilters();

        mAllSmallGroups = mRepository.getmAllSmallGroups();
    }


    public LiveData<List<EventFilter>> getmAllEventsFilter() {
        return mAllEventsFilter;
    }

    public LiveData<List<SGFilter>> getmAllSGFilter() {
        return mAllSGFilter;
    }


    public LiveData<List<MemberFilter>> getmAllMemberFilters() {
        return mAllMemberFilters;
    }

}
