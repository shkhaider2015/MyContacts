package com.example.mycontacts;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.Serializable;

import static android.content.ContentValues.TAG;

public class DisplayPerson extends Fragment implements View.OnClickListener
{
    private Button addButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.display_person, container, false);
        init(view);


        Contacts contacts = (Contacts) getArguments().getSerializable("contact");


        Log.d(TAG, "onCreateView: Name :: -->> " + contacts.getFullName());

        return view;
    }
    private void init(View view)
    {
        addButton.setVisibility(View.GONE);

    }

    @Override
    public void onClick(View v) {

    }
}
