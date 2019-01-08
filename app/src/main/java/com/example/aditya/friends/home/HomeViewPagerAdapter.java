package com.example.aditya.friends.home;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class HomeViewPagerAdapter extends FragmentStatePagerAdapter{

    public HomeViewPagerAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 : {
                ProfileFragment profileFragment = new ProfileFragment();
                return profileFragment;
            }
            case 1 : {
                MatchFragment matchFragment = new MatchFragment();
                return matchFragment;
            }
        }

        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0 : {
                return "Profile";
            }
            case 1 : {
                return "Matching";
            }
        }
        return null;
    }
}
