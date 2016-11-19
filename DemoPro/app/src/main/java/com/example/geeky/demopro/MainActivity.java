package com.example.geeky.demopro;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnlogin;
    Button btnreg;
    Button btncancel;

    EditText username;
    EditText password;
    String user;
    String pass;

    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();

    private static final String LOGIN_URL = Constants.URL + "login.php";


    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Buttons
        btnlogin = (Button) findViewById(R.id.btnlogin );
        btnreg = (Button) findViewById(R.id.btncreate);
        btncancel = (Button) findViewById(R.id.btnquit);

        username = (EditText) findViewById(R.id.inputname);
        password = (EditText) findViewById(R.id.inputpassword);

        btnreg.setOnClickListener(this);
        btnlogin.setOnClickListener(this);
        btncancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.btnlogin:
                user = username.getText().toString();
                pass = password.getText().toString();
                new AttemptLogin().execute();
                break;
            case R.id.btncreate:
                Intent i = new Intent(this, RegActivity.class);
                startActivity(i);
                finish();
                break;

            case R.id.btnquit:
                finish();
                break;

            default:
                break;
        }
    }

    class AttemptLogin extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        boolean failure = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Logging in User...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            // Check for success tag
            int success;

            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("username", user));
                params.add(new BasicNameValuePair("password", pass));

                Log.d("request!", "starting");
                // getting product details by making HTTP request
                JSONObject json = jsonParser.makeHttpRequest(
                        LOGIN_URL, "POST", params);

                // check your log for json response
                Log.d("Login attempt", "[" + json.toString() + "]");

                // json success tag
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d("Login Successful!", json.toString());
                    Intent i = new Intent(MainActivity.this, Screen_Activity.class);
                    i.putExtra("userName", user);
                    finish();
                    startActivity(i);
                    return json.getString(TAG_MESSAGE);
                }else{
                    Log.d("Login Failure!", json.getString(TAG_MESSAGE));
                    return json.getString(TAG_MESSAGE);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                return null;
            }

            return null;

        }
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
            pDialog.dismiss();
            if (file_url != null){
                Toast.makeText(MainActivity.this, file_url, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(MainActivity.this, "Server Not Found try after some time!!",
                        Toast.LENGTH_LONG).show();
            }

        }

    }
}
