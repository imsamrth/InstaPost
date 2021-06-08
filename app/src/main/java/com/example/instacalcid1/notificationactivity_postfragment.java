package com.example.instacalcid1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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


public class notificationactivity_postfragment extends Fragment {

    ImageView postimage,comment_button;
    CircleImageView user_profile;
    TextView username ,likebutton ,timestamp ,captiontext;
    StorageReference mstorgerefrence_post, mstorgerefrence_profile ;
    DatabaseReference postrefernece ;
    String getLikes_count ,profile_url;



        //comment_field.setHeight(0);



String postid ,userid;


    public notificationactivity_postfragment(String postid,String userid) {

        this.postid = postid ;
        this.userid = userid ;
        this.profile_url = userid+"p0";
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) { }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final Boolean[] bool = new Boolean[1];

        View view =  inflater.inflate(R.layout.fragment_notificationactivity_postfragment, container, false);
        comment_button=view.findViewById(R.id.post_fragment_commentbutton);
        user_profile = view.findViewById(R.id.post_fragment_userprofile);
        postimage = view.findViewById(R.id.post_fragment_postimage);
        likebutton = view.findViewById(R.id.post_fragment_likebutton);
        username = view.findViewById(R.id.post_fragment_username);
        //username.setText(userid);
        timestamp = view.findViewById(R.id.post_fragment_timestamp);
        captiontext = view.findViewById(R.id.post_fragment_captiontext);

        postrefernece = FirebaseDatabase.getInstance().getReference().child("posts").child(postid);


        FirebaseDatabase storage = FirebaseDatabase.getInstance();

        DatabaseReference databaseReferencevalue = storage.getReference("likes");
        Query checkUser = databaseReferencevalue.child(postid).orderByKey().equalTo(loggined_user_details.getPhone());

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                   likebutton.setBackgroundResource(R.drawable.round_favorite_24);
                    bool[0]=true ;
                }else {bool[0]= false ;
                }

                // holder.username.setText(String.valueOf(bool[0]));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Query query = FirebaseDatabase.getInstance().getReference("posts").orderByKey().equalTo(postid);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                likebutton.setText(snapshot.child(postid).child("likes_count").getValue(String.class));
                getLikes_count = likebutton.getText().toString();
                username.setText(snapshot.child(postid).child("user_name").getValue(String.class));
                captiontext.setText(snapshot.child(postid).child("caption").getValue(String.class));
                timestamp.setText(snapshot.child(postid).child("timestamp").getValue(String.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mstorgerefrence_profile = FirebaseStorage.getInstance().getReference().child(profile_url);
        try {
            final File localfile2 = File.createTempFile(profile_url,"jpeg");
            mstorgerefrence_profile.getFile(localfile2).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    //Toast.makeText(getApplicationContext(),"file retrived",Toast.LENGTH_SHORT).show();
                    Bitmap bitmap = BitmapFactory.decodeFile(localfile2.getAbsolutePath());
                 user_profile.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    //Toast.makeText(getApplicationContext(),"error in retriving",Toast.LENGTH_SHORT).show();
                }
            });

        }catch (IOException e){
            e.printStackTrace();
        }

        mstorgerefrence_post = FirebaseStorage.getInstance().getReference().child(postid);
        try {
            final File localfile = File.createTempFile(postid,"jpeg");
            mstorgerefrence_post.getFile(localfile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    //Toast.makeText(getApplicationContext(),"file retrived",Toast.LENGTH_SHORT).show();
                    Bitmap bitmap = BitmapFactory.decodeFile(localfile.getAbsolutePath());
                    postimage.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    //Toast.makeText(getApplicationContext(),"error in retriving",Toast.LENGTH_SHORT).show();
                }
            });

        }catch (IOException e){
            e.printStackTrace();
        }



       likebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bool[0]==true) {}
                else {bool[0]= true ;
                    likebutton.setBackgroundResource(R.drawable.round_favorite_24);
                    DatabaseReference notification,notification_count,likes_count,likes;

                    likes = storage.getReference("likes").child(postid);
                    likes.child(loggined_user_details.getPhone()).setValue("liked");

                    if(userid != loggined_user_details.getPhone()) {
                        notification = storage.getReference("notifications").child(userid).child(loggined_user_details.getNotification_count());
                        notification.setValue(new createnew_notification(1,postid,userid));
                        notification_count=storage.getReference("users").child(loggined_user_details.getPhone());
                        notification_count.child("notification_count").setValue(String.valueOf(Integer.parseInt(loggined_user_details.getNotification_count())+1));
                        loggined_user_details.setNotification_count(String.valueOf(Integer.parseInt(loggined_user_details.getNotification_count())+1));
                    }

                    likes_count = storage.getReference("posts").child(postid);
                    likes_count.child("likes_count").setValue(String.valueOf(Integer.parseInt(getLikes_count)+1));

                }
            }
        });

       user_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!userid.matches(loggined_user_details.getPhone())){
                    Intent intent = new Intent(v.getContext(),second_profile_activity.class);
                    intent.putExtra("userId",userid);
                    v.getContext().startActivity(intent);
                }
                else {Intent intent = new Intent( v.getContext(),profile_activity.class) ;
                    v.getContext().startActivity(intent);}}
        });

        // Inflate the layout for this fragment
     return  view ;
    }
}