package com.example.instacalcid1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class mainactivity_postfragment extends Fragment {

    RecyclerView recyclerView ;
    posthomeadapter adapter;

    public mainactivity_postfragment() {

        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.recyclerview_fragment, container, false);
        recyclerView = view.findViewById(R.id.profile_activity_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseRecyclerOptions<createnew_post> options = new FirebaseRecyclerOptions.Builder<createnew_post>().
                setQuery(FirebaseDatabase.getInstance().getReference().child("posts")
                                //.equalTo("8319085865p1")
                        , createnew_post.class).build();
        adapter = new posthomeadapter( options);
        adapter.startListening();
        recyclerView.setAdapter(adapter);
        return  view ;
    }

}
