package com.example.instacalcid1;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class profile_activity extends AppCompatActivity {

    //private AppBarConfiguration mAppBarConfiguration;
    Dialog add_post_dialog;
    ImageButton profileeditor;
    CircleImageView profile_image;
    TextView username, description, followerscount, followingcount;
    DatabaseReference reference;
    StorageReference mstoragerefrence ;
    ViewParent navheaderlayout;
    ViewPager2 viewPager2 ;
    TabLayout tabLayout ;
    profileactivity_fragmentadapter fragmentStateAdapter ;

    String profile_url ;


    int present_selected_item;
    BottomNavigationView bottom_nav_view;
    loggined_user_details uservalues = (loggined_user_details) this.getApplication();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);


        tabLayout = this.findViewById(R.id.profile_activity_tablayout);
        viewPager2 = this.findViewById(R.id.profile_activity_viewpager);
        FragmentManager fragmentManager = getSupportFragmentManager() ;
        fragmentStateAdapter = new profileactivity_fragmentadapter(fragmentManager,getLifecycle());
        viewPager2.setAdapter(fragmentStateAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });



        profile_image = this.findViewById(R.id.account_profile_image);
        // assigning variables
        FloatingActionButton logout_fab, add_post_fab;
        //add_post_fab = findViewById(R.id.fab);
        add_post_dialog = new Dialog(profile_activity.this);
        description = this.findViewById(R.id.account_description);
        username = this.findViewById(R.id.username);
        profile_url = loggined_user_details.getPhone()+"p0";

        followerscount = this.findViewById(R.id.followers_count);
        followingcount = this.findViewById(R.id.following_count);
        description.setText(uservalues.getDescription());
        followerscount.setText(String.valueOf(uservalues.getFollowers_count()));
        followingcount.setText(String.valueOf(uservalues.getFollowing_count()));
        username.setText(uservalues.getName());
        Toast.makeText(this, loggined_user_details.getPhone(),Toast.LENGTH_SHORT).show();


        showalluserdata();

        bottom_nav_view = this.findViewById(R.id.bottom_nav_view);
        bottom_nav_view.setSelectedItemId(R.id.bottom_profile);
        bottom_nav_view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment temp_fragment;

                switch (item.getItemId()) {
                    case R.id.bottom_home:
                        Intent intent_home = new Intent(profile_activity.this, MainActivity.class);
                        startActivity(intent_home);
                        break;
                    case R.id.bottom_search:
                        Intent intent_search = new Intent(profile_activity.this, search_activity.class);
                        startActivity(intent_search);
                        break;
                    case R.id.bottom_reels:

                        // TODO MAKE IT UNRESPONSIVE
                        //add_post_dialog.show();
                        Intent intent_gallery = new Intent(getApplicationContext(), addpost_activity.class);
                        startActivity(intent_gallery);
                        present_selected_item = R.id.bottom_nav_menu_profile;
                        bottom_nav_view.setSelectedItemId(R.id.bottom_nav_menu_profile);
                        break;
                    case R.id.bottom_notification:
                        Intent intent_notification = new Intent(getApplicationContext(), notifications_activity.class);
                        startActivity(intent_notification);
                        present_selected_item = item.getItemId();
                        break;
                    case R.id.bottom_profile:
                        //Intent intent_profile = new Intent(profile_activity.this, profile_activity.class);
                        // startActivity(intent_profile);
                        add_post_dialog.show();

//                        Toast.makeText(getApplicationContext(), "you logged outt", Toast.LENGTH_SHORT).show();
//                        //TODO make snack bar saying succesfully logout
//                        Intent intent = new Intent(profile_activity.this, login_activity.class);
//                        startActivity(intent);
                        break;
                }


                return true;
            }


        });



        //lide.with(this).load("https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png").into(profile_image);

        mstoragerefrence = FirebaseStorage.getInstance().getReference().child(profile_url);
        try {
            final File localfile = File.createTempFile(profile_url,"jpeg");
            mstoragerefrence.getFile(localfile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getApplicationContext(),"file retrived",Toast.LENGTH_SHORT).show();
                    Bitmap bitmap = BitmapFactory.decodeFile(localfile.getAbsolutePath());
                    profile_image.setImageBitmap(bitmap);
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


        reference = FirebaseDatabase.getInstance().getReference("users");

        profileeditor = this.findViewById(R.id.profile_editor_button);
        profileeditor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), profile_editor_activity.class);
                startActivity(intent);
            }
        });


        //setting dialog box
        add_post_dialog.setContentView(R.layout.add_post_dialog_box);
        add_post_dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        add_post_dialog.setCancelable(true);

        //setting up dialog box layout views
        LinearLayout writepost_linear_layout = add_post_dialog.findViewById(R.id.add_post_write);
        LinearLayout camera_linear_layout = add_post_dialog.findViewById(R.id.add_post_camera);
        //LinearLayout gallery_linear_layout = add_post_dialog.findViewById(R.id.add_post_gallery);

        //setting up dialog box view on click listener
        writepost_linear_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                add_post_dialog.dismiss();
            }
        });

//        gallery_linear_layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(profile_activity.this, gallery_editor_activity.class);
//                startActivity(intent);
//                add_post_dialog.dismiss();
//            }
//        });

        camera_linear_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "you logged outt", Toast.LENGTH_SHORT).show();
                //TODO make snack bar saying succesfully logout
                Intent intent = new Intent(profile_activity.this, login_activity.class);
                startActivity(intent);
                add_post_dialog.dismiss();
            }
        });


    }


    private void showalluserdata() {
        description.setText(uservalues.getDescription());
        //username.setText(intent.getStringExtra("name"));
        //description.setText(intent.getStringExtra("description"));
    }

}


//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if (item.getItemId() == R.id.profile_activity_add_post) {
//
//            //TODO make snack bar saying succesfully logout
//            Intent intent = new Intent(profile_activity.this,splash_activity.class);
//            startActivity(intent);
//            finish();
//
//
//        }
//        return super.onOptionsItemSelected(item);
//    }

