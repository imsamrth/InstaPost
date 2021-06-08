package com.example.instacalcid1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class notificationhomeadapter extends FirebaseRecyclerAdapter<createnew_notification,notificationhomeadapter.mynotiviewholder> {


    public notificationhomeadapter(@NonNull FirebaseRecyclerOptions<createnew_notification> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull mynotiviewholder holder, int position, @NonNull createnew_notification model) {

        holder.notification_model_action.setText(model.getNotification());
        String post_url = model.getPost_id();
        String profile_url = model.getUser()+"p0";
        holder.mstorgerefrence_post = FirebaseStorage.getInstance().getReference().child(post_url);
        holder.mstorgerefrence_profile = FirebaseStorage.getInstance().getReference().child(profile_url);
        try {
            final File localfile2 = File.createTempFile(post_url,"jpeg");
            holder.mstorgerefrence_post.getFile(localfile2).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    //Toast.makeText(getApplicationContext(),"file retrived",Toast.LENGTH_SHORT).show();
                    Bitmap bitmap = BitmapFactory.decodeFile(localfile2.getAbsolutePath());
                    holder.notification_model_post.setImageBitmap(bitmap);
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

        try {
            final File localfileprofile = File.createTempFile(profile_url,"jpeg");
            holder.mstorgerefrence_profile.getFile(localfileprofile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    //Toast.makeText(getApplicationContext(),"file retrived",Toast.LENGTH_SHORT).show();
                    Bitmap bitmap = BitmapFactory.decodeFile(localfileprofile.getAbsolutePath());
                    holder.notification_model_second.setImageBitmap(bitmap);
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

        holder.notification_model_second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(model.getUser().matches(loggined_user_details.getPhone())){
                    Intent intent = new Intent(v.getContext(),second_profile_activity.class);
                    intent.putExtra("userId",model.getUser());
                    v.getContext().startActivity(intent);
                }
                else {Intent intent = new Intent( v.getContext(),profile_activity.class) ;
                    v.getContext().startActivity(intent);}
            }
        });

        holder.notification_model_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Intent intent = new Intent(v.getContext(),second_profile_activity.class);
                //intent.putExtra("userId",model.getUser());
               // v.getContext().startActivity(intent);
               // AppCompatActivity activity = (AppCompatActivity) v.getContext();
Intent intent = new Intent(v.getContext(),singlepostactivity.class);
intent.putExtra("postid",model.getPost_id());
intent.putExtra("userid",model.getUser());
    v.getContext().startActivity(intent);

               //notificationactivity_postfragment post_fragment_profileactivityobject = new notificationactivity_postfragment(model.getPost_id(),model.getUser());
               // activity.getSupportFragmentManager().beginTransaction().replace(R.id.notificationactivity_postholder ,post_fragment_profileactivityobject).addToBackStack(null).commit();
            }
        });
    }

    @NonNull
    @Override
    public mynotiviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_model_layout,parent,false);
        return new mynotiviewholder(view);
    }

    class mynotiviewholder extends RecyclerView.ViewHolder{
        ImageView notification_model_second,notification_model_post;
        TextView notification_model_action;
        StorageReference mstorgerefrence_post, mstorgerefrence_profile ;
        public mynotiviewholder(@NonNull View itemView){
            super(itemView);
            notification_model_second=itemView.findViewById(R.id.notification_model_second);
            notification_model_post=itemView.findViewById(R.id.notification_model_post);
            notification_model_action=itemView.findViewById(R.id.notification_model_action);
        }
    }

    }




