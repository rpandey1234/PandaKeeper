package com.example.rahul.todoapp;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.query.Select;

import java.sql.Date;
import java.util.List;


public class Task extends Model implements Parcelable {
    @Column(name="Name")
    public String name;

    @Column(name = "Detail")
    public String detail;

    @Column(name = "DueDate")
    public Date dueDate;

    @Column(name = "Color")
    public int color;

    public Task() {
        super();
    }

    public Task(String name, String detail, Date dueDate, int color) {
        this.name = name;
        this.detail = detail;
        this.dueDate = dueDate;
        this.color = color;
    }

    public static List<Task> getAll() {
        return new Select()
                .from(Task.class)
                .orderBy("dueDate ASC")
                .execute();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }
}
