package com.example.user.restapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.provider.MediaStore.Audio.AudioColumns.ARTIST_ID;

public class AllRestaurants extends AppCompatActivity {

    DatabaseReference ref;
    DatabaseReference restsRef;
    DatabaseReference uploadsRef;
    List<Restaurant> restsList;
    ListView lvr;
    FirebaseDatabase database;
    private List<Upload> uploads;
    private ProgressDialog progressDialog;

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
        lvr = (ListView)findViewById(R.id.lvr);
        lvr.setOnItemClickListener(itemClickListener);
        //progressDialog = (ProgressDialog) findViewById(R.id.progressBar);
        prepareImagesRetreivalFromFirebase();
    }

    private void prepareImagesRetreivalFromFirebase() {
        uploads = new ArrayList<>();

        //displaying progress dialog while fetching images
        //progressDialog.setMessage("Please wait...");
        //progressDialog.show();
        uploadsRef = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);

        //adding an event listener to fetch values
        uploadsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //dismissing the progress dialog
                //progressDialog.dismiss();

                //iterating through all the values in database
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Upload upload = postSnapshot.getValue(Upload.class);
                    uploads.add(upload);
                }
                //creating adapter
                //adapter = new MyAdapter(getApplicationContext(), uploads);

                //adding adapter to recyclerview
                //recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });
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

    public final static String name="name";
    public final static String description="description";
    public final static String phone="phone";
    public final static String website="website";
    public final static String facebook="facebook";
    public final static String whatsapp="whatsapp";
    public final static String location="location";
    public final static String image = "image";

    AdapterView.OnItemClickListener  itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Restaurant rest = restsList.get(i);
            Intent intent = new Intent(AllRestaurants.this,OneRestActivity.class);
            intent.putExtra(name,rest.getName());
            intent.putExtra(description,rest.getDescription());
            intent.putExtra(phone,rest.getPhone());
            intent.putExtra(website,rest.getWebsite());
            intent.putExtra(facebook,rest.getFaceebook());
            intent.putExtra(whatsapp,rest.getWhatsapp());
            intent.putExtra(location,rest.getLocation());
            intent.putExtra(image,rest.getImage());
            startActivity(intent);
        }
    };


}
