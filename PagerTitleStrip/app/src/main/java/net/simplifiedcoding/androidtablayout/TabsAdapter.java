package net.simplifiedcoding.androidtablayout;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsAdapter extends FragmentPagerAdapter {

    public TabsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {
        if (index < 6) {
            switch (index) {
                case 0:
                    return CustomFragment.newInstance("Texto de la pestaña nº 1.");
                case 1:
                    return CustomFragment.newInstance("Texto de la pestaña nº 2.");
                case 2:
                    return CustomFragment.newInstance("Texto de la pestaña nº 3.");
                case 3:
                    return CustomFragment.newInstance("Texto de la pestaña nº 4.");
                case 4:
                    return CustomFragment.newInstance("Texto de la pestaña nº 5.");
            }
        }
        return null;
    }

    @Override
    public int getCount() {
        return 5;
    }

}