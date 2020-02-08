package com.example.mycontacts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class AddContact extends AppCompatActivity {

    @BindView(R.id.activity_add_contact_image)
    ImageView imageView;

    @BindViews({R.id.activity_add_contact_fullname, R.id.activity_add_contact_phonenumber, R.id.activity_add_contact_email})
    EditText mFullName, mPhoneNumber, mEmail;

    @BindView(R.id.activity_add_contact_radio_group)
    RadioGroup mRadioGroup;

    @BindViews({R.id.activity_add_contact_friend, R.id.activity_add_contact_family, R.id.activity_add_contact_classmate})
    RadioButton mFriend, mFamily, mClassMate;

    @BindView(R.id.activity_add_contact_submit)
    Button mSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        ButterKnife.bind(this);
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
        contactsObject.setCategory(getCategory());

    }

    private String getCategory()
    {
        String category = "";

        int mSelectedId = mRadioGroup.getCheckedRadioButtonId();

        if (mSelectedId == mClassMate.getId())
            category = "Classmate";
        else if (mSelectedId == mFamily.getId())
            category = "Family";
        else
            category = "Friend";

        return category;
    }
}
