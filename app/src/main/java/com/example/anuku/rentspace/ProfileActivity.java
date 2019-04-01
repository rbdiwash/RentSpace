package com.example.anuku.rentspace;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity
{
    private FirebaseAuth firebaseAuth;
    private TextView textViewUserEmail;
    private TextView textViewUserDisplayName;
    private TextView textViewUserPhone;
    private ImageView profilePhoto;

    private Button buttonLogOut;
    private Button home;
    private Button myPlaces;
    private Button addPlaces;
    private Button profilePhotoChoose;
    DatabaseReference mref;
    public String user_id;
    private static final int PICK_IMAGE_REQUEST = 1;

    public static final String USER_ID = "com.example.anuku.rentspace.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null)
        {
            finish();
            startActivity(new Intent(ProfileActivity.this,LoginActivity.class));
        }

        textViewUserEmail=(TextView) findViewById(R.id.email_display);
        textViewUserDisplayName=(TextView) findViewById(R.id.name_display);
        textViewUserPhone=(TextView) findViewById(R.id.phone_display);
        profilePhoto = (ImageView) findViewById(R.id.profile_photo);
        home=(Button)findViewById(R.id.home);

        String uid = firebaseAuth.getUid();
        user_id = uid;

        mref = FirebaseDatabase.getInstance().getReference("Users").child(uid);

        mref.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                User usr = dataSnapshot.getValue(User.class);
                String name = usr.getName();
                String email = usr.getEmail();
                String phone = usr.getPhone();
                textViewUserEmail.setText(email);
                textViewUserDisplayName.setText(name);
                textViewUserPhone.setText(phone);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });

        buttonLogOut = (Button)findViewById(R.id.logout_button);
        myPlaces = (Button) findViewById(R.id.my_places_button);
        addPlaces = (Button) findViewById(R.id.add_places_button);
        profilePhotoChoose = (Button) findViewById(R.id.profile_photo_choose);


        buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(v == buttonLogOut)
                {
                    firebaseAuth.signOut();
                    finish();
                    startActivity(new Intent (ProfileActivity.this,LoginActivity.class));
                }
            }
        });

        myPlaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view == myPlaces)
                {
                    Intent intent = new Intent(ProfileActivity.this, MyPlaces.class);
                    intent.putExtra(USER_ID, user_id);
                    startActivity(intent);
                }
            }
        });

        addPlaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == addPlaces)
                {
                    Intent intent = new Intent(ProfileActivity.this, AddPlaces.class);
                    intent.putExtra(USER_ID, user_id);
                    startActivity(intent);
                }
                }
        });

       home.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if (view == home)
               {
                   Intent intent = new Intent(ProfileActivity.this, CategorySelect.class);
                   startActivity(intent);
               }
           }
       });

        profilePhotoChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

    }

    private void openFileChooser()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            Uri mImageUri = data.getData();
            Picasso.with(this).load(mImageUri).into(profilePhoto);
            //profilePhoto.setImageURI(mImageUri);
        }
    }
}
