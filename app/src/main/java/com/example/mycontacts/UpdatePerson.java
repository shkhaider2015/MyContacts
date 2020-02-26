package com.example.mycontacts;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class UpdatePerson extends AppCompatActivity {

    private static final int updateImageChooser = 102;
    private static final String TAG = "UpdatePerson";

    private ImageView imageView;

    private EditText mFullName;
    private EditText mPhoneNumber;
    private EditText mEmail;


    private RadioGroup mRadioGroup;

    private RadioButton mFriend;
    private RadioButton mFamily;
    private RadioButton mClassmate;

    private Button mUpdate;


    private byte[] imageBytes;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_person);
        init();

        final Contacts contact = (Contacts) getIntent().getSerializableExtra("update_contact");
        Log.d(TAG, "onCreate: contact  details : " + contact.getFullName() + " Image : " + contact.getImagePath());
        loadInfo(contact);

        imageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent imgIntent = new Intent();
                imgIntent.setType("image/*");
                imgIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(imgIntent, "Select Profile Picture"), updateImageChooser);
            }
        });
        mUpdate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                updateInfo(contact);
            }
        });
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


        imageBytes = null;
    }


    private void loadInfo(Contacts contacts)
    {
        imageBytes = contacts.getImagePath();
        Bitmap bitmap = null;
        if (imageBytes != null)
        {
            bitmap = ImageUtility.getImage(imageBytes);
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

        if (contacts.getCategory().equalsIgnoreCase("Family"))
            mFamily.setChecked(true);
        else if (contacts.getCategory().equalsIgnoreCase("Classmate"))
            mClassmate.setChecked(true);
        else
            mFriend.setChecked(true);

    }

    private void updateInfo(Contacts contacts)
    {
        String name = mFullName.getText().toString().trim();
        String number = mPhoneNumber.getText().toString().trim();
        String email = mEmail.getText().toString().trim();
        String category = getCategory();



        class UpdateInformation extends AsyncTask<Void, Void, Void>
        {

            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClint
                        .getInstance(UpdatePerson.this)
                        .getAppDatabase()
                        .contactsDao()
                        .update(contacts);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT)
                        .show();

                startActivity(new Intent(UpdatePerson.this, MainActivity.class));
            }
        }

        if(name.isEmpty())
        {
            mFullName.setError("Name is required");
            mFullName.requestFocus();
            return;
        }
        if (number.isEmpty())
        {
            mPhoneNumber.setError("Number is required");
            mPhoneNumber.requestFocus();
            return;
        }

        contacts.setFullName(name);
        contacts.setPhoneNumber(number);
        contacts.setEmail(email);
        contacts.setCategory(category);
        contacts.setImagePath(imageBytes);

        UpdateInformation updateInformation = new UpdateInformation();
        updateInformation.execute();
    }

    private String getCategory()
    {
        String checked = null;
        switch (mRadioGroup.getCheckedRadioButtonId())
        {
            case R.id.activity_update_friend:
                checked = "Friend";
                break;
            case R.id.activity_update_family:
                checked = "Family";
            case R.id.activity_update_classmate:
                checked = "Classmate";
        }

        return checked;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
            Uri selectedPic = null;

            if (requestCode == updateImageChooser && resultCode == RESULT_OK && data != null && data.getData() != null)
            {
                selectedPic = data.getData();

                imageView.setImageURI(selectedPic);

                try {
                    imageBytes = ImageUtility.getBytes(getContentResolver().openInputStream(selectedPic));
                }catch (IOException e)
                {
                    Log.e(TAG, "onActivityResult: ", e);
                }
            }
            else
            {
                Log.d(TAG, "onActivityResult: else run ");
                return;
            }


    }


}
