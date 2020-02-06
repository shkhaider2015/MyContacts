package com.example.mycontacts;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContactsDao {

    @Query("SELECT * FROM contacts")
    List<Contacts> getAll();

    @Insert
    void insert(Contacts contact);

    @Update
    void update(Contacts contact);

    @Delete
    void delete(Contacts contact);
}
