package com.example.kerrymbisset.valleybeta3.EventRelated;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.kerrymbisset.valleybeta3.LoginActivity;
import com.example.kerrymbisset.valleybeta3.MemberRelated.MemberInfoAdapter;
import com.example.kerrymbisset.valleybeta3.MemberRelated.MembershipList;
import com.example.kerrymbisset.valleybeta3.R;
import com.example.kerrymbisset.valleybeta3.models.Events;
import com.example.kerrymbisset.valleybeta3.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EventList extends AppCompatActivity {

    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "Event List Activity";
    private final ArrayList<String> mEventList = new ArrayList<>();
    private final ArrayList<String> mEventDate = new ArrayList<>();
    private final ArrayList<String> mEventKey = new ArrayList<>();
    public static final String FILTER = "com.example.kerrymbisset.valleybeta3.FILTER";

    private RecyclerView mRecyclerView;
    private EventInfoAdapter mAdapter;
    private String mTitle;
    private String mKey;
    private String mDates;
    private String mFilter;

    private int Sub1 = -1;
    private int Sub2 = -1;
    private int Sub3 = -1;
    private int Sub4 = -1;
    private int Sub5 = -1;


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        mFilter = getIntent().getStringExtra(FILTER);
        setSupportActionBar(toolbar);
        setupFirebaseAuth();

        setContentView(R.layout.activity_event_list);

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            getMemberSubscriptions();
        }
        getEventData();

        FloatingActionButton fab = findViewById(R.id.fab_add_event);

        fab.setOnClickListener(v -> {
            Intent intent = new Intent (EventList.this, CreateEventActivity.class);
            startActivity(intent);
        });

    }


    private void getEventData() {
        Log.d(TAG, "getUserAccountData: getting the user's account information");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        /*
            ---------- QUERY Method 1 ----------
         */
        Query query1 = reference.child(getString(R.string.dbnode_events))
                .orderByChild("event_millis");
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //this loop will return a single result
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
//                    Log.d(TAG, "onDataChange: (QUERY METHOD 1) found user: "
//                           + singleSnapshot.getValue(User.class).toString());
                    Events events = singleSnapshot.getValue(Events.class);

                    mKey = (events.getEvent_key());
                    mTitle = (events.getEvent_title());
                    mDates = (events.getEvent_date());
                    if (!mFilter.toLowerCase().equals("all")) {

                        if (mFilter.equals("SMALL GROUP")) {
                            int groupNumber = Integer.parseInt(events.getGroup_number());
                            if (groupNumber == Sub1 || groupNumber == Sub2 || groupNumber == Sub3 ||
                                    groupNumber == Sub4 || groupNumber == Sub5) {
                                if (mEventKey.contains(mKey)) {
                                    Log.d(TAG, "Name already in list");
                                } else {
                                    mEventList.add(mTitle);
                                    mEventDate.add(mDates);
                                    mEventKey.add(mKey);
                                }
                                if (mEventList.size() == 0 || mEventDate.size() ==0 || mEventKey.size() == 0) {
                                    Toast.makeText(getBaseContext(), "You are not subscribed to any small group", Toast.LENGTH_SHORT).show();
                                }

                            }
                        } else if (mFilter.equals(events.getEvent_filter())) {
                            if (mEventKey.contains(mKey)) {
                                Log.d(TAG, "Name already in list");
                            } else {
                                mEventList.add(mTitle);
                                mEventDate.add(mDates);
                                mEventKey.add(mKey);
                            }

                        }

                    } else {
                        if (mEventKey.contains(mKey)) {
                            Log.d(TAG, "Name already in list");
                        } else {
                            mEventList.add(mTitle);
                            mEventDate.add(mDates);
                            mEventKey.add(mKey);
                        }
                    }

                }
                mAdapter = new EventInfoAdapter(EventList.this, mEventList, mEventDate , mEventKey);
                mRecyclerView = findViewById(R.id.recycler_view);
                // Connect the adapter with the recycler view.
                mRecyclerView.setAdapter(mAdapter);
                // Give the recycler view a default layout manager.
                mRecyclerView.setLayoutManager(new LinearLayoutManager(EventList.this));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void getMemberSubscriptions() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        /*
            ---------- QUERY Method 1 ----------
         */
        Query query1 = reference.child(getString(R.string.dbnode_users))
                .orderByKey()
                .equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //this loop will return a single result
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
//                    Log.d(TAG, "onDataChange: (QUERY METHOD 1) found user: "
//                           + singleSnapshot.getValue(User.class).toString());
                    User user = singleSnapshot.getValue(User.class);

                    if (!user.getSmall_group_subscription1().equals("")) {
                        Sub1 = Integer.parseInt(user.getSmall_group_subscription1());
                    }

                    if (!user.getSmall_group_subscription2().equals("")) {
                        Sub2 = Integer.parseInt(user.getSmall_group_subscription2());
                    }

                    if (!user.getSmall_group_subscription3().equals("")) {
                        Sub3 = Integer.parseInt(user.getSmall_group_subscription3());
                    }

                    if (!user.getSmall_group_subscription4().equals("")) {
                        Sub4 = Integer.parseInt(user.getSmall_group_subscription4());
                    }

                    if (!user.getSmall_group_subscription5().equals("")) {
                        Sub5 = Integer.parseInt(user.getSmall_group_subscription5());
                    }


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    /*
           ----------------------------- Firebase setup ---------------------------------
        */
    private void setupFirebaseAuth() {
        Log.d(TAG, "setupFirebaseAuth: started.");

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    //toastMessage("Successfully signed in with: " + user.getEmail());


                } else {
                       Log.d(TAG, "No user signed in");
                       Toast.makeText(getBaseContext(), "Information may be limited until signed in", Toast.LENGTH_SHORT).show();

                }
                // ...
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthListener);
        }

    }





}
