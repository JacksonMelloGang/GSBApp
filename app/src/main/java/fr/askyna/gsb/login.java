package fr.askyna.gsb;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class login extends AppCompatActivity {

    private static Button login_button;
    private static TextView username_TV;
    private static TextView password_TV;
    private static TextView text_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button login_button = (Button) findViewById(R.id.login_button);
        username_TV = findViewById(R.id.username_input);
        password_TV = findViewById(R.id.password_input);
        text_info = findViewById(R.id.text_info);

       /*
        ConnectivityManager  cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if((cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected()) || !isInternetConnected()){
            login_button.setClickable(false);
            text_info.setText(R.string.noWifi);
            return;
        }
        */

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginController LC = new LoginController(view.getContext());

                String username = username_TV.getText().toString();
                String password = password_TV.getText().toString();
                if(username.isEmpty()){
                    text_info.setText(R.string.empty_username);
                    return;
                }

                if(password.isEmpty()){
                    text_info.setText(R.string.empty_password);
                    return;
                }

                LC.execute(username, password);
            }
        });
    }

    private boolean isInternetConnected() {
        try {
            InetAddress ipAddr = InetAddress.getByName("www.google.com");
            if(ipAddr.equals("")) {
                return true;
            } else {
                return false;
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean isTaskRoot() {
        return super.isTaskRoot();
    }

    public static Button getLogin_button() {
        return login_button;
    }

    public static TextView getPassword_TV() {
        return password_TV;
    }

    public static TextView getText_info() {
        return text_info;
    }

    public static TextView getUsername_TV() {
        return username_TV;
    }
}