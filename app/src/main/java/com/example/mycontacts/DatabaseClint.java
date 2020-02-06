package com.example.mycontacts;

import android.content.Context;

import androidx.room.Room;

public class DatabaseClint {

    private Context mCTX;
    private static DatabaseClint mInstance;
    private AppDatabase appDatabase;

    private DatabaseClint(Context mCTX)
    {
        this.mCTX = mCTX;

        // Here ContactsDatabase is the name of the Database
        appDatabase = Room
                .databaseBuilder(mCTX, AppDatabase.class, "Contactsdatabase")
                .build();

    }

    public static synchronized DatabaseClint getInstance(Context mCTX)
    {
        if (mInstance == null)
        {
            mInstance = new DatabaseClint(mCTX);
        }

        return mInstance;
    }


    public AppDatabase getAppDatabase()
    {
        return appDatabase;
    }


}
