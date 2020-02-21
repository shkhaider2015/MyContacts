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

public class DisplayPerson extends AppCompatActivity implements View.OnClickListener {

    private ImageView mImageView;
    private Button mCall, mUpdate;
    private TextView mName, mNumber, mEmail, mcategory;
    private Contacts contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_person);
        init();
        contact = (Contacts) getIntent().getSerializableExtra("contact");
        mCall.setOnClickListener(this::onClick);
        mUpdate.setOnClickListener(this::onClick);



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
        mUpdate = findViewById(R.id.display_update);

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.display_call:
                // call code
                break;
            case R.id.display_update:

                Intent intent = new Intent(DisplayPerson.this, UpdatePerson.class);
                intent.putExtra("update_contact", contact);
                this.startActivity(intent);
                break;
        }

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
