package com.example.anuku.rentspace;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SearchBox extends AppCompatActivity {


    Button bn,sbp;
    EditText slocation,sprice;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.e("Apkflow","SearchActivity");

        Bundle bundle2=getIntent().getExtras();
        final String category=bundle2.getString("category");
        Log.e("Apkflow",category);



        final Bundle bundle=new Bundle();
        setContentView(R.layout.activity_search_box);
        bn=(Button)findViewById(R.id.button2);
       sbp=(Button)findViewById(R.id.button3);


        slocation=(EditText)findViewById(R.id.slocation);
        sprice=(EditText)findViewById(R.id.sprice);





        bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String seprice= sprice.getText().toString();
                final String selocation=slocation.getText().toString();

                Log.e("ApkFLow","LOCATION BASED");
                Intent i=new Intent(SearchBox.this,MyPlaces2.class);
                bundle.putString("location",selocation);
                bundle.putString("pricerange","NO");
                bundle.putString("category",category);
                i.putExtras(bundle);
                startActivity(i);

            }
        });
        sbp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("ApkFLow","PRICE BASED");

                final String seprice= sprice.getText().toString();
                final String selocation=slocation.getText().toString();

                Intent i=new Intent(SearchBox.this,MyPlaces2.class);

                bundle.putString("location","NO");
                bundle.putString("pricerange",seprice);
                bundle.putString("category",category);

                i.putExtras(bundle);
                startActivity(i);

            }
        });
    }
}
