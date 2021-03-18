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

    @Query("Select * From units Where location Like :text OR cabinet Like :text OR title Like :text OR description Like :text")
    LiveData<List<UnitEntry>> loadUnitListFiltered(String text);

    @Query("Select * From units Where id = :unitId")
    LiveData<UnitEntry> loadUnitById(int unitId);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertUnit(UnitEntry unitEntry); // INSERT

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateUnit(UnitEntry unitEntry); // UPDATE

    @Query("Delete From units Where id = :unitId")
    void deleteUnit(int unitId);

    @Query("DELETE FROM units")
    void deleteAll();
}
