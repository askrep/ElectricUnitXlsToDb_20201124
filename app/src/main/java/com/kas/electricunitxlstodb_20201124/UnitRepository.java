package com.kas.electricunitxlstodb_20201124;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.kas.electricunitxlstodb_20201124.dao.UnitDao;
import com.kas.electricunitxlstodb_20201124.dao.UnitEntry;
import com.kas.electricunitxlstodb_20201124.di.UnitLocalDataSource;
import com.kas.electricunitxlstodb_20201124.di.UnitRemoteDataSource;

import java.util.List;

public class UnitRepository {
    private static final String TAG = "#_UNIT_REPOSITORY";
    private static Repository instance;

    private UnitDao unitDao;

    private LiveData<List<UnitEntry>> unitsLiveData;
    private Application application;

    public UnitRepository(UnitLocalDataSource unitLocalDataSource, UnitRemoteDataSource unitRemoteDataSource) {
       //TODO
    }


}
