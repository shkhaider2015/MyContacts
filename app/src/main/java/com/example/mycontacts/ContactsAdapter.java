package com.example.mycontacts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder> {


    private Context mCTX;
    private List<Contacts> contactsList;

    public ContactsAdapter(Context mCTX, List<Contacts> contactsList)
    {
        this.mCTX = mCTX;
        this.contactsList = contactsList;
    }
    @NonNull
    @Override
    public ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater
                .from(mCTX)
                .inflate(R.layout.recyclerview_contacts, parent, false);

        return new ContactsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsViewHolder holder, int position)
    {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ContactsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        public ContactsViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
