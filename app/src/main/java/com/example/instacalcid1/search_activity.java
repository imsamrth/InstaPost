package com.example.instacalcid1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class search_activity extends AppCompatActivity {

    private RecyclerView recyclerView ;
    private RecyclerView.LayoutManager layoutManager ;

    loggined_user_details myapplication = (loggined_user_details) this.getApplication();
    ImageButton query_person,query_post,query_tag,sort_button;
    EditText search_query_text ;
    WebView webView ;

    int present_selected_item;
    BottomNavigationView bottom_nav_view;
    com.google.android.material.circularreveal.CircularRevealFrameLayout frameLayoutView ;
    posthomeadapter posthomeadapter ;
    userhomeadapter userhomeadapter ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //   activity oparameter setup

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        //assigning view variables

        query_person =this.findViewById(R.id.querytype_button_person );
        query_post =this.findViewById(R.id.querytype_button_post );
        query_tag =this.findViewById(R.id.querytype_button_tag);
        sort_button = this.findViewById(R.id.sort_result_button);
        frameLayoutView  = this.findViewById(R.id.single_post_container);
        search_query_text = this.findViewById(R.id.searchactivity_query);
        webView = this.findViewById(R.id.search_activity_webview);


        // assigning recyclerview variable

        recyclerView = findViewById(R.id.searchactivity_recyclerview);
        recyclerView.setVisibility(View.INVISIBLE);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        WebSettings webSettings = webView.getSettings() ;
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new Callback());
        webView.loadUrl("https://www.google.com/search?q=top+news&safe=active&hl=en&sxsrf=ALeKk020TLhfWDbgeXabSIXr5SceGuTQkw:1622975317637&source=lnms&tbm=nws&sa=X&ved=2ahUKEwi7rKWa5oLxAhWByzgGHVcOCI4Q_AUoAXoECAEQAw&biw=1280&bih=648&dpr=1.5");

//        FirebaseRecyclerOptions<createnewpost> options =
//                new FirebaseRecyclerOptions.Builder<createnewpost>().setQuery(FirebaseDatabase.getInstance().
//                        getReference().child("posts")
//                       ,createnewpost.class).build();

        //posthomeadapter = new posthomeadapter(options);
       // posthomeadapter.startListening();
        //recyclerView.setAdapter(posthomeadapter);




        //getting dataliist of posts to be displayed

        //post_query_result = myapplication.getPost_list();

        //bottom nav view setup

        bottom_nav_view = this.findViewById(R.id.bottom_nav_view);
        bottom_nav_view.setSelectedItemId(R.id.bottom_nav_menu_search);
        bottom_nav_view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment temp_fragment ;

                switch (item.getItemId()) {
                    case R.id.bottom_nav_menu_home:
                        Intent intent_home = new Intent(search_activity.this, MainActivity.class);
                        startActivity(intent_home);
                        break;
                    case R.id.bottom_nav_menu_search:
                       // Intent intent_search = new Intent(search_activity.this, search_activity.class);
                       // startActivity(intent_search);
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
                        Intent intent_notification = new Intent( getApplicationContext(), notifications_activity.class);
                        startActivity(intent_notification);
                        present_selected_item = item.getItemId();
                        break;
                    case R.id.bottom_nav_menu_profile:
                        Intent intent_profile = new Intent(search_activity.this, profile_activity.class);
                        startActivity(intent_profile);
                        break;
                }


                return true;
            }
        });



        // setting all the required on click function to call corresponding activity

        query_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                adapter = new post_query_adapter(post_query_result,search_activity.this);
//                recyclerView.setAdapter(adapter);

                firebasepostsearchquery(search_query_text.getText().toString());
            }
        });

        query_person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseusersearchquery(search_query_text.getText().toString().toUpperCase());
            }
        });

        sort_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(search_activity.this,"sort worked",Toast.LENGTH_SHORT).show();

            }
        });

    }





    private void firebasepostsearchquery(String query){

        recyclerView.setVisibility(View.VISIBLE);

        Toast.makeText(getApplicationContext(),"search started",Toast.LENGTH_SHORT).show();

        Query search_query = FirebaseDatabase.getInstance().getReference().child("posts").orderByChild("caption").startAt(query).endAt(query+"\uf8ff");

        FirebaseRecyclerOptions<createnew_post> options =
                new FirebaseRecyclerOptions.Builder<createnew_post>().setQuery(search_query, createnew_post.class).build();

        posthomeadapter = new posthomeadapter(options);
        posthomeadapter.startListening();
        recyclerView.setAdapter(posthomeadapter);
    }

    private void firebaseusersearchquery(String query){

        recyclerView.setVisibility(View.VISIBLE);

        Toast.makeText(getApplicationContext(),"search started",Toast.LENGTH_SHORT).show();

        Query search_query = FirebaseDatabase.getInstance().getReference().child("users").orderByChild("name_caps").startAt(query.toUpperCase()).endAt(query.toUpperCase()+"\uf8ff");

        FirebaseRecyclerOptions<createnew_user> options =
                new FirebaseRecyclerOptions.Builder<createnew_user>().setQuery(search_query, createnew_user.class).build();

        userhomeadapter = new userhomeadapter(options);
        userhomeadapter.startListening();
        recyclerView.setAdapter(userhomeadapter);
    }

    private  class  Callback extends WebViewClient {
        @Override
        public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
            return false;
        }
    }

}