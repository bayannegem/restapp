
package com.example.user.restapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class RestFeedActivity extends AppCompatActivity {

    private Button btnAddRest;
    DatabaseReference ref;
    DatabaseReference restsRef;
    List<Restaurant> restsList;
    ListView lvr;
    FirebaseDatabase database;
    List<Restaurant> List2 = new ArrayList<Restaurant>();
    EditText Search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_feed);

        init();
    }

    private void init() {

        btnAddRest = (Button)findViewById(R.id.btnAddRest);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference();
        restsRef = ref.child("restaurants");
        restsList = new ArrayList<>();
        lvr = (ListView)findViewById(R.id.listViewSearch);
        Search = (EditText) findViewById(R.id.searchView);
        lvr.setOnItemClickListener(itemClickListener);


        btnAddRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RestFeedActivity.this, AddRestaurantActivity.class);
                startActivity(i);

            }
        });

        Search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                SearchList(s.toString(),restsList);
            }
        });

        restsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try {
                    restsList.clear();
                    for (DataSnapshot artistSnapShot : dataSnapshot.getChildren()) {
                        Restaurant rest = artistSnapShot.getValue(Restaurant.class);
                        restsList.add(rest);
                    }

                    RestList adapter = new RestList(RestFeedActivity.this, restsList);
                    lvr.setAdapter(adapter);
                }
                catch(Exception ex)
                {
                    Log.e("onDataChange", ex.getMessage());
                    Toast.makeText(RestFeedActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void SearchList(String s, List<Restaurant> allusers) {
        List2.clear();
        int i = 0;
        for (Restaurant restaurant : allusers) {
            if (restaurant.getName().toLowerCase().contains(s.toLowerCase()) ||
                    restaurant.getDescription().toLowerCase().contains(s.toLowerCase()) ||
                    restaurant.getName().toLowerCase().contains(s.toLowerCase()) ||
                    restaurant.getFaceebook().contains(s.toLowerCase()) ||
                    restaurant.getWebsite().contains(s.toLowerCase()) ||
                    restaurant.getLocation().contains(s.toLowerCase())) {
                List2.add(restaurant);
            }
            i++;
        }

        RestList adapter = new RestList(RestFeedActivity.this, List2);
        lvr.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    public void gotoAllRests(View view) {

        Intent i = new Intent(this, AllRestaurants.class);
        startActivity(i);
    }

    public final static String name="name";
    public final static String description="description";
    public final static String phone="phone";
    public final static String website="website";
    public final static String faceebook="facebook";
    public final static String whatsapp="whatsapp";
    public final static String location="location";
    public final static String Image = "Image";

    AdapterView.OnItemClickListener  itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Restaurant rest = restsList.get(i);
            Intent intent = new Intent(RestFeedActivity.this,OneRestActivity.class);
            intent.putExtra(name,rest.getName());
            intent.putExtra(description,rest.getDescription());
            intent.putExtra(phone,rest.getPhone());
            intent.putExtra(website,rest.getWebsite());
            intent.putExtra(faceebook,rest.getFaceebook());
            intent.putExtra(whatsapp,rest.getWhatsapp());
            intent.putExtra(location,rest.getLocation());
            intent.putExtra(Image,rest.getImage());
            startActivity(intent);
        }
    };

}
