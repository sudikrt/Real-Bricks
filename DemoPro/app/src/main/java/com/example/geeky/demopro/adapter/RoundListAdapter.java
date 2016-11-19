package com.example.geeky.demopro.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.geeky.demopro.Constants;
import com.example.geeky.demopro.R;
import com.example.geeky.demopro.model.ListModel;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by geekyint on 5/8/16.
 */
public class RoundListAdapter extends RecyclerView.Adapter<RoundListAdapter.Holder> {

        protected List<ListModel> list;

        private static String LINK = Constants.URL + "upload/";

        private Context context;

        public class Holder extends RecyclerView.ViewHolder {
            public TextView j_title, j_username, j_message, j_price, j_state, j_phone, j_location;
            public ImageView j_img;

            public Holder(View view) {
                super(view);
                j_username = (TextView) view.findViewById(R.id.username);
                j_message = (TextView) view.findViewById(R.id.message);
                j_title = (TextView) view.findViewById(R.id.title);
                j_img = (ImageView) view.findViewById(R.id.image);
                j_price = (TextView) view.findViewById(R.id.id_price);
                j_state = (TextView) view.findViewById(R.id.id_status);
                j_phone = (TextView) view.findViewById(R.id.id_phone);
                j_location = (TextView) view.findViewById(R.id.id_location);
            }
        }

        public RoundListAdapter(Context context, List<ListModel> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.single_post, parent, false);
            return new Holder(view);
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            ListModel data = list.get(position);

            holder.j_title.setText(data.getTitle());
            holder.j_message.setText(data.getContent());
            holder.j_price.setText("Price : " + data.getPrice());
            holder.j_username.setText("User : " + data.getUsername());
            holder.j_state.setText(data.getStatus());
            holder.j_phone.setText("Ph. : " + data.getPhone());
            holder.j_location.setText("City.:" + data.getLocation());
            Picasso.with(context)
                    .load(LINK + data.getImgurl())
                    .placeholder(R.drawable.cx)
                    .error(R.mipmap.ic_launcher)
                    .into(holder.j_img);

        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }
