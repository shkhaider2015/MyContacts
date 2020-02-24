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

    @Query("SELECT * FROM contacts where category='Friend' ")
    List<Contacts> getFriends();

    @Query("SELECT * FROM contacts where category='Family' ")
    List<Contacts> getFamily();

    @Query("SELECT * FROM contacts where category='Classmate' ")
    List<Contacts> getClassmate();

    @Insert
    void insert(Contacts contact);

    @Update
    void update(Contacts contact);

    @Delete
    void delete(Contacts contact);
}
