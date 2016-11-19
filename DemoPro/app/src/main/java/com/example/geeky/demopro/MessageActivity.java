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

public class MessageActivity extends AppCompatActivity {

    // Progress Dialog
    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();
    EditText name;
    EditText email;
    EditText msg;

    String Name;
    String Email;
    String Message;

    Button btnsend;
    Button btncancel;

    // url to create new product
    private static String url_new_user = Constants.URL + "new_msg.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        // Edit Text
        name = (EditText) findViewById(R.id.inputfname);
        email = (EditText) findViewById(R.id.inputemail);
        msg = (EditText) findViewById(R.id.inputmessage);



        // Create button
        btnsend = (Button) findViewById(R.id.btnSend);
        btncancel = (Button) findViewById(R.id.btnBack);

        // button click event
        btnsend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Name = name.getText().toString();
                Email = email.getText().toString();
                Message = msg.getText().toString();
                // creating new product in background thread
                new CreateNewProduct().execute();
            }
        });

        btncancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
                Intent i = new Intent(getApplicationContext(), Screen_Activity.class);
                startActivity(i);
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
            pDialog = new ProgressDialog(MessageActivity.this);
            pDialog.setMessage("Sending Message..");
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
            params.add(new BasicNameValuePair("name", Name));
            params.add(new BasicNameValuePair("email", Email));
            params.add(new BasicNameValuePair("msg", Message));


            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_new_user,
                    "POST", params);

            // check log cat fro response
            Log.d("Create Response", json.toString());

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully created product
                    finish();
                    Intent i = new Intent(getApplicationContext(), Screen_Activity.class);
                    startActivity(i);
                    Log.d("Gathering Failure!", json.getString(TAG_MESSAGE));
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
            if (file_url != null) {
                Toast.makeText(MessageActivity.this, file_url, Toast.LENGTH_LONG).show();
            }
        }

    }
}
