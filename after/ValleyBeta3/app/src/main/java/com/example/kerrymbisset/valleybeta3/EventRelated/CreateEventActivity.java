package com.example.kerrymbisset.valleybeta3.EventRelated;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.kerrymbisset.valleybeta3.AllConstants;
import com.example.kerrymbisset.valleybeta3.DatePickerFragment;
import com.example.kerrymbisset.valleybeta3.IDialogListener;
import com.example.kerrymbisset.valleybeta3.List_Activity;
import com.example.kerrymbisset.valleybeta3.LoginActivity;
import com.example.kerrymbisset.valleybeta3.R;
import com.example.kerrymbisset.valleybeta3.SmallGroupRelated.SmallGroupDialog;
import com.example.kerrymbisset.valleybeta3.TimePickerFragment;
import com.example.kerrymbisset.valleybeta3.models.Events;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.UUID;

import static com.example.kerrymbisset.valleybeta3.List_Activity.FILTER;


public class CreateEventActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener, AllConstants, View.OnClickListener, IDialogListener {

    private final String TAG = getClass().getSimpleName();
    public static final String EVENTKEY = "com.example.kerrymbisset.valleybeta3.EventRelated.FILTER";
    private FirebaseAuth.AuthStateListener mAuthListener;
    private boolean mIsNewEvent = true;
    private EditText mEventTitle;
    private EditText mEventDate;
    private EditText mEventDesc;
    private EditText mEventTime;
    private EditText mEventLocation;
    private long mEventDateMillis;
    private int mEventHour;
    private int mEventMinute;
    private int mEventDay;
    private int mEventMonth;
    private int mEventYear;
    private String mEventFilter = "";
    private long mGroupNumber = 0;
    private String mEventKey;
    private long eventMillis;
    private FloatingActionButton fab;
    private RadioButton eventFilterChurch;
    private RadioButton eventFilterSG;
    private RadioButton eventFilterYouth;
    private RadioButton eventFilterAdult;
    private SmallGroupDialog mSmallGroupDialog;
    private RadioGroup group;


    public void setmEventDateMillis(long mEventDateMillis) {
        this.mEventDateMillis = mEventDateMillis;
    }

    public int getmEventHour() {
        return mEventHour;
    }

    public void setmEventHour(int mEventHour) {
        this.mEventHour = mEventHour;
    }

    public int getmEventMinute() {
        return mEventMinute;
    }

    public void setmEventMinute(int mEventMinute) {
        this.mEventMinute = mEventMinute;
    }

    public int getmEventDay() {
        return mEventDay;
    }

    public void setmEventDay(int mEventDay) {
        this.mEventDay = mEventDay;
    }

    public int getmEventMonth() {
        return mEventMonth;
    }

    public void setmEventMonth(int mEventMonth) {
        this.mEventMonth = mEventMonth;
    }

    public int getmEventYear() {
        return mEventYear;
    }

    public void setmEventYear(int mEventYear) {
        this.mEventYear = mEventYear;
    }

    public CreateEventActivity() {
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        String eventName = getIntent().getStringExtra(EVENTKEY);
        if (getIntent().hasExtra(EVENTKEY)) {
            mEventTitle = findViewById(R.id.create_event_title);
            mEventTitle.requestFocus();
            mEventDate = findViewById(R.id.create_event_date);
            mEventDesc = findViewById(R.id.create_event_desc);
            mEventTime = findViewById(R.id.create_event_time);
            mEventLocation = findViewById(R.id.create_event_location);

            getEventData(eventName);
        }


    }


    private void getEventData(String eventKey) {
        Log.d(TAG, "getUserAccountData: getting the user's account information");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        /*
            ---------- QUERY Method 1 ----------
         */
        Query query1 = reference.child(getString(R.string.dbnode_events))
                .equalTo(eventKey);
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //this loop will return a single result
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
//                    Log.d(TAG, "onDataChange: (QUERY METHOD 1) found user: "
//                           + singleSnapshot.getValue(User.class).toString());
                    Events events = singleSnapshot.getValue(Events.class);

                    mEventTitle.setText(events.getEvent_title());
                    mEventDate.setText(events.getEvent_date());
                    mEventTime.setText(events.getEvent_time());
                    mEventDesc.setText(events.getEvent_desc());

                    mEventDateMillis = Long.parseLong(events.getEvent_millis());
                    mGroupNumber = Long.parseLong(events.getGroup_number());
                    mEventFilter = (events.getEvent_filter());

                    eventFilterChurch = findViewById(R.id.radio_church);
                    eventFilterSG = findViewById(R.id.radio_sg);
                    eventFilterYouth = findViewById(R.id.radio_youth);
                    eventFilterAdult = findViewById(R.id.radio_adult);

                    switch (mEventFilter) {
                        case CHURCHFILTER:
                            mEventFilter = CHURCHFILTER;
                            eventFilterChurch.setChecked(true);
                            break;
                        case SMALLGROUPFILTER:
                            mEventFilter = SMALLGROUPFILTER;
                            eventFilterSG.setChecked(true);
                            break;
                        case YOUTHFILTER:
                            mEventFilter = YOUTHFILTER;
                            eventFilterYouth.setChecked(true);
                            break;
                        case ADULTFILTER:
                            mEventFilter = ADULTFILTER;
                            eventFilterAdult.setChecked(true);
                    }


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        setupFirebaseAuth();
        mEventTitle = findViewById(R.id.create_event_title);
        mEventDate = findViewById(R.id.create_event_date);
        mEventDesc = findViewById(R.id.create_event_desc);
        mEventTime = findViewById(R.id.create_event_time);
        eventFilterChurch = findViewById(R.id.radio_church);
        eventFilterSG = findViewById(R.id.radio_sg);
        eventFilterYouth = findViewById(R.id.radio_youth);
        eventFilterAdult = findViewById(R.id.radio_adult);
        mEventLocation = findViewById(R.id.create_event_location);


        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar_create);
        setSupportActionBar(toolbar);


        final Bundle extras = getIntent().getExtras();

        if (getIntent().hasExtra(EVENTKEY)) {
            mIsNewEvent = false;
            mEventKey = getIntent().getStringExtra(EVENTKEY);
        } else {
            mIsNewEvent = true;

        }


        mEventDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        mEventTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });


        //Set checked flag
        switch (mEventFilter) {
            case CHURCHFILTER:
                mEventFilter = CHURCHFILTER;
                eventFilterAdult.setChecked(true);
                break;
            case SMALLGROUPFILTER:
                mEventFilter = SMALLGROUPFILTER;
                eventFilterSG.setChecked(true);
                break;
            case YOUTHFILTER:
                mEventFilter = YOUTHFILTER;
                eventFilterYouth.setChecked(true);
                break;
            case ADULTFILTER:
                mEventFilter = ADULTFILTER;
                eventFilterAdult.setChecked(true);
        }

        eventFilterChurch.setOnClickListener(this);

        eventFilterSG.setOnClickListener(this);

        eventFilterYouth.setOnClickListener(this);

        eventFilterAdult.setOnClickListener(this);

        Button button = findViewById(R.id.save_event);
        button.setOnClickListener(this);
    }

    private void DeselectAll() {
        RadioButton eventFilterChurch = findViewById(R.id.radio_church);
        RadioButton eventFilterSG = findViewById(R.id.radio_sg);
        RadioButton eventFilterYouth = findViewById(R.id.radio_youth);
        RadioButton eventFilterAdult = findViewById(R.id.radio_adult);

        eventFilterAdult.setChecked(false);
        eventFilterChurch.setChecked(false);
        eventFilterSG.setChecked(false);
        eventFilterYouth.setChecked(false);
    }

    ///////Date Picker Section

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        setmEventMonth(month);
        setmEventDay(dayOfMonth);
        setmEventYear(year);


        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        mEventDate.setText(currentDateString);
        updateEventMillis();

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        int hour = hourOfDay;
        int min = minute;
        setmEventHour(hour);
        setmEventMinute(min);
        String timeSet;
        if (hour > 12) {
            hour -= 12;
            timeSet = "PM";
        } else if (hour == 0) {
            hour += 12;
            timeSet = "AM";
        } else if (hour == 12) {
            timeSet = "PM";
        } else {
            timeSet = "AM";
        }

        String minutes;
        if (minute < 10) {
            minutes = "0" + minute;
        } else {
            minutes = String.valueOf(minute);
        }
        String time = String.format("%s:%s %s", hour, minutes, timeSet);
        mEventTime.setText(time);
        updateEventMillis();

    }

    private void updateEventMillis() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, getmEventYear());
        c.set(Calendar.MONTH, getmEventMonth());
        c.set(Calendar.DAY_OF_MONTH, getmEventDay());
        c.set(Calendar.HOUR_OF_DAY, getmEventHour());
        c.set(Calendar.MINUTE, getmEventMinute());

        setmEventDateMillis(c.getTimeInMillis());


    }


    ///Back Button
    public boolean onKeyDown(int keycode, KeyEvent event) {
        if (keycode == KeyEvent.KEYCODE_BACK) {
            exitByBackKey();
            return true;
        }
        return super.onKeyDown(keycode, event);
    }


    private void exitByBackKey() {
        AlertDialog alertbox = new AlertDialog.Builder(this)
                .setMessage("Do you want to exit without saving")
                .setPositiveButton("Yes", (dialogInterface, i) -> {
                    Intent intent = new Intent(CreateEventActivity.this, List_Activity.class);
                    intent.putExtra(FILTER, EVENTFILTERSEL);
                    startActivity(intent);
                    finish();

                })
                .setNegativeButton("No", (dialogInterface, i) -> {
                    if (saveEvent()) {
                        Intent intent = new Intent(CreateEventActivity.this, List_Activity.class);
                        intent.putExtra(FILTER, EVENTFILTERSEL);
                        startActivity(intent);
                        finish();
                    } else {
                        dialogInterface.cancel();
                    }
                })
                .show();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.event_detail_menu, menu);
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

        // add an event to google calendar
        if (id == R.id.addtocalendar) {
            if (mEventDateMillis == 0) {

                Intent intent = new Intent(Intent.ACTION_INSERT)
                        .setType("vnd.android.cursor.item/event")
                        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, System.currentTimeMillis())
                        .putExtra("title", mEventTitle.getText())
                        .putExtra(CalendarContract.Events.DESCRIPTION, mEventTitle.getText());
                startActivity(intent);


            } else {

                Intent intent = new Intent(Intent.ACTION_INSERT)
                        .setType("vnd.android.cursor.item/event")
                        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, mEventDateMillis)
                        .putExtra(CalendarContract.Events.TITLE, mEventTitle.getText())
                        .putExtra(CalendarContract.Events.DESCRIPTION, mEventDateMillis);
                startActivity(intent);

            }


            return true;

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save_event: {
                if (saveEvent()) {
                    Intent intent = new Intent(CreateEventActivity.this, List_Activity.class);
                    intent.putExtra(FILTER, EVENTFILTERSEL);
                    startActivity(intent);
                    finish();
                }

                break;

            }
            case R.id.radio_church: {
                mEventFilter = CHURCHFILTER;
                DeselectAll();
                eventFilterChurch.setChecked(true);
                Toast.makeText(CreateEventActivity.this, "Event Filter set to Church", Toast.LENGTH_SHORT).show();
                break;
            }

            case R.id.radio_sg: {
                mEventFilter = SMALLGROUPFILTER;
                DeselectAll();
                eventFilterSG.setChecked(true);
                openDialog();
                Toast.makeText(CreateEventActivity.this, "Event Filter set to Small Group", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.radio_youth: {
                mEventFilter = YOUTHFILTER;
                DeselectAll();
                eventFilterYouth.setChecked(true);
                Toast.makeText(CreateEventActivity.this, "Event Filter set to Youth", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.radio_adult: {
                mEventFilter = ADULTFILTER;
                DeselectAll();
                eventFilterAdult.setChecked(true);
                Toast.makeText(CreateEventActivity.this, "Event Filter set to Adult", Toast.LENGTH_SHORT).show();
                break;
            }

        }

    }

    private boolean saveEvent() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
            /*
            ------ Change Name -----
             */
        boolean isFieldFilled = true;
        if (mIsNewEvent) {
            mEventKey = UUID.randomUUID().toString();


            if (!mEventTitle.getText().toString().equals("")) {
                reference.child(getString(R.string.dbnode_events))
                        .child(mEventKey)
                        .child(getString(R.string.field_event_title))
                        .setValue(mEventTitle.getText().toString());

            } else {
                Toast.makeText(this, "Please add event title before saving", Toast.LENGTH_SHORT).show();
                isFieldFilled = false;
            }

            if (!mEventDate.getText().toString().equals("")) {
                reference.child(getString(R.string.dbnode_events))
                        .child(mEventKey)
                        .child(getString(R.string.field_event_date))
                        .setValue(mEventDate.getText().toString());
            } else {
                Toast.makeText(this, "Please add event date before saving", Toast.LENGTH_SHORT).show();
                isFieldFilled = false;

            }

            if (!mEventTime.getText().toString().equals("")) {
                reference.child(getString(R.string.dbnode_events))
                        .child(mEventKey)
                        .child(getString(R.string.field_event_time))
                        .setValue(mEventTime.getText().toString());
            } else {

                Toast.makeText(this, "Please add event time before saving", Toast.LENGTH_SHORT).show();
                isFieldFilled = false;
            }

            if (mEventDateMillis != 0) {
                reference.child(getString(R.string.dbnode_events))
                        .child(mEventKey)
                        .child(getString(R.string.field_event_millis))
                        .setValue(String.valueOf(mEventDateMillis));
            }

            if (!mEventDesc.getText().toString().equals("")) {
                reference.child(getString(R.string.dbnode_events))
                        .child(mEventKey)
                        .child(getString(R.string.field_event_desc))
                        .setValue(mEventDesc.getText().toString());
            } else {
                Toast.makeText(this, "Please add event description before saving", Toast.LENGTH_SHORT).show();
                isFieldFilled = false;
                mEventDesc.requestFocus();
            }

            if (!mEventLocation.getText().toString().equals("")) {
                reference.child(getString(R.string.dbnode_events))
                        .child(mEventKey)
                        .child(getString(R.string.field_event_location))
                        .setValue(mEventLocation.getText().toString());
            } else {
                Toast.makeText(this, "Please add event location before saving", Toast.LENGTH_SHORT).show();
                isFieldFilled = false;
                mEventLocation.requestFocus();
            }


            if (mEventFilter != "") {
                reference.child(getString(R.string.dbnode_events))
                        .child(mEventKey)
                        .child(getString(R.string.field_event_filter))
                        .setValue(mEventFilter);
            } else {
                Toast.makeText(this, "Please add an event category before saving", Toast.LENGTH_SHORT).show();
                isFieldFilled = false;
            }

            if (mGroupNumber != -1) {
                reference.child(getString(R.string.dbnode_events))
                        .child(mEventKey)
                        .child(getString(R.string.field_event_group))
                        .setValue(String.valueOf(mGroupNumber));
            } else {
                if (mEventFilter.equals("SMALL GROUP")) {
                    Toast.makeText(this, "Please add a small group before saving", Toast.LENGTH_SHORT).show();
                    isFieldFilled = false;
                }
            }

            reference.child(getString(R.string.dbnode_events))
                    .child(mEventKey)
                    .child(getString(R.string.field_event_key))
                    .setValue(String.valueOf(mEventKey));

            reference.child(getString(R.string.dbnode_events))
                    .child(mEventKey)
                    .child(getString(R.string.field_event_creator))
                    .setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());


        }
        return isFieldFilled;
    }

    private void openDialog() {
        group = findViewById(R.id.event_radio_group);
        group.setVisibility(View.GONE);

        mSmallGroupDialog = new SmallGroupDialog();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.small_group_list, mSmallGroupDialog, "home");
        transaction.commit();


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
                Toast.makeText(CreateEventActivity.this, "Signed out", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CreateEventActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        };
    }


    @Override
    public void getSmallGroupData(String name, String number) {
        mGroupNumber = Integer.parseInt(number);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.remove(mSmallGroupDialog);
        transaction.commit();
        Toast.makeText(this, "You selected " + name, Toast.LENGTH_SHORT).show();
        group.setVisibility(View.VISIBLE);
    }

    @Override
    public void passBackTrueFalse(boolean answer) {

    }
}