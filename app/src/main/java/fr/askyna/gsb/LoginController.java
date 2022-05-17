package fr.askyna.gsb;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class LoginController extends AsyncTask<String, Integer, String> {

    private int statusreponse;
    private String detail;
    private Context context;

    public LoginController(Context context) {
        this.context = context;
    }


    @Override
    protected String doInBackground(String... strings) {

        // Create Http Post
        HttpPost loginpost = new HttpPost("https://api.gsb-lycee.ga/android/login.php");
        ArrayList<NameValuePair> data = new ArrayList<NameValuePair>();

        // Add params into a list
        data.add(new BasicNameValuePair("login", "login"));
        data.add(new BasicNameValuePair("username", strings[0]));
        data.add(new BasicNameValuePair("password", strings[1]));

        // set params into the post request
        loginpost.addHeader("Content-Type", "application/x-www-form-urlencoded");
        loginpost.setEntity(new UrlEncodedFormEntity(data));

        // init sender
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            CloseableHttpResponse response = httpClient.execute(loginpost);
            HttpEntity entity = response.getEntity(); // send response
            if (entity != null)
            {
                InputStream instream = entity.getContent();

                BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
                StringBuilder sb = new StringBuilder();
                String line = null;
                try
                {
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line + "\n");
                    }
                    instream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String reponseinstring = sb.toString();
                JSONObject obj = new JSONObject(reponseinstring);

                this.statusreponse = obj.getInt("response");
                this.detail = obj.getString("error");
            }

            EntityUtils.consume(entity);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return String.valueOf(statusreponse);
    }

    @Override
    protected void onPostExecute(String s) {

        switch(statusreponse){
            case -1:
                login.getText_info().setText(R.string.empty_passorusername);
                break;
            case 0:
                login.getText_info().setText(R.string.invalid_auth);
                break;
            case 1:
                login.getText_info().setText(R.string.success_auth);
                context.startActivity(new Intent(context, MainActivity.class));
                break;
        }
    }

}
