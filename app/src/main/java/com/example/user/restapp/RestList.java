package com.example.user.restapp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


/**
 * Created by USER on 14/03/2018.
 */

    public class RestList extends ArrayAdapter<Restaurant> {
        private Activity context;
        private List<Restaurant> restList;
        public RestList(Activity context, List<Restaurant> restList)
        {
            super(context, R.layout.rest_item,restList);
            this.context = context;
            this.restList = restList;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View view = inflater.inflate(R.layout.rest_item,null);
            TextView  tvNameRestItem = (TextView)view.findViewById(R.id.tvNameRestItem);
            TextView tvPhoneRestItem = (TextView)view.findViewById(R.id.tvPhoneRestItem);

            Restaurant rest = restList.get(position);
            tvNameRestItem.setText(rest.getName());
            tvPhoneRestItem.setText(rest.getPhone());
            return view;
        }

}
