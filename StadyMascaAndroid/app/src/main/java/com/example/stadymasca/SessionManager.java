package com.example.stadymasca;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;

    public Context context;
    int PRIVATE_MODE= 0;
    private static final String PREF_NAME="login";
    private static final String LOGIN ="IS_LOGIN";
    public static final String FirstName ="firstName";
    public static final String EMAIL ="email";
    public static final String LastName ="lastName";
    public static final String Department ="department";
    public static final String Fac ="faculty";
    public static final String p1 ="p1";
    public static final String p2 ="p2";
    public static final String p3 ="p3";
    public static final String ID= "id";
    public static final String Photo= "photo";
    public static final String STATUS= "status";
    public static final String respo= "responsibility";
    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences= context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor= sharedPreferences.edit();
    }
    public  void  createSession(String firstName ,String email, String lastName,String P1,String P2,String P3 ,String id,String department, String photo, String status, String responsibility,String fac ){
        editor.putBoolean(LOGIN,true);
        editor.putString(FirstName,firstName);
        editor.putString(EMAIL,email);
        editor.putString(LastName,lastName);

        editor.putString(Fac,fac);
        editor.putString(Department,department);
        editor.putString(Photo,photo);
        editor.putString(STATUS,status);
        editor.putString(p1,P1);
        editor.putString(p2,P2);
        editor.putString(p3,P3);
        editor.putString(ID,id);
        editor.putString(respo,responsibility);
        editor.apply();
    }
    public  void  createSession2(String firstName ,String email, String lastName ,String id){
        editor.putBoolean(LOGIN,true);
        editor.putString(FirstName,firstName);
        editor.putString(EMAIL,email);
        editor.putString(LastName,lastName);
        editor.putString(ID,id);
        editor.apply();
    }

    public  Boolean isLoggin(){
        return sharedPreferences.getBoolean(LOGIN,false);
    }
    public void checkLogin1(){
        if(!this.isLoggin()){
            Intent i = new Intent(context, Login.class);
            context.startActivity(i);
            ((etudient) context).finish();

        }
    }

    public void checkLogin2(){
        if(!this.isLoggin()){
            Intent i = new Intent(context, Login.class);
            context.startActivity(i);
            ((AdminMain) context).finish();

        }
    }

    public void checkLogin3(){
        if(!this.isLoggin()){
            Intent i = new Intent(context, Login.class);
            context.startActivity(i);
            ((teacher) context).finish();

        }
    }



    public HashMap<String, String>getUserDetal(){
        HashMap<String, String> user= new HashMap<>();
        user.put(FirstName,sharedPreferences.getString(FirstName, null));
        user.put(LastName,sharedPreferences.getString(LastName, null));
        user.put(Department,sharedPreferences.getString(Department, null));
        user.put(Fac,sharedPreferences.getString(Fac, null));
        user.put(p3,sharedPreferences.getString(p3, null));
        user.put(p2,sharedPreferences.getString(p2, null));
        user.put(p1,sharedPreferences.getString(p1, null));
        user.put(EMAIL,sharedPreferences.getString(EMAIL, null));
        user.put(Photo,sharedPreferences.getString(Photo, null));
        user.put(STATUS,sharedPreferences.getString(STATUS, null));
        user.put(ID,sharedPreferences.getString(ID, null));
        user.put(respo,sharedPreferences.getString(respo, null));
        return user;
    }
    public void logout1(){
        editor.clear();
        editor.commit();
        Intent i = new Intent(context, Login.class);
        context.startActivity(i);
        ((etudient) context).finish();
    }
    public void logout2(){
        editor.clear();
        editor.commit();
        Intent i = new Intent(context, Login.class);
        context.startActivity(i);
        ((AdminMain) context).finish();
    }
    public void logout3(){
        editor.clear();
        editor.commit();
        Intent i = new Intent(context, Login.class);
        context.startActivity(i);
        ((teacher) context).finish();
    }
}
