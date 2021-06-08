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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
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

import java.io.InputStream;
import java.util.List;

public class addpost_activity extends AppCompatActivity {



    int present_selected_item;
    BottomNavigationView bottom_nav_view;
    loggined_user_details myApplication  = (loggined_user_details) this.getApplication();
   // List<post> postList ;
    FloatingActionButton gallery_add ;
    Boolean imgadd = false ;
    Uri filepath ;
    Bitmap bitmap ;
    ImageView galleryeditorselectediamge ;
    EditText caption_text ;

    String new_post_name,nextpostcount ,caption;

    DatabaseReference databaseReferencevalue;
    Query checkUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.galleryeditor_activity);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        galleryeditorselectediamge = this.findViewById(R.id.galleryeditor_activity_selected_image);
        caption_text = this.findViewById(R.id.edit_text_caption);


        databaseReferencevalue = FirebaseDatabase.getInstance().getReference("users");
        checkUser = databaseReferencevalue.orderByKey().equalTo(loggined_user_details.getPhone());

        gallery_add = this.findViewById(R.id.gallery_activity_add);

        gallery_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                if(imgadd == false) {
                    Toast.makeText(getApplicationContext(), "permission granted", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");

                    startActivityForResult(intent.createChooser(intent, "please select image"), 1);

                    Dexter.withActivity(addpost_activity.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
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
                }else {galleryeditorselectediamge.setImageResource(R.drawable.outline_image_24);
                       imgadd = false ;
                gallery_add.setImageResource(R.drawable.outline_add_a_photo_24);} }
        });





        bottom_nav_view = this.findViewById(R.id.bottom_nav_view);
        bottom_nav_view.setSelectedItemId(R.id.bottom_nav_menu_reels);
        bottom_nav_view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.bottom_nav_home:
                        Intent intent_home = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent_home);
                        break;
                    case R.id.bottom_nav_search:
                         Intent intent_search = new Intent(getApplicationContext(), search_activity.class);
                         startActivity(intent_search);
                        break;
                    case R.id.bottom_nav_reels:

                        // TODO MAKE IT UNRESPONSIVE
                        caption = String.valueOf(caption_text.getText());
                        if(imgadd == true){
                            checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    int total_post  = Integer.valueOf(snapshot.child(loggined_user_details.getPhone()).child("totalpost_count").getValue(String.class));
                                    nextpostcount = String.valueOf(total_post+1);
                                    new_post_name = loggined_user_details.getPhone() + "p" + nextpostcount;
                                    Toast.makeText(addpost_activity.this,"inside if pass"+ nextpostcount + new_post_name,Toast.LENGTH_SHORT).show();
                                    uploadimage();
                                    uploadpost_details(caption);
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {


                                }
                            });


                            galleryeditorselectediamge.setImageResource(R.drawable.outline_image_24);
                            imgadd = false ;
                            gallery_add.setImageResource(R.drawable.outline_add_a_photo_24);
                            Toast.makeText(getApplicationContext(),"Image uploaded succesfully",Toast.LENGTH_SHORT).show();
                            caption_text.setText(null);
                        }
                        present_selected_item = item.getItemId();
                        break;
                    case R.id.bottom_nav_notification:

                        Intent intent_notification = new Intent( getApplicationContext(), notifications_activity.class);
                        startActivity(intent_notification);
                        present_selected_item = item.getItemId();
                        break;
                    case R.id.bottom_nav_profile:
                        Intent intent_profile = new Intent(getApplicationContext(), profile_activity.class);
                        startActivity(intent_profile);
                        break;
                }
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.galleryeditor_menu, menu);
        return true;
    }

    public void uploadimage() {

                FirebaseStorage storage = FirebaseStorage.getInstance();
        Toast.makeText(getApplicationContext(),new_post_name,Toast.LENGTH_SHORT).show();

                StorageReference uploader = storage.getReference().child(new_post_name);
                uploader.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getApplicationContext(),"filesuplosded",Toast.LENGTH_SHORT).show();
                       databaseReferencevalue.child(loggined_user_details.getPhone()).child("totalpost_count").setValue(nextpostcount);
                        loggined_user_details.setTotalpost_count(nextpostcount);
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    }
                });
                 Toast.makeText(this,"done",Toast.LENGTH_SHORT).show();
            }

    public void uploadpost_details(String caption){

        createnew_post postdatahelpervalues = new createnew_post(caption,nextpostcount);

       // String NEW_POST_ONE = applicationStatus.getPhone()+"p"+applicationStatus.getTotal_post();

        Toast.makeText(getApplicationContext(),caption,Toast.LENGTH_SHORT).show();


        FirebaseDatabase rootnode ;
        DatabaseReference reference;
        rootnode =FirebaseDatabase.getInstance();
        reference = rootnode.getReference("posts");
//
      reference.child(new_post_name).setValue(postdatahelpervalues);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 1 && resultCode == RESULT_OK){

            filepath = data.getData();
            try
            {
                InputStream inputStream = getContentResolver().openInputStream(filepath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                galleryeditorselectediamge.setImageBitmap(bitmap);
                gallery_add.setImageResource(R.drawable.outline_remove_circle_24);
                imgadd = true ;
                Toast.makeText(addpost_activity.this,imgadd.toString(), Toast.LENGTH_SHORT).show();
            }
            catch (Exception e){
                Toast.makeText(getApplicationContext(),"error in loading image", Toast.LENGTH_SHORT).show();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}