package com.dylanritchings.Activities;

import android.content.Intent;
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

import com.dylanritchings.spots.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
        configureForgotPasswordBtn();

    }

    private void configureLoginBtn(){
        Button loginBtn = (Button) findViewById(R.id.loginBtn);

        //Click
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String[] emailPassword = getEmailAndPassword();
                try {
                    signIn(emailPassword[0], emailPassword[1]);
                } catch (Exception e){
                    updateUI(null,null,null);

                }
            }
        });
    }

    private void configureForgotPasswordBtn(){
        Button forgotPasswordBtn = (Button) findViewById(R.id.forgotPasswordBtn);

        //Click
        forgotPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String[] emailPassword = getEmailAndPassword();
                try {
                    forgotPassword(emailPassword[0]);
                } catch (Exception e){
                    updateUI(null,null,null);

                }
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

    private void createAccount(final String email, final String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user,email,password);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null,null,null);
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
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user,email,password);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null,null,null);
                        }

                    }
                });
    }

    private void updateUI(FirebaseUser user,String email,String password){
        TextView errorTextView = (TextView) findViewById(R.id.errorTxt);
        if (user != null){
            errorTextView.setText("   ");
            Intent intent = new Intent(this, MapsActivity.class);
            intent.putExtra(FIREBASE_USER, user);
            storeUser(email,password);
            startActivity(intent);
            finish();
        }
        else {
            errorTextView.setText("Incorrect email or password.");

        }
    }

    private void forgotPassword(String email){

        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        TextView errorTextView = (TextView) findViewById(R.id.errorTxt);
                        if (task.isSuccessful()) {
                            errorTextView.setText("Email sent.");
                        }
                        else{
                            errorTextView.setText("Account doesn't exist.");
                        }
                    }
                });
    }
    private void storeUser(String email, String password){
        AuthPreferences.setUser(email);
        AuthPreferences.setToken(password);
    }

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

