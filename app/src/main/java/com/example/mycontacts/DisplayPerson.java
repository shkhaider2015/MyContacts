package com.example.mycontacts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DisplayPerson extends AppCompatActivity {

    private ImageView mImageView;
    private Button mCall;
    private TextView mName, mNumber, mEmail, mcategory;
    private Contacts contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_person);
        init();
        contact = (Contacts) getIntent().getSerializableExtra("contact");

        mCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



        loadInformation(contact);
    }

    private void init()
    {
        mImageView = findViewById(R.id.display_imageView);
        mCall = findViewById(R.id.display_call);
        mName = findViewById(R.id.display_name);
        mNumber = findViewById(R.id.display_number);
        mEmail = findViewById(R.id.display_email);
        mcategory = findViewById(R.id.display_category);

    }

    private void loadInformation(Contacts contacts)
    {
        Bitmap bitmap = ImageUtility.getImage(contacts.getImagePath());

        if (bitmap != null)
        {
            mImageView.setImageBitmap(bitmap);
        }
        else
        {
            mImageView.setImageResource(R.drawable.ic_profile);
        }



        mName.setText(contacts.getFullName());
        mEmail.setText(contacts.getEmail());
        mNumber.setText(contacts.getPhoneNumber());
        mcategory.setText(contacts.getCategory());

    }
}
