package com.kas.electricunitxlstodb_20201124.di;

import android.content.Context;

import com.kas.electricunitxlstodb_20201124.UnitRepository;

public class AppContainer {

    private Context context;
    private UnitLocalDataSource unitLocalDataSource;
    private UnitRemoteDataSource unitRemoteDataSource = new UnitRemoteDataSource();

    public UnitRepository repository = new UnitRepository(unitRemoteDataSource);


    // LoginContainer will be null when the user is NOT in the login flow
    public SharedContainer sharedContainer;

    public void setContext(Context context) {
         this.context = context;
        unitLocalDataSource= new UnitLocalDataSource(context);
        repository.setUnitLocalDataSource(unitLocalDataSource);
    }

}
