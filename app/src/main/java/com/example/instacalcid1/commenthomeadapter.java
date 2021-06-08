package com.example.instacalcid1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class commenthomeadapter extends FirebaseRecyclerAdapter<create_comments, commenthomeadapter.mycomviewholder> {


    public commenthomeadapter(@NonNull FirebaseRecyclerOptions<create_comments> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull mycomviewholder holder, int position, @NonNull create_comments model) {

         holder.comment.setText(model.getComments());
         String profile_url = model.getUserId()+"p0";
         holder.mstorgerefrence_profile = FirebaseStorage.getInstance().getReference().child(profile_url);
                try {
                    final File localfileprofile = File.createTempFile(profile_url,"jpeg");
                    holder.mstorgerefrence_profile.getFile(localfileprofile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(localfileprofile.getAbsolutePath());
                            holder.commenter_profile.setImageBitmap(bitmap);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) { }
                    });

                }catch (IOException e){ e.printStackTrace(); }
    }

    @NonNull
    @Override
    public mycomviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_comment_layout,parent,false);
        return new mycomviewholder(view);
    }

    class mycomviewholder extends RecyclerView.ViewHolder{
       ImageView commenter_profile;
        TextView comment;
        StorageReference  mstorgerefrence_profile ;
        public mycomviewholder(@NonNull View itemView){
            super(itemView);
            comment=itemView.findViewById(R.id.profileofcommenter);
           commenter_profile=itemView.findViewById(R.id.imageView);
        }
    }



}


