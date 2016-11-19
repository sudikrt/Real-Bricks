package com.example.geeky.demopro;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.example.geeky.demopro.adapter.RoundListAdapter;
import com.example.geeky.demopro.list.DividerItemDecoration;
import com.example.geeky.demopro.model.ListModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ViewProperty extends AppCompatActivity {

    private ProgressDialog pDialog;

    private static final String READ_COMMENTS_URL = Constants.URL + "comments.php";


    private JSONArray mComments = null;

    private RoundListAdapter mRoundListAdapter;

    private RecyclerView mRecyclerView;

    private List<ListModel> mStringList  = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_property);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        // loading the comments via AsyncTask
        mStringList.clear();
        new LoadComments().execute();
    }


    public void updateJSONdata () {
        JSONParser jParser = new JSONParser();

        JSONObject json = jParser.getJSONFromUrl(READ_COMMENTS_URL);

        try {
            mComments = json.getJSONArray("posts");

            for (int i = 0; i < mComments.length(); i++) {
                JSONObject c = mComments.getJSONObject(i);

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
        mRecyclerView.addItemDecoration(new DividerItemDecoration(ViewProperty.this,
                LinearLayoutManager.VERTICAL));
        mRecyclerView.addOnItemTouchListener(new RecycleTouchListener(
                getApplicationContext(),
                mRecyclerView,
                new ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                }
        ));
    }

    public class LoadComments extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ViewProperty.this);
            pDialog.setMessage("Loading Records...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... arg0) {
            updateJSONdata();
            return null;

        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            pDialog.dismiss();
            updateList();
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
}
