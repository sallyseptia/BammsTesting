package com.example.asus.bamms;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class UserSessionManager {
    SharedPreferences pref;

    //Editor reference for shared preferences
    SharedPreferences.Editor editor;

    //Context
    Context _context;

    //Shared pref mode
    int PRIVATE_MODE = 0;

    //Sharedpref file name
    private static final String PREFER_NAME = "AndroidExamplePref";

    //All Shared Preferences Keys
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";

    //User phone (make variable public to access from outside)
    public static final String KEY_PHONE = "phone";

    //User phone (make variable public to access from outside)
    public static final String KEY_NAME = "name";

    //Constructor
    public UserSessionManager(Context context)
    {
        this._context= context;
        pref = _context.getSharedPreferences(PREFER_NAME,PRIVATE_MODE);
        editor = pref.edit();
    }

    //Create Login Session
    public void createUserLoginSession(String phone , String name)
    {
        //Storing login value as true
        editor.putBoolean(IS_USER_LOGIN, true);

        //Storing email in pref
        editor.putString(KEY_PHONE, phone);

        //Storing name in pref
        editor.putString(KEY_NAME, name);

        //commit changes
        editor.commit();

        editor.apply();


    }

    //Check login method will check user login status
    //If false it will redirect user to login page
    //Else do anything

    public boolean isUserLoggedIn(){
        return pref.getBoolean(IS_USER_LOGIN,false);
    }

    public boolean checkLogin()
    {
        //Check login status
        if(!this.isUserLoggedIn())
        {
            //user is not logged in redirect to login activity
            Intent i = new Intent(_context, LogInActivity.class);

            //Closing all the activities from stack
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            //Add new flag to start new activity
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            //Staring login activity
            _context.startActivity(i);

            return true;
        }
        return false;
    }

    //Get Stored session data
    public HashMap<String, String> getUserDetails()
    {
        //Use Hashmap to store user credentials
        HashMap<String, String> user = new HashMap<String, String>();

        //User's phone
        user.put(KEY_PHONE, pref.getString(KEY_PHONE, null));

        //User's name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));

        //return user
        return user;
    }

    //Clear session details
    public void logoutUser(String phone , String name)
    {
        //Clearing all user data from Shared Preferences
        editor.clear();
        editor.commit();

//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
//        reference.child("User").child(phone)
//                .child("messaging_token")
//                .setValue("");


        //After logout redirect user to Login Activity
        Intent i = new Intent(_context, LogInActivity.class);
//
//        //Closing all the activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        //Add new flag to start new activity
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        finishAffinity();
//        //Staring login activity
        editor.putBoolean(IS_USER_LOGIN, false);
        _context.startActivity(i);

    }
}
