package com.example.instacalcid1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class profile_editor_activity extends AppCompatActivity {

    CircleImageView profileimageeditor;
    EditText edit_description;
    FloatingActionButton change_porfile ;
    Button updateprofilebutton;
    Boolean image_added = false ;
    Uri filepath ;
    Bitmap bitmap ;
    DatabaseReference databaseReferencevalue;
    Query checkUser;
    String profile_name ,nextpostcount,new_post_name;
    StorageReference mstoragerefrence ;
    String profile_url ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_editor_activity);


        profileimageeditor= this.findViewById(R.id.profile_image_editor);
        edit_description = this.findViewById(R.id.edit_desc);
        edit_description.setText(loggined_user_details.getDescription());
        updateprofilebutton = this.findViewById(R.id.update_profile);
        change_porfile = this.findViewById(R.id.change_profile);

        profile_url = loggined_user_details.getPhone()+"p0";
        mstoragerefrence = FirebaseStorage.getInstance().getReference().child(profile_url);
        profile_url = loggined_user_details.getPhone()+"p0";
        try {
            final File localfile = File.createTempFile(profile_url,"jpeg");
            mstoragerefrence.getFile(localfile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getApplicationContext(),"file retrived",Toast.LENGTH_SHORT).show();
                    Bitmap bitmap = BitmapFactory.decodeFile(localfile.getAbsolutePath());
                    profileimageeditor.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),"error in retriving",Toast.LENGTH_SHORT).show();
                }
            });

        }catch (IOException e){
            e.printStackTrace();
        }

        change_porfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                if(image_added == false) {
                    Toast.makeText(getApplicationContext(), "permission granted", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");

                    startActivityForResult(intent.createChooser(intent, "please select image"), 1);

                    Dexter.withActivity(profile_editor_activity.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse response) {
                            Toast.makeText(getApplicationContext(), "permission granted", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Intent.ACTION_PICK);
                            intent.setType("image/*");
                            startActivityForResult(intent.createChooser(intent, "please select image"), 1);

                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse response) {
                            Toast.makeText(getApplicationContext(), "permission denied", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                            Toast.makeText(getApplicationContext(), "permission asked", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {profileimageeditor.setImageResource(R.drawable.outline_image_24);
                    image_added = false ;
                    change_porfile.setImageResource(R.drawable.outline_add_a_photo_24);} }
        });


        databaseReferencevalue = FirebaseDatabase.getInstance().getReference("users");
        checkUser = databaseReferencevalue.orderByKey().equalTo(loggined_user_details.getPhone());


        updateprofilebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(image_added == true){
                    checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            new_post_name = loggined_user_details.getPhone() + "p0";
                            uploadImage();

                            Toast.makeText(getApplicationContext(),"Image uploaded succesfully",Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
               updatecaption();
            }
        });

    }

    public void uploadImage() {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference uploader = storage.getReference().child(new_post_name);
        uploader.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getApplicationContext(),"image uploaded",Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
            }
        });
        // Toast.makeText(this,"done",Toast.LENGTH_SHORT).show();
    }



    private void updatecaption() {

        FirebaseDatabase rootnode;
        DatabaseReference reference;
        rootnode = FirebaseDatabase.getInstance();
        reference = rootnode.getReference("users");

        reference.child("8319085865").child("description").setValue(edit_description.getText().toString());
        loggined_user_details.setDescription(edit_description.getText().toString());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 1 && resultCode == RESULT_OK){

            filepath = data.getData();
            try
            {
                InputStream inputStream = getContentResolver().openInputStream(filepath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                profileimageeditor.setImageBitmap(bitmap);
                image_added = true ;
            }
            catch (Exception e){
                Toast.makeText(getApplicationContext(),"error in loading image", Toast.LENGTH_SHORT).show();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}