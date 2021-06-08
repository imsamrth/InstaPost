package com.example.instacalcid1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

        BottomNavigationView bottom_nav_view;
        Toolbar home_activity_toolbar ;
        int present_selected_item;
        //RecyclerView recyclerView ;
        posthomeadapter adapter;

          ViewPager2 viewPager2 ;
          mainactivity_fragmentadapter fragmentStateAdapter ;

    @Override
        protected void onCreate(Bundle savedInstanceState) {

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

            super.onCreate(savedInstanceState);
            setContentView(R.layout.main_activity);

            // toolbar setup code

            home_activity_toolbar = findViewById(R.id.home_activity_toolbar);
            setSupportActionBar(home_activity_toolbar);
            home_activity_toolbar.setTitle("InstaPost");

        viewPager2 = this.findViewById(R.id.main_activity_viewpager);
        FragmentManager fragmentManager = getSupportFragmentManager() ;
        fragmentStateAdapter = new mainactivity_fragmentadapter(fragmentManager,getLifecycle());
        viewPager2.setAdapter(fragmentStateAdapter);

            // bottom navigation complete code

            bottom_nav_view = this.findViewById(R.id.bottom_nav_view);
            bottom_nav_view.setSelectedItemId(R.id.bottom_nav_menu_home);
            bottom_nav_view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {



                        switch (item.getItemId()) {
                            case R.id.bottom_nav_menu_home:
                                if(present_selected_item != item.getItemId())
                               // home_activty_parent_layout.addView(home_activity_toolbar,0);
                             //   temp_fragment = new home_fragment();
                              //  present_selected_item = item.getItemId();
                             //   getSupportFragmentManager().beginTransaction().replace(R.id.post_container, temp_fragment).commit();
                                present_selected_item = item.getItemId();
                                break;
                            case R.id.bottom_nav_menu_search:

                                Intent intent_search = new Intent(MainActivity.this, search_activity.class);
                                startActivity(intent_search);
                                break;
                            case R.id.bottom_nav_menu_reels:

                                // TODO MAKE IT UNRESPONSIVE
//                                temp_fragment = new reels_fragment();
//                                home_activity_toolbar.setTitle("Reels");
//                                getSupportFragmentManager().beginTransaction().replace(R.id.post_container, temp_fragment).commit();
                                Intent intent_addpost = new Intent( getApplicationContext(), addpost_activity.class);
                                startActivity(intent_addpost);
                                present_selected_item = item.getItemId();
                                break;
                            case R.id.bottom_nav_menu_notification:
                                Intent intent_notification = new Intent( getApplicationContext(), notifications_activity.class);
                                startActivity(intent_notification);
                                //getSupportFragmentManager().beginTransaction().replace(R.id.post_container, temp_fragment).commit();
                                //home_activty_parent_layout.removeView(home_activity_toolbar);

                                present_selected_item = item.getItemId();
                                break;
                            case R.id.bottom_nav_menu_profile:
                                Intent intent_profile = new Intent(MainActivity.this, profile_activity.class);
                                startActivity(intent_profile);
                                break;
                        }


                        return true;
                    }


                });

            // recyclerview setup code

        }


        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.home_activity_toolbar_menu,menu);
            return super.onCreateOptionsMenu(menu);
        }

        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.toolbar_menu_chat :
                    Toast.makeText(this,"you clicked messages",Toast.LENGTH_SHORT).show();
                    break;
            }
            return super.onOptionsItemSelected(item);
        }

        public  void open(){
            Toast.makeText(this,"sow comment box",Toast.LENGTH_SHORT).show();
        }

//    @Override
//    protected void onStart() {
//        super.onStart();
//       adapter.startListening();
//    }
//    @Override
//    protected void onStop() {
//        super.onStop();
//      adapter.stopListening();
//    }
}


