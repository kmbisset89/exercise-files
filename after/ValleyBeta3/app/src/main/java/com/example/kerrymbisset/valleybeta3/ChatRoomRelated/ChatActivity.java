package com.example.kerrymbisset.valleybeta3.ChatRoomRelated;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.kerrymbisset.valleybeta3.R;
import com.example.kerrymbisset.valleybeta3.models.Chat_Messages;
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

import static com.example.kerrymbisset.valleybeta3.ChatRoomRelated.ChatroomListActivity.CHATKEY;

public class ChatActivity extends AppCompatActivity {

    private String TAG = getClass().getSimpleName();
    private ArrayList<Chat_Messages> mMessages = new ArrayList<>();
    private ImageButton addMessage;
    private EditText messageText;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private boolean loggedIn = false;
    private String chatroomKey;
    private ArrayList<String> mBadWords = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        addMessage = findViewById(R.id.messageBtn);
        messageText = findViewById(R.id.messagetext);

        chatroomKey = getIntent().getStringExtra(CHATKEY);
        getInappropriateWord();
        setupFirebaseAuth();
        getOlderMessages();


        addMessage.setOnClickListener(v-> {
            if (checkAppropriate()) {
                saveMessage();
            }

        });

    }

    private void getInappropriateWord() {

        mBadWords.add(getResources().getString(R.string.badword1));
        mBadWords.add(getResources().getString(R.string.badword2));
        mBadWords.add(getResources().getString(R.string.badword3));
        mBadWords.add(getResources().getString(R.string.badword4));
        mBadWords.add(getResources().getString(R.string.badword5));
        mBadWords.add(getResources().getString(R.string.badword6));
        mBadWords.add(getResources().getString(R.string.badword7));
        mBadWords.add(getResources().getString(R.string.badword8));
        mBadWords.add(getResources().getString(R.string.badword9));
        mBadWords.add(getResources().getString(R.string.badword10));
        mBadWords.add(getResources().getString(R.string.badword11));

    }

    private boolean checkAppropriate() {
         boolean staysAppropriate = true;

         String message = messageText.getText().toString();

         String[] scannableWords = message.split("\\s");

         for( String eachWordIn : scannableWords )
         {
            String comparableWord = eachWordIn;





         }



        return staysAppropriate;
    }

    private void saveMessage() {


    }

    private void getOlderMessages() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        Query query1 = reference.child(getString(R.string.dbnode_chat))
                .orderByKey()
                .equalTo(chatroomKey);
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //this loop will return a single result
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {

                    Chat_Messages chat = singleSnapshot.getValue(Chat_Messages.class);



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

    }


}
