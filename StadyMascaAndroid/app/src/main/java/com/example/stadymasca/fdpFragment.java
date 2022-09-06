package com.example.stadymasca;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class fdpFragment extends Fragment implements AdapterView.OnItemSelectedListener , View.OnClickListener {
    EditText addF,addD,addP;
    AppCompatSpinner spinner,sp2,sp3;
    ArrayList<String> facltList = new ArrayList<>();
    ArrayList<String> departList = new ArrayList<>();
    ArrayList<String> yeartList = new ArrayList<>();
    ArrayAdapter<String> facultAdapter;
    ArrayAdapter<String> depatAdapter;
    ArrayAdapter<String> yearAdapter;
    RequestQueue requestQueue;
    AppCompatButton btnaddFa, btnaddD,btnaddP,btnaddL;
    ImageView delF,delD,delP;
     String del_faclt="http://192.168.43.22/StadyMasca/deletFaculty.php";
     String del_deprt ="http://192.168.43.22/StadyMasca/deletDeprt.php";
     String del_promo = "http://192.168.43.22/StadyMasca/deletPromo.php";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_fdp, container, false);
        addF = view.findViewById(R.id.addF);
        addD = view.findViewById(R.id.addD);
        addP = view.findViewById(R.id.addP);
        btnaddD = view.findViewById(R.id.btnaddD);
        btnaddFa = view.findViewById(R.id.btnaddF);
        btnaddP = view.findViewById(R.id.btnaddP);
        btnaddL = view.findViewById(R.id.btnaddL);
        delF = view.findViewById(R.id.deletFacult);
        delD = view.findViewById(R.id.deletDeprt);
        delP = view.findViewById(R.id.deletPromo);

        delD.setOnClickListener(this);
        delP.setOnClickListener(this);
        btnaddFa.setOnClickListener(this);
        btnaddD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String addDpart;
                String addFaclty;
                addFaclty = String.valueOf(spinner.getSelectedItem());
                addDpart = String.valueOf(addD.getText());
                if (!addDpart.equals("")){
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[2];
                            field[0] = "department";
                            field[1] = "faculty";
                            String[] data = new String[2];
                            data[0] = addDpart;
                            data[1] = addFaclty;
                            PutData putData = new PutData("http://192.168.43.22/StadyMasca/addDpart1.php", "POST", field, data);
                            if (putData.startPut()) {

                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                        departList.add(addDpart);
                                    addD.setText("");

                                    Toast toast= Toast.makeText(getActivity(), "Sucess ", Toast.LENGTH_LONG);
                                    toast.getView().setBackgroundColor(Color.parseColor("#9ede73"));
                                    toast.show();


                                    //End ProgressBar (Set visibility to GONE)
                                }
                            }
                            //End Write and Read data with URL
                        }
                    });
                } else Toast.makeText(getActivity(), "All fields are required", Toast.LENGTH_SHORT).show();

            }
        });
        btnaddP.setOnClickListener(this);
        btnaddL.setOnClickListener(this);
        delF.setOnClickListener(this);

        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        spinner= view.findViewById(R.id.faculty1);
        sp2= view.findViewById(R.id.department1);
                sp3= view.findViewById(R.id.promo1);
        String url = "http://192.168.43.22/StadyMasca/Data.php";
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
        sp2.setOnItemSelectedListener(this);
        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId()== R.id.faculty1){
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

                }
            });
            requestQueue.add(jsonObjectRequest);


        }
        if(adapterView.getId()== R.id.department1){
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


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnaddF:
                String addFaclt;
                addFaclt = String.valueOf(addF.getText());
                if (!addFaclt.equals("")){
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[1];
                            field[0] = "faculty";
                            String[] data = new String[1];
                            data[0] = addFaclt;

                            PutData putData = new PutData("http://192.168.43.22/StadyMasca/addFacult.php", "POST", field, data);
                            if (putData.startPut()) {

                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if (result.equals("Add Success")) {
                                        facltList.add(addFaclt);

                                        addF.setText("");
                                        Toast toast= Toast.makeText(getActivity(), "Sucess ", Toast.LENGTH_LONG);
                                        toast.getView().setBackgroundColor(Color.parseColor("#9ede73"));
                                        toast.show();

                                    } else {
                                        Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
                                    }
                                    //End ProgressBar (Set visibility to GONE)
                                }
                            }
                            //End Write and Read data with URL
                        }
                    });
                } else Toast.makeText(getActivity(), "All fields are required", Toast.LENGTH_SHORT).show();

                break;

            case R.id.btnaddP:
                String addM1,addM2;
                String addDpr;
                addDpr  = String.valueOf(sp2.getSelectedItem());
                addM1 = String.valueOf("M1 "+ addP.getText());
                addM2 = String.valueOf("M2 "+ addP.getText());
                if (!addM1.equals("")){
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[2];
                            field[0] = "department";
                            field[1] = "promoNom";
                            String[] data = new String[2];
                            data[0] = addDpr;
                            data[1] = addM1;
                            PutData putData = new PutData("http://192.168.43.22/StadyMasca/addPromo.php", "POST", field, data);
                            if (putData.startPut()) {

                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                        yeartList.add(addM1);
                                    addP.setText("");
                                    Toast toast= Toast.makeText(getActivity(), "Sucess ", Toast.LENGTH_LONG);
                                    toast.getView().setBackgroundColor(Color.parseColor("#9ede73"));
                                    toast.show();

                                    //End ProgressBar (Set visibility to GONE)
                                }
                            }
                            //End Write and Read data with URL
                        }
                    });
                } else Toast.makeText(getActivity(), "All fields are required", Toast.LENGTH_SHORT).show();
                if (!addM2.equals("")){
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[2];
                            field[0] = "department";
                            field[1] = "promoNom";
                            String[] data = new String[2];
                            data[0] = addDpr;
                            data[1] = addM2;
                            PutData putData = new PutData("http://192.168.43.22/StadyMasca/addPromo.php", "POST", field, data);
                            if (putData.startPut()) {

                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    yeartList.add(addM1);
                                    addP.setText("");
                                    Toast toast= Toast.makeText(getActivity(), "Sucess ", Toast.LENGTH_LONG);
                                    toast.getView().setBackgroundColor(Color.parseColor("#9ede73"));
                                    toast.show();
                                    //End ProgressBar (Set visibility to GONE)
                                }
                            }
                            //End Write and Read data with URL
                        }
                    });
                } else Toast.makeText(getActivity(), "All fields are required", Toast.LENGTH_SHORT).show();

                break;
            case R.id.btnaddL:
                String  addL2, addL3;
                String addDp;

                addDp  = String.valueOf(sp2.getSelectedItem());
                addL2 = String.valueOf(sp2.getSelectedItem()+" L2");
                addL3 = String.valueOf(sp2.getSelectedItem()+" L3");
                if (!addL2.equals("") ){
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[2];
                            field[0] = "department";
                            field[1] = "promoNom";
                            String[] data = new String[2];
                            data[0] = addDp;
                            data[1] = addL2;

                            PutData putData = new PutData("http://192.168.43.22/StadyMasca/addPromo.php", "POST", field, data);
                            if (putData.startPut()) {

                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    yeartList.add(addL2);
                                    addP.setText("");
                                    Toast toast= Toast.makeText(getActivity(), "Sucess ", Toast.LENGTH_LONG);
                                    toast.getView().setBackgroundColor(Color.parseColor("#9ede73"));
                                    toast.show();
                                    //End ProgressBar (Set visibility to GONE)
                                }
                            }
                            //End Write and Read data with URL
                        }
                    });
                } else Toast.makeText(getActivity(), "All fields are required", Toast.LENGTH_SHORT).show();
                if (!addL3.equals("") ){
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[2];
                            field[0] = "department";
                            field[1] = "promoNom";
                            String[] data = new String[2];
                            data[0] = addDp;
                            data[1] = addL3;

                            PutData putData = new PutData("http://192.168.43.22/StadyMasca/addPromo.php", "POST", field, data);
                            if (putData.startPut()) {

                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    yeartList.add(addL3);
                                    addP.setText("");
                                    Toast toast= Toast.makeText(getActivity(), "Sucess ", Toast.LENGTH_LONG);
                                    toast.getView().setBackgroundColor(Color.parseColor("#9ede73"));
                                    toast.show();
                                    //End ProgressBar (Set visibility to GONE)
                                }
                            }
                            //End Write and Read data with URL
                        }
                    });
                } else Toast.makeText(getActivity(), "All fields are required", Toast.LENGTH_SHORT).show();


                break;
            case R.id.deletFacult:
                AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
                builder.setTitle("Delete "+spinner.getSelectedItem());
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        String faculty1  = String.valueOf(spinner.getSelectedItem());
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, del_faclt,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonObject= new JSONObject(response);
                                            String check = jsonObject.getString("state");
                                            if(check.equals("delete")){
                                                Toast toast= Toast.makeText(getActivity(), "Delete ", Toast.LENGTH_LONG);
                                                toast.getView().setBackgroundColor(Color.parseColor("#f55c47"));
                                                toast.show();
                                                delet(spinner.getSelectedItemPosition());
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap<String,String> desetParams = new HashMap<>();
                                desetParams.put("faculty",faculty1);

                                return desetParams;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                        requestQueue.add(stringRequest);

                    }
                });
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog= builder.create();
                dialog.show();
                break;

            case R.id.deletDeprt:
                AlertDialog.Builder buil= new AlertDialog.Builder(getActivity());
                buil.setTitle("Delete "+sp2.getSelectedItem());
                buil.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        String dep  = String.valueOf(sp2.getSelectedItem());
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, del_deprt,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonObjec= new JSONObject(response);
                                            String check1 = jsonObjec.getString("state");
                                            if(check1.equals("delete")){

                                                Toast toast= Toast.makeText(getActivity(), "Delete ", Toast.LENGTH_LONG);
                                                toast.getView().setBackgroundColor(Color.parseColor("#f55c47"));
                                                toast.show();
                                                deletd(sp2.getSelectedItemPosition());
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap<String,String> desetParams = new HashMap<>();
                                desetParams.put("department",dep);

                                return desetParams;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                        requestQueue.add(stringRequest);

                    }
                });
                buil.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog1= buil.create();
                dialog1.show();
                break;
            case R.id.deletPromo:
                AlertDialog.Builder build= new AlertDialog.Builder(getActivity());
                build.setTitle("Delete "+sp3.getSelectedItem());
                build.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        String prm  = String.valueOf(sp3.getSelectedItem());
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, del_promo,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonObject= new JSONObject(response);
                                            String check = jsonObject.getString("state");
                                            if(check.equals("delete")){

                                                Toast toast= Toast.makeText(getActivity(), "Delete ", Toast.LENGTH_LONG);
                                                toast.getView().setBackgroundColor(Color.parseColor("#f55c47"));
                                                toast.show();
                                                deletP(sp3.getSelectedItemPosition());
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap<String,String> desetParams = new HashMap<>();
                                desetParams.put("promoNom",prm);

                                return desetParams;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                        requestQueue.add(stringRequest);

                    }
                });
                build.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog2= build.create();
                dialog2.show();
                break;




        }
    }
    public void delet(int item){
        facltList.remove(item);
    }
    public void deletd(int item){
        departList.remove(item);
    }
    public void deletP(int item){
        yeartList.remove(item);
    }
}