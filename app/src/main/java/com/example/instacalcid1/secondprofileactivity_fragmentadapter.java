package com.example.instacalcid1;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;



    public class secondprofileactivity_fragmentadapter extends FragmentStateAdapter {

        String userId ;
        public secondprofileactivity_fragmentadapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, String userId) {
            super(fragmentManager,lifecycle);
            this.userId = userId ;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {

            switch (position){
                case 1 :
                    return new status_fragment(userId,false) ;
                case 2:
                    return new followers_fragment(userId) ;

            }
            return new profileactivity_postfragment(userId);
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }

