package com.example.instacalcid1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
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

public class followershomeadapter extends FirebaseRecyclerAdapter<String,followershomeadapter.myfollowersviewholder> {

    String userId ;

    public followershomeadapter(@NonNull FirebaseRecyclerOptions<String> options,String userId) {
        super(options);
        this.userId = userId ;

    }

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users");

    @Override
    protected void onBindViewHolder(@NonNull followershomeadapter.myfollowersviewholder holder, int position, @NonNull String model) {



        DatabaseReference currentfollower  = databaseReference.child(model);
        final boolean[] isFollowing = {false};
        String profile_url  = model+"p0";

        currentfollower.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                holder.user_query_username.setText(snapshot.child("name").getValue().toString());
                String profile_url  = model+"p0";
                holder.user_query_followerscount.setText(snapshot.child("followers_count").getValue().toString());

                holder.mstorgerefrence_profile = FirebaseStorage.getInstance().getReference().child(profile_url);
                try {
                    final File localfile2 = File.createTempFile(profile_url,"jpeg");
                    holder.mstorgerefrence_profile.getFile(localfile2).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            //Toast.makeText(getApplicationContext(),"file retrived",Toast.LENGTH_SHORT).show();
                            Bitmap bitmap = BitmapFactory.decodeFile(localfile2.getAbsolutePath());
                            holder.user_query_image.setImageBitmap(bitmap);
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if(model.matches(loggined_user_details.getPhone())) holder.user_query_follow.setText("you");
        isFollowing[0]= true ;

      //createnewuser currentfollower = FirebaseDatabase.getInstance().getReference().child("users").child(model).getDatabase();

        Query following_query = FirebaseDatabase.getInstance().getReference("followers").child(model).orderByKey().equalTo(loggined_user_details.getPhone());

        following_query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    holder.user_query_follow.setText("Following");
                    isFollowing[0] =true ;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.user_query_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFollowing[0] == false) {
                    isFollowing[0]= true;
                    holder.user_query_follow .setText("Following");
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference followersreference = database.getReference().child("followers");
                    followersreference.child(model).child(loggined_user_details.getPhone()).setValue(loggined_user_details.getName());
                    DatabaseReference followingreference = database.getReference().child("following");
                    followingreference.child(loggined_user_details.getPhone()).child(model).setValue(model);
                    DatabaseReference following_countreference = database.getReference().child("users").child(loggined_user_details.getPhone());
                    following_countreference.child("following_count").setValue(String.valueOf(Integer.parseInt(loggined_user_details.getFollowing_count()) + 1));
                    loggined_user_details.setFollowing_count(String.valueOf(Integer.parseInt(loggined_user_details.getFollowing_count()) + 1));
                    DatabaseReference follower_countreference = database.getReference().child("users").child(model);
                    follower_countreference.child("followers_count").setValue(String.valueOf(Integer.parseInt((String) holder.user_query_followerscount.getText()) + 1));
                    // followerscount.setText(String.valueOf(Integer.parseInt((String) followerscount.getText()) + 1));
                }
            }
        });


        holder.user_query_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(model.matches(loggined_user_details.getPhone())){

                    Intent intent = new Intent( v.getContext(),profile_activity.class) ;
                    v.getContext().startActivity(intent);

                }
                else { Intent intent = new Intent(v.getContext(),second_profile_activity.class);
                    intent.putExtra("userId",model);
                    v.getContext().startActivity(intent);}
            }
        });

    }

    @NonNull
    @Override
    public followershomeadapter.myfollowersviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_user_list_item,parent,false);
        return new followershomeadapter.myfollowersviewholder(view);
    }

    class myfollowersviewholder extends RecyclerView.ViewHolder{
        CircleImageView user_query_image;
        TextView user_query_username;
        Button user_query_follow ;
        TextView user_query_followerscount;
        View user_query_parentlayout ;
        StorageReference mstorgerefrence_profile;
        public myfollowersviewholder(@NonNull View itemView){
            super(itemView);
            user_query_image= itemView.findViewById(R.id.list_item_user_profile);
            user_query_follow= itemView.findViewById(R.id.single_user_follow_button);
            user_query_username= itemView.findViewById(R.id.list_item_username);
            user_query_followerscount= itemView.findViewById(R.id.list_item_followerscount);
            user_query_parentlayout = itemView.findViewById(R.id.list_item_user);
        }
    }



}


