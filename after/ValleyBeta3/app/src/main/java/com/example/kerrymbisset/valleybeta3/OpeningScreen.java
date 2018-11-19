package com.example.kerrymbisset.valleybeta3;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.kerrymbisset.valleybeta3.MemberRelated.MembershipList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.kerrymbisset.valleybeta3.List_Activity.CHOICE;


public class OpeningScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AllConstants {

    //Firebase
    private FirebaseAuth.AuthStateListener mAuthListener;
    private final String TAG = getClass().getSimpleName();;
    private boolean ISLOGGEDIN;
    private String status = null;
    private MenuItem loggedout;
    private MenuItem loggedin;
    private MenuItem profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: started.");
        setupFirebaseAuth();
        setContentView(R.layout.activity_opening_screen);
        Toolbar toolbar = findViewById(R.id.toolbar_opening);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);





    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        loggedout = menu.findItem(R.id.action_logout);
        loggedin = menu.findItem(R.id.action_login);
        profile = menu.findItem(R.id.action_settings);
        if (!ISLOGGEDIN) {
            loggedout.setVisible(false);
            profile.setVisible(false);
            loggedin.setVisible(true);
        } else {
            loggedout.setVisible(true);
            loggedin.setVisible(false);
            profile.setVisible(true);
        }


        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.opening_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        if (id == R.id.action_login) {
            startActivity(new Intent(this, LoginActivity.class));
            return true;
        }

        if (id == R.id.action_logout) {

            signOut();
            ISLOGGEDIN = false;

            loggedout.setVisible(false);
            loggedin.setVisible(true);

            Toast.makeText(this, "Successfully Logged out", Toast.LENGTH_SHORT).show();


            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void signOut() {
        Log.d(TAG, "signOut: signing out");
        FirebaseAuth.getInstance().signOut();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_event) {

            Intent intent = new Intent(OpeningScreen.this, List_Activity.class)
                    .putExtra(CHOICE, EVENTFILTERSEL);
            startActivity(intent);
        } else if (id == R.id.nav_small_group) {
            Intent intent = new Intent(OpeningScreen.this, List_Activity.class)
                    .putExtra(CHOICE, SMALLGROUPFILTERSEL);
            startActivity(intent);

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_connect) {
            Intent intent = new Intent(OpeningScreen.this, MembershipList.class)
                    .putExtra(CHOICE, MEMBERSFILTERSEL);
            startActivity(intent);


        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void selectNavigationMenuItem(int id) {
        NavigationView navigationView = findViewById(R.id.nav_event);
        Menu menu = navigationView.getMenu();
        menu.findItem(id).setChecked(true);
    }

    @Override
    public android.support.v4.app.LoaderManager getSupportLoaderManager() {
        return super.getSupportLoaderManager();
    }


    /*
            ----------------------------- Firebase setup ---------------------------------
         */
    private void setupFirebaseAuth() {
        Log.d(TAG, "setupFirebaseAuth: started.");

        mAuthListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {

                Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                ISLOGGEDIN = true;

            } else {
                Log.d(TAG, "onAuthStateChanged:signed_out");

                ISLOGGEDIN = false;
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

    @Override
    protected void onResume() {
        super.onResume();
        checkAuthenticationState();
    }

    private void checkAuthenticationState() {
        Log.d(TAG, "checkAuthenticationState: checking authentication state.");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null) {
            Log.d(TAG, "checkAuthenticationState: user is null, the are logged out.");
            ISLOGGEDIN = false;
        } else {
            Log.d(TAG, "checkAuthenticationState: user is authenticated.");
            ISLOGGEDIN = true;
        }
    }


}


