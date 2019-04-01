package com.example.anuku.rentspace;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class CategorySelect extends AppCompatActivity {
    Button roombtn,officebtn,apartmentbtn,hotelbtn,profilebtn,settingsbtn;

    private FirebaseAuth firebaseAuth;
    String user_id;
    public static final String USER_ID = "com.example.anuku.rentspace.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_select);

        firebaseAuth = FirebaseAuth.getInstance();
        String uid = firebaseAuth.getUid();
        user_id = uid;


        final Bundle bundle=new Bundle();

        roombtn=(Button)findViewById(R.id.roombtn);
        apartmentbtn=(Button)findViewById(R.id.apartmentbtn);
        officebtn=(Button)findViewById(R.id.officebtn);
        hotelbtn=(Button)findViewById(R.id.hotelbtn);
        profilebtn=(Button)findViewById(R.id.profile);
        settingsbtn=(Button)findViewById(R.id.settings) ;

        roombtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Apkflow","Category:Room");
                Intent i=new Intent(CategorySelect.this,SearchBox.class);
                bundle.putString("category","room");
                i.putExtras(bundle);
              //  Log.e("ApkFlow",bundle.getString("category"));
                startActivity(i);

            }
        });

        apartmentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Apkflow","Category:Apartment");

                Intent i=new Intent(CategorySelect.this,SearchBox.class);
                bundle.putString("category","apartment");
                i.putExtras(bundle);
                startActivity(i);

            }
        });
        officebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Apkflow","Category:Office");
                Intent i=new Intent(CategorySelect.this,SearchBox.class);
                bundle.putString("category","office");
                i.putExtras(bundle);
                startActivity(i);


            }
        });
        hotelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Apkflow","Category:Hotel/Restaurants");
                Intent i=new Intent(CategorySelect.this,SearchBox.class);
                bundle.putString("category","hotel");
                i.putExtras(bundle);
                startActivity(i);


            }
        });
        profilebtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                Log.e("Apkflow","Profile in Registered Mode Clicked");


                Intent i = new Intent(CategorySelect.this, ProfileActivity.class);
                i.putExtras(bundle);
                startActivity(i);


            }
        });
        settingsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(CategorySelect.this,Settings.class);

                startActivity(i);


            }
        });

    }
}
