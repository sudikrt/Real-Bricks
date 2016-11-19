package com.example.geeky.demopro;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
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

/**
 * Created by Sudarshan on 10/22/2016.
 */
public class RegActivity extends Activity {

    // Progress Dialog
    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();
    EditText fname;
    EditText lname;
    EditText username;
    EditText password;
    EditText location;
    EditText contact;

    Button btnreg;
    Button btncancel;

    // url to create new product
    private static String url_new_user = Constants.URL + "create.php";


    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    String Firstname;
    String Lastname;
    String Username;
    String Password;
    String Address;
    String Contact;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_activity);

        // Edit Text
        fname = (EditText) findViewById(R.id.fname);
        lname = (EditText) findViewById(R.id.lname);
        username = (EditText) findViewById(R.id.uname);
        password = (EditText) findViewById(R.id.pass);
        location = (EditText) findViewById(R.id.addr);
        contact = (EditText) findViewById(R.id.contact);


        // Create button
        btnreg = (Button) findViewById(R.id.btnreg);
        btncancel = (Button) findViewById(R.id.btncancel);

        // button click event
        btnreg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Firstname = fname.getText().toString();
                Lastname = lname.getText().toString();
                Username = username.getText().toString();
                Password = password.getText().toString();
                Address = location.getText().toString();
                Contact = contact.getText().toString();

                // creating new product in background thread
                new CreateNewProduct().execute();
            }
        });

        btncancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    /**
     * Background Async Task to Create new product
     * */
    class CreateNewProduct extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(RegActivity.this);
            pDialog.setMessage("Registering New User..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("fname", Firstname));
            params.add(new BasicNameValuePair("lname", Lastname));
            params.add(new BasicNameValuePair("username", Username));
            params.add(new BasicNameValuePair("password", Password));
            params.add(new BasicNameValuePair("location", Address));
            params.add(new BasicNameValuePair("contact", Contact));

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_new_user,
                    "POST", params);

            // check log cat fro response
            Log.d("Create Response","[" + json.toString() + "]");

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    Log.d("User Creation Success!", json.getString(TAG_MESSAGE));
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    finish();
                    return json.getString(TAG_MESSAGE);
                } else {
                    Log.d("User Creation Failure!", json.getString(TAG_MESSAGE));
                    return json.getString(TAG_MESSAGE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
            if (file_url != null){
                Toast.makeText(RegActivity.this, file_url, Toast.LENGTH_LONG).show();
            }
        }

    }
}
