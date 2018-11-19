package com.example.kerrymbisset.valleybeta3.EventRelated;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kerrymbisset.valleybeta3.LoginActivity;
import com.example.kerrymbisset.valleybeta3.R;
import com.example.kerrymbisset.valleybeta3.SettingsActivity;
import com.example.kerrymbisset.valleybeta3.models.Events;
import com.example.kerrymbisset.valleybeta3.models.Reservation;
import com.example.kerrymbisset.valleybeta3.models.User;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

public class Event_Details extends AppCompatActivity {
    private static final String TAG = "EventDetails";
    public static final String EVENTKEY = "com.example.kerrymbisset.valleybeta3.EventRelated.EVENTKEY";
    private String mEventKey;
    private TextView mConEmail, mConPhone, mConAddress;
    private TextView mEventTitle;
    private TextView mEventDate;
    private TextView mEventTime;
    private TextView mEventDesc;
    private TextView mEventLocation;
    private RSVPYesFragment mRSPVYesFragment;
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    private String mReservationKey;
    private Bundle args;
    private String mMemberName;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onResume() {
        super.onResume();
        mEventKey = getIntent().getStringExtra(EVENTKEY);
        mEventTitle = findViewById(R.id.event_detail_title);
        mEventDate = findViewById(R.id.event_detail_date);
        mEventTime = findViewById(R.id.event_detail_time);
        mEventDesc = findViewById(R.id.event_detail_desc);
        mEventLocation = findViewById(R.id.event_detail_location);
        setupFirebaseAuth();
        getEventDetails();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event__details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupFirebaseAuth();
        mEventKey = getIntent().getStringExtra(EVENTKEY);
        mEventTitle = findViewById(R.id.event_detail_title);
        mEventDate = findViewById(R.id.event_detail_date);
        mEventTime = findViewById(R.id.event_detail_time);
        mEventDesc = findViewById(R.id.event_detail_desc);
        mEventLocation = findViewById(R.id.event_detail_location);

        mEventKey = getIntent().getStringExtra(EVENTKEY);

        args = new Bundle();
        args.putString("EventKey", mEventKey);

        mRSPVYesFragment = new RSVPYesFragment();
        mRSPVYesFragment.setArguments(args);


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frame_rsvp_yes, mRSPVYesFragment, "home");

        transaction.commit();


        Button RSVP = findViewById(R.id.RSVP);
        RSVP.setOnClickListener(v -> {
            boolean answer = true;
            addToList(answer);

            args = new Bundle();
            args.putString("EventKey", mEventKey);

            mRSPVYesFragment = new RSVPYesFragment();
            mRSPVYesFragment.setArguments(args);

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.detach(mRSPVYesFragment)
                    .add(R.id.frame_rsvp_yes, mRSPVYesFragment, "home")
                    .attach(mRSPVYesFragment).commit();

        });

        Button decline = findViewById(R.id.Decline);
        decline.setOnClickListener(v -> {
            boolean answer = false;
            addToList(answer);

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.detach(mRSPVYesFragment).attach(mRSPVYesFragment).commit();
        });

    }

    private void addToList(boolean answer) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        mReservationKey = UUID.randomUUID().toString();

        reference.child(getString(R.string.dbnode_reservation))
                .child(mEventKey)
                .child(mReservationKey)
                .child(getString(R.string.field_reservation_member_number))
                .setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());

        reference.child(getString(R.string.dbnode_reservation))
                .child(mEventKey)
                .child(mReservationKey)
                .child(getString(R.string.field_reservation_event_key))
                .setValue(mEventKey);



        if (answer) {

            reference.child(getString(R.string.dbnode_reservation))
                    .child(mEventKey)
                    .child(mReservationKey)
                    .child(getString(R.string.field_reservation_reply))
                    .setValue("YES");
        } else {
            reference.child(getString(R.string.dbnode_reservation))
                    .child(mEventKey)
                    .child(mReservationKey)
                    .child(getString(R.string.field_reservation_reply))
                    .setValue("NO");
        }

        reference.child(getString(R.string.dbnode_reservation))
                .child(mEventKey)
                .child(mReservationKey)
                .child(getString(R.string.field_reservation_member_name))
                .setValue(mMemberName);

    }


    private void getEventDetails() {
        Log.d(TAG, "getUserAccountData: getting the event's information");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        String intentName = getIntent().getStringExtra(EVENTKEY);
        /*
            ---------- QUERY Method 1 ----------
         */
        Query query1 = reference.child(getString(R.string.dbnode_events))
                .orderByKey();
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //this loop will return a single result
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
//                    Log.d(TAG, "onDataChange: (QUERY METHOD 1) found user: "
//                           + singleSnapshot.getValue(User.class).toString());
                    Events events = singleSnapshot.getValue(Events.class);

                    if (events.getEvent_key().equals(intentName)) {
                        mEventTitle.setText(events.getEvent_title());
                        mEventDate.setText(events.getEvent_date());
                        mEventTime.setText(events.getEvent_time());
                        mEventDesc.setText(events.getEvent_desc());
                        mEventLocation.setText(events.getEvent_location());


                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Query query2 = reference.child(getString(R.string.dbnode_users))
                .orderByKey()
                .equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //this loop will return a single result
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
//                    Log.d(TAG, "onDataChange: (QUERY METHOD 1) found user: "
//                           + singleSnapshot.getValue(User.class).toString());
                    User user = singleSnapshot.getValue(User.class);

                    if (user.getUser_id().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                       mMemberName = user.getName();
                    }


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.event_detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.addtocalendar) {

                Intent intent = new Intent(Intent.ACTION_EDIT)
                        .setType("vnd.android.cursor.item/event");
                        intent.putExtra(CalendarContract.Events.TITLE, mEventTitle.toString())
                        .putExtra(CalendarContract.Events.EVENT_LOCATION, mEventLocation.toString())
                        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, mEventTime.toString());
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }

            return true;
        }


        return super.onOptionsItemSelected(item);
    }


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
