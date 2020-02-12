package com.example.mycontacts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";


    private Button mAdd;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        mAdd.setOnClickListener(this::onClick);

    }
    private void init()
    {
        mAdd = findViewById(R.id.add_button);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClick(View v) {
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
            }
        }
    }
}
