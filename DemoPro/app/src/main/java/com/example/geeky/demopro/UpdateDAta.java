package com.example.geeky.demopro;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sudarshan on 11/10/2016.
 */

public class UpdateDAta extends AppCompatActivity {

    private Button btn_update, btn_Cancel;
    private EditText ed_name, ed_loc, ed_city, ed_price, ed_desc, ed_contact;
    private RadioButton btn_for_sale, btn_sold;
    private String Name, Location, City, Price, Desc, Contact, status, user;
    int pid;

    int success = 0;

    private ProgressDialog pDialog;

    private static String TAG = "UpdateProp";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_SUCCESS = "success";

    private static String url_update = Constants.URL + "update_prop.php";

    JSONParser jsonParser = new JSONParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_details);


        btn_for_sale = (RadioButton) findViewById(R.id.for_sale_update);
        btn_sold = (RadioButton) findViewById(R.id.sold_update);

        btn_Cancel = (Button) findViewById(R.id.btnKanso_update);
        btn_update = (Button) findViewById(R.id.btnNew_update);

        ed_name = (EditText) findViewById(R.id.name_update);
        ed_city = (EditText) findViewById(R.id.city_update);
        ed_loc = (EditText) findViewById(R.id.location_update);
        ed_price = (EditText) findViewById(R.id.price_update);
        ed_desc = (EditText) findViewById(R.id.desc_update);
        ed_contact = (EditText) findViewById(R.id.cont_update);

        ed_name.setText(ChangeStatus.model.getTitle());
        ed_loc.setText(ChangeStatus.model.getLocation());
        ed_city.setText(ChangeStatus.model.getCity());
        ed_price.setText(ChangeStatus.model.getPrice());
        ed_desc.setText(ChangeStatus.model.getContent());
        ed_contact.setText(ChangeStatus.model.getPhone());

        user = ChangeStatus.model.getUsername();
        pid = ChangeStatus.model.getPid();
        status = ChangeStatus.model.getStatus();
        if (status.equalsIgnoreCase("For-Sale")) {
            btn_for_sale.setChecked(true);
        } else {
            btn_sold.setChecked(true);
        }

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_sold.isChecked()) {
                    status = "Sold";
                }
                if (btn_for_sale.isChecked()) {
                    status = "For-Sale";
                }
                Name = ed_name.getText().toString().trim();
                Contact = ed_contact.getText().toString().trim();
                Location = ed_loc.getText().toString().trim();
                City = ed_city.getText().toString().trim();
                Price = ed_price.getText().toString().trim();
                Desc = ed_desc.getText().toString().trim();

                new UpdateDetailsAsync().execute();
            }
        });

        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(UpdateDAta.this, ChangeStatus.class));
            }
        });
    }

    class UpdateDetailsAsync extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(UpdateDAta.this);
            pDialog.setMessage("Updating Property..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Creating product
         */
        protected String doInBackground(String... args) {


            String pid1 = String.valueOf(pid).toString();
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("userName", user));
            params.add(new BasicNameValuePair("contact", Contact));
            params.add(new BasicNameValuePair("name", Name));
            params.add(new BasicNameValuePair("location", Location));
            params.add(new BasicNameValuePair("city", City));
            params.add(new BasicNameValuePair("status", status));
            params.add(new BasicNameValuePair("price", Price));
            params.add(new BasicNameValuePair("description", Desc));
            params.add(new BasicNameValuePair("pid",pid1));

            JSONObject json = jsonParser.makeHttpRequest(url_update, "POST", params);
            // check for success tag
            try {
                success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully created product
                    return json.getString(TAG_MESSAGE);
                } else {
                    // failed to create product
                    return json.getString(TAG_MESSAGE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
            if (file_url != null) {
                Toast.makeText(UpdateDAta.this, file_url, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(UpdateDAta.this, "Server Not Found try after some time!!",
                        Toast.LENGTH_LONG).show();
            }
            if (success == 1) {
                finish();
                startActivity(new Intent(UpdateDAta.this, ChangeStatus.class));
            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
