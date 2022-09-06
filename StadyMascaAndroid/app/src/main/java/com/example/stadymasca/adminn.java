package com.example.stadymasca;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;


public class adminn extends Fragment {

    private ChipNavigationBar chipNavigationBar;
    private Fragment fragment = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_adminn, container, false);

        chipNavigationBar = view.findViewById(R.id.chipNav);
        chipNavigationBar.setItemSelected(R.id.fdp, true);
        getFragmentManager().beginTransaction().replace(R.id.container, new fdpFragment()).commit();



        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i) {
                    case R.id.fdp:
                        fragment = new fdpFragment();
                        break;

                    case R.id.proffrg:
                        fragment = new ProfFragment();
                        break;
                    case R.id.etudfrg:
                        fragment = new etudientFrg();
                        break;
                }

                if (fragment != null) {
                    getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                }
            }
        });
        return view;
    }
}