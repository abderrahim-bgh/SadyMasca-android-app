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


public class Profil_Frag extends Fragment implements View.OnClickListener {
    private TextView fullName, emaill, promo, debt;
    SessionManager sessionManager;
   AppCompatButton btnEdit;
    CircularImageView profil_image;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profil_, container, false);
        fullName = view.findViewById(R.id.profil_fullName);
        emaill = view.findViewById(R.id.profil_email);
        promo = view.findViewById(R.id.profil_promo);
        btnEdit= view.findViewById(R.id.edit_profil);

         profil_image= view.findViewById(R.id.profil_image);

        btnEdit.setOnClickListener(this);
        debt = view.findViewById(R.id.profil_debt);
        sessionManager = new SessionManager(getActivity().getApplicationContext());
        sessionManager.checkLogin1();
        HashMap<String, String> user = sessionManager.getUserDetal();
        String Exnme = user.get(sessionManager.FirstName);
        String Exnm = user.get(sessionManager.LastName);
        String Exemail = user.get(sessionManager.EMAIL);
        String pr1 = user.get(sessionManager.p1);
        String pr2 = user.get(sessionManager.p2);
        String pr3 = user.get(sessionManager.p3);
        String pic = user.get(sessionManager.Photo);
        emaill.setText(Exemail);
        fullName.setText(Exnme + " " + Exnm);
        promo.setText(pr1);
        debt.setText(pr2 + "\n" + pr3);


        if (pic.isEmpty()){}
        else  { Picasso.get().load(pic).into(profil_image);}



        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_profil:
                Intent intent = new Intent(getActivity(),Edit_Profil.class);
                startActivity(intent);
                getActivity().finish();


                break;

        }
    }


}