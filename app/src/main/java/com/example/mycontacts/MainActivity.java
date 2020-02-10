package com.example.mycontacts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";


    private Button mAdd;

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
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, AddContact.class);
        startActivity(intent);
    }
}
