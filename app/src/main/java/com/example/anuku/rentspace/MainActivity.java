package com.example.anuku.rentspace;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private Button buttonRegister;
    private Button guestMode;
    private EditText enterEmail;
    private EditText enterPhone;
    private EditText enterName;
    private EditText enterPassword;
    private TextView alreadyRegistered;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference; //DatabaseReference

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser user= firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");  //To root directory
//        databaseReference= FirebaseDatabase.getInstance().getReference();

        progressDialog=new ProgressDialog(this);

        enterName=(EditText) findViewById(R.id.enterName);
        enterPhone=(EditText) findViewById(R.id.enterPhone);
        buttonRegister=(Button)findViewById(R.id.buttonRegister);

        enterEmail=(EditText)findViewById(R.id.enterEmail);
        enterPassword=(EditText)findViewById(R.id.enterPassword);
        alreadyRegistered=(TextView)findViewById(R.id.alreadyRegistered);
        guestMode=(Button)findViewById(R.id.guestMode);

//        //Validate Email
//        enterEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
////                if(enterEmail.getText().toString()=="^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"){
//                if(enterEmail.getText().toString()!="^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"){
//
//
//                    enterEmail.setError("Incorrect Email");
//                }
//            }
//        });

//        enterPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if(enterPhone.getText().length()<7||enterPhone.getText().length()>14){
//                    enterPhone.setError("Incorrect Number");
//                }
//            }
//        });
//        //PHONE NUMBER VALIDATION
//        btn_validate_phone=(Button)findViewById(R.id.btn_validate_phone);
//        btn_validate_phone.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                if(isValidPhone(enterPhone.getText().toString())) {
//                    Toast.makeText(getApplicationContext(), "Phone number is valid", Toast.LENGTH_SHORT).show();
//                    }else{
//                    Toast.makeText(getApplicationContext(),"Phone number is not valid ",Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        guestMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==guestMode){
                    //Guest Mode
                    Log.e("ApkFlow","Guest Mode Clicked");
                    startActivity(new Intent(MainActivity.this,GuestMode.class));
                }
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v==buttonRegister){
                    registerUser();
                }
            }
        });

        alreadyRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==alreadyRegistered){
                    //User Login
                    startActivity(new Intent(MainActivity.this ,LoginActivity.class));
                }
            }
        });

    }

    private void registerUser() {
        String email = enterEmail.getText().toString().trim();
        String password = enterPassword.getText().toString().trim();
        String user_name = enterName.getText().toString().trim();
        final String phone = enterPhone.getText().toString().trim();

        if (TextUtils.isEmpty(user_name)) {
            //Name is empty
            Toast.makeText(this, "Please Enter your Name", Toast.LENGTH_SHORT).show();
            //Stop function execution
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            //Email is empty
            Toast.makeText(this, "Please Enter your Phone Number", Toast.LENGTH_SHORT).show();
            //Stop function execution
            return;
        }
        if (TextUtils.isEmpty(email)) {
            //Email is empty
            Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show();
            //Stop function execution
            return;
//        }else{
//            checkEmailValidity(null);
        }


        if (TextUtils.isEmpty(password)) {
            //Password is empty
            Toast.makeText(this, "Please enter Password", Toast.LENGTH_SHORT).show();
            return;
        }
        //If validation is OK
        //Show progress bar
        progressDialog.setMessage("Registering user . . . ");
        progressDialog.show();

//        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if(task.isSuccessful()){
//                        //Profile Activity Here
//                        finish();
//                        startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
//
//                    //User registered and logged in
//                    //Start profile activity
//                    Toast.makeText(MainActivity.this,"Registered Successfully",Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(MainActivity.this,"Failed to Register",Toast.LENGTH_SHORT).show();
//                }
//                progressDialog.dismiss();
//            }
//        });


        final String enterNAME = enterName.getText().toString().trim();
        final String Email = enterEmail.getText().toString().trim();
        final String Phone = enterPhone.getText().toString().trim();


        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //Send verificaton email
                    sendVerificationEmail();
                    String user_id = firebaseAuth.getCurrentUser().getUid();        //Take Uid from Auth for Database
                    DatabaseReference current_user_db = databaseReference.child(user_id);  //Create new child
                    User usr =  new User(user_id,enterNAME,Phone,Email);
                    current_user_db.setValue(usr);

//                    current_user_db.child("image").setValue("default");

                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this,"Email sent to Verify User",Toast.LENGTH_SHORT).show();
                    firebaseAuth.signOut();


                    //Sign Out
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }else{
                    Toast.makeText(MainActivity.this,"Unable to Register User",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });

    }

    public void sendVerificationEmail(){
        FirebaseUser user= firebaseAuth.getInstance().getCurrentUser();

        if(user!=null) {
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(MainActivity.this,"Verification Email Sent.",Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Couldn't send email", Toast.LENGTH_SHORT).show();


                    }
                }
            });
        }
    }
}
