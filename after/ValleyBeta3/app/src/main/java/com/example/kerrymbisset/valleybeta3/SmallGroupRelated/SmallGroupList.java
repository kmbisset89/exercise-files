package com.example.kerrymbisset.valleybeta3.SmallGroupRelated;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Filter;
import android.widget.Toast;

import com.example.kerrymbisset.valleybeta3.LoginActivity;
import com.example.kerrymbisset.valleybeta3.MemberRelated.MemberInfoAdapter;
import com.example.kerrymbisset.valleybeta3.MemberRelated.MembershipList;
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

import static com.example.kerrymbisset.valleybeta3.List_Activity.FILTER;

public class SmallGroupList extends AppCompatActivity {
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "Membership List Activity";
    ;
    private RecyclerView mRecyclerView;
    private SmallGroupListAdapter mAdapter;
    private String mKey;
    private String mListFilter;

    private final ArrayList<String> mSmallGroupKey = new ArrayList<>();
    private final ArrayList<String> mSmallGroupName = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_small_group_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupFirebaseAuth();
        mListFilter = getIntent().getStringExtra(FILTER);

        getSmallGroupData();


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

                    mKey = small_groups.getSg_key();
                    if (!mListFilter.toLowerCase().equals("all")) {
                        if (mListFilter.toLowerCase().equals(small_groups.getSg_filter().toLowerCase())) {
                            if (mSmallGroupKey.contains(mKey)) {
                                Log.d(TAG, "key is already contained");
                            } else {
                                mSmallGroupKey.add(mKey);
                                mSmallGroupName.add(small_groups.getSg_title());
                            }
                        } else {
                            Log.d(TAG, "filter not present");
                        }
                    } else {
                        if (mSmallGroupKey.contains(mKey)) {
                            Log.d(TAG, "key is already contained");
                        } else {
                            mSmallGroupKey.add(mKey);
                            mSmallGroupName.add(small_groups.getSg_title());
                        }

                    }


                    mAdapter = new SmallGroupListAdapter(SmallGroupList.this, mSmallGroupName, mSmallGroupKey);
                    mRecyclerView = findViewById(R.id.recycler_view);
                    // Connect the adapter with the recycler view.
                    mRecyclerView.setAdapter(mAdapter);
                    // Give the recycler view a default layout manager.
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(SmallGroupList.this));


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    private void setupFirebaseAuth() {
        Log.d(TAG, "setupFirebaseAuth: started.");

        mAuthListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                // User is signed in
                Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                //toastMessage("Successfully signed in with: " + user.getEmail());


            } else {
                // User is signed out

            }
            // ...
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
