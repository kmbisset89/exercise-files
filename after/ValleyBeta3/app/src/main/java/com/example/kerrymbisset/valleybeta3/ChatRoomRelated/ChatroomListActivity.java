package com.example.kerrymbisset.valleybeta3.ChatRoomRelated;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.example.kerrymbisset.valleybeta3.IDialogListener;
import com.example.kerrymbisset.valleybeta3.R;
import com.example.kerrymbisset.valleybeta3.models.Chatroom;
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

public class ChatroomListActivity extends AppCompatActivity {

    private String TAG = "ChatroomListActivity";
    private FirebaseAuth.AuthStateListener mAuthListener;
    private boolean loggedIn = false;
    private ArrayList<String> mChatroomName = new ArrayList<>();
    private ArrayList<String> mChatroomSubName = new ArrayList<>();
    private ArrayList<String> mChatroomKey = new ArrayList<>();
    private ArrayList<String> mSmallGroupSub = new ArrayList<>();
    private String mKey;
    public static final String NUMBER = "com.example.kerrymbisset.valleybeta3.ChatRoomRelated.NUMBER";
    public static final String CHATKEY = "com.example.kerrymbisset.valleybeta3.ChatRoomRelated.CHATKEY";
    private ChatroomListAdapter mAdapter;
    private RecyclerView mRecyclerView;

    private Bundle args;
    private IDialogListener mListener;

    private String Sub1 = "";
    private String Sub2 = "";
    private String Sub3 = "";
    private String Sub4 = "";
    private String Sub5 = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupFirebaseAuth();


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(ChatroomListActivity.this, CreateAChatRoom.class);

            startActivity(intent);



        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        checkAuthenticationState();
        getMemberSubscriptions();
        getChatRooms();
    }

    private void getChatRooms() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        /*
            ---------- QUERY Method 1 ----------
         */
        Query query1 = reference.child(getString(R.string.dbnode_chatroom))
                .orderByKey();
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //this loop will return a single result
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
//                    Log.d(TAG, "onDataChange: (QUERY METHOD 1) found user: "
//                           + singleSnapshot.getValue(User.class).toString());
                    Chatroom chatroom = singleSnapshot.getValue(Chatroom.class);

                    mKey = chatroom.getChatroom_key();

                    if (mSmallGroupSub.contains(chatroom.getChatroom_group_key())) {
                        if (mChatroomKey.contains(mKey)) {
                            Log.d(TAG, "key is already contained");
                        } else {
                            mChatroomKey.add(mKey);
                            mChatroomName.add(chatroom.getChatroom_name());
                            mChatroomSubName.add(chatroom.getChatroom_subname());
                        }
                    } else {
                        if (mChatroomKey.size() == 0) {
                            Log.d(TAG, "no subscribed chatrooms");
                        }
                    }


                    mAdapter = new ChatroomListAdapter(ChatroomListActivity.this, mChatroomName, mChatroomSubName, mChatroomKey);
                    mRecyclerView = findViewById(R.id.chatrecycler);
                    // Connect the adapter with the recycler view.
                    mRecyclerView.setAdapter(mAdapter);
                    // Give the recycler view a default layout manager.
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(ChatroomListActivity.this));


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void getMemberSubscriptions() {
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

                        if (user.getUser_id().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                            if (!user.getSmall_group_subscription1().equals("")) {
                                Sub1 = user.getSmall_group_subscription1();
                                mSmallGroupSub.add(Sub1);
                            }

                            if (!user.getSmall_group_subscription2().equals("")) {
                                Sub2 = user.getSmall_group_subscription2();
                                mSmallGroupSub.add(Sub2);
                            }

                            if (!user.getSmall_group_subscription3().equals("")) {
                                Sub3 = user.getSmall_group_subscription3();
                                mSmallGroupSub.add(Sub3);
                            }

                            if (!user.getSmall_group_subscription4().equals("")) {
                                Sub4 = user.getSmall_group_subscription4();
                                mSmallGroupSub.add(Sub4);
                            }

                            if (!user.getSmall_group_subscription5().equals("")) {
                                Sub5 = user.getSmall_group_subscription5();
                                mSmallGroupSub.add(Sub5);
                            }

                        }

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

                Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                loggedIn = true;

            } else {
                Log.d(TAG, "onAuthStateChanged:signed_out");

                loggedIn = false;
//                    Intent intent = new Intent(OpeningScreen.this, LoginActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(intent);
//                    finish();
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
        mSmallGroupSub.clear();

    }

    private void checkAuthenticationState() {
        Log.d(TAG, "checkAuthenticationState: checking authentication state.");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null) {
            Log.d(TAG, "checkAuthenticationState: user is null, the are logged out.");
            loggedIn = false;
        } else {
            Log.d(TAG, "checkAuthenticationState: user is authenticated.");
            loggedIn = true;
        }
    }


}
