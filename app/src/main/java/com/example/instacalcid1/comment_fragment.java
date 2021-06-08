package com.example.instacalcid1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class comment_fragment extends Fragment {


    Button comment_send ;
    EditText editeed_comment ;
    String postId,userId ;
    RecyclerView comment_recyclerView ;
    commenthomeadapter comment_adapter;

    public comment_fragment(String postid,String userId) {
        // Required empty public constructor
        this.postId = postid;
        this.userId = userId ;

    }


    // TODO: Rename and change types and number of parameters
//    public static comment_fragment newInstance(String param1, String param2) {
//        comment_fragment fragment = new comment_fragment();
//        Bundle args = new Bundle();
//
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       //
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.comment_fragment, container, false);
        Toast.makeText(container.getContext(),postId,Toast.LENGTH_LONG).show();
        comment_recyclerView =view.findViewById(R.id.comment_receycler);
        FirebaseRecyclerOptions<create_comments> options = new FirebaseRecyclerOptions.Builder<create_comments>().
                setQuery(FirebaseDatabase.getInstance().getReference("comments").child(postId),create_comments.class).build();
        comment_adapter = new commenthomeadapter( options);
        comment_adapter.startListening();
        comment_recyclerView.setHasFixedSize(true);
        comment_recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));

        comment_recyclerView.setAdapter(comment_adapter);
        comment_send = view.findViewById(R.id.comment_send_button);
        editeed_comment= view.findViewById(R.id.comment_edited);



        comment_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("comments");
                databaseReference.child(postId).child(loggined_user_details.getPhone()).setValue(new create_comments(editeed_comment.getText().toString()));

                if(!userId.matches( loggined_user_details.getPhone())) {
                    DatabaseReference notification = FirebaseDatabase.getInstance().getReference("notifications").child(userId).child(loggined_user_details.getNotification_count());
                    notification.setValue(new createnew_notification(2,postId,userId));
                    DatabaseReference notification_count=FirebaseDatabase.getInstance().getReference("users").child(loggined_user_details.getPhone());
                    notification_count.child("notification_count").setValue(String.valueOf(Integer.parseInt(loggined_user_details.getNotification_count())+1));
                    loggined_user_details.setNotification_count(String.valueOf(Integer.parseInt(loggined_user_details.getNotification_count())+1));
                }
            }
        });

   return view ;
    }
}