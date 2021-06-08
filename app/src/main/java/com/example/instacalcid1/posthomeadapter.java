package com.example.instacalcid1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.transition.Hold;
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

public class posthomeadapter extends FirebaseRecyclerAdapter<createnew_post,posthomeadapter.myviewholder> {



    public posthomeadapter(@NonNull FirebaseRecyclerOptions<createnew_post> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull createnew_post model) {
        final Boolean[] bool = new Boolean[1];
        holder.username.setText(model.getUser_name());
        holder.captiontext.setText(model.getCaption());
        holder.likebutton.setText(model.getLikes_count());
        holder.timestamp.setText(model.getTimestamp());
        String post_url = model.getUserId()+"p"+model.getPost_count();
        String profile_url  = model.getUserId()+"p0";

        FirebaseDatabase storage = FirebaseDatabase.getInstance();

        DatabaseReference databaseReferencevalue = storage.getReference("likes");
        Query checkUser = databaseReferencevalue.child(post_url).orderByKey().equalTo(loggined_user_details.getPhone());

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    holder.likebutton.setBackgroundResource(R.drawable.round_favorite_24);
                    bool[0]=true ;
                }else {bool[0]= false ;
                }

               // holder.username.setText(String.valueOf(bool[0]));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.mstorgerefrence_profile = FirebaseStorage.getInstance().getReference().child(profile_url);
        try {
            final File localfile2 = File.createTempFile(profile_url,"jpeg");
            holder.mstorgerefrence_profile.getFile(localfile2).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    //Toast.makeText(getApplicationContext(),"file retrived",Toast.LENGTH_SHORT).show();
                    Bitmap bitmap = BitmapFactory.decodeFile(localfile2.getAbsolutePath());
                    holder.user_profile.setImageBitmap(bitmap);
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

        holder.mstorgerefrence_post = FirebaseStorage.getInstance().getReference().child(post_url);
        try {
            final File localfile = File.createTempFile(post_url,"jpeg");
            holder.mstorgerefrence_post.getFile(localfile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    //Toast.makeText(getApplicationContext(),"file retrived",Toast.LENGTH_SHORT).show();
                    Bitmap bitmap = BitmapFactory.decodeFile(localfile.getAbsolutePath());
                    holder.postimage.setImageBitmap(bitmap);
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

        holder.likebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bool[0]==true) {}
                else {bool[0]= true ;
                    holder.likebutton.setBackgroundResource(R.drawable.round_favorite_24);
                    DatabaseReference notification,notification_count,likes_count,likes;

                    likes = storage.getReference("likes").child(post_url);
                    likes.child(loggined_user_details.getPhone()).setValue("liked");

                  if(!model.getUserId() .matches(loggined_user_details.getPhone())) {
                  notification = storage.getReference("notifications").child(model.getUserId()).child(loggined_user_details.getNotification_count());
                    notification.setValue(new createnew_notification(1,model.getPost_count(),model.getUserId()));
                    notification_count=storage.getReference("users").child(loggined_user_details.getPhone());
                    notification_count.child("notification_count").setValue(String.valueOf(Integer.parseInt(loggined_user_details.getNotification_count())+1));
                    loggined_user_details.setNotification_count(String.valueOf(Integer.parseInt(loggined_user_details.getNotification_count())+1));
                  }

                    likes_count = storage.getReference("posts").child(post_url);
                    likes_count.child("likes_count").setValue(String.valueOf(Integer.parseInt(model.getLikes_count())+1));

                }
            }
        });


        holder.comment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               AppCompatActivity activity = (AppCompatActivity) v.getContext();
            comment_fragment post_fragment_profileactivityobject = new comment_fragment(post_url,model.getUserId());
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.commentholder ,post_fragment_profileactivityobject).addToBackStack(null).commit();
            }
        });

        holder.user_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!model.getUserId().matches(loggined_user_details.getPhone())){
                Intent intent = new Intent(v.getContext(),second_profile_activity.class);
                intent.putExtra("userId",model.getUserId());
                v.getContext().startActivity(intent);
            }
            else {Intent intent = new Intent( v.getContext(),profile_activity.class) ;
            v.getContext().startActivity(intent);}}
        });

     holder.sharebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitmapDrawable drawable = (BitmapDrawable) holder.postimage.getDrawable();
                Bitmap bitmap = drawable.getBitmap();

                String bitmappath = MediaStore.Images.Media.insertImage(v.getContext().getContentResolver(),bitmap,"post",null);

                Uri uri = Uri.parse(bitmappath);

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType( "image/png");
                intent.putExtra(Intent.EXTRA_STREAM,uri);
                intent.putExtra(Intent.EXTRA_TEXT,"checkout this post");
                v.getContext().startActivity(Intent.createChooser(intent,"share"));
            }
        });

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_fragment,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder{
        ImageView postimage,comment_button,sharebutton;
        CircleImageView user_profile;
        TextView username ,likebutton ,timestamp ,captiontext;
        StorageReference  mstorgerefrence_post, mstorgerefrence_profile ;
        public myviewholder(@NonNull View itemView){
            super(itemView);

            //comment_field.setHeight(0);
            sharebutton= itemView.findViewById(R.id.post_fragment_sharebutton);
            comment_button=itemView.findViewById(R.id.post_fragment_commentbutton);
            user_profile = itemView.findViewById(R.id.post_fragment_userprofile);
            postimage = itemView.findViewById(R.id.post_fragment_postimage);
            likebutton = itemView.findViewById(R.id.post_fragment_likebutton);
            username = itemView.findViewById(R.id.post_fragment_username);
            timestamp = itemView.findViewById(R.id.post_fragment_timestamp);
            captiontext = itemView.findViewById(R.id.post_fragment_captiontext);
        }
    }

}
