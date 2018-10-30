package com.example.kerrymbisset.valleybeta3;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.kerrymbisset.valleybeta3.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";

    private static final String DOMAIN_NAME = "gmail.com";

    //widgets
    private EditText mEmail, mPassword, mConfirmPassword;
    private Button mRegister;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mEmail = findViewById(R.id.input_email);
        mPassword = findViewById(R.id.input_password);
        mConfirmPassword = findViewById(R.id.input_confirm_password);
        mRegister = findViewById(R.id.btn_register);
        mProgressBar = findViewById(R.id.progressBar);

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: attempting to register.");

                //check for null valued EditText fields
                if (!isEmpty(mEmail.getText().toString())
                        && !isEmpty(mPassword.getText().toString())
                        && !isEmpty(mConfirmPassword.getText().toString())) {

                    //check if user has a company email address
                    if (isValidEmail(mEmail.getText().toString())) {

                        //check if passwords match
                        if (doStringsMatch(mPassword.getText().toString(), mConfirmPassword.getText().toString())) {

                            //Initiate registration task
                            registerNewEmail(mEmail.getText().toString(), mPassword.getText().toString());
                        } else {
                            Toast.makeText(RegisterActivity.this, "Passwords do not Match", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RegisterActivity.this, "Please Register with Company Email", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(RegisterActivity.this, "You must fill out all the fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        hideSoftKeyboard();

    }

    private void sendVerififcaitonEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Sent Verification Email", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(RegisterActivity.this, "Was not able to send verification email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    /**
     * Register a new email and password to Firebase Authentication
     *
     * @param email
     * @param password
     */
    public void registerNewEmail(final String email, String password) {

        showDialog();

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                    if (task.isSuccessful()) {
                        Log.d(TAG, "onComplete: AuthState: " + FirebaseAuth.getInstance().getCurrentUser().getUid());

                        sendVerififcaitonEmail();

                        User user = new User();
                        user.setName(email.substring(0, email.indexOf("@")));
                        user.setPhone("1");
                        user.setProfile_image("");
                        user.setSecurity_level("1");
                        user.setUser_id(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        user.setEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                        user.setStreet_address("");
                        user.setCity("");
                        user.setState("");
                        user.setZip("");
                        user.setMember_filter("Member");
                        user.setSmall_group_subscription1("");
                        user.setSmall_group_subscription2("");
                        user.setSmall_group_subscription3("");
                        user.setSmall_group_subscription4("");
                        user.setSmall_group_subscription5("");
                        user.setShow_phone("No");
                        user.setShow_email("Yes");
                        user.setShow_address("No");

                        FirebaseDatabase.getInstance().getReference()
                                .child(getString(R.string.dbnode_users))
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user)
                                .addOnCompleteListener(task1 -> {
                                    FirebaseAuth.getInstance().signOut();
                                    redirectLoginScreen();
                                }).addOnFailureListener(e -> {
                                    FirebaseAuth.getInstance().signOut();
                                    redirectLoginScreen();
                                    Toast.makeText(RegisterActivity.this, "Database Problem ",Toast.LENGTH_SHORT);

                                });





                    }
                    if (!task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "Unable to Register",
                                Toast.LENGTH_SHORT).show();
                    }
                    hideDialog();

                    // ...
                });
    }

    /**
     * Returns True if the user's email contains '@tabian.ca'
     *
     * @param email
     * @return
     */
    private boolean isValidEmail(String email) {
        Log.d(TAG, "isValidDomain: verifying email has correct annotation: " + email);
        String domain = email.substring(email.indexOf("@") + 1).toLowerCase();
        Log.d(TAG, "isValidDomain: users domain: " + domain);
        return domain.equals(DOMAIN_NAME);
    }

    /**
     * Redirects the user to the login screen
     */
    private void redirectLoginScreen() {
        Log.d(TAG, "redirectLoginScreen: redirecting to login screen.");

        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Return true if @param 's1' matches @param 's2'
     *
     * @param s1
     * @param s2
     * @return
     */
    private boolean doStringsMatch(String s1, String s2) {
        return s1.equals(s2);
    }

    /**
     * Return true if the @param is null
     *
     * @param string
     * @return
     */
    private boolean isEmpty(String string) {
        return string.equals("");
    }


    private void showDialog() {
        mProgressBar.setVisibility(View.VISIBLE);

    }

    private void hideDialog() {
        if (mProgressBar.getVisibility() == View.VISIBLE) {
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void hideSoftKeyboard() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }


}
















