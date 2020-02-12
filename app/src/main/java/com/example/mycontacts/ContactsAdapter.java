package com.example.mycontacts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
        Contacts contacts = contactsList.get(position);

        holder.textView.setText(contacts.getFullName());

    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }

    class ContactsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        ImageView imageView;
        TextView textView;

        public ContactsViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.recyclerview_contacts_image);
            textView = itemView.findViewById(R.id.recyclerview_contacts_name);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {

        }
    }
}
