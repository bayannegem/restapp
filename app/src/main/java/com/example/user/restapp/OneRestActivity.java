package com.example.user.restapp;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.security.Permission;

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
    private String image;

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
    public final static String REST_IMAGE="image";


    private void init() {
        name= getIntent().getStringExtra(REST_NAME);
        description= getIntent().getStringExtra(REST_DESC);
        phone= getIntent().getStringExtra(REST_PHONE);
        website= getIntent().getStringExtra(REST_WEB);
        facebook= getIntent().getStringExtra(REST_FACE);
        whatsapp= getIntent().getStringExtra(REST_WHATS);
        location= getIntent().getStringExtra(REST_LOCATE);
        image= getIntent().getStringExtra(REST_IMAGE);


        txtName = (TextView) findViewById(R.id.txtNameOne);
        txtDesc = (TextView) findViewById(R.id.txtDescOne);
        imgbtnPhone = (ImageButton)findViewById(R.id.imgbtnPhoneOne);
        imgbtnFacebook = (ImageButton)findViewById(R.id.imgbtnPhoneOne);
        imgbtnWhatsapp = (ImageButton)findViewById(R.id.imgbtnWhatsOne);
        imgRest = findViewById(R.id.imgRest);
        Picasso.with(getApplicationContext()).load(image).into(imgRest);

        txtName.setText(name);
        txtDesc.setText(description);
        imgbtnPhone.setTag(phone);
        imgbtnFacebook.setTag(facebook);
        imgbtnWhatsapp.setTag(whatsapp);


    }

    private void dialContactPhone(final String phoneNumber) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
    }

    public void openRestFBPage(View view)
    {
        String YourPageURL = "http://www.facebook.com";//imgbtnFacebook.getTagya().toString() ;
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(YourPageURL));

        startActivity(browserIntent);
    }

    public void onClickWhatsApp(View view) {
        String mPhoneNumber;
        PackageManager pm=getPackageManager();
        try {

            if (!checkReadPhoneStatePermission())
            {
                Toast.makeText(this, "Error!", Toast.LENGTH_LONG).show();
                return;
            }
            else
            {
                TelephonyManager tMgr = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
                mPhoneNumber = tMgr.getLine1Number();
            }

            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("text/plain");
            String text = "I want to reserve a table at your restaurant. Please reserve me one. This is my number: " + mPhoneNumber;

            PackageInfo info=pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            //Check if package exists or not. If not then code
            //in catch block will be called
            waIntent.setPackage("com.whatsapp");

            waIntent.putExtra(Intent.EXTRA_TEXT, text);
            startActivity(Intent.createChooser(waIntent, "Share with"));

        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(this, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private boolean checkReadPhoneStatePermission()
    {
        String permission = Manifest.permission.READ_PHONE_STATE;
        int res = this.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    public void onClickDialPhone(View view) {
        dialContactPhone(imgbtnPhone.getTag().toString());
    }
}
