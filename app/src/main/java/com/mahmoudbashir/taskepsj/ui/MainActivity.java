package com.mahmoudbashir.taskepsj.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.mahmoudbashir.taskepsj.R;
import com.mahmoudbashir.taskepsj.fragments.ApiData_Fragment;
import com.mahmoudbashir.taskepsj.fragments.MainScreen_Fragment;
import com.mahmoudbashir.taskepsj.fragments.SavedData_Fragment;
import com.mahmoudbashir.taskepsj.fragments.ViewPagerImages_Fragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        doInitialize();

        nvDrawer.setNavigationItemSelectedListener(this);
        doTransactionFrag(new MainScreen_Fragment());
    }

    private void doInitialize(){
        mDrawer = findViewById(R.id.drawer_layout);
        nvDrawer = findViewById(R.id.nav_view);

        drawerToggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        Fragment fragment =null;
        switch (id){
            case  R.id.home_fragment:
                fragment = new MainScreen_Fragment();
                break;
            case  R.id.savedData_Fragment:
                fragment = new SavedData_Fragment();
                break;
            case  R.id.apiData_Fragment:
                fragment = new ApiData_Fragment();
                break;
            case  R.id.viewPagerImages_Fragment:
                fragment = new ViewPagerImages_Fragment();
                break;
            default:
                fragment = new MainScreen_Fragment();
        }

        doTransactionFrag(fragment);
        item.setChecked(true);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void doTransactionFrag(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame,fragment).commit();
    }
}