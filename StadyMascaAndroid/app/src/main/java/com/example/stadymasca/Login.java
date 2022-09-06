package com.example.stadymasca;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
     Button callSignUp;
    AppCompatButton btn_Login;
    ImageView image;
    TextView logoText;
    TextInputLayout email, password;
    TextInputEditText textInputEditTextemail ,textInputEditTextpassword;
  SessionManager sessionManager;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager =new SessionManager(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        callSignUp = findViewById(R.id.signup_screen);
        image = findViewById(R.id.logoimg);
        logoText = findViewById(R.id.logotext);
        email = findViewById(R.id.email3);
        password = findViewById(R.id.password);
        btn_Login = findViewById(R.id.logIn_btn);
        textInputEditTextemail = findViewById(R.id.email4);
        textInputEditTextpassword = findViewById(R.id.password10);
         String em = textInputEditTextemail.getText().toString() ;


        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (  !validateEmail(textInputEditTextemail) || !ValidPass(textInputEditTextpassword) ) {
                    validateEmail(textInputEditTextemail);
                    ValidPass(textInputEditTextpassword);
                }
                else {
                    String mEmail = textInputEditTextemail.getText().toString();
                String mPass = textInputEditTextpassword.getText().toString();

                if (!mEmail.isEmpty() && !mPass.isEmpty()) {

                    login(mEmail, mPass);

                }
                }
            }
        });
        callSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,SignUp.class);
                Pair[] pairs = new Pair[6];
                pairs [0]= new Pair<View,String>(image,"logo_img");
                pairs [1]= new Pair<View,String>(logoText,"logo_text");
                pairs [2]= new Pair<View,String>(email,"username_tran");
                pairs [3]= new Pair<View,String>(password,"password_tran");
                pairs [4]= new Pair<View,String>(btn_Login,"button_tran");
                pairs [5]= new Pair<View,String>(callSignUp,"login_signup_tran");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this,pairs);
                startActivity(intent,options.toBundle());




            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        RequestQueue requestQueue;
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://192.168.43.22/StadyMasca/isBlock.php";
        if (SharedPrefManager.getInstance(getApplication()).isLoggedIn()){

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(JSONObject response) {


                try {
                    JSONArray jsonArray = response.getJSONArray("adherent");
                    for (int i = 0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String blockUser = jsonObject.optString("block");
                        String stat = jsonObject.optString("status");
                        if (stat.equals("etudient")){
                                Intent intent = new Intent(getApplication(), etudient.class);
                                Toast.makeText(Login.this, stat, 2500).show();

                                startActivity(intent);

                        }
                        else if (stat.equals("prof")){
                                Intent intent = new Intent(getApplication(), teacher.class);
                                startActivity(intent);

                        }else{
                                Intent intent = new Intent(getApplication(), AdminMain.class);
                                startActivity(intent);

                        }
                    }} catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener()

        {
            @Override
            public void onErrorResponse (VolleyError error){

            }

        });


            queue.add(jsonObjectRequest);

    }

    }



    private void login(String email , String password){
        StringRequest stringRequest= new StringRequest(Request.Method.POST, "http://192.168.43.22/StadyMasca/login.php", new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("login");
                    if (success.equals("4")){
                        Toast toast= Toast.makeText(Login.this, "your account is bloked", Toast.LENGTH_LONG);
                        toast.getView().setBackgroundColor(Color.BLUE);
                        toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
                        toast.show();
                    }
                    else if (success.equals("1")){
                        for (int i=0; i<jsonArray.length(); i++){
                            JSONObject object= jsonArray.getJSONObject(i);
                            String email= object.getString("email").trim();
                            String firstName= object.getString("firstName").trim();
                            String lastName= object.getString("lastName").trim();
                            String p1= object.getString("p1").trim();
                            String p2= object.getString("p2").trim();
                            String p3= object.getString("p3").trim();
                            String Id= object.getString("id").trim();
                            String responsibility= object.getString("responsibility").trim();
                            String status= object.getString("status").trim();
                            String photo= object.getString("photo").trim();
                            String department= object.getString("department").trim();
                            String fac= object.getString("faculty").trim();
                            Toast toast= Toast.makeText(Login.this, "Sucess Login", Toast.LENGTH_LONG);
                            toast.getView().setBackgroundColor(Color.parseColor("#9ede73"));
                            toast.show();
                            sessionManager.createSession(firstName,email,lastName,p1,p2,p3,Id,department,photo,status,responsibility,fac);
                            sessionManager.createSession2(firstName,email,lastName,Id);
                            Intent intent = new Intent(getApplicationContext(),loading.class);
                            intent.putExtra("email",email);
                            intent.putExtra("firstName",firstName);
                            intent.putExtra("lastName",lastName);
                            intent.putExtra("department",department);

                            intent.putExtra("faculty",fac);
                            intent.putExtra("p1",p1);
                            intent.putExtra("p2",p2);
                            intent.putExtra("p3",p3);
                            intent.putExtra("photo",photo);
                            intent.putExtra("status",status);
                            intent.putExtra("responsibility",responsibility);
                            startActivity(intent);
                            finish();


                        }

                    }
                    else if (success.equals("2")){
                        for (int i=0; i<jsonArray.length(); i++){
                            JSONObject object= jsonArray.getJSONObject(i);
                            String email= object.getString("email").trim();
                            String firstName= object.getString("firstName").trim();
                            String lastName= object.getString("lastName").trim();
                            String p1= object.getString("p1").trim();
                            String p2= object.getString("p2").trim();
                            String p3= object.getString("p3").trim();
                            String Id= object.getString("id").trim();
                            String status= object.getString("status").trim();
                            String photo= object.getString("photo").trim();

                            String fac= object.getString("faculty").trim();
                            String responsibility= object.getString("responsibility").trim();
                            String department= object.getString("department").trim();

                           Toast toast= Toast.makeText(Login.this, "Sucess Login", Toast.LENGTH_LONG);
                            toast.getView().setBackgroundColor(Color.parseColor("#9ede73"));

                           toast.setDuration(6000);
                           toast.show();
                            sessionManager.createSession(firstName,email,lastName,p1,p2,p3,Id,department,photo,status,responsibility,fac);
                            sessionManager.createSession2(firstName,email,lastName,Id);
                            Intent intent = new Intent(getApplicationContext(),loading1.class);
                            intent.putExtra("email",email);
                            intent.putExtra("firstName",firstName);
                            intent.putExtra("lastName",lastName);
                            intent.putExtra("department",department);
                            intent.putExtra("faculty",fac);
                            intent.putExtra("p1",p1);
                            intent.putExtra("p2",p2);
                            intent.putExtra("p3",p3);
                            intent.putExtra("photo",photo);
                            intent.putExtra("status",status);
                            intent.putExtra("responsibility",responsibility);
                            startActivity(intent);
                            finish();


                        }

                    }
                   else if (success.equals("3")){
                        for (int i=0; i<jsonArray.length(); i++){
                            JSONObject object= jsonArray.getJSONObject(i);
                            String email= object.getString("email").trim();
                            String firstName= object.getString("firstName").trim();
                            String lastName= object.getString("lastName").trim();
                            String department= object.getString("department").trim();
                            String fac= object.getString("faculty").trim();
                            String p1= object.getString("p1").trim();
                            String p2= object.getString("p2").trim();
                            String p3= object.getString("p3").trim();
                            String Id= object.getString("id").trim();
                            String status= object.getString("status").trim();
                            String photo= object.getString("photo").trim();
                            String responsibility= object.getString("responsibility").trim();
                            Toast toast= Toast.makeText(Login.this, "Sucess Login", Toast.LENGTH_LONG);
                            toast.getView().setBackgroundColor(Color.parseColor("#9ede73"));
                            toast.setDuration(6000);
                            toast.show();
                            sessionManager.createSession(firstName,email,lastName,p1,p2,p3,Id,department,photo,status,responsibility,fac);
                            sessionManager.createSession2(firstName,email,lastName,Id);
                            Intent intent = new Intent(getApplicationContext(),load.class);
                            intent.putExtra("email",email);
                            intent.putExtra("firstName",firstName);
                            intent.putExtra("lastName",lastName);
                            intent.putExtra("department",department);
                            intent.putExtra("faculty",fac);
                            intent.putExtra("p1",p1);
                            intent.putExtra("p2",p2);
                            intent.putExtra("p3",p3);
                            intent.putExtra("photo",photo);
                            intent.putExtra("status",status);
                            intent.putExtra("responsibility",responsibility);
                            startActivity(intent);
                            finish();
                        }

                    }else {
                        Toast toast= Toast.makeText(Login.this, "error pass", 6000);
                        toast.getView().setBackgroundColor(Color.parseColor("#f55c47"));
                        toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
                        toast.show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast toast= Toast.makeText(Login.this, e.getMessage(), 6000);
                    toast.getView().setBackgroundColor(Color.parseColor("#f55c47"));
                    toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
                    toast.show();

                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params = new HashMap<>();
                params.put("email",email);
                params.put("password",password);

                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private Boolean validateEmail(TextInputEditText email) {
        String emailIn = email.getText().toString();
        if(!emailIn.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailIn).matches()){
            return true;
        }
        else {
            email.setError("there is an error in the email");
            return  false;
        }

    }
    private Boolean ValidPass(TextInputEditText pass){
        String passIN = pass.getText().toString();

        if(passIN.length()>6){

            return true;
        }
        else {
            pass.setError("there is an error in the Password");

            return false;
        }
    }


}