package com.example.mycontacts;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder> {

    private static final String TAG = "ContactsAdapter";

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
        byte[] outputData = null;

        holder.textView.setText(contacts.getFullName());


        outputData = contacts.getImagePath();

        if (outputData != null)
        {
            holder.imageView.setImageBitmap(ImageUtility.getImage(outputData));
        }

        holder.menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(mCTX, holder.menuIcon);
                popup.inflate(R.menu.recyclerview_menu);

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId())
                        {
                            case R.id.menu_item_update:
                                //updarte here
                                break;
                            case R.id.menu_item_delete:
                                // delete
                                break;
                        }

                        return false;
                    }


                });

                popup.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }

    class ContactsViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView, menuIcon;
        TextView textView;
        LinearLayout mLinearLayout;

        public ContactsViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.recyclerview_contacts_image);
            menuIcon = itemView.findViewById(R.id.recyclerview_contacts_menu);
            textView = itemView.findViewById(R.id.recyclerview_contacts_name);
            mLinearLayout = itemView.findViewById(R.id.recyclerview_contacts_linear_layout);


            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Contacts contacts = contactsList.get(getAdapterPosition());

                    Intent intent = new Intent(mCTX, DisplayPerson.class);
                    intent.putExtra("contact", contacts);

                    mCTX.startActivity(intent);
                }
            });


        }


    }

    


}
