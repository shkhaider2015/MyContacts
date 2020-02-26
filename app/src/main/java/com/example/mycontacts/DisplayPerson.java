package com.example.mycontacts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class DisplayPerson extends AppCompatActivity {
    private static final String TAG = "DisplayPerson";
    private static final int permissionrequestcode = 200;

    private View view;

    private ImageView mImageView, mCatImage;
    private Button mCall;
    private TextView mName, mNumber, mEmail, mcategory;
    private Contacts contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_person);
        init();
        contact = (Contacts) getIntent().getSerializableExtra("contact");

        mCall.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                doCall(contact.getPhoneNumber());
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
        mCatImage = findViewById(R.id.display_category_image);

    }

    private void loadInformation(Contacts contacts)
    {
        byte[] imgUri = contacts.getImagePath();
        Bitmap bitmap =  null;

        if (imgUri != null)
        {
            bitmap = ImageUtility.getImage(imgUri);
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
        setmCatImage(contacts.getCategory());

    }

    private void doCall(String number)
    {
        String temp = "tel:92";
        String phoneNumber = temp + number.substring(1, 11);
        Log.d(TAG, "doCall: " + phoneNumber);

        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse(phoneNumber));

        if (checkPermission())
        {
            Log.d(TAG, "doCall: Permission already Granted");
            startActivity(callIntent);
        }
        else
        {
            Log.d(TAG, "doCall: Permission denied requestPermission called !!");
            requestPermission();
        }
    }

    private boolean checkPermission()
    {
        int call = ContextCompat.checkSelfPermission(DisplayPerson.this, Manifest.permission.CALL_PHONE);
        Log.d(TAG, "checkPermission: ----------------||||||||||||||||||||_____________>>> " + Manifest.permission.CALL_PHONE);
        return call == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission()
    {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, permissionrequestcode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == permissionrequestcode)
        {
            if (grantResults.length > 0)
            {
                boolean callAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                if (callAccepted)
                {
                    Toast.makeText(this, "Permissin Granted", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    {
                        if (shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE))
                        {
                            showMessageOkCancel("You need allow call permission", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                                    {
                                        requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, permissionrequestcode);
                                    }
                                }
                            });
                            return;

                        }
                    }
                }

            }
        }
    }

    private void showMessageOkCancel(String Message, DialogInterface.OnClickListener okListner)
    {
        new AlertDialog.Builder(DisplayPerson.this)
                .setMessage(Message)
                .setPositiveButton("OK", okListner)
                .setNegativeButton("Cancel", null)
                .create()
                .show();

    }

    private void setmCatImage(String cat)
    {
        if (cat.equalsIgnoreCase("Family"))
            mCatImage.setImageResource(R.drawable.ic_col_family);
        else if (cat.equalsIgnoreCase("Classmate"))
            mCatImage.setImageResource(R.drawable.ic_col_classmate);
        else
            mCatImage.setImageResource(R.drawable.ic_col_friend);


    }
}
