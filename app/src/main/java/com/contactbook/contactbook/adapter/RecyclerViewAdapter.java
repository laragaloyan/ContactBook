package com.contactbook.contactbook.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;


import com.contactbook.contactbook.Contact;
import com.contactbook.contactbook.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RVViewHolder> {


    private Context mContext;
    private List<Contact> mData;

    public RecyclerViewAdapter(Context mContext, List<Contact> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }


    @NonNull
    @Override
    public RVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_contact, parent, false);
        return new RVViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RVViewHolder holder, int position) {
   holder.bind(mData.get(position));


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class RVViewHolder extends RecyclerView.ViewHolder{
     private TextView tv_name;
     private TextView tv_number;
     private CheckBox fav_checkbox;


        public RVViewHolder(View itemView) {
            super(itemView);

            tv_name = (TextView) itemView.findViewById(R.id.name_contact);
            tv_number = (TextView) itemView.findViewById(R.id.phone_number);
            fav_checkbox = (CheckBox) itemView.findViewById(R.id.fav_checkbox);
        }

        public void bind(Contact contact){
            tv_name.setText(contact.getName());
            tv_number.setText(contact.getPhone());
        }

    }

}
