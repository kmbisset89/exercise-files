package com.example.kerrymbisset.valleybeta3;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.provider.CalendarContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

import static com.example.kerrymbisset.valleybeta3.List_Activity.EXTRA_DATA_ID;
import static com.example.kerrymbisset.valleybeta3.List_Activity.EXTRA_DATA_UPDATE_EVENT_DATE;
import static com.example.kerrymbisset.valleybeta3.List_Activity.EXTRA_DATA_UPDATE_EVENT_DESC;
import static com.example.kerrymbisset.valleybeta3.List_Activity.EXTRA_DATA_UPDATE_EVENT_FILTER;
import static com.example.kerrymbisset.valleybeta3.List_Activity.EXTRA_DATA_UPDATE_EVENT_MILLIS;
import static com.example.kerrymbisset.valleybeta3.List_Activity.EXTRA_DATA_UPDATE_EVENT_TIME;
import static com.example.kerrymbisset.valleybeta3.List_Activity.EXTRA_DATA_UPDATE_EVENT_TITLE;


public class CreateEventActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener, AllConstants {
    public static final int LOADER_EVENTS = 0;

    public static final String EXTRA_REPLY_TITLE = "com.example.android.roomwordssample.REPLY_TITLE";
    public static final String EXTRA_REPLY_DATE = "com.example.android.roomwordssample.REPLY_DATE";
    public static final String EXTRA_REPLY_TIME = "com.example.android.roomwordssample.REPLY_TIME";
    public static final String EXTRA_REPLY_DESC = "com.example.android.roomwordssample.REPLY_DESC";
    public static final String EXTRA_REPLY_MILLIS = "com.example.android.roomwordssample.REPLY_MILLIS";
    public static final String EXTRA_REPLY_FILTER = "com.example.android.roomwordssample.REPLY_FILTER";
    public static final String EXTRA_REPLY_ID = "com.android.example.roomwordssample.REPLY_ID";

    private final String TAG = getClass().getSimpleName();

    private boolean mIsNewEvent = true;
    private EditText mEventTitle;
    private EditText mEventDate;
    private EditText mEventDesc;
    private EditText mEventTime;
    private int mEventId;
    private String mOriginalEventTitle;
    private String mOriginalEventDate;
    private String mOriginalEventDesc;
    private String mOriginalEventTime;
    private long mEventDateMillis;
    private int mEventHour;
    private int mEventMinute;
    private int mEventDay;
    private int mEventMonth;
    private int mEventYear;
    private int mEventFilter =0;
    private long eventMillis;
    int eventFilter;
    private String eventDesc;
    private String eventTime;
    private String eventDate;
    private String eventTitle;


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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        mEventTitle = findViewById(R.id.create_event_title);
        mEventDate = findViewById(R.id.create_event_date);
        mEventDesc = findViewById(R.id.create_event_desc);
        mEventTime = findViewById(R.id.create_event_time);



        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar_create);
        setSupportActionBar(toolbar);


        final Bundle extras = getIntent().getExtras();




        if (extras != null) {
            mIsNewEvent =false;
            eventTitle = extras.getString(EXTRA_DATA_UPDATE_EVENT_TITLE, "");
            if (!eventTitle.isEmpty()) {
                mEventTitle.setText(eventTitle);
                mOriginalEventTitle = eventTitle;
                mEventTitle.setSelection(eventTitle.length());
                mEventTitle.setEnabled(false);
            }
            eventDate = extras.getString(EXTRA_DATA_UPDATE_EVENT_DATE, "");
            if (!eventDate.isEmpty()) {
                mEventDate.setText(eventDate);
                mOriginalEventDate = eventDate;
                mEventDate.setSelection(eventDate.length());
                mEventDate.setEnabled(false);
            }
            eventTime = extras.getString(EXTRA_DATA_UPDATE_EVENT_TIME, "");
            if (!eventTime.isEmpty()) {
                mEventTime.setText(eventTime);
                mOriginalEventTime = eventTime;
                mEventTime.setSelection(eventTime.length());
                mEventTime.setEnabled(false);
            }
            eventDesc = extras.getString(EXTRA_DATA_UPDATE_EVENT_DESC, "");
            if (!eventDesc.isEmpty()) {
                mEventDesc.setText(eventDesc);
                mOriginalEventDesc = eventDesc;
                mEventDesc.setSelection(eventDesc.length());
                mEventDesc.setEnabled(false);
                mEventDesc.setTextColor(Color.parseColor("#000000"));
            }
            setmEventDateMillis(extras.getLong(EXTRA_DATA_UPDATE_EVENT_MILLIS, 0));
            eventFilter = extras.getInt(EXTRA_DATA_UPDATE_EVENT_FILTER, 0);
        }



        final RadioGroup eventRadioGroup = findViewById(R.id.event_radio_group);
        if (!mIsNewEvent)
            eventRadioGroup.setVisibility(View.INVISIBLE);
        else
            eventRadioGroup.setVisibility(View.VISIBLE);



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

        final FloatingActionButton fabedit = findViewById(R.id.fab_edit_event);
        fabedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEventTitle.setEnabled(true);
                mEventDate.setEnabled(true);
                mEventTime.setEnabled(true);
                mEventDesc.setEnabled(true);
                mEventTitle.requestFocus();
                mEventDate.requestFocus();
                mEventTime.requestFocus();
                mEventDesc.requestFocus();


                eventRadioGroup.setVisibility(View.VISIBLE);

                fabedit.hide();


            }
        });

        RadioButton eventFilterChurch = findViewById(R.id.radio_church);
        RadioButton eventFilterSG = findViewById(R.id.radio_sg);
        RadioButton eventFilterYouth = findViewById(R.id.radio_youth);
        RadioButton eventFilterAdult = findViewById(R.id.radio_adult);

//        eventRadioGroup.addView(eventFilterChurch);
//        eventRadioGroup.addView(eventFilterSG);
//        eventRadioGroup.addView(eventFilterYouth);
//        eventRadioGroup.addView(eventFilterAdult);


        //Set checked flag
        switch (eventFilter) {
            case CHURCHFILTER:
                mEventFilter =CHURCHFILTER;
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

        eventFilterChurch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEventFilter = CHURCHFILTER;
                DeselectAll();
                eventFilterChurch.setChecked(true);
                Toast.makeText(CreateEventActivity.this, "Event Filter set to Church", Toast.LENGTH_SHORT).show();
            }
        });

        eventFilterSG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEventFilter = SMALLGROUPFILTER;
                DeselectAll();
                eventFilterSG.setChecked(true);
                Toast.makeText(CreateEventActivity.this, "Event Filter set to Small Group", Toast.LENGTH_SHORT).show();
            }
        });

        eventFilterYouth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEventFilter = YOUTHFILTER;
                DeselectAll();
                eventFilterYouth.setChecked(true);
                Toast.makeText(CreateEventActivity.this, "Event Filter set to Youth", Toast.LENGTH_SHORT).show();
            }
        });

        eventFilterAdult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEventFilter = ADULTFILTER;
                DeselectAll();
                eventFilterAdult.setChecked(true);
                Toast.makeText(CreateEventActivity.this, "Event Filter set to Adult", Toast.LENGTH_SHORT).show();
            }
        });




        FloatingActionButton fab = findViewById(R.id.fab_save_event);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the new info that the user entered.

                sendDataBack(extras, false);
            }
        });

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

    private void sendDataBack(Bundle extras, boolean original) {
        Intent replyIntent = new Intent(this, List_Activity.class);
        if (TextUtils.isEmpty(mEventTitle.getText())) {
            // No info was entered, set the result accordingly.
            setResult(RESULT_CANCELED, replyIntent);
        } else {
            // Get the new info that the user entered.
            String eventTitle = mEventTitle.getText().toString();
            String eventDate = mEventDate.getText().toString();
            String eventTime = mEventTime.getText().toString();
            String eventDesc = mEventDesc.getText().toString();
            long eventMillis =mEventDateMillis;
            int eventFilter = mEventFilter;


            if (!original) {
                // Put the new events details in the extras for the reply Intent.
                replyIntent.putExtra(EXTRA_REPLY_TITLE, eventTitle);
                replyIntent.putExtra(EXTRA_REPLY_DATE, eventDate);
                replyIntent.putExtra(EXTRA_REPLY_TIME, eventTime);
                replyIntent.putExtra(EXTRA_REPLY_DESC, eventDesc);
                replyIntent.putExtra(EXTRA_REPLY_MILLIS, eventMillis);
                replyIntent.putExtra(EXTRA_REPLY_FILTER, eventFilter);

                if (extras != null && extras.containsKey(EXTRA_DATA_ID)) {
                    int id = extras.getInt(EXTRA_DATA_ID, -1);
                    if (id != -1) {
                        replyIntent.putExtra(EXTRA_REPLY_ID, id);
                    }
                }
                // Set the result status to indicate success.
                setResult(RESULT_OK, replyIntent);
            } else {
                setResult(RESULT_CANCELED, replyIntent);
            }


        }
        finish();
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
        String time =String.format("%s:%s %s", hour, minutes, timeSet);
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
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final Bundle extras = getIntent().getExtras();
                        sendDataBack(extras, true);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final Bundle extras = getIntent().getExtras();
                        sendDataBack(extras, false);
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
            if (mEventDateMillis== 0) {

                Intent intent = new Intent(Intent.ACTION_INSERT)
                        .setType("vnd.android.cursor.item/event")
                        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, System.currentTimeMillis())
                        .putExtra("title", eventTitle)
                        .putExtra(CalendarContract.Events.DESCRIPTION, eventDesc);
                startActivity(intent);


            } else {

                Intent intent = new Intent(Intent.ACTION_INSERT)
                        .setType("vnd.android.cursor.item/event")
                        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, mEventDateMillis)
                        .putExtra(CalendarContract.Events.TITLE, eventTitle)
                        .putExtra(CalendarContract.Events.DESCRIPTION, eventDesc);
                startActivity(intent);

            }


            return true;

        }
        return super.onOptionsItemSelected(item);
    }


}