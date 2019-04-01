package com.example.anuku.rentspace;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyPlaces2 extends AppCompatActivity {

    DatabaseReference db_ref;
    FirebaseDatabase db;

    ListView listViewRooms;
    List<Room_details2> roomsList;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_places2);

        listViewRooms = (ListView) findViewById(R.id.list_view_rooms);
        roomsList = new ArrayList<>();

        Bundle bundle=getIntent().getExtras();
        String location=bundle.getString("location");
        String pricerange=bundle.getString("pricerange");
        String category=bundle.getString("category");

        db = FirebaseDatabase.getInstance();
        db_ref = db.getReference(category);
       // Log.e("ApkFLow", String.valueOf(db_ref));


        String toCompare=location.toUpperCase();
        String toCompare2=pricerange.toString().toUpperCase();




        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                roomsList.clear();
                for(DataSnapshot roomSnapshot : dataSnapshot.getChildren())
                {
                    Room_details2 room = roomSnapshot.getValue(Room_details2.class);
                    roomsList.add(room);

                }

                Rooms_List2 adapter = new Rooms_List2(MyPlaces2.this, roomsList);
                listViewRooms.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };


        String Compare=toCompare2;
        String parameter="price";

            if(pricerange.equals("NO")){parameter="address";
            Compare=toCompare;}


             String room="rooms";
            if (category.equals("apartment")){room="apartments";}
             if (category.equals("hotel")){room="hotel";}
            if (category.equals("office")){room="office";}

        Log.e("ApkFLow",room);
        Log.e("ApkFLow",parameter);
        Log.e("ApkFLow",Compare);







                Query query = FirebaseDatabase.getInstance().getReference(room)
                        .orderByChild(parameter)
                        .equalTo(Compare);

//                if(query!=null)
//                {
                    query.addListenerForSingleValueEvent(valueEventListener);
//                }
//                else
//                {
//                    Toast.makeText(MyPlaces2.this,"No results",Toast.LENGTH_LONG).show();
//                }




    }
}
