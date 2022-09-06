package com.example.stadymasca;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class etudientFrg extends Fragment implements AdapterView.OnItemSelectedListener  {
   private RecyclerView recyclerView;
   private final String url_edt="http://192.168.43.22/StadyMasca/infoForAdmin.php";
    String url = "http://192.168.43.22/StadyMasca/Data.php";
   public static final String Edit_users= "http://192.168.43.22/StadyMasca/profil_Edit.php";
   private UserAdapter userAdapter;
   private List<UsersADM> usersList;
    AppCompatSpinner spinner,sp2,sp3;
    ArrayList<String> facltList = new ArrayList<>();
    ArrayList<String> yeartList = new ArrayList<>();
    ArrayAdapter<String> facultAdapter;
    ArrayList<String> departList = new ArrayList<>();
    ArrayAdapter<String> depatAdapter;
    ArrayAdapter<String> yearAdapter;
    RequestQueue requestQueue;
    ImageView admin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_etudient_frg, container, false);
        recyclerView = view.findViewById(R.id.infoEdt);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        usersList= new ArrayList<>();



        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        spinner= view.findViewById(R.id.facultAdmn);
        sp2 = view.findViewById(R.id.departmentAdm);
        sp3= view.findViewById(R.id.promoAdm);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("facult");

                    for (int i = 0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String facultty = jsonObject.optString("faculty");
                        facltList.add(facultty);
                        facultAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item,facltList);
                        facultAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(facultAdapter);


                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        });

        requestQueue.add(jsonObjectRequest);
        spinner.setOnItemSelectedListener( this);
        sp2.setOnItemSelectedListener( this);
        sp3.setOnItemSelectedListener( this);



        return view;
    }

    private void LoadAllUsers( ) {

        JsonArrayRequest request = new JsonArrayRequest(url_edt, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray array) {

                for (int i =0;i<array.length();i++){
                    try {
                        JSONObject object= array.getJSONObject(i);
                        String firstName= object.getString("firstName").trim();
                        String lastName= object.getString("lastName").trim();
                        String email= object.getString("email").trim();
                        String block= object.getString("block").trim();
                        String 	faculty= object.getString("faculty").trim();
                        String department= object.getString("department").trim();
                        String p1= (object.getString("p1").trim()).toLowerCase();
                        String promo= String.valueOf(sp3.getSelectedItem()).toLowerCase();
                        if (block.equals("yes")){
                            if (p1.equals(promo)) {
                                UsersADM users = new UsersADM();
                                users.setFirstName(firstName);
                                users.setLastName(lastName);
                                users.setEmail(email);
                                users.setBlock("paused");
                                usersList.add(users);
                            }

                        }
                        else{
                        if (p1.equals(promo)) {
                            UsersADM users = new UsersADM();
                            users.setFirstName(firstName);
                            users.setLastName(lastName);
                            users.setEmail(email);
                            usersList.add(users);
                            users.setBlock("active");
                        }}

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                userAdapter= new UserAdapter(getActivity(),usersList);
                recyclerView.setAdapter(userAdapter);

            }
        }, new Response.ErrorListener() {
            @Override

            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(request);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        if(adapterView.getId()== R.id.facultAdmn){

            departList.clear();

            String selectedfaculty = adapterView.getSelectedItem().toString();

            String url = "http://192.168.43.22/StadyMasca/department.php?faculty="+selectedfaculty;
            requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
            JsonObjectRequest  jsonObjectRequest= new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray jsonArray = response.getJSONArray("depart");

                        for (int i = 0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String department = jsonObject.optString("department");
                            departList.add(department);
                            depatAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item,departList);
                            depatAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            sp2.setAdapter(depatAdapter);


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getActivity(), "err", Toast.LENGTH_SHORT).show();
                }
            });
            requestQueue.add(jsonObjectRequest);


        }
        if(adapterView.getId()== R.id.departmentAdm){
            yeartList.clear();


            String selecteddepartment = adapterView.getSelectedItem().toString();
            String url = "http://192.168.43.22/StadyMasca/promo.php?department="+selecteddepartment;
            requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
            JsonObjectRequest  jsonObjectRequest= new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray jsonArray = response.getJSONArray("promo");

                        for (int i = 0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String prom = jsonObject.optString("promoNom");
                            yeartList.add(prom);
                            yearAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item,yeartList);
                            yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            sp3.setAdapter(yearAdapter);


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            requestQueue.add(jsonObjectRequest);


        }
        if(adapterView.getId()== R.id.promoAdm){

            usersList.clear();
            LoadAllUsers();
        }

        

      }



    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}