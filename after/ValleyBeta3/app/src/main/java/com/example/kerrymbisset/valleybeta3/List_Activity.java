package com.example.kerrymbisset.valleybeta3;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.example.kerrymbisset.valleybeta3.Database.ValleyViewModel;
import com.example.kerrymbisset.valleybeta3.EventRelated.CreateEventActivity;
import com.example.kerrymbisset.valleybeta3.EventRelated.EventList;
import com.example.kerrymbisset.valleybeta3.SmallGroupRelated.CreateSmallGroupActivity;
import com.example.kerrymbisset.valleybeta3.SmallGroupRelated.SmallGroupList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.kerrymbisset.valleybeta3.EventRelated.CreateEventActivity.*;



public class List_Activity extends AppCompatActivity   {


    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_WORD_ACTIVITY_REQUEST_CODE = 2;
    public static final String CHOICE = "com.example.kerrymbisset.valleybeta3.CHOICE";
    public static final String FILTER = "com.example.kerrymbisset.valleybeta3.FILTER";
    public static final String KEY = "com.example.kerrymbisset.valleybeta3.KEY";
    public static final String EXTRA_DATA_UPDATE_EVENT_TITLE = "extra_event_title_to_be_updated";
    public static final String EXTRA_DATA_UPDATE_EVENT_DATE = "extra_event_date_to_be_updated";
    public static final String EXTRA_DATA_UPDATE_EVENT_TIME = "extra_event_time_to_be_updated";
    public static final String EXTRA_DATA_UPDATE_EVENT_DESC = "extra_event_desc_to_be_updated";
    public static final String EXTRA_DATA_UPDATE_EVENT_MILLIS = "extra_event_millis_to_be_updated";
    public static final String EXTRA_DATA_UPDATE_EVENT_FILTER = "extra_event_filter_to_be_updated";
    public static final String EXTRA_DATA_ID = "extra_data_id";
    private String TAG ="List_Activity";
    private FirebaseAuth.AuthStateListener mAuthListener;

    private ValleyViewModel mViewModel;
    private int choice;

    private static int FILTERLEVEL =0;
    private int ALL =1 ;
    private boolean isLoggedIn;
    private String loggedInName;
    private int MEMBERACCESSLEVEL;
    private FloatingActionButton fab;

    public static int getFILTERLEVEL() {
        return FILTERLEVEL;
    }

    public void setFILTERLEVEL(int FILTERLEVEL) {
        this.FILTERLEVEL = FILTERLEVEL;
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

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_activity);
        setFILTERLEVEL( getIntent().getIntExtra(FILTER, 0));
        fab = findViewById(R.id.fab1);
        setupFirebaseAuth();
        choice = getIntent().getIntExtra(CHOICE,0);



        //Prep work for switch statment
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        // Set up the WordViewModel.
        mViewModel = ViewModelProviders.of(this).get(ValleyViewModel.class);

        switch (choice) {
            case EVENTFILTERSEL:

                final EventFilterRecyclerAdapter adapter2 = new EventFilterRecyclerAdapter(this);
                recyclerView.setAdapter(adapter2);
                recyclerView.setLayoutManager(new GridLayoutManager(this,2));

                // Get all the words from the database
                // and associate them to the adapter.
                mViewModel.getmAllEventsFilter().observe(this, new Observer<List<EventFilter>>() {
                    @Override
                    public void onChanged(@Nullable final List<EventFilter> eventFilters) {
                        // Update the cached copy of the words in the adapter.
                        adapter2.setEventsFilter(eventFilters);



                    }
                });

                adapter2.setOnItemClickListener(new EventFilterRecyclerAdapter.ClickListener()  {

                    @Override
                    public void onItemClick(View v, int position) {
                        EventFilter eventFilter = adapter2.getEventAtPosition(position);
                        Intent intent = new Intent(List_Activity.this, EventList.class);
                        intent
                                .putExtra(CHOICE, EVENTS)
                                .putExtra(FILTER, eventFilter.getFilterName());
                        startActivity(intent);
                    }
                });

                break;

            case EVENTS:

                    break;

            case SMALLGROUPFILTERSEL:
                final SmallGroupFilterRecyclerAdapter smallGroupFilterRecyclerAdapter = new SmallGroupFilterRecyclerAdapter(this);
                recyclerView.setAdapter(smallGroupFilterRecyclerAdapter);
                recyclerView.setLayoutManager(new GridLayoutManager(this,2));



                // Get all the words from the database
                // and associate them to the adapter.
                mViewModel.getmAllSGFilter().observe(this, new Observer<List<SGFilter>>() {
                    @Override
                    public void onChanged(@Nullable final List<SGFilter> sgFilters) {
                        // Update the cached copy of the words in the adapter.
                        smallGroupFilterRecyclerAdapter.setSGFilter(sgFilters);
                    }
                });

                smallGroupFilterRecyclerAdapter.setOnItemClickListener((v, position) -> {
                    SGFilter sgFilter = smallGroupFilterRecyclerAdapter.getSGAtPosition(position);
                    Intent intent = new Intent(List_Activity.this, SmallGroupList.class);
                    intent
                            .putExtra(CHOICE, SMALLGROUP)
                            .putExtra(FILTER, sgFilter.getFilterName());
                    startActivity(intent);
                });


                break;

            case MEMBERSFILTERSEL:
                final MembersFilterRecyclerAdapter membersFilterRecyclerAdapter = new MembersFilterRecyclerAdapter(this);
                recyclerView.setAdapter(membersFilterRecyclerAdapter);
                recyclerView.setLayoutManager(new GridLayoutManager(this,2));

                // Get all the words from the database
                // and associate them to the adapter.
                mViewModel.getmAllMemberFilters().observe(this, new Observer<List<MemberFilter>>() {
                    @Override
                    public void onChanged(@Nullable final List<MemberFilter> memberFilters) {
                        // Update the cached copy of the words in the adapter.
                        membersFilterRecyclerAdapter.setSGFilter(memberFilters);
                    }
                });

                membersFilterRecyclerAdapter.setOnItemClickListener(new MembersFilterRecyclerAdapter.ClickListener()  {

                    @Override
                    public void onItemClick(View v, int position) {
                        MemberFilter sgFilter = membersFilterRecyclerAdapter.getMemberAtPosition(position);
                        Intent intent = new Intent(List_Activity.this, List_Activity.class);
                        intent
                                .putExtra(CHOICE, MEMBERS)
                                .putExtra(FILTER, position+1);
                        startActivity(intent);
                    }
                });

                break;

            case MEMBERS:


                break;
        }


////////////////////////////End of Switch///////////


        // Floating action button setup

        fab = findViewById(R.id.fab1);
        fab.setOnClickListener(view -> {
            if (choice == EVENTFILTERSEL) {
                Intent intent = new Intent(List_Activity.this, CreateEventActivity.class);
                startActivity(intent);
            }
            if (choice == SMALLGROUPFILTERSEL) {
                Intent intent = new Intent(List_Activity.this, CreateSmallGroupActivity.class);
                startActivity(intent);
            }

        });


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
                isLoggedIn =true;
                fab.setVisibility(View.VISIBLE);

            } else {
                isLoggedIn =false;
                fab.setVisibility(View.GONE);
            }
        };
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }

    // The options menu has a single item "Clear all data now"
    // that deletes all the entries in the database.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, as long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == 0) {
//            // Add a toast just for confirmation
//            Toast.makeText(this, R.string.clear_data_toast_text, Toast.LENGTH_LONG).show();
//
//            // Delete the existing data.
//            mWordViewModel.deleteAll();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**

     * @param requestCode ID for the request
     * @param resultCode indicates success or failure
     * @param data The Intent sent back from the CreateEventActivity,
     *             which includes the word that the user entered
     */



}
