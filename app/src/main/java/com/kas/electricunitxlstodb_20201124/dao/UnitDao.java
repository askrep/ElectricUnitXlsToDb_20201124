package com.kas.electricunitxlstodb_20201124.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UnitDao {

    @Query("Select * From units")
    LiveData<List<UnitEntry>> selectAll(); // SELECT ALL

    @Query("Select * From units Where title Like :text OR description Like :text")
    LiveData<List<UnitEntry>> loadUnitListFiltered(String text);

    @Query("Select * From units Where id = :unitId")
    LiveData<UnitEntry> loadUnitById(int unitId);

    @Insert
    void insertUnit(UnitEntry unitEntry); // INSERT

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateUnit(UnitEntry unitEntry); // UPDATE

    @Query("Delete From units Where id = :unitId")
    void deleteUnit(int unitId);
}
