package com.example.mycontacts;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class UpdatePerson extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView;

    private EditText mFullName;
    private EditText mPhoneNumber;
    private EditText mEmail;


    private RadioGroup mRadioGroup;

    private RadioButton mFriend;
    private RadioButton mFamily;
    private RadioButton mClassmate;

    private Button mUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_person);
        init();

        final Contacts contact = (Contacts) getIntent().getSerializableExtra("update_contact");
        loadInfo(contact);
    }

    private void init()
    {
        imageView = findViewById(R.id.activity_update_image);
        mFullName = findViewById(R.id.activity_update_fullname);
        mPhoneNumber = findViewById(R.id.activity_update_phonenumber);
        mEmail = findViewById(R.id.activity_update_email);
        mRadioGroup = findViewById(R.id.activity_update_radio_group);
        mFriend = findViewById(R.id.activity_update_friend);
        mFamily = findViewById(R.id.activity_update_family);
        mClassmate = findViewById(R.id.activity_update_classmate);
        mUpdate = findViewById(R.id.activity_update_update);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.activity_update_update:

                break;
        }

    }

    private void loadInfo(Contacts contacts)
    {
        Bitmap bitmap = ImageUtility.getImage(contacts.getImagePath());
        if (bitmap != null)
        {
            imageView.setImageBitmap(bitmap);
        }
        else
        {
            imageView.setImageResource(R.drawable.ic_profile);
        }

        mFullName.setText(contacts.getFullName());
        mPhoneNumber.setText(contacts.getPhoneNumber());
        mEmail.setText(contacts.getEmail());

        if (contacts.getCategory().equalsIgnoreCase("Friend"))
            mFriend.setChecked(true);
        if (contacts.getCategory().equalsIgnoreCase("Family"))
            mFamily.setChecked(true);
        if (contacts.getCategory().equalsIgnoreCase("Classmate"))
            mClassmate.setChecked(true);

    }
}
