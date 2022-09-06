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


public class Profil_Prof extends Fragment implements View.OnClickListener {
    private TextView fullName, emaill, promo, departm;
    SessionManager sessionManager;
    AppCompatButton btnEdit, pub;
    CircularImageView profil_image;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profil__prof, container, false);
        fullName = view.findViewById(R.id.profil_fullName);
        emaill = view.findViewById(R.id.profil_email);
        promo = view.findViewById(R.id.profil_promo);
        btnEdit= view.findViewById(R.id.edit_profil);
        departm=view.findViewById(R.id.profil_deprt);

        pub = view.findViewById(R.id.pub_prof);
        pub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),publication.class);
                startActivity(intent);
            }
        });
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
        String pic = user.get(sessionManager.Photo);
        departm.setText(deprt);
        emaill.setText(Exemail);
        fullName.setText(Exnme + " " + Exnm);
        promo.setText(pr1);
        if (pic.isEmpty()){}
        else { Picasso.get().load(pic).into(profil_image);}





        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_profil:
                Intent intent = new Intent(getActivity(),Edit_Prpfil_prof.class);
                startActivity(intent);
                getActivity().finish();


                break;
        }
    }
}