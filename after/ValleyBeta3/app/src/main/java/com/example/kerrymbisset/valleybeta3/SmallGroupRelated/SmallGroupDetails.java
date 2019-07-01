package com.example.kerrymbisset.valleybeta3.SmallGroupRelated;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.kerrymbisset.valleybeta3.EventRelated.EventList;
import com.example.kerrymbisset.valleybeta3.LoginActivity;
import com.example.kerrymbisset.valleybeta3.R;
import com.example.kerrymbisset.valleybeta3.models.Small_Groups;
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

public class SmallGroupDetails extends AppCompatActivity implements View.OnClickListener {
    private TextView mSGDetailTitle, mSGDetailPOC, mSGDetailDesc, mSGDetailLocation;
    private Button mSubscribe, mMemeber;
    private ImageView mInfoIcon, mMapIcon;
    public static final String KEY = "com.example.kerrymbisset.valleybeta3.SmallGroupRelated.KEY";
    private String mKey;
    private String mGroupNumber;
    private ArrayList<String> mSubscriptionNumbers = new ArrayList<>();
    private ArrayList<String> mEmptySlot = new ArrayList<>();
    private String TAG = "SmallGroupDetail";
    private FirebaseAuth.AuthStateListener mAuthListener;
    private boolean loggedIn = false;



    @Override
    protected void onResume() {
        super.onResume();

        checkAuthenticationState();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_small_group_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupFirebaseAuth();
        if (loggedIn) {
            checkSubscriptions();
        }
        mSGDetailTitle = findViewById(R.id.Small_Group_Title);
        mSGDetailPOC = findViewById(R.id.sg_detail_POC);
        mSGDetailDesc = findViewById(R.id.sg_detail_desc);
        mSGDetailLocation = findViewById(R.id.sg_detail_location);
        mSubscribe = findViewById(R.id.subscribe_button);
        mMemeber = findViewById(R.id.sg_member_btn);
        mMapIcon = findViewById(R.id.sg_detail_map_btn);
        mInfoIcon = findViewById(R.id.info_icon_btn);


        getSmallGroupData();

        checkAuthenticationState();
        if (loggedIn) {
            mSubscribe.setOnClickListener(V -> {
                addSubscription();
            });
            
            mMemeber.setOnClickListener(v -> {
                showMember();
            });

        } else {
            mSubscribe.setVisibility(View.GONE);
            mMemeber.setVisibility(View.GONE);
        }
    }

    private void showMember() {
    }

    private void addSubscription() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        boolean notAlreadySubscribed = true;
        for (int i = 0; i < 5; i++) {

            if (mSubscriptionNumbers.get(i) != "" && mSubscriptionNumbers.get(i).equals(mGroupNumber)) {
                notAlreadySubscribed = false;
                Toast.makeText(getBaseContext(), "Already subscribed to this small group", Toast.LENGTH_SHORT).show();
                break;
            }

            if (mSubscriptionNumbers.get(i).equals("") && notAlreadySubscribed) {
                reference.child(getString(R.string.dbnode_users))
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child((findSubscriptionSpot(i)))
                        .setValue(mGroupNumber);
                Toast.makeText(getBaseContext(),
                        "You have successfully subscribed to this small group", Toast.LENGTH_SHORT).show();
                break;

            } else {
                Toast.makeText(getBaseContext(),
                        "You have already subscribed to the maximum number of small groups", Toast.LENGTH_SHORT).show();
            }
        }

    }


    private String findSubscriptionSpot(int i) {
        String choice;
        switch (i) {
            case 0:
                choice = getString(R.string.field_subscription1);
                break;
            case 1:
                choice = getString(R.string.field_subscription2);
                break;
            case 2:
                choice = getString(R.string.field_subscription3);
                break;
            case 3:
                choice = getString(R.string.field_subscription4);
                break;
            case 4:
                choice = getString(R.string.field_subscription5);
                break;
            default:
                choice = "";
        }

        return choice;
    }


    private void checkSubscriptions() {
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

                    mSubscriptionNumbers.add(user.getSmall_group_subscription1());
                    mSubscriptionNumbers.add(user.getSmall_group_subscription2());
                    mSubscriptionNumbers.add(user.getSmall_group_subscription3());
                    mSubscriptionNumbers.add(user.getSmall_group_subscription4());
                    mSubscriptionNumbers.add(user.getSmall_group_subscription5());

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void getSmallGroupData() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        /*
            ---------- QUERY Method 1 ----------
         */
        Query query1 = reference.child(getString(R.string.dbnode_small_groups))
                .orderByChild("sg_title");
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //this loop will return a single result
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
//                    Log.d(TAG, "onDataChange: (QUERY METHOD 1) found user: "
//                           + singleSnapshot.getValue(User.class).toString());
                    Small_Groups small_groups = singleSnapshot.getValue(Small_Groups.class);

                    mKey = getIntent().getStringExtra(KEY);

                    if (mKey.equals(small_groups.getSg_key())) {
                        mSGDetailTitle.setText(small_groups.getSg_title());
                        mSGDetailPOC.setText(small_groups.getSg_point_of_contact());
                        mSGDetailDesc.setText(small_groups.getSg_desc());
                        mSGDetailLocation.setText(small_groups.getSg_meeting_place());
                        mGroupNumber = small_groups.getSg_group_number();


                    }


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onClick(View v) {

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
                    loggedIn = true;


                } else {
                    loggedIn = false;
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

    private void checkAuthenticationState() {
        Log.d(TAG, "checkAuthenticationState: checking authentication state.");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null) {
            Log.d(TAG, "checkAuthenticationState: user is null, the are logged out.");
            loggedIn = false;
        } else {
            Log.d(TAG, "checkAuthenticationState: user is authenticated.");
            loggedIn = true;
        }
    }

}
