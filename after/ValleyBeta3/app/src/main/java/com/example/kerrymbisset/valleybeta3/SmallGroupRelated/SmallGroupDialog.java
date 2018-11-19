package com.example.kerrymbisset.valleybeta3.SmallGroupRelated;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.kerrymbisset.valleybeta3.EventRelated.CreateEventActivity;
import com.example.kerrymbisset.valleybeta3.EventRelated.EventInfoAdapter;
import com.example.kerrymbisset.valleybeta3.EventRelated.EventList;
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

public class SmallGroupDialog extends Fragment {
    //constants
    private static final int NUM_COLUMNS = 1;
    private String TAG = "SmallGroupDialog";

    //widgets
    private RecyclerView mRecyclerView;
    private SmallGroupDialogAdapter mAdapter;
    private FirebaseAuth.AuthStateListener mAuthListener;
    //vars
    private ArrayList<String> mSmallGroupNames = new ArrayList<>();
    private ArrayList<String> mSmallGroupNumber = new ArrayList<>();
    private String mNumber;
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    private SmallGroupDialogAdapter mRecyclerViewAdapter;
    private String mSelectedInterest;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fraglayout, container, false);
        mRecyclerView = view.findViewById(R.id.mRecyerID);
        setupFirebaseAuth();
        getInitInfo();

        return view;
    }

    private void getInitInfo() {

        Log.d(TAG, "getUserAccountData: getting the user's account information");

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

                    mNumber = (small_groups.getSg_group_number());
                    if (mSmallGroupNumber.contains(mNumber))
                    {
                        Log.d(TAG, "Name already in list");
                    } else {
                        mSmallGroupNumber.add(mNumber);
                        mSmallGroupNames.add(small_groups.getSg_title());
                    }

                }

                mAdapter = new SmallGroupDialogAdapter(getActivity(), mSmallGroupNames, mSmallGroupNumber);
                // Connect the adapter with the recycler view.
                mRecyclerView.setAdapter(mAdapter);
                // Give the recycler view a default layout manager.
                mRecyclerView.setLayoutManager(new GridLayoutManager( getActivity(),2 ));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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
