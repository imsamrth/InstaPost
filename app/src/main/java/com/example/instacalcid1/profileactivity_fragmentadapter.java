package com.example.instacalcid1;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class profileactivity_fragmentadapter extends FragmentStateAdapter {
    public profileactivity_fragmentadapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager,lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position){
            case 1 :
                return new status_fragment(loggined_user_details.getPhone(),true) ;
            case 2:
                return new followers_fragment(loggined_user_details.getPhone()) ;

        }
        return new profileactivity_postfragment(loggined_user_details.getPhone());
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
