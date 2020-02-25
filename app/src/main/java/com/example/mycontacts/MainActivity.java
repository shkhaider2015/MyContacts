package com.example.mycontacts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private static final String TAG = "MainActivity";


    private ImageButton mAdd;
    private RecyclerView mRecyclerView;
    private Spinner mSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        mAdd.setOnClickListener(this::onClick);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category_array, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(this);


    }
    private void init()
    {
        mAdd = findViewById(R.id.add_button);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSpinner = findViewById(R.id.category_spinner);
    }

    @Override
    public void onClick(View v)
    {

        Intent intent = new Intent(MainActivity.this, AddContact.class);
        startActivity(intent);

    }

    private void getContacts()
    {
        class GetContacts extends AsyncTask<Void, Void, List<Contacts>>
        {

            @Override
            protected List<Contacts> doInBackground(Void... voids) {
                List<Contacts> contactsList = DatabaseClint.getInstance(getApplicationContext())
                        .getAppDatabase()
                        .contactsDao()
                        .getAll();

                return contactsList;
            }

            @Override
            protected void onPostExecute(List<Contacts> contacts) {
                super.onPostExecute(contacts);
                ContactsAdapter contactsAdapter = new ContactsAdapter(MainActivity.this, contacts);
                mRecyclerView.setAdapter(contactsAdapter);
            }
        }

        GetContacts getContacts = new GetContacts();
        getContacts.execute();
    }

    private void getFriendContacts()
    {
        class FriendContacts extends AsyncTask<Void, Void, List<Contacts>>
        {

            @Override
            protected List<Contacts> doInBackground(Void... voids) {

                return DatabaseClint
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .contactsDao()
                        .getFriends();
            }

            @Override
            protected void onPostExecute(List<Contacts> contactsList) {
                super.onPostExecute(contactsList);
                ContactsAdapter contactsAdapter = new ContactsAdapter(MainActivity.this, contactsList);
                mRecyclerView.setAdapter(contactsAdapter);
            }
        }

        FriendContacts friendContacts = new FriendContacts();
        friendContacts.execute();
    }

    private void getFamilyContacts()
    {
        class FamilyContacts extends AsyncTask<Void, Void, List<Contacts>>
        {

            @Override
            protected List<Contacts> doInBackground(Void... voids) {
                return DatabaseClint
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .contactsDao()
                        .getFamily();
            }

            @Override
            protected void onPostExecute(List<Contacts> contactsList) {
                super.onPostExecute(contactsList);
                ContactsAdapter contactsAdapter = new ContactsAdapter(MainActivity.this, contactsList);
                mRecyclerView.setAdapter(contactsAdapter);
            }
        }
        FamilyContacts familyContacts = new FamilyContacts();
        familyContacts.execute();

    }

    private void getClassmateContacts()
    {
        class ClassmateContacts extends AsyncTask<Void, Void, List<Contacts>>
        {

            @Override
            protected List<Contacts> doInBackground(Void... voids) {
                return DatabaseClint
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .contactsDao()
                        .getClassmate();
            }

            @Override
            protected void onPostExecute(List<Contacts> contactsList) {
                super.onPostExecute(contactsList);
                ContactsAdapter contactsAdapter = new ContactsAdapter(MainActivity.this, contactsList);
                mRecyclerView.setAdapter(contactsAdapter);
            }
        }

        ClassmateContacts classmateContacts = new ClassmateContacts();
        classmateContacts.execute();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        switch (position)
        {
            case 0:
                // all
                getContacts();
                break;
            case 1:
                // friend
                getFriendContacts();
                break;
            case 2:
                // family
                getFamilyContacts();
                break;
            case 3:
                // classmate
                getClassmateContacts();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }
}
