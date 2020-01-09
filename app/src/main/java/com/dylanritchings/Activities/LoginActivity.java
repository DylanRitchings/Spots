package com.dylanritchings.Activities;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountAuthenticatorActivity;
import android.content.Intent;
import android.media.session.MediaSession;
import android.os.Bundle;
import android.util.Log;
import com.dylanritchings.Utils.AuthPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.dylanritchings.Activities.MapsActivity;
import com.dylanritchings.Utils.AuthPreferences;
import com.dylanritchings.spots.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private AuthPreferences authPreferences;
    private static final String TAG = "LoginActivity";
    public static final String FIREBASE_USER = "com.dylanritchings.Spots";
    protected void onCreate(Bundle savedInstanceState) {

        // Initialize Firebase Auth
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        Button forgotPasswordBtn = (Button) findViewById(R.id.forgotPasswordBtn);

        configureLoginBtn();
        configureRegisterBtn();

    }

    private void configureLoginBtn(){
        Button loginBtn = (Button) findViewById(R.id.loginBtn);

        //Click
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String[] emailPassword = getEmailAndPassword();
                signIn(emailPassword[0],emailPassword[1]);
            }
        });
    }

    private void configureRegisterBtn(){
        Button registerBtn = (Button) findViewById(R.id.registerBtn);

        //Click
        registerBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //Crashlytics.getInstance().crash(); // Force a crash
                String[] emailPassword = getEmailAndPassword();
                createAccount(emailPassword[0],emailPassword[1]);
            }
        });

    }

    private String[] getEmailAndPassword(){

        //Creates email and password objects
        EditText emailEditText = (EditText) findViewById(R.id.emailEditText);
        EditText passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String[] emailPassword = new String[]{email, password};
        return emailPassword;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }
    private void createAccount(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void signIn(final String email, final String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("TEST","1");
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            Log.d("TEST","2");
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void updateUI(FirebaseUser user){
        TextView errorTextView = (TextView) findViewById(R.id.errorTextView);
        if (user != null){
            Log.d("TEST","3");
            errorTextView.setText("   ");
            Intent intent = new Intent(this, MapsActivity.class);
            intent.putExtra(FIREBASE_USER, user);
            //storeUser(email,password);
            startActivity(intent);
            finish();
        }
        else {
            Log.d("TEST","4");
            errorTextView.setText("Incorrect email or password.");

        }
    }

//    private void storeUser(String email, String password){
//        AuthPreferences.setUser(email);
//        AuthPreferences.setToken(password);
//    }

    /*
    private void updateUI1(FirebaseUser user) {
        if (user != null) {
            mStatusTextView.setText(getString(R.string.google_status_fmt, user.getEmail()));
            mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));

            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
        } else {
            mStatusTextView.setText(R.string.signed_out);
            mDetailTextView.setText(null);

            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
        }
    }
     */

}
