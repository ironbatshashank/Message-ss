package com.example.message_ss;

import android.content.Intent;
import android.os.Bundle;
/*
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
*/
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;
    private EditText mPhoneNumber, mCode;
    private Button mSend;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;


    String mVerificationId;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);


        userIsLoggedIn();
        mPhoneNumber = findViewById(R.id.phoneNumber);
        mCode = findViewById(R.id.code);
        mSend = findViewById(R.id.send);

        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mVerificationId != null)
                    verifyPhoneNummberWithCode();
                else
                    startPhoneNumberVerification();
            }
        });


        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

            }

            @Override
            public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(verificationId, forceResendingToken);

                mVerificationId = verificationId;
                mSend.setText("Verify Code");
            }
        };

        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void verifyPhoneNummberWithCode(){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, mCode.getText().toString());
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential phoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                    userIsLoggedIn();
            }
        });
    }

    private void userIsLoggedIn() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if( user != null){
            startActivity(new Intent(getApplicationContext(), MainPageActivity.class));
            finish();
            return;
        }
    }

    private void startPhoneNumberVerification() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mPhoneNumber.getText().toString(),
                 60,
                TimeUnit.SECONDS,
                (Executor) this,
                mCallbacks
        );
    }

}
