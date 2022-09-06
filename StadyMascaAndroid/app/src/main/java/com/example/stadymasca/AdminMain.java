package com.example.stadymasca;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;

public class AdminMain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private Fragment fragment = null;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        drawerLayout= findViewById(R.id.drwbl);
        navigationView = findViewById(R.id.nav_vview);
        toolbar = findViewById(R.id.toolbrmenu);


        navigationView.setNavigationItemSelectedListener(this);
        setSupportActionBar(toolbar);


        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this, drawerLayout, toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.btn_container,new HomeFreg()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }


        BottomNavigationView bottomNavigationView = findViewById(R.id.botton_navg);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home1:
                        fragment = new HomeFreg();
                        break;



                    case R.id.notification22:
                        fragment = new notification();
                        break;

                }

                if (fragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.btn_container, fragment).commit();
                }
                return true;
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.btn_container, new HomeFreg()).commit();
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin2();

        HashMap<String, String> user = sessionManager.getUserDetal();
        String Exnme = user.get(sessionManager.FirstName);
        String Exnm = user.get(sessionManager.LastName);
        toolbar.setTitle(Exnme+" "+Exnm);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.btn_container,new HomeFreg()).commit();

                break;
            case R.id.nav_profil:
                getSupportFragmentManager().beginTransaction().replace(R.id.btn_container,new profil_admin()).commit();

                break;

            case R.id.nav_pub:
                Intent intent = new Intent(AdminMain.this,pubAdmin.class);
                startActivity(intent);
                finish();
                break;
            case R.id.nav_logout:
                sessionManager.logout2();
                break;
            case R.id.nav_admin:
                getSupportFragmentManager().beginTransaction().replace(R.id.btn_container,new adminn()).commit();

                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

}