package com.example.anuku.rentspace;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class Rooms_List extends ArrayAdapter<Room_details>
{
    private Activity context;
    private List<Room_details>roomsList;

    public Rooms_List(Activity context, List<Room_details>roomsList)
    {
        super(context, R.layout.activity_my_places, roomsList);
        this.context = context;
        this.roomsList = roomsList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.activity_my_places, null, true);

        TextView textViewAddress = (TextView) listViewItem.findViewById(R.id.room_address_view);
        TextView textViewPrice = (TextView) listViewItem.findViewById(R.id.room_price_view);
        TextView textViewArea = (TextView) listViewItem.findViewById(R.id.room_area_view);
        TextView textViewDescription = (TextView) listViewItem.findViewById(R.id.room_description_view);
        TextView textViewHeading = (TextView) listViewItem.findViewById(R.id.heading_my_places);

        Room_details room = roomsList.get(position);
        textViewHeading.setText("Room " + (position+1));
        textViewAddress.setText("Address: " + room.getAddress());
        textViewPrice.setText("Price: Rs." +room.getPrice());
        textViewArea.setText("Area: " +room.getArea());
        textViewDescription.setText("Description: " +room.getDescription());

        return listViewItem;
    }
}
