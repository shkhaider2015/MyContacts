package com.example.mycontacts;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
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

import java.io.FileNotFoundException;
import java.io.IOException;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddContact extends AppCompatActivity implements View.OnClickListener
{

    private static final String TAG = "AddContact";
    private static final int CHOOSE_IMAGE = 101;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        Contacts contactsObject = new Contacts();

        String fullname = mFullName.getText().toString().trim();
        String phonenumber = mPhoneNumber.getText().toString().trim();

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

        contactsObject.setFullName(fullname);
        contactsObject.setPhoneNumber(phonenumber);
        contactsObject.setEmail(mEmail.getText().toString().trim());
        Log.d(TAG, "saveInfo: Email :: " + mEmail.getText().toString().trim());
        contactsObject.setCategory(getCategory());
        if (selectedPic != null)
        {
            contactsObject.setImagePath(selectedPic.toString());
        }


        check(contactsObject);

    }

    private void loadToDatabase(Contacts contacts)
    {
        
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
                break;
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

        if (requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK & data != null & data.getData() != null)
        {
            selectedPic = data.getData();
            Bitmap bitmap = null;
            try {
                Log.d(TAG, "onActivityResult: data :: " + data);
                Log.d(TAG, "onActivityResult: data.getData() :: " + data.getData());
                Log.d(TAG, "onActivityResult: resultCode  :: " + resultCode);



                if (Build.VERSION.SDK_INT < 28)
                {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedPic);
                }
                else
                {
                    ImageDecoder.Source source = ImageDecoder.createSource(getContentResolver(), selectedPic);
                    bitmap = ImageDecoder.decodeBitmap(source);
                }


            }catch (NullPointerException e)
            {
                Log.e(TAG, "onActivityResult: NullPointerException  ", e);
            }catch (FileNotFoundException e)
            {
                Log.e(TAG, "onActivityResult: FileNotFoundException ", e);
            }catch (IOException e)
            {
                Log.e(TAG, "onActivityResult: IOException ", e);
            }

            imageView.setImageBitmap(bitmap);

        }
    }


}
