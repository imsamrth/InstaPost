package com.example.instacalcid1;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;


    public class mainactivity_fragmentadapter extends FragmentStateAdapter {
        public mainactivity_fragmentadapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager,lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {

            switch (position){
                case 1 :
                    return new status_fragment(loggined_user_details.getPhone(),false);

            }
            return new mainactivity_postfragment();
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }
