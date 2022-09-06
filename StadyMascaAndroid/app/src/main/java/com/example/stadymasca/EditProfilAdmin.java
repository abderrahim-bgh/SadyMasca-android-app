package com.example.stadymasca;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditProfilAdmin extends AppCompatActivity {
    private static final String TAG = Edit_Profil.class.getSimpleName(); //getting info
    ImageView close, save;
    private EditText firstNameE,lastnameE, emaill, promo, pass;
    private TextView deprt;
    Button editPic;
    AppCompatButton change;
    SessionManager sessionManager;
    ArrayList<String> yeartList = new ArrayList<>();
    ArrayAdapter<String> yearAdapter;
    String getId;
    String URL_UPLOAD="http://192.168.43.22/StadyMasca/upload.php";
    private Bitmap bitmap;
    CircularImageView profil_image;
    AppCompatSpinner sp3;
    private static String url ="http://192.168.43.22/StadyMasca/profil.php";
    private static String urlEdit ="http://192.168.43.22/StadyMasca/profil_Edit.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profil_admin);
        close= findViewById(R.id.clos);
        change =findViewById(R.id.change_promo);
        editPic= findViewById(R.id.btn_photo);
        profil_image= findViewById(R.id.profil_image);
        save= findViewById(R.id.save);
        firstNameE = findViewById(R.id.profilEditFName);
        lastnameE = findViewById(R.id.lastnameE);
        emaill = findViewById(R.id.profil_emailE);
        sp3= findViewById(R.id.year);
        deprt= findViewById(R.id.profil_deprt);
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin3();

        HashMap<String, String> user = sessionManager.getUserDetal();
        getId= user.get(SessionManager.ID);
        String getFacult = user.get(sessionManager.Fac);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateEmail(emaill) || !Validname(lastnameE) || !Validname(firstNameE)) {
                    validateEmail(emaill);
                    Validname(firstNameE);
                    Validname(lastnameE);}
                else {
                SaveEditDetail();
                Intent intent = new Intent(EditProfilAdmin.this, AdminMain.class);
                startActivity(intent);
                finish();
            }
            }

        });
        Fragment fragment = new Profil_Frag();
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfilAdmin.this , AdminMain.class);
                startActivity(intent);
                finish();
            }

        });
        editPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFile();

            }
        });
        String selectefaculty =getFacult;

        String urlp = "http://192.168.43.22/StadyMasca/department.php?faculty="+selectefaculty;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.POST, urlp,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("depart");


                    for (int i = 0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String prom = jsonObject.optString("department");
                        yeartList.add(prom);
                        yearAdapter = new ArrayAdapter<>(EditProfilAdmin.this, android.R.layout.simple_spinner_item,yeartList);
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
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(EditProfilAdmin.this);
                builder.setTitle("Change Department ");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String  getMail = user.get(sessionManager.EMAIL);
                        final String P1 = sp3.getSelectedItem().toString();
                        if ( getMail.isEmpty()){
                            Toast.makeText(EditProfilAdmin.this, "Some Filds Empty", Toast.LENGTH_SHORT).show();
                        }else {
                            String change_promo="http://192.168.43.22/StadyMasca/cahngeD1.php";
                            StringRequest request = new StringRequest(Request.Method.POST, change_promo,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                            Toast toast= Toast.makeText(EditProfilAdmin.this, response, Toast.LENGTH_SHORT);
                                            toast.getView().setBackgroundColor(Color.parseColor("#9ede73"));
                                            toast.show();                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(EditProfilAdmin.this, error.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    HashMap<String, String> params = new HashMap<>();
                                    params.put("email",getMail);
                                    params.put("p1",P1);

                                    return params;

                                }
                            };
                            RequestQueue queue = Volley.newRequestQueue(EditProfilAdmin.this);
                            queue.add(request);
                        }
                    }

                });
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog= builder.create();
                dialog.show();}

        });

        requestQueue.add(jsonObjectRequest);
    }
    //save
    private void SaveEditDetail() {
        final String firstName = this.firstNameE.getText().toString().trim();
        final String lastName = this.lastnameE.getText().toString().trim();
        final String email = this.emaill.getText().toString().trim();
        if (!validateEmail(emaill) || !Validname(lastnameE) || !Validname(firstNameE)) {
            validateEmail(emaill);
            Validname(firstNameE);
            Validname(lastnameE);

        } else {

            final String id = getId;


            StringRequest stringRequest = new StringRequest(Request.Method.POST, urlEdit,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("success");
                                if (success.equals("1")) {
                                    Toast toast = Toast.makeText(getApplicationContext(), " Success ", Toast.LENGTH_LONG);
                                    toast.getView().setBackgroundColor(Color.GREEN);
                                    toast.show();
                                    sessionManager.createSession2(firstName, email, lastName, id);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast toast = Toast.makeText(getApplicationContext(), "Error  " + e.toString(), Toast.LENGTH_LONG);
                                toast.getView().setBackgroundColor(Color.RED);
                                toast.show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Error  " + error.toString(), Toast.LENGTH_LONG);
                    toast.getView().setBackgroundColor(Color.RED);
                    toast.show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("firstName", firstName);
                    params.put("lastName", lastName);
                    params.put("email", email);
                    params.put("id", id);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);

        }
    }




    //get user Detail
    private void getUserDetail(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, response.toString());
                        try {
                            JSONObject jsonObject= new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray= jsonObject.getJSONArray("read");
                            if (success.equals("1")){
                                for (int i =0; i<jsonArray.length();i++){
                                    JSONObject jsonObject1= jsonArray.getJSONObject(i);

                                    String firstName= jsonObject1.getString("firstName").trim();
                                    String lastName= jsonObject1.getString("lastName").trim();
                                    String email= jsonObject1.getString("email").trim();
                                    firstNameE.setText(firstName);
                                    lastnameE.setText(lastName);
                                    emaill.setText(email);

                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(EditProfilAdmin.this, "Error reading "+e, Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EditProfilAdmin.this, "Error  "+error, Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params= new HashMap<>();
                params.put("id",getId);
                return params;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void chooseFile(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==1 && resultCode == RESULT_OK && data!= null && data.getData() != null){
            Uri filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                profil_image.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            UploadPic(getId, getStringImage(bitmap));
        }

    }

    private void UploadPic(final String id , final String photo) {
        final ProgressDialog progressDialog= new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UPLOAD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.i(TAG, response.toString());
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success= jsonObject.getString("success");
                    if (success.equals("1")){
                        Toast.makeText(EditProfilAdmin.this, "Success", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(EditProfilAdmin.this, "try Again "+e.toString(), Toast.LENGTH_SHORT).show();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(EditProfilAdmin.this, "try Again "+error.toString(), Toast.LENGTH_SHORT).show();

            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                params.put("photo",photo);

                return params;
            }
        };
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public String getStringImage(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageByteArray = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageByteArray, Base64.DEFAULT);

        return encodedImage;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserDetail();
    }

    private Boolean validateEmail(EditText email) {
        String emailIn = email.getText().toString();
        if(!emailIn.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailIn).matches()){
            return true;
        }
        else {
            email.setError("enter a valid email address");
            return  false;
        }

    }

    private Boolean Validname(EditText name){
        String nameIN = name.getText().toString();
        String n1= "[a-zA-Z]+";
        if(nameIN.length()>3 || nameIN.equals(n1)){

            return true;
        }
        else {
            name.setError("at least 3 characters and don't use numbers");

            return false;
        }
    }

}

