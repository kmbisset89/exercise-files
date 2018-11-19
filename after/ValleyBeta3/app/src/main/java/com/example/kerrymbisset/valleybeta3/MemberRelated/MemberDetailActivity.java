package com.example.kerrymbisset.valleybeta3.MemberRelated;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kerrymbisset.valleybeta3.LoginActivity;
import com.example.kerrymbisset.valleybeta3.R;
import com.example.kerrymbisset.valleybeta3.SettingsActivity;
import com.example.kerrymbisset.valleybeta3.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MemberDetailActivity extends AppCompatActivity {

    private TextView mName, mPhone, mEmail, mAddress, mCity, mState, mZip, mStatus;
    private TextView mConEmail, mConPhone, mConAddress;
    private ImageButton mCall;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "MemberDetailActivity";
    public static final String MEMBERNAME = "com.example.kerrymbisset.valleybeta3.MemberRelated.MEMBERNAME";
    private String intentName;

    @Override
    protected void onResume() {
        super.onResume();
        intentName = getIntent().getStringExtra(MEMBERNAME);
        mName = findViewById(R.id.member_name_detail);
        mPhone = findViewById(R.id.member_phone_detail);
        mEmail = findViewById(R.id.member_email_detail);
        mAddress = findViewById(R.id.member_address_detail);
        mCity = findViewById(R.id.member_city_detail);
        mState = findViewById(R.id.member_state_detail);
        mZip = findViewById(R.id.member_zip_detail);
        mStatus = findViewById(R.id.member_status_detail);
        mConEmail = findViewById(R.id.constant_email);
        mConPhone = findViewById(R.id.constant_phone);
        mConAddress = findViewById(R.id.constant_address);
        mCall = findViewById(R.id.call);
       getUserAccountData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        intentName = getIntent().getStringExtra(MEMBERNAME);

        mName = findViewById(R.id.member_name_detail);
        mPhone = findViewById(R.id.member_phone_detail);
        mEmail = findViewById(R.id.member_email_detail);
        mAddress = findViewById(R.id.member_address_detail);
        mCity = findViewById(R.id.member_city_detail);
        mState = findViewById(R.id.member_state_detail);
        mZip = findViewById(R.id.member_zip_detail);
        mStatus = findViewById(R.id.member_status_detail);
        mConEmail = findViewById(R.id.constant_email);
        mConPhone = findViewById(R.id.constant_phone);
        mConAddress = findViewById(R.id.constant_address);
        mCall = findViewById(R.id.call);

        setupFirebaseAuth();
        getUserAccountData();

        mCall.setOnClickListener(v -> dialPhoneNumber(mPhone.getText().toString()));
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
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    Toast.makeText(MemberDetailActivity.this, "Signed out", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MemberDetailActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
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

    private void getUserAccountData() {
        Log.d(TAG, "getUserAccountData: getting the user's account information");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        /*
            ---------- QUERY Method 1 ----------
         */
        Query query1 = reference.child(getString(R.string.dbnode_users))
                .orderByKey();
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //this loop will return a single result
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
//                    Log.d(TAG, "onDataChange: (QUERY METHOD 1) found user: "
//                           + singleSnapshot.getValue(User.class).toString());
                    User user = singleSnapshot.getValue(User.class);

                    String intentName = getIntent().getStringExtra(MEMBERNAME);
                    if (user.getName().equals(intentName)){
                        mName.setText(user.getName());
                        mEmail.setText(user.getEmail());
                        mPhone.setText(user.getPhone());
                        mAddress.setText(user.getStreet_address());
                        mCity.setText(user.getCity());
                        mState.setText(user.getState());
                        mState.setText(user.getState());
                        mZip.setText(user.getZip());
                        mStatus.setText(user.getMember_filter());

                        if (user.getShow_email().equals("YES")) {
                            mEmail.setVisibility(View.VISIBLE);
                            mConEmail.setVisibility(View.VISIBLE);
                        } else {
                            mEmail.setVisibility(View.GONE);
                            mConEmail.setVisibility(View.GONE);
                        }

                        if (user.getShow_phone().equals("YES")) {
                            mPhone.setVisibility(View.VISIBLE);
                            mConPhone.setVisibility(View.VISIBLE);
                            mCall.setVisibility(View.VISIBLE);
                        } else {
                            mPhone.setVisibility(View.GONE);
                            mConPhone.setVisibility(View.GONE);
                            mCall.setVisibility(View.GONE);
                        }

                        if (user.getShow_address().equals("YES")) {
                            mAddress.setVisibility(View.VISIBLE);
                            mCity.setVisibility(View.VISIBLE);
                            mState.setVisibility(View.VISIBLE);
                            mZip.setVisibility(View.VISIBLE);
                            mConAddress.setVisibility(View.VISIBLE);
                        } else {
                            mAddress.setVisibility(View.GONE);
                            mCity.setVisibility(View.GONE);
                            mState.setVisibility(View.GONE);
                            mZip.setVisibility(View.GONE);
                            mConAddress.setVisibility(View.GONE);
                        }

                    }
                }
            }
            @Override
            public void onCancelled (DatabaseError databaseError){

            }
        });

    }

    public void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(intent);
        }
    }


