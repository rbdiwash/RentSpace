package com.example.anuku.rentspace;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddPlaces extends AppCompatActivity implements View.OnClickListener
{
    EditText address, price, dimension, description;
    Button submit;
    FirebaseDatabase db;
    DatabaseReference db_ref;
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_places);

        db = FirebaseDatabase.getInstance();
        db_ref = db.getReference("rooms");
        Intent intent = getIntent();
        user_id = intent.getStringExtra(ProfileActivity.USER_ID);
        Log.e("Apk Fow","user_id");

        address = (EditText) findViewById(R.id.add_places_edit_address);
        price = (EditText) findViewById(R.id.add_places_edit_price);
        dimension = (EditText) findViewById(R.id.add_places_edit_area);
        description = (EditText) findViewById(R.id.add_places_edit_description);
        submit = (Button) findViewById(R.id.add_places_submit_button);

        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.add_places_submit_button:
                String room_address = address.getText().toString().toUpperCase();
                String room_price = price.getText().toString();
                String room_dimension = dimension.getText().toString();
                String room_description = description.getText().toString();
                if(!TextUtils.isEmpty(room_address) && !TextUtils.isEmpty(room_price) && !TextUtils.isEmpty(room_dimension))
                {
                    String id = db_ref.push().getKey();
                    Room_details room = new Room_details(room_address, room_price, room_dimension, room_description, user_id);
                    db_ref.child(id).setValue(room);
                    Toast.makeText(this, "A new room has been added!!", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(this, "The fields cannot be empty!!", Toast.LENGTH_LONG).show();
                }
        }
    }
}
