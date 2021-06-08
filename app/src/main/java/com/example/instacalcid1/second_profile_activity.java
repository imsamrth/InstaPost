package com.example.instacalcid1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class second_profile_activity extends AppCompatActivity {

    CircleImageView profile_image;
    TextView username, description, followerscount, followingcount;
    StorageReference mstoragerefrence ;

    TabLayout tabLayout ;
    ViewPager2 viewPager2 ;
   secondprofileactivity_fragmentadapter fragmentStateAdapter ;
   Button follow_button ;

    String userId ;
    boolean isFollowing = false ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_profile_activity);

        userId = getIntent().getStringExtra("userId");

        tabLayout = this.findViewById(R.id.second_profile_activity_tablayout);
        viewPager2 = this.findViewById(R.id.second_profile_activity_viewpager);
        profile_image = this.findViewById(R.id.second_account_profile_image);
        description = this.findViewById(R.id.second_account_description);
        username = this.findViewById(R.id.second_username);
        followerscount = this.findViewById(R.id.second_followers_count);
        followingcount = this.findViewById(R.id.second_following_count);
        follow_button = this.findViewById(R.id.activity_followbutton);


        mstoragerefrence = FirebaseStorage.getInstance().getReference().child(userId+"p0");
        try {
            final File localfile = File.createTempFile(userId+"p0","jpeg");
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


        FragmentManager fragmentManager = getSupportFragmentManager() ;
        fragmentStateAdapter = new secondprofileactivity_fragmentadapter(fragmentManager,getLifecycle(),userId);
        viewPager2.setAdapter(fragmentStateAdapter);


       Query QUERY = FirebaseDatabase.getInstance().getReference("users").child(userId);

        QUERY.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    description.setText(snapshot.child("description").getValue(String.class));
                    followerscount.setText(snapshot.child("followers_count").getValue(String.class));
                    followingcount.setText(snapshot.child("following_count").getValue(String.class));
                    username.setText(snapshot.child("name").getValue(String.class));
                    if(userId.matches("8319085865")) username.setCompoundDrawablesWithIntrinsicBounds(null,null,getDrawable(R.drawable.outline_verified_24),null);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Query following_query = FirebaseDatabase.getInstance().getReference("followers").child(userId).orderByKey().equalTo(loggined_user_details.getPhone());

        following_query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    follow_button.setText("Following");
                    isFollowing=true ;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


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

        follow_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFollowing == false) {
                    isFollowing= true;
                    follow_button.setText("following");
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference followersreference = database.getReference().child("followers");
                    followersreference.child(userId).child(loggined_user_details.getPhone()).setValue(loggined_user_details.getPhone());
                    DatabaseReference followingreference = database.getReference().child("following");
                    followingreference.child(loggined_user_details.getPhone()).child(userId).setValue(userId);
                    DatabaseReference following_countreference = database.getReference().child("users").child(loggined_user_details.getPhone());
                    following_countreference.child("following_count").setValue(String.valueOf(Integer.parseInt(loggined_user_details.getFollowing_count()) + 1));
                    loggined_user_details.setFollowing_count(String.valueOf(Integer.parseInt(loggined_user_details.getFollowing_count()) + 1));
                    DatabaseReference follower_countreference = database.getReference().child("users").child(userId);
                    follower_countreference.child("followers_count").setValue(String.valueOf(Integer.parseInt((String) followerscount.getText()) + 1));
                    followerscount.setText(String.valueOf(Integer.parseInt((String) followerscount.getText()) + 1));
                }

            }
        });

    }

}
