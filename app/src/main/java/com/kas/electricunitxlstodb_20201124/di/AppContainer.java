package com.kas.electricunitxlstodb_20201124.di;

import android.content.Context;
import android.util.Log;

import com.kas.electricunitxlstodb_20201124.UnitRepository;

public class AppContainer {

    private static final String TAG = "#_AppContainer";
    private Context context;
    private UnitLocalDataSource unitLocalDataSource;
    private UnitRemoteDataSource unitRemoteDataSource;
    public UnitRepository myRepository;
    // LoginContainer will be null when the user is NOT in the login flow
    public SharedContainer sharedContainer;

    public AppContainer() {
        Log.d(TAG, "AppContainer: constructor invoked");
    }

    public void initSharedContainer(Context context) {
        this.context = context;
        unitLocalDataSource = new UnitLocalDataSource(context);
        unitRemoteDataSource = new UnitRemoteDataSource(context);
        myRepository = new UnitRepository(unitLocalDataSource, unitRemoteDataSource);

    }

}
