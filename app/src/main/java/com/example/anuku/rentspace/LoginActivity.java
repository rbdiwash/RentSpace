package com.example.anuku.rentspace;

import android.app.ProgressDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private Button buttonLogin;
    private EditText enterLoginEmail;
    private EditText enterLoginPassword;
    private TextView notRegistered;
    private FirebaseAuth firebaseAuth;  //Firebase Reference
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance(); //To root Directory

        if(firebaseAuth.getCurrentUser()!=null){
            //Profile Activity Here
            finish();
            startActivity(new Intent(getApplicationContext(),CategorySelect.class));
        }else{

        }

        enterLoginEmail=(EditText) findViewById(R.id.enterloginEmail);
        enterLoginPassword=(EditText) findViewById(R.id.enterloginPassword);
        buttonLogin=(Button) findViewById(R.id.buttonLogin);
        notRegistered=(TextView) findViewById(R.id.notRegistered);
        progressDialog= new ProgressDialog(this);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==buttonLogin){
                    userLogin();
                }
            }
        });

        notRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==notRegistered){
                    finish();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
            }
        });

    }
    private void userLogin(){
        String email= enterLoginEmail.getText().toString().trim();
        String password=enterLoginPassword.getText().toString().trim();


        if(TextUtils.isEmpty(email)){
            //Email is empty
            Toast.makeText(this,"Please Enter Email",Toast.LENGTH_SHORT).show();
            //Stop function execution
            return ;
        }
        if(TextUtils.isEmpty(password)){
            //Password is empty
            Toast.makeText(this,"Please enter Password",Toast.LENGTH_SHORT).show();
            return;
        }
        //If validation is OK
        //Show progress bar
        progressDialog.setMessage("Logging in. .  . ");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();

                FirebaseUser user= firebaseAuth.getCurrentUser();

                if (!task.isSuccessful()) {
                    //Error Logging In
                    Toast.makeText(LoginActivity.this,"Failed to Login",Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));

                }else{
                    try {
                        if (user.isEmailVerified()) {
                            Toast.makeText(LoginActivity.this, "User is email verified", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, CategorySelect.class);
                            startActivity(intent);

                        }else{
                            Toast.makeText(LoginActivity.this, "Email is not verified",Toast.LENGTH_SHORT).show();
                            firebaseAuth.signOut();
                        }
                    }catch(NullPointerException e){

                    }
                }

            }
        });

    }
}
