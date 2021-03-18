package com.kas.electricunitxlstodb_20201124.dao;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "units")
public class UnitEntry {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public String location;
    public String cabinet;
    public String title;
    public String description;

    public UnitEntry(int id, String location, String cabinet, String title, String description) {
        this.id = id;
        this.location = location;
        this.cabinet = cabinet;
        this.title = title;
        this.description = description;
    }

    @Ignore
    public UnitEntry(String location, String cabinet, String title, String description) {
        this.location = location;
        this.cabinet = cabinet;
        this.title = title;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCabinet() {
        return cabinet;
    }

    public void setCabinet(String cabinet) {
        this.cabinet = cabinet;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
