package com.contactbook.contactbook;
import java.util.ArrayList;
import java.util.List;

public class ContactManager {

    private List<Contact> mContacts;
    private static ContactManager mInstance;

    public static ContactManager getInstance() {
        if (mInstance == null) {
            mInstance = new ContactManager();
        }
        return mInstance;
    }

    private ContactManager() {
        mContacts = new ArrayList<>();
    }

    public void add(Contact contact) {
        mContacts.add(contact);
    }

    public List<Contact> getContacts() {
        return mContacts;
    }
}