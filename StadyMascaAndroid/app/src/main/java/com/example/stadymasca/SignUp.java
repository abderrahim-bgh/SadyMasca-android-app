package com.example.stadymasca;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Pair;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SignUp extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button callLogin;
    AppCompatButton signUP;
    ImageView image;
    TextView logoText;
    TextInputLayout lastname, password;
    TextInputEditText textInputEditTextfullname,textInputEditTextlastname,textInputEditTextpassword,textInputEditTextfullemail;
    CheckBox prof ;
    TextInputEditText numReg;
    TextInputLayout numReg1;
    AppCompatSpinner spinner,sp2,sp3;
    ArrayList<String> facltList = new ArrayList<>();
    ArrayList<String> departList = new ArrayList<>();
    ArrayList<String> yeartList = new ArrayList<>();
    ArrayAdapter<String> facultAdapter;
    ArrayAdapter<String> depatAdapter;
    ArrayAdapter<String> yearAdapter;
    RequestQueue requestQueue;
    int g;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);
        callLogin = findViewById(R.id.callLogin);
        image = findViewById(R.id.imaglog);
        logoText = findViewById(R.id.log_txt);
        lastname = findViewById(R.id.lastname);
        password = findViewById(R.id.password2);
        textInputEditTextfullname = findViewById(R.id.fullName2);
        textInputEditTextlastname= findViewById(R.id.lastName2);
        textInputEditTextpassword = findViewById(R.id.password3);
        textInputEditTextfullemail= findViewById(R.id.email);
        prof = findViewById(R.id.prof);
        numReg = findViewById(R.id.numRegst);
        numReg1 = findViewById(R.id.nymReg);
        requestQueue = Volley.newRequestQueue(this);
        spinner= findViewById(R.id.facult);
        sp2 = findViewById(R.id.deprt);
        sp3 = findViewById(R.id.year);



        signUP = findViewById(R.id.nextbtn);

        callLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, Login.class);
                Pair[] pairs = new Pair[6];
                pairs[0] = new Pair<View, String>(image, "logo_img");
                pairs[1] = new Pair<View, String>(logoText, "logo_text");
                pairs[2] = new Pair<View, String>(lastname, "username_tran");
                pairs[3] = new Pair<View, String>(password, "password_tran");
                pairs[4] = new Pair<View, String>(signUP, "button_tran");
                pairs[5] = new Pair<View, String>(callLogin, "login_signup_tran");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp.this, pairs);
                startActivity(intent, options.toBundle());
            }
        });
        signUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (  !validateEmail(textInputEditTextfullemail) || !ValidPass(textInputEditTextpassword) || !Validname(textInputEditTextfullname) || !Validname(textInputEditTextlastname) ) {
                    validateEmail(textInputEditTextfullemail);
                    ValidPass(textInputEditTextpassword);
                    Validname(textInputEditTextfullname);
                    Validname(textInputEditTextlastname);
                    if(!Validname(numReg) || !prof.isChecked())
                        Validname(numReg);
                }




                else {
                    String firstName;
                    String lastName;
                    String password;
                    String email;
                    String numRegs;
                    String department ;
                    String faculty;
                    String status;
                    String p1 ;




                    firstName = String.valueOf(textInputEditTextfullname.getText());
                    lastName = String.valueOf(textInputEditTextlastname.getText());
                    password = String.valueOf(textInputEditTextpassword.getText());
                    email = String.valueOf(textInputEditTextfullemail.getText());
                    numRegs = String.valueOf(numReg.getText());
                    department= String.valueOf(sp2.getSelectedItem());
                    faculty= String.valueOf(spinner.getSelectedItem());

                    if(prof.isChecked()) status = "prof"; else status ="etudient";
                    if(status=="etudient")
                        p1 = String.valueOf(sp3.getSelectedItem());
                    else
                        p1 = ("teacher");

                    /*if(status== "prof"){
                        Random r = new Random();
                        String r1 = String.valueOf(r.nextInt());
                        vkey = r1;
                    }
                    else{

                            Random r = new Random();
                            String r1 = String.valueOf(r.nextInt());
                            vkey = r1;

                    }*/










                    if (!firstName.equals("") && !lastName.equals("") && !password.equals("") && !email.equals("") && (!numRegs.equals("") || status!="etudient" ) ) {



                        //Start ProgressBar first (Set visibility VISIBLE)
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                //Starting Write and Read data with URL
                                //Creating array for parameters
                                String[] field = new String[9];
                                field[0] = "firstName";
                                field[1] = "lastName";
                                field[2]= "email";
                                field[3] = "password";
                                field[4] = "numRegst";
                                field[5] = "department";
                                field[6] = "faculty";
                                field[7] = "status";
                                field[8] = "p1";


                                //Creating array for data
                                String[] data = new String[9];
                                data[0] = firstName;
                                data[1] =lastName;
                                data[2]= email;
                                data[3] = password;
                                data[4]= numRegs;
                                data[5] = department;
                                data[6]= faculty;
                                data[7]= status;
                                data[8]= p1;


                                PutData putData = new PutData("http://192.168.43.22/StadyMasca/signup.php", "POST", field, data);
                                if (putData.startPut()) {

                                    if (putData.onComplete()) {
                                        String result = putData.getResult();
                                        if (result.equals("Sign Up Success")) {
                                            Toast toast= Toast.makeText(SignUp.this, "Sign Up Success", Toast.LENGTH_LONG);
                                            toast.getView().setBackgroundColor(Color.parseColor("#9ede73"));
                                            toast.show();


                                            Intent intent = new Intent(SignUp.this, registring.class);
                                            startActivity(intent);
                                            finish();

                                        } else {
                                            Toast toast= Toast.makeText(SignUp.this, result, Toast.LENGTH_LONG);
                                            toast.getView().setBackgroundColor(Color.RED);
                                            toast.show();

                                        }
                                        //End ProgressBar (Set visibility to GONE)
                                    }
                                }
                                //End Write and Read data with URL
                            }
                        });
                    } else Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_SHORT).show();
                }
            }


        });


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
                        facultAdapter = new ArrayAdapter<>(SignUp.this, android.R.layout.simple_spinner_item,facltList);
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

        spinner.setOnItemSelectedListener(this);
        sp2.setOnItemSelectedListener(this);

        sp3.setOnItemSelectedListener(this);

    }



    public void supNumCart(View view){
        if ( prof.isChecked()){
            numReg.setVisibility(View.GONE);
            numReg1.setVisibility(View.GONE);
            sp3.setVisibility(View.GONE);


        }
        else if (prof.isChecked()== false){ numReg.setVisibility(View.VISIBLE);
            numReg1.setVisibility(View.VISIBLE);
            sp3.setVisibility(View.VISIBLE);

        }
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        if(adapterView.getId()== R.id.facult){
            departList.clear();
            g= (int) spinner.getSelectedItemId();
            String selectedfaculty = adapterView.getSelectedItem().toString();

            String url = "http://192.168.43.22/StadyMasca/department.php?faculty="+selectedfaculty;

            requestQueue = Volley.newRequestQueue(this);
            JsonObjectRequest  jsonObjectRequest= new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray jsonArray = response.getJSONArray("depart");

                        for (int i = 0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String department = jsonObject.optString("department");
                            departList.add(department);
                            depatAdapter = new ArrayAdapter<>(SignUp.this, android.R.layout.simple_spinner_item,departList);
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

        if(adapterView.getId()== R.id.deprt){
            yeartList.clear();
            String selecteddepartment = adapterView.getSelectedItem().toString();
            String url = "http://192.168.43.22/StadyMasca/promo.php?department="+selecteddepartment;
            requestQueue = Volley.newRequestQueue(this);
            JsonObjectRequest  jsonObjectRequest= new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray jsonArray = response.getJSONArray("promo");

                        for (int i = 0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String prom = jsonObject.optString("promoNom");
                            yeartList.add(prom);
                            yearAdapter = new ArrayAdapter<>(SignUp.this, android.R.layout.simple_spinner_item,yeartList);
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



    private Boolean validateEmail(TextInputEditText email) {
        String emailIn = email.getText().toString();
        if(!emailIn.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailIn).matches()){
            return true;
        }
        else {
            email.setError("enter a valid email address");
            return  false;
        }

    }
    private Boolean ValidPass(TextInputEditText pass){
        String passIN = pass.getText().toString();

        if(passIN.length()>5 || passIN.length()>21){

            return true;
        }
        else {
            pass.setError("between 6 and 20 characters ");

            return false;
        }
    }
    private Boolean Validname(TextInputEditText name) {
        String nameIN = name.getText().toString();
        String n1 = "[a-zA-Z]+";
        if (nameIN.length() > 3 || nameIN.equals(n1)) {

            return true;
        } else {
            name.setError("at least 3 characters and don't use numbers");

            return false;
        }
    }



}
