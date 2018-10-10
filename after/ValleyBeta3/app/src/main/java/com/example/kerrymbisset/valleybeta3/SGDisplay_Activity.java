//package com.example.kerrymbisset.valleybeta3;
//
//import android.app.LoaderManager;
//import android.content.CursorLoader;
//import android.content.Intent;
//import android.content.Loader;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.net.Uri;
//import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.v7.app.AppCompatActivity;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//
//import java.util.StringTokenizer;
//
//
//public class SGDisplay_Activity extends AppCompatActivity {
//    //public fields
//    public static final int LOADER_SG = 1;
//    public static final String SG_ID = "com.example.kerrymbisset.valleybeta3.EVENT_ID";
//    public static final String ORIGINAL_SG_TITLE = "com.example.kerrymbisset.valleybeta3.SG_TITLE";
//    public static final String ORIGINAL_SG_POC = "com.example.kerrymbisset.valleybeta3.SG_POC";
//    public static final String ORIGINAL_SG_DESC = "com.example.kerrymbisset.valleybeta3.SG_DESC";
//    public static final String ORIGINAL_SG_MP = "com.example.kerrymbisset.valleybeta3.SG_DESC";
//
//    //private fields
//
//
//    private int mSGId;
//    private int ID_NOT_SET = -1;
//    private boolean mIsNewSmallGroup;
//    private int mSGPosition;
//    private String mOriginalSGTitle;
//    private Small_Group_Info mSG = new Small_Group_Info("","", "", "");
//    private String mOriginalSGPOC;
//    private String mOriginalSGDesc;
//    private String mOriginalSGMP;
//    private TextView mSGTitle;
//    private TextView mSGPOC;
//    private TextView mSGDesc;
//    private boolean mSGQueryFinished;
//    private Cursor mSGCursor;
//    private int mSGTitlePos;
//    private int mSGPOCPos;
//    private int mSGDescPos;
//    private int mSGMPPos;
//    private TextView mSGMeetingPlace;
//    private Button button_map;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_small_group_details);
//        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar_details);
//        setSupportActionBar(toolbar);
//
//
//
//        readDisplayStateValues();
//        if (savedInstanceState == null) {
//            saveOriginalEventValues();
//        } else {
//            restoreOriginalEventValues(savedInstanceState);
//        }
//
//        mSGTitle =  findViewById(R.id.sg_title);
//        mSGPOC = findViewById(R.id.sg_point_of_contact);
//        mSGDesc = findViewById(R.id.sg_desc);
//        mSGMeetingPlace =findViewById(R.id.sg_meeting_place);
//
//
//
//        button_map = findViewById(R.id.direction);
//        button_map.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                StringBuilder location = new StringBuilder();
//                location.append("geo:34.6993, -86.7483?q=");
//
//
//
//                String sgMPParsing = (String) mSGMeetingPlace.getText();
//                StringTokenizer st1 =
//                        new StringTokenizer(sgMPParsing, " ,");
//
//                while (st1.hasMoreTokens()) {
//                    String holder = st1.nextToken().toString();
//                    location.append(holder);
//                    location.append("+");
//
//                };
//
//                Uri gmmIntentUri = Uri.parse(String.valueOf(location));
//                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//                mapIntent.setPackage("com.google.android.apps.maps");
//                startActivity(mapIntent);
//            }
//        });
//
//
//
//    }
//
//    private void restoreOriginalEventValues(Bundle savedInstanceState) {
//        mOriginalSGTitle = savedInstanceState.getString(ORIGINAL_SG_TITLE);
//        mOriginalSGPOC = savedInstanceState.getString(ORIGINAL_SG_POC);
//        mOriginalSGDesc = savedInstanceState.getString(ORIGINAL_SG_DESC);
//        mOriginalSGMP=(savedInstanceState.getString(ORIGINAL_SG_MP));
//
//    }
//
//    private void saveOriginalEventValues() {
//            if (mIsNewSmallGroup)
//                return;
//            mOriginalSGTitle = mSG.getmSGTitle();
//            mOriginalSGPOC = mSG.getmSGPOC();
//            mOriginalSGDesc = mSG.getmSGDesc();
//            mOriginalSGMP= mSG.getmSGMP();
//
//    }
//
//    @Override
//    protected void onSaveInstanceState (Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putString(ORIGINAL_SG_TITLE, mOriginalSGTitle);
//        outState.putString(ORIGINAL_SG_POC, mOriginalSGPOC);
//        outState.putString(ORIGINAL_SG_DESC, mOriginalSGDesc);
//        outState.putString(ORIGINAL_SG_MP, mOriginalSGMP);
//    }
//
//    private void readDisplayStateValues() {
//        Intent intent = getIntent();
//        mSGId = intent.getIntExtra(SG_ID, ID_NOT_SET);
//        mIsNewSmallGroup = mSGPosition == ID_NOT_SET;
//        if (mIsNewSmallGroup) {
//            createNewSG();
//        }
//    }
//
//    private void createNewSG() {
//
//
//    }
//
//
//    // Creates an Intent that will load a map of San Francisco
//
//}
