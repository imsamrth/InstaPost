package com.example.instacalcid1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class followers_fragment extends Fragment {

    RecyclerView recyclerView ;
  followershomeadapter adapter;
    String userId ;


    public followers_fragment(String userId) {

        this.userId = userId ;
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.post_profileactivity, container, false);

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.recyclerview_fragment, container, false);
        recyclerView = view.findViewById(R.id.profile_activity_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseRecyclerOptions<String> options = new FirebaseRecyclerOptions.Builder<String>().
                setQuery(FirebaseDatabase.getInstance().getReference().child("followers").child(userId)
                                //.equalTo("8319085865p1")
                        ,String.class).build();
        adapter = new followershomeadapter(options,userId);
        adapter.startListening();
        recyclerView.setAdapter(adapter);
        return  view ;
    }
}