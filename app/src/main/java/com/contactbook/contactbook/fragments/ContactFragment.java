package com.contactbook.contactbook.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.contactbook.contactbook.Contact;
import com.contactbook.contactbook.ContactManager;
import com.contactbook.contactbook.R;
import com.contactbook.contactbook.adapter.RecyclerViewAdapter;
import com.contactbook.contactbook.adapter.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ContactFragment extends Fragment {

     View view;
    private RecyclerView recyclerView;
    private List<Contact> listContact;
    private ViewPagerAdapter mViewPagerAdapter;

    public ContactFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_contact, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.contact_recyclerview);
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext(), listContact);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerViewAdapter);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listContact = new ArrayList<>();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        init(view);
        askForContactPermission();}

    private void init (View v){
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext(), ContactManager.getInstance().getContacts());

     recyclerView = view.findViewById(R.id.contact_recyclerview);
     recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
     recyclerView.setAdapter(recyclerViewAdapter);
 }


private void getContacts(){
    Cursor phones = Objects.requireNonNull(getActivity()).getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
while (phones.moveToNext())
    {
        Contact contact = new Contact();
        String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
        String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
        contact.setName(name);
        contact.setPhone(phoneNumber);
        ContactManager.getInstance().add(contact);
    }
        phones.close();}

    public void askForContactPermission() {
        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.READ_CONTACTS)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                /*builder.setTitle("ContactManager access needed");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setMessage("please confirm ContactManager access");//TODO put real question*/
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        requestPermissions(
                                new String[]{Manifest.permission.READ_CONTACTS},
                                0);
                        getContacts();

                    }
                });
                builder.show();
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_CONTACTS},
                        0);
                getContacts();

            }
        } else {
            getContacts();
        }

    }

}