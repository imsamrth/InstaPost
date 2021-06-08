package com.example.instacalcid1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
//import com.example.instacalcid1.offline.post;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class single_post_activity extends AppCompatActivity {

//    loggined_user_details applicationStatus = (loggined_user_details) this.getApplication();
//    TextView username,caption,timestamp ,likebutton;
//    ImageView userprofile,postimage,commentbutton , sharebutton ;
//    boolean LIKE_BUTTON_LIT = false ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.singlepost_activity);
//        List<post> post_data_result = applicationStatus.getPost_list();
//
//
//        post current_post = post_data_result.get(getIntent().getIntExtra("postID", 1) + 1);
//
//        caption = this.findViewById(R.id.post_fragment_captiontext);
//        timestamp = this.findViewById(R.id.post_fragment_timestamp);
//        username = this.findViewById(R.id.post_fragment_username);
//        userprofile = this.findViewById(R.id.post_fragment_userprofile);
//        postimage = this.findViewById(R.id.post_fragment_postimage);
//        Glide.with(this).load("https://www.google.com/url?sa=i&url=https%3A%2F%2Ftwitter.com%2Fsamarthkt&psig=AOvVaw0O15Do_hvzzBw5UcrXUr_g&ust=1621680830205000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCMjh0ofO2vACFQAAAAAdAAAAABAD").into(postimage);
//        likebutton = this.findViewById(R.id.post_fragment_likebutton);
//        commentbutton = this.findViewById(R.id.post_fragment_commentbutton);
//        sharebutton = this.findViewById(R.id.post_fragment_sharebutton);
//
//        caption.setText("caption");
//        timestamp.setText("thodi der pehle");
//        username.setText(String.valueOf(current_post.getmPostID()));
//        LIKE_BUTTON_LIT = false;
//        commentbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //comment_layout.setVisibility(View.VISIBLE);
//                //MainActivity.POST_ID =  String.valueOf(current_post.getmPostID());
//                //MainActivity.POST_MAKER = String.valueOf(current_post.getmUserid());
////                Dialog write_comment_box = new Dialog(context);
////                write_comment_box.setContentView(R.layout.comments_activity);
////                write_comment_box.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
////                write_comment_box.setCancelable(true);
////
////
////                EditText comment_edittext = write_comment_box.findViewById(R.id.comment_edit_text);
////                FloatingActionButton comment_fab = write_comment_box.findViewById(R.id.send_comment_fab);
////                comment_fab.setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View v) {
////                        String comment = comment_edittext.getText().toString();
////                        post_comment("",comment,String.valueOf(current_post.getmUserid()),String.valueOf(current_post.getmPostID()));
////                    }
////                });
//
//                show_comment_box( String.valueOf(current_post.getmUserid()), String.valueOf(current_post.getmPostID()));
//                //create_COMMENT_notification(String.valueOf(current_post.getmUserid()),String.valueOf(current_post.getmPostID()));
//
//            }
//        });
//        likebutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!LIKE_BUTTON_LIT) {
//                    v.setBackgroundResource(R.drawable.round_favorite_24);
//                    LIKE_BUTTON_LIT = true;
//                   // create_like_notification(String.valueOf(current_post.getmUserid()), String.valueOf(current_post.getmPostID()));
////                } else {
////                    v.setBackgroundResource(R.drawable.outline_favorite_border_24);
////                    //holder.LIKE_BUTTON_LIT = false;
////                }
//
//                }
//            }
//        });
//    }
//
//
////
////
////       int postID = getIntent().getIntExtra("postID",1);
////        //Toast.makeText(getApplicationContext(),,Toast.LENGTH_SHORT).show();
////        List<post>  post_list = applicationStatus.getPost_list();
////        post current_post = post_list.get(postID);
////        username = this.findViewById(R.id.post_fragment_username);
////       username.setText(String.valueOf(current_post.getmPostID()));
////
////       likebutton.setOnClickListener(new View.OnClickListener() {
////           @Override
////           public void onClick(View v) {
////               if (!LIKE_BUTTON_LIT) {
////                   v.setBackgroundResource(R.drawable.round_favorite_24);
////                   LIKE_BUTTON_LIT = true;
////               } else {
////                   v.setBackgroundResource(R.drawable.outline_favorite_border_24);
////                   LIKE_BUTTON_LIT = false;
////               }
////
////           }
////       });
////
////
////
////    }
//        private void post_comment(String user_name,String comment,String post_maker,String post_id){
//            create_comments comment_data = new create_comments(comment);
//            // Toast.makeText(getApplicationContext(),caption,Toast.LENGTH_SHORT).show();
//
//
//            FirebaseDatabase rootnode ;
//            DatabaseReference reference;
//            rootnode =FirebaseDatabase.getInstance();
//            reference = rootnode.getReference("All_comments_box");
////
//            reference.child(post_maker).child(post_id).setValue(comment_data);
//
//        }
//
//
//        private void show_comment_box(String post_maker,String post_id){
//            Dialog write_comment_box = new Dialog(this);
//            write_comment_box.setContentView(R.layout.comments_activity);
//            write_comment_box.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//            write_comment_box.setCancelable(true);
//
//
//            EditText comment_edittext = write_comment_box.findViewById(R.id.comment_box_edit_text);
//            FloatingActionButton comment_fab = write_comment_box.findViewById(R.id.send_box_comment_fab);
//            comment_fab.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    String comment = comment_edittext.getText().toString();
//                    write_comment_box.dismiss();
//                    post_comment("",comment,post_maker,post_id);
//
//                }
//            });
//
//            write_comment_box.show();
//        }


    }
}