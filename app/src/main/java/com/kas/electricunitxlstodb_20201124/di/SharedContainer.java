package com.kas.electricunitxlstodb_20201124.di;

import com.kas.electricunitxlstodb_20201124.UnitRepository;

public class SharedContainer {

    /** Container with Login-specific dependencies*/
    private final UnitRepository unitRepository;
    public SharedViewModelFactory sharedViewModelFactory;

    public SharedContainer(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
        sharedViewModelFactory = new SharedViewModelFactory(unitRepository);
    }
}
