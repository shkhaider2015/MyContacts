package com.example.mycontacts;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddContact extends AppCompatActivity implements View.OnClickListener
{

    private static final String TAG = "AddContact";
    private static final int CHOOSE_IMAGE = 101;
    private static final int PERMISSION_REQUEST_CODE = 102;

    private ImageView imageView;

    private EditText mFullName;
    private EditText mPhoneNumber;
    private EditText mEmail;


    private RadioGroup mRadioGroup;

    private RadioButton mFriend;
    private RadioButton mFamily;
    private RadioButton mClassmate;

    private Button mSubmit;

    private Uri selectedPic = null;
    private byte[] bytes = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        init();

        mSubmit.setOnClickListener(this);
        imageView.setOnClickListener(this);
    }

    private void init()
    {
        imageView = findViewById(R.id.activity_add_contact_image);
        mFullName = findViewById(R.id.activity_add_contact_fullname);
        mPhoneNumber = findViewById(R.id.activity_add_contact_phonenumber);
        mEmail = findViewById(R.id.activity_add_contact_email);
        mRadioGroup = findViewById(R.id.activity_add_contact_radio_group);
        mFriend = findViewById(R.id.activity_add_contact_friend);
        mFamily = findViewById(R.id.activity_add_contact_family);
        mClassmate = findViewById(R.id.activity_add_contact_classmate);
        mSubmit = findViewById(R.id.activity_add_contact_submit);

    }
    private void saveInfo()
    {

        String fullname = mFullName.getText().toString().trim();
        String phonenumber = mPhoneNumber.getText().toString().trim();
        String email = mEmail.getText().toString().trim();
//        String imagePath = selectedPic.toString();
        String category = getCategory();

        if (fullname.isEmpty())
        {
            mFullName.setError("Name Required");
            mFullName.requestFocus();
            return;
        }
        if (phonenumber.isEmpty())
        {
            mPhoneNumber.setError("Phone Number Required");
            mPhoneNumber.requestFocus();
            return;
        }
        if (phonenumber.length() != 11)
        {
            mPhoneNumber.setError("Phone Number Is Not Correct");
            mPhoneNumber.requestFocus();
            return;
        }


        Log.d(TAG, "saveInfo: Email :: " + mEmail.getText().toString().trim());




        class SaveContacts extends AsyncTask<Void, Void, Void>
        {

            @Override
            protected Void doInBackground(Void... voids) {
                byte[] inputData = null;
                if (selectedPic != null)
                {
                    try
                    {
                        InputStream iStream = getContentResolver().openInputStream(selectedPic);
                        inputData = ImageUtility.getImageBytes(ImageUtility.getResizedBitmap(ImageUtility.getImage(ImageUtility.getBytes(iStream)), 200));
                    }catch (IOException e)
                    {
                        Log.e(TAG, "saveInfo: ", e);
                        Log.d(TAG, "doInBackground: Save Image ERROR ");
                    }
                }

                Contacts contacts = new Contacts();

                if (inputData != null)
                {
                    contacts.setImagePath(inputData);
                }
                contacts.setFullName(fullname);
                contacts.setPhoneNumber(phonenumber);
                contacts.setEmail(email);
                contacts.setCategory(category);

                DatabaseClint
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .contactsDao()
                        .insert(contacts);

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        }

        SaveContacts saveContacts = new SaveContacts();
        saveContacts.execute();

    }


    private String getCategory()
    {
        String category = "";

        int mSelectedId = mRadioGroup.getCheckedRadioButtonId();

        if (mSelectedId == mClassmate.getId())
            category = "Classmate";
        else if (mSelectedId == mFamily.getId())
            category = "Family";
        else if (mSelectedId == mFriend.getId())
            category = "Friend";
        else
            category = "";

        return category;
    }

    private void check(Contacts contacts)
    {
        Log.d(TAG, "check: " + contacts.getFullName());
        Log.d(TAG, "check: " + contacts.getEmail());
        Log.d(TAG, "check: " + contacts.getPhoneNumber());
        Log.d(TAG, "check: " + contacts.getCategory());
    }

    @Override
    public void onClick(View v)
    {

        switch (v.getId())
        {
            case R.id.activity_add_contact_submit:
                saveInfo();
                break;
            case R.id.activity_add_contact_image:
                showImageChooser();
        }
    }


    private void showImageChooser()
    {
        Intent imageIntent = new Intent();
        imageIntent.setType("image/*");
        imageIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(imageIntent, "SELECT PROFILE PICTURE"), CHOOSE_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null)
            {

                selectedPic = data.getData();
                if (selectedPic != null)
                    imageView.setImageURI(selectedPic);



            }
            else
            {
                Log.d(TAG, "AddContact Activity onActivityResult: else run :: -->");
                return;
            }

    }


    private boolean checkPermission()
    {
       int RES = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
       int WES = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

       return RES == PackageManager.PERMISSION_GRANTED && WES == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission()
    {
        ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE }, PERMISSION_REQUEST_CODE );

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE)
        {
            if (grantResults.length > 0)
            {
                boolean RES_accepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean WES_accepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (RES_accepted && WES_accepted)
                {
                    Toast.makeText(this, "READ and WRITE request accepted", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(this, "READ and WRITE request denied", Toast.LENGTH_SHORT).show();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M )
                    {

                    }
                }
            }
        }
    }


}
