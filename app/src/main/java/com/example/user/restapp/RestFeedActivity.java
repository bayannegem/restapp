package com.example.user.restapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RestFeedActivity extends AppCompatActivity {

    private Button btnAddRest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_feed);

        init();
    }

    private void init() {
        btnAddRest = (Button)findViewById(R.id.btnAddRest);
        btnAddRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RestFeedActivity.this, AddRestaurantActivity.class);
                startActivity(i);
            }
        });
    }

    public void gotoAllRests(View view) {

        Intent i = new Intent(this, AllRestaurants.class);
        startActivity(i);
    }
}
