package com.example.geeky.demopro;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import android.view.View;
import android.widget.Toast;

import com.example.geeky.demopro.adapter.RoundListAdapter;
import com.example.geeky.demopro.list.DividerItemDecoration;
import com.example.geeky.demopro.model.ListModel;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChangeStatus extends AppCompatActivity {

    private ProgressDialog pDialog;

    private static final String READ_COMMENTS_URL = Constants.URL + "getmydata.php";


    private JSONArray mComments = null;

    JSONParser jsonParser = new JSONParser();

    private RoundListAdapter mRoundListAdapter;

    private RecyclerView mRecyclerView;

    private List<ListModel> mStringList  = new ArrayList<>();

    public static ListModel model;

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_status);


        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_change_status);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        updateList();

    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        // loading the comments via AsyncTask
        mStringList.clear();
        new LoadComments().execute();
    }


    public void updateJSONdata (JSONArray jsonArray) {

        try {

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject c = jsonArray.getJSONObject(i);

                // gets the content of each tag
                String title    = c.getString("title");
                String content  = c.getString("message");
                String username = c.getString("username");
                String status   = c.getString("status");
                String image    = c.getString("imgurl");
                String phone    = c.getString("phone");
                String price    = c.getString("price");
                String location = c.getString("location");
                int pid         = c.getInt("post_id");
                String city     = c.getString("city");

                mStringList.add (new ListModel(title, content, username, image, phone, price,
                        status, location, pid,city));

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void updateList () {
        mRoundListAdapter = new RoundListAdapter(getApplicationContext(), mStringList);
        mRecyclerView.setAdapter(mRoundListAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(ChangeStatus.this,
                LinearLayoutManager.VERTICAL));
        mRecyclerView.addOnItemTouchListener(new RecycleTouchListener(
                getApplicationContext(),
                mRecyclerView,
                new ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        model = mStringList.get(position);
                        finish();
                        startActivity(new Intent(ChangeStatus.this, UpdateDAta.class));

                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                }
        ));

    }

    public class LoadComments extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ChangeStatus.this);
            pDialog.setMessage("Loading Records...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(Void... arg0) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("username", Screen_Activity.USER));

            JSONObject json = jsonParser.makeHttpRequest(READ_COMMENTS_URL,
                    "POST", params);

            if (json == null) {
                return null;
            }

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);
                JSONArray array = json.getJSONArray("posts");

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

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pDialog.dismiss();
            if (result != null) {
                Toast.makeText(ChangeStatus.this, result, Toast.LENGTH_LONG).show();
                updateList();
            } else {
                Toast.makeText(ChangeStatus.this, "Oops some thing went wrong", Toast.LENGTH_LONG).show();
            }

        }
    }


    public interface ClickListener {
        void onClick (View view, int position);
        void onLongClick (View view, int position);
    }

    public static class RecycleTouchListener implements RecyclerView.OnItemTouchListener {
        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecycleTouchListener (Context context,
                                     final RecyclerView recyclerView,
                                     final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context,
                    new GestureDetector.SimpleOnGestureListener() {
                        @Override
                        public boolean onSingleTapUp(MotionEvent e) {
                            //return super.onSingleTapUp(e);
                            return true;
                        }

                        @Override
                        public void onLongPress(MotionEvent e) {
                            View child = recyclerView.findChildViewUnder(e.getX(),
                                    e.getY());
                            if (child != null && clickListener != null) {
                                clickListener.onLongClick(child,
                                        recyclerView.getChildPosition(child));
                            }
                        }
                    });
        }



        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}