package com.example.user.restapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class OneRestActivity extends AppCompatActivity {

    private ImageView imgRest;
    private TextView txtName;
    private TextView txtDesc;
    private ImageButton imgbtnPhone;
    private ImageButton imgbtnFacebook;
    private ImageButton imgbtnWhatsapp;

    private String name;
    private String description;
    private String phone;
    private String website;
    private String facebook;
    private String whatsapp;
    private String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_rest);

        init();
    }

    public final static String REST_NAME="name";
    public final static String REST_DESC="description";
    public final static String REST_PHONE="phone";
    public final static String REST_WEB="website";
    public final static String REST_FACE="facebook";
    public final static String REST_WHATS="whatsapp";
    public final static String REST_LOCATE="location";

    private void init() {
        name= getIntent().getStringExtra(REST_NAME);
        description= getIntent().getStringExtra(REST_DESC);
        phone= getIntent().getStringExtra(REST_PHONE);
        website= getIntent().getStringExtra(REST_WEB);
        facebook= getIntent().getStringExtra(REST_FACE);
        whatsapp= getIntent().getStringExtra(REST_WHATS);
        location= getIntent().getStringExtra(REST_LOCATE);

        txtName = (TextView) findViewById(R.id.txtNameOne);
        txtDesc = (TextView) findViewById(R.id.txtDescOne);
        imgbtnPhone = (ImageButton)findViewById(R.id.imgbtnPhoneOne);
        imgbtnFacebook = (ImageButton)findViewById(R.id.imgbtnPhoneOne);
        imgbtnWhatsapp = (ImageButton)findViewById(R.id.imgbtnWhatsOne);

        txtName.setText(name);
        txtDesc.setText(description);
        imgbtnPhone.setTag(phone);
        imgbtnFacebook.setTag(facebook);
        imgbtnWhatsapp.setTag(whatsapp);

        imgbtnPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialContactPhone(phone);
            }
        });
    }

    private void dialContactPhone(final String phoneNumber) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
    }
}
