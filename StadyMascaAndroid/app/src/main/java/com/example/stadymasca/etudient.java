package com.example.stadymasca;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class etudient extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {
    private Fragment fragment = null;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    SessionManager sessionManager;
    MenuItem menuItem;
    private static int ID=0;
    TextView badgeConter;
    int pendingNotification=1;
    private final String CHANNEL_ID = "my notification";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etudient);
        drawerLayout= findViewById(R.id.drwbl);
        navigationView = findViewById(R.id.nav_vview);
        toolbar = findViewById(R.id.toolbrmenu);
        navigationView.setNavigationItemSelectedListener(this);
        setSupportActionBar(toolbar);

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin1();
        HashMap<String, String> user = sessionManager.getUserDetal();
        String Exnme = user.get(sessionManager.FirstName);
        String Exnm = user.get(sessionManager.LastName);
        toolbar.setTitle(Exnme+" "+Exnm);
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


       ;
        String fName1 = user.get(sessionManager.FirstName);
        String lName1 = user.get(sessionManager.LastName);

        String fullN=fName1+" "+lName1;



        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            CharSequence name = "my notification";
            String description = "Include all the simple notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,name,importance);
            notificationChannel.setDescription(description);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        String url_Pub="http://192.168.43.22/StadyMasca/pub_home.php";
        StringRequest stringR= new StringRequest(Request.Method.GET, url_Pub,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray array= new JSONArray(response);

                            for (int i=0; i<array.length();i++){
                                JSONObject publ= array.getJSONObject(i);
                                String title= publ.getString("title").trim();
                                String datte= publ.getString("datte").trim();
                                String transmitter= publ.getString("transmitter").trim();
                                String textt= publ.getString("textt").trim();
                                String id_t= publ.getString("id_t").trim();
                                int id_pub= publ.getInt("id_pub");

                                String department= publ.getString("department").trim();
                                String pic= publ.getString("pic").trim();
                                String unv = "All university";
                                String receiver= publ.getString("receiver").trim();
                                String u_img= "http://192.168.43.22/StadyMasca/Images/"+pic;

                                if (!transmitter.equals(fullN)){
                                    if (id_pub>ID) {
                                       // createNotificationChannel();
                                        notifiySher(transmitter, title);
                                    }
                                }
                            }




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(etudient.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQ= Volley.newRequestQueue(etudient.this);
        requestQ.add(stringR);



        //notification


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_navigation, menu);
        menuItem= menu.findItem(R.id.notification22);
        if (pendingNotification==0 ){
            menuItem.setActionView(null);
        }
        else menuItem.setActionView(R.layout.notification_badge);
        View view = menuItem.getActionView();
        badgeConter= view.findViewById(R.id.badgec);
        badgeConter.setText(String.valueOf(pendingNotification));
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.btn_container,new HomeFreg()).commit();

                break;
            case R.id.nav_profil:
                getSupportFragmentManager().beginTransaction().replace(R.id.btn_container,new Profil_Frag()).commit();

                break;

            case R.id.nav_logout:
                sessionManager.logout1();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
    public void notifiySher(String nom , String titre){
        NotificationCompat.Builder builder= new NotificationCompat.Builder(etudient.this,"my notification");
        builder.setContentTitle(nom);
        builder.setContentText(titre);
        builder.setSmallIcon(R.drawable.logoapp);
        builder.setAutoCancel(true);
        NotificationManagerCompat managerCompat= NotificationManagerCompat.from(etudient.this);
        managerCompat.notify(1,builder.build());


    }
    //create notification channel if you target android 8.0 or higher version
    /*private void createNotificationChannel(){
               if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                        CharSequence name = "Simple Notification";
                        String description = "Include all the simple notification";
                        int importance = NotificationManager.IMPORTANCE_DEFAULT;
            	            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,name,importance);
                        notificationChannel.setDescription(description);
                        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                        notificationManager.createNotificationChannel(notificationChannel);
               }
    }*/

}