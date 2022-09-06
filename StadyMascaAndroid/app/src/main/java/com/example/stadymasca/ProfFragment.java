package com.example.stadymasca;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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


public class ProfFragment extends Fragment implements AdapterView.OnItemSelectedListener  {

    private RecyclerView recyclerView;
    private final String url_edt="http://192.168.43.22/StadyMasca/infoAdminForProf.php";
    String url = "http://192.168.43.22/StadyMasca/Data.php";
    private ProfAdapter profAdapter;
    private List<UsersADM> usersList;
    AppCompatSpinner spinner;
    ArrayList<String> facltList = new ArrayList<>();
    ArrayAdapter<String> facultAdapter;


    RequestQueue requestQueue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_prof, container, false);
        recyclerView = view.findViewById(R.id.infoEdt);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        usersList= new ArrayList<>();


        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        spinner= view.findViewById(R.id.facultAdmn);
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




        return view;
    }

    private void LoadAllUsers() {

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
                        String facul= object.getString("faculty").trim();
                        String responsibility= object.getString("responsibility").trim();

                        String fc= String.valueOf(spinner.getSelectedItem());
                            if (block.equals("yes")){
                                if (fc.equals(facul)) {
                                    UsersADM users = new UsersADM();
                                    users.setFirstName(firstName);
                                    users.setLastName(lastName);
                                    users.setEmail(email);
                                    users.setBlock("paused");
                                    users.setResponsibility(responsibility);
                                    usersList.add(users);
                                }

                            }
                            else{
                                if ( fc.equals(facul)) {
                                    UsersADM users = new UsersADM();
                                    users.setFirstName(firstName);
                                    users.setLastName(lastName);
                                    users.setEmail(email);
                                    users.setResponsibility(responsibility);
                                    users.setBlock("active");
                                    usersList.add(users);

                                }}

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                profAdapter= new ProfAdapter(getActivity(),usersList);
                recyclerView.setAdapter(profAdapter);

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


            usersList.clear();
            LoadAllUsers();


        }



        }





    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}