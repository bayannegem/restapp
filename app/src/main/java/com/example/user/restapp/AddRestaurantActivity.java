package com.example.user.restapp;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class AddRestaurantActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 234;
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
    private ImageView imgRest;

    private Uri filePath;
    private StorageReference storageReference;


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
        imgRest = (ImageView) findViewById(R.id.imgAddPhoto);
        storageReference = FirebaseStorage.getInstance().getReference();
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

    public void addImage(View view) {
        showFileChooser();
    }


    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imgRest.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        //checking if file is available
        if (filePath != null) {
            //displaying progress dialog while image is uploading
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            //getting the storage reference
            StorageReference sRef = storageReference.child(Constants.STORAGE_PATH_UPLOADS + System.currentTimeMillis() + "." + getFileExtension(filePath));

            //adding the file to reference
            sRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //dismissing the progress dialog
                            progressDialog.dismiss();

                            //displaying success toast
                            Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();

                            //creating the upload object to store uploaded image details
                            Upload upload = new Upload(etReastaurantName.getText().toString().trim(), taskSnapshot.getDownloadUrl().toString());

                            //adding an upload to firebase database
                            String uploadId = restsRef.push().getKey();
                            restsRef.child(uploadId).setValue(upload);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //displaying the upload progress
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });
        } else {
            //display an error if no file is selected
        }
    }


    public void upload(View view) {
        uploadFile();
    }
}
