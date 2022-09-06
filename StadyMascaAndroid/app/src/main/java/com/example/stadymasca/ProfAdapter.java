package com.example.stadymasca;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfAdapter extends RecyclerView.Adapter<ProfAdapter.UserHolder>{
    Context context;
    List<UsersADM> usersList;
    public static final String Edit_users= "http://192.168.43.22/StadyMasca/profil_EditAdmin.php";
    public static final String block_user="http://192.168.43.22/StadyMasca/block.php";
    public static final String url_add="http://192.168.43.22/StadyMasca/ChangeToAddmin.php";
    public ProfAdapter(Context context, List<UsersADM> usersList) {
        this.context = context;
        this.usersList = usersList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View userLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.info_prof,parent,false);


        return new UserHolder(userLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
      UsersADM users = usersList.get(position);
      holder.firstName.setText(users.getFirstName());
        holder.lastName.setText(users.getLastName());
        holder.email.setText(users.getEmail());
        holder.block.setText(users.getBlock());

        String bl=  users.getBlock();
        holder.respons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View editLayout = LayoutInflater.from(context).inflate(R.layout.grad_prof,null);
                RadioButton r1 =editLayout.findViewById(R.id.g1);
                RadioButton r2 =editLayout.findViewById(R.id.g2);
                RadioButton r3 =editLayout.findViewById(R.id.g3);
                AlertDialog.Builder builder= new AlertDialog.Builder(context);
                builder.setView(editLayout);
                builder.setTitle("responsability "+users.getFirstName()+" "+users.getLastName());
                if (users.getResponsibility().equals("2")){
                    r3.setChecked(true);
                    r2.setChecked(false);
                    r1.setChecked(false);
                    notifyDataSetChanged();
                    notifyItemChanged(position);
                }else if (users.getResponsibility().equals("1")){
                    r3.setChecked(false);
                    r2.setChecked(true);
                    r1.setChecked(false);
                    notifyDataSetChanged();
                    notifyItemChanged(position);
                }else {
                    r3.setChecked(false);
                    r2.setChecked(false);
                    r1.setChecked(true);
                    notifyDataSetChanged();
                    notifyItemChanged(position);
                }
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String Grad;
                        if(r3.isChecked()){
                            Grad ="2";
                        }else if(r2.isChecked()){
                            Grad="1";
                        }else  Grad="0";

                        final String oldEmail = users.getEmail();

                        String grad_user="http://192.168.43.22/StadyMasca/responsibility.php";
                        StringRequest request = new StringRequest(Request.Method.POST, grad_user,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                notifyDataSetChanged();
                                                notifyItemChanged(position);
                                                
                                                Toast toast= Toast.makeText(context, response, Toast.LENGTH_LONG);
                                                toast.getView().setBackgroundColor(Color.parseColor("#9ede73"));
                                                toast.show();
                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }) {
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        HashMap<String, String> params = new HashMap<>();
                                        params.put("responsibility", Grad);
                                        params.put("oldEmail",oldEmail);

                                        return params;

                                    }
                                };
                                RequestQueue queue = Volley.newRequestQueue(context);
                                queue.add(request);

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


            }
        });


        holder.addAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View editLayout = LayoutInflater.from(context).inflate(R.layout.activity_edit_admin_info,null);
                EditText first_name= editLayout.findViewById(R.id.profilEditFName);
                EditText lastName=editLayout.findViewById(R.id.lastnameE);
                EditText email = editLayout.findViewById(R.id.profil_emailE);
                first_name.setText(users.getFirstName());
                lastName.setText(users.getLastName());
                email.setText(users.getEmail());

                    AlertDialog.Builder builder= new AlertDialog.Builder(context);
                    builder.setTitle("Add admin "+users.getFirstName()+" "+users.getLastName());
                    builder.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            final String Email = email.getText().toString();
                            final String oldEmail = users.getEmail();
                            final String status = "admin";
                            if ( Email.isEmpty()){
                                Toast.makeText(context, "Some Filds Empty", Toast.LENGTH_SHORT).show();
                            }else {
                                StringRequest request = new StringRequest(Request.Method.POST, url_add,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                               notifyDataSetChanged();
                                               notifyItemChanged(position);
                                                Toast toast= Toast.makeText(context, "Add admin", Toast.LENGTH_LONG);
                                                toast.getView().setBackgroundColor(Color.parseColor("#9ede73"));
                                                toast.show();
                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }) {
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        HashMap<String, String> params = new HashMap<>();
                                        params.put("status", status);
                                        params.put("email",Email);
                                        params.put("oldEmail",oldEmail);

                                        return params;

                                    }
                                };
                                RequestQueue queue = Volley.newRequestQueue(context);
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
                    dialog.show();


            }
        });

        if(bl.equals("paused")) {
            holder.linearLayout.setBackgroundColor(Color.parseColor("#D11E11"));

        }


        holder.block1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View editLayout = LayoutInflater.from(context).inflate(R.layout.activity_edit_admin_info,null);
                EditText first_name= editLayout.findViewById(R.id.profilEditFName);
                EditText lastName=editLayout.findViewById(R.id.lastnameE);
                EditText email = editLayout.findViewById(R.id.profil_emailE);
                first_name.setText(users.getFirstName());
                lastName.setText(users.getLastName());
                email.setText(users.getEmail());
                if(!bl.equals("paused")){

                    AlertDialog.Builder builder= new AlertDialog.Builder(context);
                builder.setTitle("paused "+users.getFirstName()+" "+users.getLastName());
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            final String fname = first_name.getText().toString();
                            final String lname = lastName.getText().toString();
                            final String Email = email.getText().toString();
                            final String oldEmail = users.getEmail();
                            final String blockk = "yes";
                            if (fname.isEmpty() || lname.isEmpty() || Email.isEmpty()) {
                                Toast.makeText(context, "Some Filds Empty", Toast.LENGTH_SHORT).show();
                               


                            } else {
                                StringRequest request = new StringRequest(Request.Method.POST, block_user,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                notifyDataSetChanged();
                                                notifyItemChanged(position);
                                                Toast toast= Toast.makeText(context, "block ", Toast.LENGTH_LONG);
                                                toast.getView().setBackgroundColor(Color.parseColor("#f55c47"));
                                                toast.show();
                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }) {
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        HashMap<String, String> params = new HashMap<>();
                                        params.put("block", blockk);
                                        params.put("firstName", fname);
                                        params.put("lastName", lname);
                                        params.put("email", Email);
                                        params.put("oldEmail", oldEmail);

                                        return params;

                                    }
                                };
                                RequestQueue queue = Volley.newRequestQueue(context);
                                queue.add(request);
                            }
                        }

                    }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog= builder.create();
                    dialog.show();}
                else{ AlertDialog.Builder builder= new AlertDialog.Builder(context);
                    builder.setTitle("active "+users.getFirstName()+" "+users.getLastName());
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            final String fname = first_name.getText().toString();
                            final String lname = lastName.getText().toString();
                            final String Email = email.getText().toString();
                            final String oldEmail = users.getEmail();
                            final String blockk = "no";
                            if (fname.isEmpty() || lname.isEmpty()|| Email.isEmpty()){
                                Toast.makeText(context, "Some Filds Empty", Toast.LENGTH_SHORT).show();
                            }else {
                                StringRequest request = new StringRequest(Request.Method.POST, block_user,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                notifyDataSetChanged();
                                                notifyItemChanged(position);
                                                Toast toast= Toast.makeText(context, "active", Toast.LENGTH_LONG);
                                                toast.getView().setBackgroundColor(Color.parseColor("#9ede73"));
                                                toast.show();
                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }) {
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        HashMap<String, String> params = new HashMap<>();
                                        params.put("block", blockk);
                                        params.put("firstName", fname);
                                        params.put("lastName",lname);
                                        params.put("email",Email);
                                        params.put("oldEmail",oldEmail);

                                        return params;

                                    }
                                };
                                RequestQueue queue = Volley.newRequestQueue(context);
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


            }
        });
        holder.edit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View editLayout = LayoutInflater.from(context).inflate(R.layout.activity_edit_admin_info,null);
                EditText first_name= editLayout.findViewById(R.id.profilEditFName);
                EditText lastName=editLayout.findViewById(R.id.lastnameE);
                EditText email = editLayout.findViewById(R.id.profil_emailE);
                first_name.setText(users.getFirstName());
                lastName.setText(users.getLastName());
                email.setText(users.getEmail());

                AlertDialog.Builder builder= new AlertDialog.Builder(context);
                builder.setTitle("Edit "+users.getFirstName()+" "+users.getLastName());
                builder.setView(editLayout);
                builder.setPositiveButton("save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String fname = first_name.getText().toString();
                        final String lname = lastName.getText().toString();
                        final String Email = email.getText().toString();
                        final String oldEmail = users.getEmail();
                        if (fname.isEmpty() || lname.isEmpty()|| Email.isEmpty()){
                            Toast.makeText(context, "Some Filds Empty", Toast.LENGTH_SHORT).show();
                        }else {
                            StringRequest stringRequest= new StringRequest(Request.Method.POST, Edit_users,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                            Toast toast= Toast.makeText(context, "edit seccuss", Toast.LENGTH_LONG);
                                            toast.getView().setBackgroundColor(Color.parseColor("#9ede73"));
                                            toast.show();
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
                                    params.put("firstName", fname);
                                    params.put("lastName",lname);
                                    params.put("email",Email);
                                    params.put("oldEmail",oldEmail);
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

            }
        });

    }
   

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public class UserHolder extends RecyclerView.ViewHolder{
        ImageView edit1, block1,addAdmin,respons;
        LinearLayout linearLayout;
      TextView firstName,lastName,email,block;
        public UserHolder(@NonNull View itemView) {
            super(itemView);
            firstName= itemView.findViewById(R.id.name1Person);
            lastName=itemView.findViewById(R.id.name2Person);
            linearLayout=itemView.findViewById(R.id.forcolor);
            email = itemView.findViewById(R.id.emailPerson);
            edit1= itemView.findViewById(R.id.edit1);
            block1=itemView.findViewById(R.id.block1);
            block =itemView.findViewById((R.id.blk));
            addAdmin=itemView.findViewById(R.id.add_admin);
            respons = itemView.findViewById(R.id.respons);
        }
    }

    public void updateReceiptsList(List<UsersADM> newlist) {
        usersList = newlist;
        this.notifyDataSetChanged();
    }

}
