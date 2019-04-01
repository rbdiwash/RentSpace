package com.example.anuku.rentspace;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyPlaces extends AppCompatActivity {

    DatabaseReference db_ref;
    FirebaseDatabase db;

    ListView listViewRooms;
    List<Room_details> roomsList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_places);

        Intent intent = getIntent();
        String user_id = intent.getStringExtra(ProfileActivity.USER_ID);

        listViewRooms = (ListView) findViewById(R.id.list_view_rooms);
        roomsList = new ArrayList<>();
        db = FirebaseDatabase.getInstance();
        db_ref = db.getReference("rooms");

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                roomsList.clear();
                for(DataSnapshot roomSnapshot : dataSnapshot.getChildren())
                {
                    Room_details room = roomSnapshot.getValue(Room_details.class);
                    roomsList.add(room);
                }

                Rooms_List adapter = new Rooms_List(MyPlaces.this, roomsList);
                listViewRooms.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        Query query = FirebaseDatabase.getInstance().getReference("rooms")
                .orderByChild("userid")
                .equalTo(user_id);
        query.addListenerForSingleValueEvent(valueEventListener);

    }
}
