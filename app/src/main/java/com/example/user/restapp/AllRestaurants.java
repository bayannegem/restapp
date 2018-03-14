package com.example.user.restapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AllRestaurants extends AppCompatActivity {

    DatabaseReference ref;
    DatabaseReference restsRef;
    List<Restaurant> restsList;
    ListView lvr;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_restaurants);

        init();
    }

    private void init() {
        database = FirebaseDatabase.getInstance();
        ref = database.getReference();
        restsRef = ref.child("restaurants");
        restsList = new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();
        restsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                restsList.clear();
                for (DataSnapshot artistSnapShot:dataSnapshot.getChildren()){
                    Restaurant rest = artistSnapShot.getValue(Restaurant.class);
                    restsList.add(rest);
                }

                RestList adapter = new RestList(AllRestaurants.this,restsList);
                lvr.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
