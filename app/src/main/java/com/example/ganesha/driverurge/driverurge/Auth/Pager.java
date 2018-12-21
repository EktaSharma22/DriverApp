package com.example.ganesha.driverurge.Auth;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class Pager extends FragmentPagerAdapter {


    public Pager(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                WelcomeMain welcomeMain = new WelcomeMain();
                return welcomeMain;
            case 1:
                DriveMain scheduleMain = new DriveMain();
                return scheduleMain;
            case 2:
                EarnMain driverMain = new EarnMain();
                return  driverMain;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 3;
    }
}