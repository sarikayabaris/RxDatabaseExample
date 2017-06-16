package com.mobsmile.rxdb.db.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * This file created by barissarikaya on 6/14/17.
 */

@Entity(nameInDb = "student")
public class Student {

    @Id(autoincrement = true)
    private long id;

    private String name;
    private String surname;

    public Student(int id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

    public Student(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    @Generated(hash = 971965475)
    public Student(long id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

    @Generated(hash = 1556870573)
    public Student() {
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {
        return name + " " + surname;
    }

    public void setId(long id) {
        this.id = id;
    }
}
