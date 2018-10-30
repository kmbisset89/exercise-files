package com.example.kerrymbisset.valleybeta3.Database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.kerrymbisset.valleybeta3.Database.DataRepository;
import com.example.kerrymbisset.valleybeta3.EventFilter;
import com.example.kerrymbisset.valleybeta3.Events;
import com.example.kerrymbisset.valleybeta3.MemberFilter;
import com.example.kerrymbisset.valleybeta3.MemberInfo;
import com.example.kerrymbisset.valleybeta3.SGFilter;
import com.example.kerrymbisset.valleybeta3.Small_Group_Info;

import java.util.List;

public class ValleyViewModel extends AndroidViewModel {

    private DataRepository mRepository;

    private LiveData<List<Events>> mAllEvents;

    private LiveData<List<EventFilter>> mAllEventsFilter;

    private LiveData<List<SGFilter>> mAllSGFilter;

    private LiveData<List<MemberFilter>> mAllMemberFilters;

    private LiveData<List<MemberInfo>> mAllMembers;

    private LiveData<List<Small_Group_Info>> mAllSmallGroups;


    public ValleyViewModel(@NonNull Application application) {
        super(application);
        mRepository = new DataRepository(application);
        mAllEvents = mRepository.getmAllEvents();
        mAllEventsFilter = mRepository.getmAllEventFilters();
        mAllSGFilter = mRepository.getmAllSGFilters();
        mAllMemberFilters =mRepository.getmAllMemberFilters();
        mAllMembers =mRepository.getmAllMembers();
        mAllSmallGroups =mRepository.getmAllSmallGroups();
    }

    public LiveData<List<Events>> getAllEvents() {
        return mAllEvents;
    }

    public LiveData<List<EventFilter>> getmAllEventsFilter() {return mAllEventsFilter;}

    public LiveData<List<SGFilter>> getmAllSGFilter() {return mAllSGFilter;}

    public LiveData<List<Events>> getmCertainEvents(long eventId) {return mRepository.getmCertainEvents(eventId);}

    public LiveData<List<MemberInfo>> getmCertainMember(long memberSort) {return mRepository.getmCertainMember(memberSort);}

    public LiveData<List<MemberInfo>> checkEmail (String memberEmail) {return mRepository.checkEmail(memberEmail);}

    public LiveData<List<MemberFilter>> getmAllMemberFilters() {
        return mAllMemberFilters;
    }

    public LiveData<List<MemberInfo>> getmAllMembers() {
        return mAllMembers;
    }

    public void insert(Events events) {
        mRepository.insert(events);
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }

    public void deleteWord(Events events) {
        mRepository.deleteWord(events);
    }

    public void update(Events events) {
        mRepository.update(events);
    }
}
