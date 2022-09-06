package com.example.stadymasca;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class profil_admin extends Fragment implements View.OnClickListener{
    private TextView fullName, emaill, promo, departm;
    SessionManager sessionManager;
    AppCompatButton btnEdit, pub;
    CircularImageView profil_image;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profil_admin, container, false);

// Inflate the layout for this fragment
        fullName = view.findViewById(R.id.profil_fullName);
        emaill = view.findViewById(R.id.profil_email);
        promo = view.findViewById(R.id.profil_promo);
        btnEdit= view.findViewById(R.id.edit_profil);
        departm=view.findViewById(R.id.profil_deprt);

        pub = view.findViewById(R.id.pub_prof);

         profil_image= view.findViewById(R.id.profil_image);
        btnEdit.setOnClickListener(this);

        sessionManager = new SessionManager(getActivity().getApplicationContext());
        sessionManager.checkLogin2();
        HashMap<String, String> user = sessionManager.getUserDetal();
        String Exnme = user.get(sessionManager.FirstName);
        String Exnm = user.get(sessionManager.LastName);
        String Exemail = user.get(sessionManager.EMAIL);
        String pr1 = user.get(sessionManager.p1);
        String deprt = user.get(sessionManager.Department);
        String facult = user.get(sessionManager.Fac);
        String pic = user.get(sessionManager.Photo);
        pub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),pubAdmin.class);
                startActivity(intent);
            }
        });
        departm.setText(deprt);
        if (pic.isEmpty()){}
        else { Picasso.get().load(pic).into(profil_image);}


        emaill.setText(Exemail);
        fullName.setText(Exnme + " " + Exnm+"");
        promo.setText(pr1);




        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_profil:
                Intent intent = new Intent(getActivity(),EditProfilAdmin.class);
                startActivity(intent);
                getActivity().finish();


                break;
        }
    }

}