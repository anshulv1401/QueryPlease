package com.example.anshulvanawat.askrat2.starting_activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anshulvanawat.askrat2.NetworkConnectionErrorFragment;
import com.example.anshulvanawat.askrat2.R;
import com.example.anshulvanawat.askrat2.WebConnector2;
import com.example.anshulvanawat.askrat2.main_activities.MainActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.StringTokenizer;

public class SplashScreen extends AppCompatActivity implements Runnable{

    String ratid;
    String firstname;
    String lastname;
    String email;
    String password;
    String contactno;
    WebConnector2 wc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        wc=new WebConnector2();

        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getApplication());

        ratid=preferences.getString(MainActivity.RATID,"");
        firstname=preferences.getString(MainActivity.FIRST_NAME,"");
        lastname=preferences.getString(MainActivity.LAST_NAME,"");
        email=preferences.getString(MainActivity.EMAIL,"");
        password=preferences.getString(MainActivity.PASSWORD,"");
        contactno=preferences.getString(MainActivity.CONTACTNO,"");

        Handler h=new Handler();
        h.postDelayed(this,1500);
    }

    @Override
    public void run() {
        if(checkConnection()) {
            if (ratid.equals("") || firstname.equals("") || lastname.equals("") || email.equals("") || password.equals("") || contactno.equals("")) {
                Intent i = new Intent(this, LoginActivity.class);
                startActivity(i);
                finish();
            } else {
                Intent intent = new Intent(getApplication(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        }else{
            FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            NetworkConnectionErrorFragment networkConnectionErrorFragment=new NetworkConnectionErrorFragment();
            fragmentTransaction.replace(R.id.splash_frame_layout,networkConnectionErrorFragment,"Error_Fragment");
            fragmentTransaction.commit();
        }

    }

    // Method to manually check connection status
    private boolean checkConnection() {

        ConnectivityManager check = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] info = check.getAllNetworkInfo();


        for (int i = 0; i<info.length; i++) {
            if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                if (wc.checkConnection()){
                    showSnack(true);
                    return true;
                } else {
                    Toast.makeText(this, "Server Connection problem", Toast.LENGTH_LONG).show();
                    return false;
                }
            }
        }
        showSnack(false);
        return false;
    }


    // Showing the status in Snackbar
    private void showSnack(boolean isConnected) {
        String message;
        int color;
        if (isConnected) {
            message = "Connected to Internet";
            color = Color.WHITE;
        } else {
            message = "Sorry! Not connected to internet";
            color = Color.RED;
        }

        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.splash_screen), message, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.show();
    }
}
