package com.example.kerrymbisset.valleybeta3.ChatRoomRelated;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kerrymbisset.valleybeta3.IDialogListener;
import com.example.kerrymbisset.valleybeta3.R;
import com.example.kerrymbisset.valleybeta3.SmallGroupRelated.SmallGroupDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class CreateAChatRoom extends AppCompatActivity implements IDialogListener {


    private Button mSaveButton;
    private EditText mChatroomName;
    private SmallGroupDialog mSmallGroupDialog;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String TAG = "CreateAChatroom";
    private String mGroupName = "";
    private String mGroupNumber = "";
    private String mChatKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_achat_room);
        mChatroomName = findViewById(R.id.textchatroomname);
        mSaveButton = findViewById(R.id.createChatroombtn);

        setupFirebaseAuth();
        getInitInfo();

        mSaveButton.setOnClickListener(v ->{
            if (savable()){
                Intent intent = new Intent(CreateAChatRoom.this, ChatroomListActivity.class);
                startActivity(intent);
                finish();

            } else {

            }

        });


    }

    private boolean savable() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        mChatKey = UUID.randomUUID().toString();
        boolean isFieldFilled= true;


        if (!mChatroomName.getText().toString().equals("")) {
            reference.child(getString(R.string.dbnode_chatroom))
                    .child(mChatKey)
                    .child(getString(R.string.field_chatroom_name))
                    .setValue(mChatroomName.getText().toString());

        } else {
            Toast.makeText(this, "Please add chatroom name before saving", Toast.LENGTH_SHORT).show();
            isFieldFilled = false;
        }

        if (!mGroupNumber.equals("")) {
            reference.child(getString(R.string.dbnode_chatroom))
                    .child(mChatKey)
                    .child(getString(R.string.field_chatroom_group_key))
                    .setValue(mGroupNumber);
        } else {
            Toast.makeText(this, "Please select a group before saving", Toast.LENGTH_SHORT).show();
            isFieldFilled = false;

        }

        reference.child(getString(R.string.dbnode_chatroom))
                .child(mChatKey)
                .child(getString(R.string.field_chatroom_create_by))
                .setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());

        reference.child(getString(R.string.dbnode_chatroom))
                .child(mChatKey)
                .child(getString(R.string.field_chatroom_subname))
                .setValue(mGroupName);

        reference.child(getString(R.string.dbnode_chatroom))
                .child(mChatKey)
                .child(getString(R.string.field_chatroom_key))
                .setValue(mChatKey);



        return isFieldFilled;

    }

    private void getInitInfo() {

        mSmallGroupDialog = new SmallGroupDialog();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.framesgd, mSmallGroupDialog, "home");
        transaction.commit();

    }


    private void setupFirebaseAuth() {
        Log.d(TAG, "setupFirebaseAuth: started.");

        mAuthListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                // User is signed in
                Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                //toastMessage("Successfully signed in with: " + user.getEmail());


            } else {

            }
            // ...
        };
    }

    @Override
    public void getSmallGroupData(String name, String number) {
        mGroupName = name;
        mGroupNumber = number;
        Toast.makeText(this, "You selected " + name, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void passBackTrueFalse(boolean answer) {

    }
}
