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

    @Ignore
    private boolean isExpanded;
    @Ignore
    private int type;
    @Ignore
    public static final int VIEW_TYPE_SELECTED = 1;
    @Ignore
    public static final int VIEW_TYPE_SIMPLE = 0;

    public UnitEntry(int id, String location, String cabinet, String title, String description) {
        this.id = id;
        this.location = location;
        this.cabinet = cabinet;
        this.title = title;
        this.description = description;
        type = VIEW_TYPE_SIMPLE;
    }

    @Ignore
    public UnitEntry(String location, String cabinet, String title, String description) {
        this.location = location;
        this.cabinet = cabinet;
        this.title = title;
        this.description = description;
    }

    @Ignore
    public UnitEntry() {
        this.location = null;
        this.cabinet = null;
        this.title = null;
        this.description = null;
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

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
        type = isExpanded ? VIEW_TYPE_SELECTED : VIEW_TYPE_SIMPLE;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
