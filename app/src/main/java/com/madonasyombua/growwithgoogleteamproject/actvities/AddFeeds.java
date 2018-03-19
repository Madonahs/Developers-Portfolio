package com.madonasyombua.growwithgoogleteamproject.actvities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.madonasyombua.growwithgoogleteamproject.R;
import com.madonasyombua.growwithgoogleteamproject.models.Feeds;
import com.madonasyombua.growwithgoogleteamproject.util.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddFeeds extends AppCompatActivity {
    private static final String TAG = "AddFeeds";
    private static final int GALLERY_REQUEST_CODE = 10;
    public static final int CAMERA_REQUEST_CODE = 11;

    //FIXME: 3:10:2018 please check the addNAme we can remove it and add other functions i can help too just got tired.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_feeds);
        ButterKnife.bind(this);

    }
   /*// @BindView(R.id.addName)
   // TextInputEditText edit_project_name;
    @BindView(R.id.addDescription)
    TextInputEditText edit_project_description;
    @BindView(R.id.cameraButton)
    ImageButton imageButton;

    String project_name;
    String project_description;

    //Firebase database
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseStorage storage;
    StorageReference storageReference;


    private ImageButton sendButton1;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_feeds);
        ButterKnife.bind(this);

        //init firebase
        database = FirebaseDatabase.getInstance();
        reference = database.getReference(Constant.FIREBASE_FEEDS);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference().child("feeds_photos");

        //clear Feeds Button
        ImageButton clearFeedsButton = findViewById(R.id.feedsClear);
        if(clearFeedsButton != null)
        clearFeedsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        //Adding to FIrebase


        /*FloatingActionButton addingFeed = findViewById(R.id.fab);
        addingFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFeedsToDb();
                finish();
            }
        });*/

        //Giving the options of taking a picture or gallery
        //Ayo - I can create a custom dialog later
        /*imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialog();
            }
        });
    }

    private void createDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(AddFeeds.this);
        builder.setTitle("Choose Image Source");
        builder.setCancelable(true);
        builder.setMessage("Choose between taking a picture or picking from gallery");

        //Camera option
        builder.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                openCamera();
                Toast.makeText(AddFeeds.this, "Camera", Toast.LENGTH_SHORT).show();
            }
        });

        //gallery option
        builder.setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                openGallery();
                Toast.makeText(AddFeeds.this, "Gallery", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
        }
    }

    // FIXME: 3/7/2018 Figure out how to connect the user to storage
    // TODO: 3/7/2018 Not fully done
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case GALLERY_REQUEST_CODE:
                if (resultCode == RESULT_OK){
                    Uri uri = data.getData();
                }
                break;
            case CAMERA_REQUEST_CODE:
                if (resultCode == RESULT_OK){

                }
        }
    }

    private void addFeedsToDb() {
      //  project_name = edit_project_name.getText().toString().trim();
        project_description = edit_project_description.getText().toString().trim();
        Log.e(TAG, "addFeedsToDb: " + project_name + " " + project_description );

        if (TextUtils.isEmpty(project_name) && TextUtils.isEmpty(project_description)) {
       //     edit_project_name.setError("Error");
            edit_project_description.setError("Error");
            Toast.makeText(this, "Please fill in the info", Toast.LENGTH_SHORT).show();
        } else {
            Feeds feeds = new Feeds(project_name, project_description);
            reference.push().setValue(feeds).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(AddFeeds.this, "The upload is successful", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddFeeds.this, "Failure", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }*/


}
