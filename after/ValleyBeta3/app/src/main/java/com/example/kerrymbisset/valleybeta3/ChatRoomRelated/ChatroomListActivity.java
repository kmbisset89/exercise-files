package com.example.kerrymbisset.valleybeta3.ChatRoomRelated;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.kerrymbisset.valleybeta3.IDialogListener;
import com.example.kerrymbisset.valleybeta3.R;
import com.example.kerrymbisset.valleybeta3.SmallGroupRelated.SmallGroupList;
import com.example.kerrymbisset.valleybeta3.SmallGroupRelated.SmallGroupListAdapter;
import com.example.kerrymbisset.valleybeta3.models.Chatroom;
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

public class ChatroomListActivity extends AppCompatActivity implements IDialogListener {

    private String TAG = "ChatroomListActivity";
    private FirebaseAuth.AuthStateListener mAuthListener;
    private boolean loggedIn = false;
    private ArrayList<String> mChatroomName = new ArrayList<>();
    private ArrayList<String> mChatroomSubName = new ArrayList<>();
    private ArrayList<String> mChatroomKey = new ArrayList<>();
    private ArrayList<Integer> mSmallGroupSub = new ArrayList<>();
    private String mKey;
    private ChatroomListAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private CreateANewChatroom mCreateANewChatroom;

    private int Sub1 = -1;
    private int Sub2 = -1;
    private int Sub3 = -1;
    private int Sub4 = -1;
    private int Sub5 = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupFirebaseAuth();
        getMemberSubscriptions();
        getChatRooms();


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {

            mCreateANewChatroom = new CreateANewChatroom();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.createnewchatframe,mCreateANewChatroom,"Create a new chat");
            transaction.commit();
        });

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
                    mRecyclerView = findViewById(R.id.recycler_view);
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

        if (loggedIn) {
            Query query1 = reference.child(getString(R.string.dbnode_users))
                    .orderByKey()
                    .equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
            query1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    //this loop will return a single result
                    for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
//                    Log.d(TAG, "onDataChange: (QUERY METHOD 1) found user: "
//                           + singleSnapshot.getValue(User.class).toString());
                        User user = singleSnapshot.getValue(User.class);

                        if (!user.getSmall_group_subscription1().equals("")) {
                            Sub1 = Integer.parseInt(user.getSmall_group_subscription1());
                            mSmallGroupSub.add(Sub1);
                        }

                        if (!user.getSmall_group_subscription2().equals("")) {
                            Sub2 = Integer.parseInt(user.getSmall_group_subscription2());
                            mSmallGroupSub.add(Sub2);
                        }

                        if (!user.getSmall_group_subscription3().equals("")) {
                            Sub3 = Integer.parseInt(user.getSmall_group_subscription3());
                            mSmallGroupSub.add(Sub3);
                        }

                        if (!user.getSmall_group_subscription4().equals("")) {
                            Sub4 = Integer.parseInt(user.getSmall_group_subscription4());
                            mSmallGroupSub.add(Sub4);
                        }

                        if (!user.getSmall_group_subscription5().equals("")) {
                            Sub5 = Integer.parseInt(user.getSmall_group_subscription5());
                            mSmallGroupSub.add(Sub5);
                        }


                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
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
                    loggedIn = true;


                } else {
                    loggedIn = false;
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


    @Override
    public void getSmallGroupData(String name, String number) {

    }
}
