package com.example.geeky.demopro;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geeky.demopro.model.ListModel;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class Profile extends AppCompatActivity {

    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();
    protected EditText fname;
    EditText lname;
    EditText username;
    EditText password;
    EditText location;
    EditText contact;

    Button btnreg;
    Button btncancel;

    private JSONArray mComments = null;
    // url to create new product
    private static String url_new_user = Constants.URL + "update_prof.php";
    private static String url_get_user = Constants.URL + "getinfo.php";

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
        setContentView(R.layout.activity_profile);



        // Edit Text
        fname = (EditText) findViewById(R.id.input_f_name);
        lname = (EditText) findViewById(R.id.input_l_name);
        username = (EditText) findViewById(R.id.input_name);
        password = (EditText) findViewById(R.id.input_password);
        location = (EditText) findViewById(R.id.input_address);
        contact = (EditText) findViewById(R.id.input_contact);


        // Create button
        btnreg = (Button) findViewById(R.id.input_submit);
        btncancel = (Button) findViewById(R.id.input_cancel);

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
                Intent i = new Intent(getApplicationContext(), Screen_Activity.class);
                startActivity(i);
                finish();
            }
        });

        new GetUserData().execute();
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
            pDialog = new ProgressDialog(Profile.this);
            pDialog.setMessage("Updating User..");
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


            JSONObject json = jsonParser.makeHttpRequest(url_new_user,
                    "POST", params);

            // check log cat fro response
            Log.d("Create Response","[" + json.toString() + "]");

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    Log.d("Update Success!", json.getString(TAG_MESSAGE));
                    Intent i = new Intent(getApplicationContext(), Screen_Activity.class);
                    startActivity(i);
                    finish();
                    return json.getString(TAG_MESSAGE);
                } else {
                    Log.d("Update Failure!", json.getString(TAG_MESSAGE));
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
                Toast.makeText(Profile.this, file_url, Toast.LENGTH_LONG).show();
            }
        }

    }


    class GetUserData extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Profile.this);
            pDialog.setMessage("Retrieving User Data");
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

            params.add(new BasicNameValuePair("username", Screen_Activity.USER));

            // getting JSON Object
            // Note that create product url accepts POST method

            JSONObject json = jsonParser.makeHttpRequest(url_get_user,
                            "POST", params);

            if (json == null) {
                return null;
            }
            // check log cat fro response
            Log.e("Create Response","[" + json.toString() + "]");

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);
                JSONArray array = json.getJSONArray("posts");
                Log.e("Create Response","[" + array.toString() + "]");

                updateJSONdata(array);
                if (success == 1) {
                    Log.d("User info. Success !", json.getString(TAG_MESSAGE));
                    return json.getString(TAG_MESSAGE);
                } else {
                    Log.d("Gathering Failure!", json.getString(TAG_MESSAGE));
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
                Toast.makeText(Profile.this, file_url, Toast.LENGTH_LONG).show();

                fname.setText(Firstname);
                lname.setText(Lastname);
                username.setText(Username);
                location.setText(Address);
                contact.setText(Contact);
            }
        }

    }

    public void updateJSONdata (JSONArray c1) {

        try {
                JSONObject c = c1.getJSONObject(0);

                Firstname   = c.getString("fname");
                Lastname    = c.getString("lname");
                Username    = c.getString("username");
                Contact     = c.getString("contact");
                Address     = c.getString("location");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
