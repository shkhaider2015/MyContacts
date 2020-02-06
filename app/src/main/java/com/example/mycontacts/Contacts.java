package com.example.mycontacts;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Contacts implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "fullname")
    private String fullName;


    @ColumnInfo(name = "phonenumber")
    private String phoneNumber;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "category")
    private String category;


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null)
            email = "";

        this.email = email;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        if (category == null)
            category = "";

        this.category = category;
    }

    public int getId() {
        return id;
    }
}
