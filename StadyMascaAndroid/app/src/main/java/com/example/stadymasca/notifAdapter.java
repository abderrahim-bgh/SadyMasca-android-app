package com.example.stadymasca;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class notifAdapter extends RecyclerView.Adapter<notifAdapter.UserHolder> {

    Context context;
    List<publicc> usersList;
    String url_Pub="http://192.168.43.22/StadyMasca/pub_home.php";
    public static final String Edit_users= "http://192.168.43.22/StadyMasca/Edit_Pub.php";

    public notifAdapter(Context context, List<publicc> usersList) {
        this.context = context;
        Collections.reverse(usersList);
        this.usersList = usersList;
        notifyDataSetChanged();

    }



    @Override
    public UserHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View userLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.notif_home,parent,false);

        return new UserHolder(userLayout);
    }

    String id_t;
    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {


        publicc users = usersList.get(position);


        holder.date.setText(users.getDatte());
        holder.t1.setText(users.getTransmitter());
        holder.t2.setText(users.getTitle());

        holder.t.setText(users.getReceiver());

         id_t = users.getId_t();
        String url_p ="http://192.168.43.22/StadyMasca/profl_prof_view.php?id_t="+id_t;
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.POST, url_p, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("adherent");

                    for (int i = 0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String emaill = jsonObject.optString("email");
                        String photo = jsonObject.optString("photo");
                        String department = jsonObject.optString("department");

                        if(photo.isEmpty()){}
                        else {
                            Picasso.get().load(photo).into(holder.imageView2);
                        }



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
        SessionManager sessionManager = new SessionManager(context);
        sessionManager.checkLogin2();
        HashMap<String, String> use = sessionManager.getUserDetal();
        String id= use.get(sessionManager.ID);
        if (!id.equals(users.getId_t())){
            holder.menu_pub.setVisibility(View.GONE);
            holder.pub_cache.setVisibility(View.VISIBLE);
        }
        else{
            holder.menu_pub.setVisibility(View.VISIBLE);
            holder.pub_cache.setVisibility(View.GONE);
        }

        holder.pub_cache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu= new PopupMenu(context,v);
                popupMenu.getMenuInflater().inflate(R.menu.item_pub_user,popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.hide_pub1:
                                String id_pub = users.getId_pub();
                                String apiurl="http://192.168.43.22/StadyMasca/cache-pub.php";
                                StringRequest request=new StringRequest(Request.Method.POST, apiurl, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response)
                                    {

                                        Toast.makeText(context,response.toString(),Toast.LENGTH_LONG).show();
                                        usersList.remove(position);
                                        notifyItemRemoved(position);
                                        notifyItemChanged(position);
                                        notifyDataSetChanged();
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(context, error.toString(),Toast.LENGTH_LONG).show();
                                    }
                                })
                                {
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError
                                    {
                                        Map<String,String> map=new HashMap<String, String>();
                                        map.put("id_pub",id_pub);
                                        map.put("id",id);

                                        return map;
                                    }
                                };

                                RequestQueue queue= Volley.newRequestQueue(context);
                                queue.add(request);

                                break;
                        }

                        return false;
                    }
                });

            }
        });




        String url_hidden="http://192.168.43.22/StadyMasca/hidden.php";
        StringRequest stringR= new StringRequest(Request.Method.GET, url_hidden,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray array= new JSONArray(response);

                            for (int i=0; i<array.length();i++){
                                JSONObject publ= array.getJSONObject(i);
                                String id_pub= publ.getString("id_pu").trim();
                                String idd= publ.getString("id").trim();
                                if (idd.equals(id) && id_pub.equals(users.getId_pub()) ){


                                    holder.linearLayout.setVisibility(View.GONE);
                                    holder.cardView.setVisibility(View.GONE);



                                }




                            }


                        } catch (JSONException e) {
                            Toast.makeText(context.getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQ= Volley.newRequestQueue(context.getApplicationContext());
        requestQ.add(stringR);






        holder.menu_pub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu= new PopupMenu(context,v);
                popupMenu.getMenuInflater().inflate(R.menu.item_pub,popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.edit_pub:
                                View editLayout = LayoutInflater.from(context).inflate(R.layout.edit_pub_home,null);
                                EditText title= editLayout.findViewById(R.id.title_pub);
                                EditText textt=editLayout.findViewById(R.id.text_pub);
                                ImageView img_pub = editLayout.findViewById(R.id.imagepub);
                                title.setText(users.getTitle());
                                textt.setText(users.getTextt());

                                AlertDialog.Builder builder= new AlertDialog.Builder(context);
                                builder.setTitle("Edit publication");
                                builder.setView(editLayout);
                                builder.setPositiveButton("save", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        final String title_P = title.getText().toString();
                                        final String textP = textt.getText().toString();
                                        final String id_pub = users.getId_pub();
                                        if (title_P.isEmpty() || textP.isEmpty()){
                                            Toast.makeText(context, "Some Filds Empty", Toast.LENGTH_SHORT).show();
                                        }else {
                                            StringRequest stringRequest= new StringRequest(Request.Method.POST, Edit_users,
                                                    new Response.Listener<String>() {
                                                        @Override
                                                        public void onResponse(String response) {
                                                            Intent intent = new Intent(context, loading2.class);
                                                            context.startActivity(intent);

                                                            Toast.makeText(context, response, Toast.LENGTH_SHORT).show();



                                                        }
                                                    }, new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                                                }
                                            }){
                                                @Override
                                                protected Map<String, String> getParams() throws AuthFailureError {
                                                    HashMap<String, String> params= new HashMap<>();
                                                    params.put("title", title_P);
                                                    params.put("textt",textP);
                                                    params.put("id_pub",id_pub);
                                                    return params;

                                                }
                                            };
                                            RequestQueue queue= Volley.newRequestQueue(context);
                                            queue.add(stringRequest);

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
                                dialog.show();
                                break;
                            case R.id.delete_pub:
                                AlertDialog.Builder builderr= new AlertDialog.Builder(context);
                                builderr.setTitle("Delete ");
                                builderr.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {
                                        String id_pub  = users.getId_pub();
                                        String del_pub="http://192.168.43.22/StadyMasca/del_pub.php";
                                        StringRequest stringRequest = new StringRequest(Request.Method.POST, del_pub,
                                                new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response) {
                                                        try {
                                                            JSONObject jsonObject= new JSONObject(response);
                                                            String check = jsonObject.getString("state");
                                                            if(check.equals("delete")){
                                                                Toast.makeText(context, "Delete", Toast.LENGTH_SHORT).show();
                                                                delet(position);
                                                            }
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }){
                                            @Override
                                            protected Map<String, String> getParams() throws AuthFailureError {
                                                HashMap<String,String> desetParams = new HashMap<>();
                                                desetParams.put("id_pub",id_pub);

                                                return desetParams;
                                            }
                                        };
                                        RequestQueue requestQueue = Volley.newRequestQueue(context);
                                        requestQueue.add(stringRequest);

                                    }
                                });
                                builderr.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {
                                        dialog.dismiss();
                                    }
                                });
                                AlertDialog dialogg= builderr.create();
                                dialogg.show();

                                break;
                            case R.id.hide_pub:
                                String id_pub = users.getId_pub();
                                String apiurl="http://192.168.43.22/StadyMasca/cache-pub.php";
                                StringRequest request=new StringRequest(Request.Method.POST, apiurl, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response)
                                    {

                                        Toast.makeText(context,response.toString(),Toast.LENGTH_LONG).show();
                                        usersList.remove(position);
                                        notifyItemRemoved(position);
                                        notifyItemChanged(position);
                                        notifyDataSetChanged();
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(context, error.toString(),Toast.LENGTH_LONG).show();

                                    }
                                })
                                {
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError
                                    {
                                        Map<String,String> map=new HashMap<String, String>();
                                        map.put("id_pub",id_pub);
                                        map.put("id",id);

                                        return map;
                                    }
                                };

                                RequestQueue queue= Volley.newRequestQueue(context);
                                queue.add(request);

                                break;
                        }

                        return false;
                    }
                });

            }
        });
        holder.t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View editLayout = LayoutInflater.from(context).inflate(R.layout.view_profil,null);
                TextView profil_fullName= editLayout.findViewById(R.id.profil_fullName);
                TextView email = editLayout.findViewById(R.id.profil_email);
                TextView dep = editLayout.findViewById(R.id.profil_dep1);
                CircularImageView img = editLayout.findViewById(R.id.profil_image);
                profil_fullName.setText(users.getTransmitter());

                RequestQueue requestQueue = Volley.newRequestQueue(context);
                JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.POST, url_p, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("adherent");

                            for (int i = 0;i<jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String emaill = jsonObject.optString("email");
                                String photo = jsonObject.optString("photo");
                                String department = jsonObject.optString("department");
                                if (photo.isEmpty()){}
                                else   Picasso.get().load(photo).into(img);
                                dep.setText(department);
                                email.setText(emaill);

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

                AlertDialog.Builder builder= new AlertDialog.Builder(context);
                builder.setTitle("Profil ");
                builder.setView(editLayout);
                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog dialog= builder.create();
                dialog.show();

            }
        });

        

    }



    @Override
    public int getItemCount() {
        return usersList.size();
    }



    public class UserHolder<itemView> extends RecyclerView.ViewHolder {
        TextView t1,t2,date,t;
        LinearLayout linearLayout;
        CardView cardView;
        ImageView  imageView2, menu_pub, pub_cache;
        public UserHolder(@NonNull View v) {
            super(v);
            t= v.findViewById(R.id.pub_lieu);
            t1= v.findViewById(R.id.trnsmiter);
            date=v.findViewById(R.id.datte);
            t2=v.findViewById(R.id.title);
            imageView2=v.findViewById(R.id.imgProf);
            menu_pub=v.findViewById(R.id.menu_pub);
            cardView = v.findViewById(R.id.card_view);
            linearLayout=v.findViewById(R.id.pub_id);
            pub_cache=v.findViewById(R.id.menu_pub_user);
        }

    }
    public void delet(int item){
        usersList.remove(item);
        notifyItemRemoved(item);
        notifyItemChanged(item);
        notifyDataSetChanged();

    }


}
