package com.example.instacalcid1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.FirebaseDatabase;

public class notifications_activity extends AppCompatActivity {

    RecyclerView recyclerView ;
    posthomeadapter posthomeadapter ;
    notificationhomeadapter notificationhomeadapter ;
    int present_selected_item;
    BottomNavigationView bottom_nav_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifications_activity);

        recyclerView = this.findViewById(R.id.example_recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        bottom_nav_view = this.findViewById(R.id.bottom_nav_view);
        bottom_nav_view.setSelectedItemId(R.id.bottom_nav_menu_notification);
        bottom_nav_view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment temp_fragment ;

                switch (item.getItemId()) {
                    case R.id.bottom_nav_menu_home:
                        Intent intent_home = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent_home);
                        break;
                    case R.id.bottom_nav_menu_search:
                        Intent intent_search = new Intent(getApplicationContext(), search_activity.class);
                        startActivity(intent_search);
                        break;
                    case R.id.bottom_nav_menu_reels:

                        // TODO MAKE IT UNRESPONSIVE
                        Intent intent_addpost = new Intent( getApplicationContext(), addpost_activity.class);
                        startActivity(intent_addpost);
                        present_selected_item = item.getItemId();
                        break;
                    case R.id.bottom_nav_menu_notification:
                        //temp_fragment = new notification_fragment();
                        // getSupportFragmentManager().beginTransaction().replace(R.id.post_container, temp_fragment).commit();
                        //Intent intent_notification = new Intent( getApplicationContext(),notification_activity.class);
                        //startActivity(intent_notification);
                        present_selected_item = item.getItemId();
                        break;
                    case R.id.bottom_nav_menu_profile:
                        Intent intent_profile = new Intent(getApplicationContext(), profile_activity.class);
                        startActivity(intent_profile);
                        break;
                }


                return true;
            }

        });


        FirebaseRecyclerOptions<createnew_notification> options =
                new FirebaseRecyclerOptions.Builder<createnew_notification>().
                        setQuery(FirebaseDatabase.getInstance().getReference("notifications").child(loggined_user_details.getPhone()), createnew_notification.class).
                        build();

        //posthomeadapter = new posthomeadapter(options);
        //recyclerView.setAdapter(posthomeadapter);
        notificationhomeadapter = new notificationhomeadapter(options);
        recyclerView.setAdapter(notificationhomeadapter);
    }

//        FirebaseRecyclerOptions<createnewpost> options =
//                new FirebaseRecyclerOptions.Builder<createnewpost>().setQuery(FirebaseDatabase.getInstance().getReference().child("posts"),createnewpost.class).build();





//    private void viewnotification(){
//
//        Toast.makeText(getApplicationContext(),"search started",Toast.LENGTH_SHORT).show();
//
//        Query search_query = FirebaseDatabase.getInstance().getReference().child("notifications").orderByChild("user").startAt("8").endAt("8"+"\uf8ff");
//        FirebaseRecyclerOptions<creatennotification> options =
//                new FirebaseRecyclerOptions.Builder<creatennotification>().
//                        setQuery(search_query,creatennotification.class).
//                        build();
//        adapter = new notificationhomeadapter( options);
//        adapter.startListening();
//        notification_recyclerView.setAdapter(adapter);}

    @Override
    protected void onStart() {
        super.onStart();
        notificationhomeadapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        notificationhomeadapter.stopListening();
    }
}