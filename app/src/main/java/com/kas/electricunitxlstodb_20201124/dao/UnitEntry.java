package com.kas.electricunitxlstodb_20201124.dao;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.kas.electricunitxlstodb_20201124.ElectricalUnit;

@Entity(tableName = "units")
public class UnitEntry implements ElectricalUnit {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public String title;
    public String description;
    public String location;

    public UnitEntry(int id, String location, String title, String description) {
        this.id = id;
        this.location = location;
        this.title = title;
        this.description = description;
    }

    @Ignore
    public UnitEntry(String location, String title, String description) {
        this.location = location;
        this.title = title;
        this.description = description;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }
}
