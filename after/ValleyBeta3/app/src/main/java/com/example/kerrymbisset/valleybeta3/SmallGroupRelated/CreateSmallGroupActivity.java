package com.example.kerrymbisset.valleybeta3.SmallGroupRelated;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.kerrymbisset.valleybeta3.AllConstants;
import com.example.kerrymbisset.valleybeta3.EventRelated.CreateEventActivity;
import com.example.kerrymbisset.valleybeta3.List_Activity;
import com.example.kerrymbisset.valleybeta3.LoginActivity;
import com.example.kerrymbisset.valleybeta3.OpeningScreen;
import com.example.kerrymbisset.valleybeta3.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class CreateSmallGroupActivity extends AppCompatActivity implements View.OnClickListener, AllConstants {
    private EditText mSmallGroupName, mPOC, mSmallGroupDesc, mSGLocation;
    private RadioGroup filter;
    private RadioButton mFilterAdult, mFilterYouth, mFilterWomens, mFilterMens;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String TAG = "CreateSmallGroupActivity";
    public static final String CHOICE = "com.example.kerrymbisset.valleybeta3.CHOICE";
    private FloatingActionButton fab;

    private String mSGName,mSGPOC, mSGDesc,mSGLoc,mSGkey, mSGFilter;


    @Override
    protected void onResume() {
        super.onResume();
        setupFirebaseAuth();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_small_group);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = findViewById(R.id.fab);
        Button saveButton = findViewById(R.id.create_small_group);
        saveButton.setOnClickListener(this);

        mFilterAdult = findViewById(R.id.sg_filter_adult);
        mFilterAdult.setOnClickListener(this);

        mFilterYouth = findViewById(R.id.sg_filter_youth);
        mFilterYouth.setOnClickListener(this);

        mFilterMens = findViewById(R.id.sg_filter_womens);
        mFilterMens.setOnClickListener(this);

        mFilterWomens = findViewById(R.id.sg_filter_womens);
        mFilterWomens.setOnClickListener(this);

        mSmallGroupName = findViewById(R.id.small_group_name);
        mSmallGroupDesc = findViewById(R.id.small_group_desc);
        mSGLocation = findViewById(R.id.small_group_meeting);
        mPOC = findViewById(R.id.small_group_poc);
    }

    /*
        ----------------------------- Firebase setup ---------------------------------
     */
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
                Log.d(TAG, "onAuthStateChanged:signed_out");
                Toast.makeText(CreateSmallGroupActivity.this, "Signed out", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CreateSmallGroupActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        };
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.create_small_group: {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

                mSGkey = UUID.randomUUID().toString();

                if (!mSmallGroupName.getText().toString().equals("")) {
                    reference.child(getString(R.string.dbnode_small_groups))
                            .child(mSGkey)
                            .child(getString(R.string.field_small_group_name))
                            .setValue(mSmallGroupName.getText().toString());

                }

                if (!mPOC.getText().toString().equals("")) {
                    reference.child(getString(R.string.dbnode_small_groups))
                            .child(mSGkey)
                            .child(getString(R.string.field_small_group_poc))
                            .setValue(mPOC.getText().toString());

                }

                if (!mSGLocation.getText().toString().equals("")) {
                    reference.child(getString(R.string.dbnode_small_groups))
                            .child(mSGkey)
                            .child(getString(R.string.field_small_group_meeting))
                            .setValue(mSGLocation.getText().toString());

                }

                if (!mSmallGroupDesc.getText().toString().equals("")) {
                    reference.child(getString(R.string.dbnode_small_groups))
                            .child(mSGkey)
                            .child(getString(R.string.field_small_group_desc))
                            .setValue(mSmallGroupDesc.getText().toString());

                }
                    int random = ThreadLocalRandom.current().nextInt(1, 1000);
                    reference.child(getString(R.string.dbnode_small_groups))
                            .child(mSGkey)
                            .child(getString(R.string.field_small_group_number))
                            .setValue(Integer.toString(random));

                if (!mSGFilter.equals("")) {
                    reference.child(getString(R.string.dbnode_small_groups))
                            .child(mSGkey)
                            .child(getString(R.string.field_small_group_filter))
                            .setValue(mSGFilter);
                }

                reference.child(getString(R.string.dbnode_small_groups))
                        .child(mSGkey)
                        .child(getString(R.string.field_small_group_created_by))
                        .setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());

                reference.child(getString(R.string.dbnode_small_groups))
                        .child(mSGkey)
                        .child(getString(R.string.field_small_group_key))
                        .setValue(mSGkey);




                Intent i = new Intent(this, List_Activity.class);
                i.putExtra(CHOICE, SMALLGROUPFILTERSEL);
                startActivity(i);
                finish();
                break;
            }
            case R.id.sg_filter_adult: {
                mSGFilter = ADULTFILTER;
                DeselectAll();
                mFilterAdult.setChecked(true);
                Toast.makeText(CreateSmallGroupActivity.this, "Small Group Filter set to Adult", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.sg_filter_youth: {
                mSGFilter = YOUTHFILTER;
                DeselectAll();
                mFilterYouth.setChecked(true);
                Toast.makeText(CreateSmallGroupActivity.this, "Small Group Filter set to Youth", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.sg_filter_mens: {
                 mSGFilter = MENSFILTER;
                DeselectAll();
                mFilterMens.setChecked(true);
                Toast.makeText(CreateSmallGroupActivity.this, "Small Group Filter set to Mens", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.sg_filter_womens: {
                mSGFilter = WOMENFILTER;
                DeselectAll();
                mFilterWomens.setChecked(true);
                Toast.makeText(CreateSmallGroupActivity.this, "Small Group Filter set to Adult", Toast.LENGTH_SHORT).show();
                break;
            }

        }

    }
    private void DeselectAll() {

        mFilterAdult.setChecked(false);
        mFilterYouth.setChecked(false);
        mFilterMens.setChecked(false);
        mFilterWomens.setChecked(false);
    }
}
