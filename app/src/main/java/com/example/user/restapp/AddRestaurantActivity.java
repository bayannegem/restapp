package com.example.user.restapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AddRestaurantActivity extends AppCompatActivity {

    private EditText etReastaurantName;
    private EditText etDescription;
    private EditText etPhone;
    private EditText etWebsite;
    private EditText etFacebook;
    private EditText etWhatsapp;
    private EditText etLocation;
    private Button btnAddRest;
    private FirebaseDatabase database;
    private DatabaseReference ref;
    private DatabaseReference restsRef;
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);

        init();
    }

    private void init() {
        etReastaurantName = (EditText)findViewById(R.id.etReastaurantName);
        etDescription = (EditText)findViewById(R.id.etDescription);
        etPhone = (EditText)findViewById(R.id.etPhone);
        etWebsite =  (EditText)findViewById(R.id.etWebsite);
        etFacebook =  (EditText)findViewById(R.id.etFacebook);
        etWhatsapp =  (EditText)findViewById(R.id.etWhatsapp);
        etLocation =  (EditText)findViewById(R.id.etLocatiom);
        btnAddRest =  (Button)findViewById(R.id.btnAddRest);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        restsRef = ref.child("restaurants");
        restsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void addRest(View view)
    {
        String name = etReastaurantName.getText().toString();
        String desc = etDescription.getText().toString();
        String phone = etPhone.getText().toString();
        String web = etWebsite.getText().toString();
        String face = etFacebook.getText().toString();
        String whats = etWhatsapp.getText().toString();
        String locate = etLocation.getText().toString();
        if (!name.isEmpty() && !desc.isEmpty() && !phone.isEmpty() && !web.isEmpty() &&
                !face.isEmpty() && !whats.isEmpty() && !locate.isEmpty() )
        {
            Restaurant rest = new Restaurant(name, desc, phone ,web, face, whats, locate);
            restsRef.push().setValue(rest);
            Toast.makeText(this, R.string.add_rest_success, Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText( AddRestaurantActivity.this, R.string.missing_data,
                    Toast.LENGTH_SHORT).show();
        }

    }
}
