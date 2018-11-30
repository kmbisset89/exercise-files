package com.example.kerrymbisset.valleybeta3.ChatRoomRelated;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kerrymbisset.valleybeta3.IDialogListener;
import com.example.kerrymbisset.valleybeta3.R;
import com.example.kerrymbisset.valleybeta3.SmallGroupRelated.SmallGroupDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class CreateANewChatroom extends Fragment implements IDialogListener {
    private SmallGroupDialog mSmallGroupDialog = new SmallGroupDialog();
    private String TAG = "CreateANewChatroom";
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText mChatroomName;
    private int mGroupNumber;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_anew_chatroom, container, false);
        mChatroomName = view.findViewById(R.id.textchatroomname);
        setupFirebaseAuth();
        getInitInfo();
        return view;

    }

    private void getInitInfo() {
        mSmallGroupDialog= new SmallGroupDialog();
        FragmentTransaction  transaction =getChildFragmentManager().beginTransaction();
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
        mGroupNumber = Integer.parseInt(number);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.remove(mSmallGroupDialog);
        transaction.commit();
        Toast.makeText(getContext(), "You selected " + name, Toast.LENGTH_SHORT).show();
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
