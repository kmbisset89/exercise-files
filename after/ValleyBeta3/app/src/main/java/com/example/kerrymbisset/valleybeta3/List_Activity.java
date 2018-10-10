package com.example.kerrymbisset.valleybeta3;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.example.kerrymbisset.valleybeta3.Database.ValleyViewModel;

import java.util.List;

import static com.example.kerrymbisset.valleybeta3.CreateEventActivity.*;
import static com.example.kerrymbisset.valleybeta3.CreateEventActivity.EXTRA_REPLY_MILLIS;


public class List_Activity extends AppCompatActivity   {


    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_WORD_ACTIVITY_REQUEST_CODE = 2;
    public static final String CHOICE = "com.example.kerrymbisset.valleybeta3.CHOICE";
    public static final String FILTER = "com.example.kerrymbisset.valleybeta3.FILTER";
    public static final String EXTRA_DATA_UPDATE_EVENT_TITLE = "extra_event_title_to_be_updated";
    public static final String EXTRA_DATA_UPDATE_EVENT_DATE = "extra_event_date_to_be_updated";
    public static final String EXTRA_DATA_UPDATE_EVENT_TIME = "extra_event_time_to_be_updated";
    public static final String EXTRA_DATA_UPDATE_EVENT_DESC = "extra_event_desc_to_be_updated";
    public static final String EXTRA_DATA_UPDATE_EVENT_MILLIS = "extra_event_millis_to_be_updated";
    public static final String EXTRA_DATA_UPDATE_EVENT_FILTER = "extra_event_filter_to_be_updated";
    public static final String EXTRA_DATA_ID = "extra_data_id";

    private ValleyViewModel mViewModel;
    private int choice;

    private static int FILTERLEVEL =0;
    private int ALL =1 ;

    public static int getFILTERLEVEL() {
        return FILTERLEVEL;
    }

    public void setFILTERLEVEL(int FILTERLEVEL) {
        this.FILTERLEVEL = FILTERLEVEL;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_activity);
        setFILTERLEVEL( getIntent().getIntExtra(FILTER, 0));

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
                        Intent intent = new Intent(List_Activity.this, List_Activity.class);
                        intent
                                .putExtra(CHOICE, EVENTS)
                                .putExtra(FILTER, position+1);
                        startActivity(intent);
                    }
                });

                break;

            case EVENTS:
                final EventRecyclerAdapter adapter = new EventRecyclerAdapter(this);

                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));

                // Get all the words from the database
                // and associate them to the adapter.

                if (FILTERLEVEL == ALL) {
                    // Get all the events from the database
                    // and associate them to the adapter.
                    mViewModel.getAllEvents().observe(this, new Observer<List<Events>>() {
                        @Override
                        public void onChanged(@Nullable final List<Events> events) {
                            // Update the cached copy of the words in the adapter.
                            adapter.setEvents(events);

                        }
                    });
                }

                if (FILTERLEVEL > ALL) {
                    // Get filtered events from the database
                    // and associate them to the adapter.
                    mViewModel.getmCertainEvents(FILTERLEVEL).observe(this, new Observer<List<Events>>() {
                        @Override
                        public void onChanged(@Nullable final List<Events> events) {
                            // Update the cached copy of the words in the adapter.
                            adapter.setEvents(events);

                        }
                    });

                }

                // Add the functionality to swipe items in the
                // RecyclerView to delete the swiped item.
                ItemTouchHelper helper = new ItemTouchHelper(
                        new ItemTouchHelper.SimpleCallback(0,
                                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                            @Override
                            // We are not implementing onMove() in this app.
                            public boolean onMove(RecyclerView recyclerView,
                                                  RecyclerView.ViewHolder viewHolder,
                                                  RecyclerView.ViewHolder target) {
                                return false;
                            }

                            @Override
                            // When the use swipes a word,
                            // delete that word from the database.
                            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                                int position = viewHolder.getAdapterPosition();
                                Events myEvents = adapter.getEventAtPosition(position);
                                Toast.makeText(List_Activity.this,"Deleted Event", Toast.LENGTH_LONG).show();

                                // Delete the word.
                                mViewModel.deleteWord(myEvents);
                            }
                        });
                // Attach the item touch helper to the recycler view.
                helper.attachToRecyclerView(recyclerView);

                adapter.setOnItemClickListener(new EventRecyclerAdapter.ClickListener()  {

                    @Override
                    public void onItemClick(View v, int position) {
                        Events events = adapter.getEventAtPosition(position);
                        launchUpdateEventDetailsActivity(events);
                    }
                });
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

                smallGroupFilterRecyclerAdapter.setOnItemClickListener(new SmallGroupFilterRecyclerAdapter.ClickListener()  {

                    @Override
                    public void onItemClick(View v, int position) {
                        SGFilter sgFilter = smallGroupFilterRecyclerAdapter.getSGAtPosition(position);
                        Intent intent = new Intent(List_Activity.this, List_Activity.class);
                        intent
                                .putExtra(CHOICE, SMALLGROUP)
                                .putExtra(FILTER, position+1);
                        startActivity(intent);
                    }
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
                final MembersRecyclerAdapter membersRecyclerAdapter = new MembersRecyclerAdapter(this);

                recyclerView.setAdapter(membersRecyclerAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));

                // Get all the words from the database
                // and associate them to the adapter.

                if (FILTERLEVEL == ALL) {
                    // Get all the events from the database
                    // and associate them to the adapter.
                    mViewModel.getmAllMembers().observe(this, new Observer<List<MemberInfo>>() {
                        @Override
                        public void onChanged(@Nullable final List<MemberInfo> memberInfos) {
                            // Update the cached copy of the words in the adapter.
                            membersRecyclerAdapter.setMemberInfo(memberInfos);

                        }
                    });
                }

                if (FILTERLEVEL > ALL) {
                    // Get filtered events from the database
                    // and associate them to the adapter.
                    mViewModel.getmCertainMember(FILTERLEVEL).observe(this, new Observer<List<MemberInfo>>() {
                        @Override
                        public void onChanged(@Nullable final List<MemberInfo> memberInfos) {
                            // Update the cached copy of the words in the adapter.
                            membersRecyclerAdapter.setMemberInfo(memberInfos);

                        }
                    });

                }

                break;
        }


////////////////////////////End of Switch///////////


        // Floating action button setup
        FloatingActionButton fab = findViewById(R.id.fab1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(List_Activity.this, CreateEventActivity.class);
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
        });


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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Events events = new Events(
                    data.getStringExtra(EXTRA_REPLY_TITLE),
                            data.getStringExtra(EXTRA_REPLY_DATE),
                                    data.getStringExtra(EXTRA_REPLY_TIME),
                                            data.getStringExtra(EXTRA_REPLY_DESC),
                                                    data.getLongExtra(EXTRA_REPLY_MILLIS, 0),
                                                            data.getIntExtra(EXTRA_REPLY_FILTER, 1)
                            );
            // Save the data.
            mViewModel.insert(events);
        } else if (requestCode == UPDATE_WORD_ACTIVITY_REQUEST_CODE
                && resultCode == RESULT_OK) {
            int id = data.getIntExtra(EXTRA_REPLY_ID, -1);

            if (id != -1) {
                mViewModel.update(new Events(id,
                        data.getStringExtra(EXTRA_REPLY_TITLE),
                        data.getStringExtra(EXTRA_REPLY_DATE),
                        data.getStringExtra(EXTRA_REPLY_TIME),
                        data.getStringExtra(EXTRA_REPLY_DESC),
                        data.getLongExtra(EXTRA_REPLY_MILLIS, 0),
                        data.getIntExtra(EXTRA_REPLY_FILTER, 1)));
            } else {
                Toast.makeText(this,"Unable to Update",
                        Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(
                    this, "Nothing Changed", Toast.LENGTH_LONG).show();
        }
    }

    public void launchUpdateEventDetailsActivity(Events events) {
        Intent intent = new Intent(this, CreateEventActivity.class);
        intent.putExtra(EXTRA_DATA_UPDATE_EVENT_TITLE, events.getEventTitle());
        intent.putExtra(EXTRA_DATA_UPDATE_EVENT_DATE, events.getEventDate());
        intent.putExtra(EXTRA_DATA_UPDATE_EVENT_TIME, events.getEventTime());
        intent.putExtra(EXTRA_DATA_UPDATE_EVENT_DESC, events.getEventDesc());
        intent.putExtra(EXTRA_DATA_UPDATE_EVENT_MILLIS, events.getEventMillis());
        intent.putExtra(EXTRA_DATA_UPDATE_EVENT_FILTER, events.getEventFilter());
        intent.putExtra(EXTRA_DATA_ID, events.getId());
        startActivityForResult(intent, UPDATE_WORD_ACTIVITY_REQUEST_CODE);
    }

}
