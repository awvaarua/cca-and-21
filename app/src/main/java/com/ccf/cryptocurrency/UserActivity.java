package com.ccf.cryptocurrency;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.ccf.cryptocurrency.fragments.TestFragment;
import com.ccf.cryptocurrency.fragments.WalletFragment;

public class UserActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener  = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    getFragmentManager().beginTransaction().replace(R.id.rootLayout, new WalletFragment()).commit();
                    return true;
                case R.id.navigation_dashboard:
                    getFragmentManager().beginTransaction().replace(R.id.rootLayout, new TestFragment()).commit();
                    return true;
                case R.id.navigation_notifications:
                    getFragmentManager().beginTransaction().replace(R.id.rootLayout, new TestFragment()).commit();
                    return true;
            }
            return false;
        }
    };

    protected void changeFragment(Fragment newFragment) {
        getFragmentManager().beginTransaction().replace(R.id.rootLayout, newFragment).commit();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        if (savedInstanceState == null){
            getFragmentManager().beginTransaction().add(R.id.rootLayout, new WalletFragment()).commit();
        }

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
