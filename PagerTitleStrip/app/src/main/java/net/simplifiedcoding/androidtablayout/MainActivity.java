package net.simplifiedcoding.androidtablayout;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
/*
    https://software.intel.com/es-es/android/articles/building-dynamic-ui-for-android-devices
    http://www.proyectosimio.com/es/programacion-android-como-usar-fragments-con-viewpager-y-efecto-swipe/

*/



    public class MainActivity extends ActionBarActivity implements
            ActionBar.TabListener {
        private ViewPager vPager;
        private TabsAdapter tAdapter;
        private ActionBar aBar;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            vPager = (ViewPager) findViewById(R.id.pager);
            tAdapter = new TabsAdapter(getSupportFragmentManager());
            vPager.setAdapter(tAdapter);
            aBar = getSupportActionBar();
            aBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

            for (String title : getResources().getStringArray(R.array.tabs)) {
                aBar.addTab(aBar.newTab().setText(title).setTabListener(this));
            }

            vPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                @Override
                public void onPageSelected(int position) {
                    aBar.setSelectedNavigationItem(position);
                }

                @Override
                public void onPageScrolled(int arg0, float arg1, int arg2) {
                }

                @Override
                public void onPageScrollStateChanged(int arg0) {
                }
            });
        }


        @Override
        public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {
            vPager.setCurrentItem(tab.getPosition());
        }

        @Override
        public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

        }

        @Override
        public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

        }
    }
