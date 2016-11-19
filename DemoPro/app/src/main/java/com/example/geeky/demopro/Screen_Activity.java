package com.example.geeky.demopro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Screen_Activity extends AppCompatActivity {

    Button btnView, btnNew;
    Button btnlogout;
    Button btncontact;
    Button btnview_prof, btnview_;
    String user;
    public static String USER;


    @Override
    protected void onPause() {
        super.onPause();
        MyApplication.activityPaused();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            user = bundle.getString("userName");
            USER = user;
        }


        // Buttons
        btnView = (Button) findViewById(R.id.btnViewProducts);
        btnNew = (Button) findViewById(R.id.btnAdd);
        btnlogout = (Button) findViewById(R.id.btnlogout);
        btncontact = (Button) findViewById(R.id.btnContact);
        btnview_prof = (Button) findViewById(R.id.btnprofile);
        btnview_ = (Button) findViewById(R.id.btnupdate_da);

        // view products click event
        btnView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching All products Activity

                //finish();
                Intent i = new Intent(getApplicationContext(), ViewProperty.class);
                i.putExtra("userName", user);
                startActivity(i);

            }
        });

        btnNew.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching All products Activity
                Intent i = new Intent(getApplicationContext(), NewPropertyActivity.class);
                i.putExtra("userName", user);
                startActivity(i);

            }
        });

        // view products click event


        btncontact.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching create new product activity
                Intent i = new Intent(getApplicationContext(), MessageActivity.class);
                i.putExtra("userName", user);
                startActivity(i);

            }
        });

        btnlogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Launching create new product activity
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnview_prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Profile.class);
                i.putExtra("userName", user);
                startActivity(i);
            }
        });

        btnview_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ChangeStatus.class);
                i.putExtra("userName", user);
                startActivity(i);
            }
        });
    }
}
