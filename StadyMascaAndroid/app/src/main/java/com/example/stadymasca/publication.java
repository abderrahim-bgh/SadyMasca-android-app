package com.example.stadymasca;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class publication extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
  ImageView back , add, img;
  TextView fullN;
  EditText t1,t2;
  Button share;
    SessionManager sessionManager;
    Bitmap bitmap;
    String encodedimage ;
    String deprt,facult,resp,id_t;
    Calendar calendar;
    Spinner spinner,spinner0,spinner1;
    AppCompatSpinner sp3;
    ArrayList<String> yeartList = new ArrayList<>();
    ArrayAdapter<String> yearAdapter;
    RequestQueue requestQueue;

    private static final String apiurl="http://192.168.43.22/StadyMasca/pub.php";
    private static final String TAG = Edit_Profil.class.getSimpleName(); //getting info

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publication);
        back = findViewById(R.id.back);
        add = findViewById(R.id.img);
        img =findViewById(R.id.img_pub);
        fullN=findViewById(R.id.fullName3);
         calendar= Calendar.getInstance();
        t1=(EditText)findViewById(R.id.share_title);
        t2=(EditText)findViewById(R.id.share_text);
        spinner= findViewById(R.id.sp_pub);
        spinner0= findViewById(R.id.sp_pub0);
        spinner1= findViewById(R.id.sp_pub1);
        sp3= findViewById(R.id.year);


        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this, R.array.typ_pub, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        ArrayAdapter<CharSequence> adapter0= ArrayAdapter.createFromResource(this, R.array.typ_pub0, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner0.setAdapter(adapter0);
        spinner0.setOnItemSelectedListener(this);


        ArrayAdapter<CharSequence> adapter1= ArrayAdapter.createFromResource(this, R.array.typ_pub1, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(this);

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin2();
        HashMap<String, String> user = sessionManager.getUserDetal();
        String fName1 = user.get(sessionManager.FirstName);
        String lName1 = user.get(sessionManager.LastName);
        deprt = user.get(sessionManager.Department);
        facult= user.get(sessionManager.Fac);
        resp= user.get(sessionManager.respo);
        id_t=user.get(sessionManager.ID);
        fullN.setText(fName1+" "+lName1);
        share =findViewById(R.id.sharebtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(publication.this, teacher.class);
                startActivity(intent);
                finish();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"), 1);            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadtoserver();
            }
        });
        String selecteddepartment =deprt.toString();

        String urlp = "http://192.168.43.22/StadyMasca/promo.php?department="+selecteddepartment;
        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.POST, urlp,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("promo");

                    for (int i = 0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String prom = jsonObject.optString("promoNom");
                        yeartList.add(prom);
                        yearAdapter = new ArrayAdapter<>(publication.this, android.R.layout.simple_spinner_item,yeartList);
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
        if(resp.equals("2")){
            spinner.setVisibility(View.VISIBLE);
            spinner1.setVisibility(View.GONE);
            spinner0.setVisibility(View.GONE);
        }
        else if (resp.equals("1")){
            spinner.setVisibility(View.GONE);
            spinner1.setVisibility(View.VISIBLE);
            spinner0.setVisibility(View.GONE);
        }else {
            spinner.setVisibility(View.GONE);
            spinner1.setVisibility(View.GONE);
            spinner0.setVisibility(View.VISIBLE);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1 && resultCode==RESULT_OK && data!= null) {
        Uri filepath= data.getData();
            try {
                InputStream stream= getContentResolver().openInputStream(filepath);
                bitmap= BitmapFactory.decodeStream(stream);
                img.setImageBitmap(bitmap);
                encodebitmap(bitmap);
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void encodebitmap(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);

        byte[] byteofimages=byteArrayOutputStream.toByteArray();
        encodedimage=android.util.Base64.encodeToString(byteofimages, Base64.DEFAULT);
    }

    private void uploadtoserver()
    {

        final String title=t1.getText().toString().trim();
        final String text=t2.getText().toString().trim();
        final String transmitter= fullN.getText().toString().trim();
        final String Date= DateFormat.getDateInstance().format(calendar.getTime());
        final String dep=deprt.trim();
        final String receiver;
        if(resp.equals("2")) {
            if (spinner.getSelectedItem().equals("Select promo")) {
                receiver = sp3.getSelectedItem().toString();
            } else if (spinner.getSelectedItem().equals("Deprtment affiliation")) {
                receiver = dep;
            } else if (spinner.getSelectedItem().equals("Faculty affiliation")) {
                receiver = facult;
            } else {
                receiver = spinner.getSelectedItem().toString();
            }
        }
        else if(resp.equals("1")) {
            if (spinner1.getSelectedItem().equals("Select promo")) {
                receiver = sp3.getSelectedItem().toString();
            } else if (spinner1.getSelectedItem().equals("Deprtment affiliation")) {
                receiver = dep;
            } else if (spinner1.getSelectedItem().equals("Faculty affiliation")) {
                receiver = facult;
            } else {
                receiver = spinner1.getSelectedItem().toString();
            }
        }
        else {
            if (spinner0.getSelectedItem().equals("Select promo")) {
                receiver = sp3.getSelectedItem().toString();
            } else if (spinner0.getSelectedItem().equals("Deprtment affiliation")) {
                receiver = dep;
            } else {
                receiver = spinner0.getSelectedItem().toString();
            }
        }

        StringRequest request=new StringRequest(Request.Method.POST, apiurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                t1.setText("");
                t2.setText("");
                img.setImageResource(R.drawable.logoapp);
                Toast toast= Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG);
                toast.getView().setBackgroundColor(Color.parseColor("#9ede73"));
                toast.show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast toast= Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG);
                toast.getView().setBackgroundColor(Color.parseColor("#f55c47"));
                toast.show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String,String> map=new HashMap<String, String>();
                map.put("title",title);
                map.put("textt",text);
                map.put("department",dep);
                map.put("fac",facult);
                map.put("transmitter",transmitter);
                map.put("receiver",receiver);
                map.put("datte",Date);
                map.put("id_t",id_t);
                if(encodedimage== null){
                    encodedimage="";
                }
                map.put("image",encodedimage);
                return map;
            }
        };

        RequestQueue queue= Volley.newRequestQueue(this);
        queue.add(request);


    }  // end of function uploadto DB


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (spinner.getSelectedItem().equals("Select promo")){
            sp3.setVisibility(View.VISIBLE);

        }

         else if (spinner0.getSelectedItem().equals("Select promo")){
            sp3.setVisibility(View.VISIBLE);

        }

        else if (spinner1.getSelectedItem().equals("Select promo")){
            sp3.setVisibility(View.VISIBLE);
        }else {
            sp3.setVisibility(View.GONE);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}