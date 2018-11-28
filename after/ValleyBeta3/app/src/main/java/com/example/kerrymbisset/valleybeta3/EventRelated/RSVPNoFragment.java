package com.example.kerrymbisset.valleybeta3.EventRelated;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kerrymbisset.valleybeta3.R;
import com.example.kerrymbisset.valleybeta3.models.Reservation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class RSVPNoFragment extends Fragment {

    private static final int NUM_COLUMNS = 1;
    private String TAG = "SmallGroupDialog";

    //widgets
    private RecyclerView mRecyclerView;
    private RSVPNoAdapter mAdapter;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String mEventKey;
    private String mReservationKey;
    //vars
    private ArrayList<String> mMemberName = new ArrayList<>();
    private String mMember;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fraglayout, container, false);
        mRecyclerView = view.findViewById(R.id.mRecyerID);
        setupFirebaseAuth();
        Bundle b = getArguments();
        mEventKey = b.getString("EventKey");
        getInitInfo();

        return view;
    }

    private void getInitInfo() {

        Log.d(TAG, "getUserAccountData: getting the user's account information");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        /*
            ---------- QUERY Method 1 ----------
         */
        Query query1 = reference.child(getString(R.string.dbnode_reservation))
                .child(mEventKey)
                .orderByKey();
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //this loop will return a single result
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
//                    Log.d(TAG, "onDataChange: (QUERY METHOD 1) found user: "
//                           + singleSnapshot.getValue(User.class).toString());
                    Reservation reservation = singleSnapshot.getValue(Reservation.class);

                    mMember= reservation.getMember_name();

                    if (reservation.getReply().equals("NO")) {
                        if (mMemberName.contains(mMember)) {
                            Log.d(TAG, "Member already in view");
                        } else {
                            mMemberName.add(mMember);
                        }
                    } else {
                        Log.d(TAG,  "Member replied no");
                        if (mMemberName.contains(mMember)){
                           int position = mMemberName.indexOf(mMember);
                           mMemberName.remove(position);
                           mAdapter.notifyItemRemoved(position);
                           mAdapter.notifyItemRangeChanged(position, mMemberName.size());
                        }

                    }


                }
                Context context = getActivity();
                if (context != null) {
                    mAdapter = new RSVPNoAdapter(context, mMemberName);
                }
                // Connect the adapter with the recycler view.
                mRecyclerView.setAdapter(mAdapter);
                // Give the recycler view a default layout manager.
                mRecyclerView.setLayoutManager(new GridLayoutManager( getActivity(),1 ));
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
