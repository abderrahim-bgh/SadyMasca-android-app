package com.example.stadymasca;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
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


public class messageFreg extends Fragment  {

    String url_Pub="http://192.168.43.22/StadyMasca/pub_home.php";

    RecyclerView homeView;
    List<publicc> userList;
    private pubAdapter publicationAdpter;
    SessionManager sessionManager;

    String pr1,pr2,pr3, deprt,facultyy,status, idd;
    androidx.appcompat.widget.SearchView searchView ;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.message_freg, container, false);
        homeView = view.findViewById(R.id.home_view);

        homeView.setHasFixedSize(true);
        homeView.setLayoutManager(new LinearLayoutManager(getActivity()));
        searchView = view.findViewById(R.id.sherchw);

        userList= new ArrayList<>();
        sessionManager = new SessionManager(getActivity().getApplicationContext());
        sessionManager.checkLogin2();
        HashMap<String, String> user = sessionManager.getUserDetal();

        homeView.setVisibility(View.GONE);
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                homeView.setVisibility(View.GONE);
                return false;
            }
        });


        pr1 = user.get(sessionManager.p1);
        pr2 = user.get(sessionManager.p2);
        idd= user.get(sessionManager.ID);
        pr3 = user.get(sessionManager.p3);
        deprt = user.get(sessionManager.Department);
        facultyy = user.get(sessionManager.Fac);
        status= user.get(sessionManager.STATUS);


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





                                if (pr1.equals(receiver) || pr2.equals(receiver) || pr3.equals(receiver)
                                        || deprt.equals(receiver) || facultyy.equals(receiver)
                                        || unv.equals(receiver) ||((status.equals("prof")
                                        ||status.equals("admin")) && department.equals(deprt))){

                                    publicc pu= new publicc();
                                    pu.setImageurl(u_img);
                                    pu.setId_pub(id_pub);
                                    pu.setTitle(title);
                                    pu.setReceiver(receiver);
                                    pu.setDatte(datte);
                                    pu.setTransmitter(transmitter);
                                    pu.setTextt(textt);
                                    pu.setId_t(id_t);


                                    userList.add(pu);

                                }




                            }


                            publicationAdpter = new pubAdapter(getActivity(),userList);
                            homeView.setAdapter(publicationAdpter);

                            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                                @Override
                                public boolean onQueryTextSubmit(String query) {
                                    homeView.setVisibility(View.VISIBLE);
                                    return false;
                                }

                                @Override
                                public boolean onQueryTextChange(String newText) {
                                    homeView.setVisibility(View.VISIBLE);
                                    filter(newText);
                                    return false;
                                }


                            });


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
    private void filter(String text) {
        ArrayList<publicc> filteredList = new ArrayList<>();

        for (publicc item : userList) {
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        publicationAdpter.filterList(filteredList);
    }



}