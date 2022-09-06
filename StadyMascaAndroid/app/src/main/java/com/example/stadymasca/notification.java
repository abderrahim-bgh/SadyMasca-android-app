package com.example.stadymasca;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class notification extends Fragment {

    String url_Pub="http://192.168.43.22/StadyMasca/pub_home.php";

    RecyclerView homeView;
    List<publicc> userList;
    private notifAdapter publicationAdpter;
    SessionManager sessionManager;
    String fullN ;
    String pr1,pr2,pr3, deprt,facultyy,status, idd;
    private static int ID=0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_notification, container, false);
        homeView = view.findViewById(R.id.home_view);
        homeView.setHasFixedSize(true);
        homeView.setLayoutManager(new LinearLayoutManager(getActivity()));

        userList= new ArrayList<>();
        sessionManager = new SessionManager(getActivity().getApplicationContext());
        sessionManager.checkLogin2();
        HashMap<String, String> user = sessionManager.getUserDetal();

        pr1 = user.get(sessionManager.p1);
        pr2 = user.get(sessionManager.p2);
        idd= user.get(sessionManager.ID);
        pr3 = user.get(sessionManager.p3);
        deprt = user.get(sessionManager.Department);
        facultyy = user.get(sessionManager.Fac);
        String fName1 = user.get(sessionManager.FirstName);
        String lName1 = user.get(sessionManager.LastName);

        fullN=fName1+" "+lName1;
        status= user.get(sessionManager.STATUS);





        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            CharSequence name = "my notification";
            String description = "Include all the simple notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel = new NotificationChannel("my notification",name,importance);
            notificationChannel.setDescription(description);
            NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(getActivity().NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        String url_noti="http://192.168.43.22/StadyMasca/notification.php?id_pub="+ID;
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
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQ= Volley.newRequestQueue(getContext());
        requestQ.add(stringR);
        loadpublication();
        return view;
    }



    private void loadpublication() {


        StringRequest stringRequest= new StringRequest(Request.Method.GET, url_Pub,
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
                                String id_pub= publ.getString("id_pub").trim();

                                String department= publ.getString("department").trim();
                                String pic= publ.getString("pic").trim();
                                String unv = "All university";
                                String receiver= publ.getString("receiver").trim();
                                String u_img= "http://192.168.43.22/StadyMasca/Images/"+pic;

                                publicc pubb= new publicc();



                                if ((pr1.equals(receiver) || pr2.equals(receiver) || pr3.equals(receiver)
                                        || deprt.equals(receiver) || facultyy.equals(receiver)
                                        || unv.equals(receiver) ||((status.equals("prof")
                                        ||status.equals("admin")) && department.equals(deprt)))
                                        && !fullN.equals(transmitter)){


                                    publicc pu= new publicc();
                                    pu.setId_pub(id_pub);
                                    pu.setTitle(title);
                                    pu.setReceiver(receiver);
                                    pu.setDatte(datte);
                                    pu.setTransmitter(transmitter);
                                    pu.setId_t(id_t);


                                    userList.add(pu);

                                }



                            }


                            publicationAdpter = new notifAdapter(getActivity(),userList);
                            homeView.setAdapter(publicationAdpter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);


    }
    public void notifiySher(String nom , String titre){
        NotificationCompat.Builder builder= new NotificationCompat.Builder(getActivity(),"my notification");
        builder.setContentTitle(nom);
        builder.setContentText(titre);
        builder.setSmallIcon(R.drawable.logoapp);
        builder.setAutoCancel(true);
        NotificationManagerCompat managerCompat= NotificationManagerCompat.from(getActivity());
        managerCompat.notify(1,builder.build());


    }



}