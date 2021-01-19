package com.kas.electricunitxlstodb_20201124.di;

import com.kas.electricunitxlstodb_20201124.UnitRepository;

public class SharedViewModelFactory implements Factory {

    private final UnitRepository unitRepository;

    public SharedViewModelFactory(UnitRepository unitRepository) {

        this.unitRepository = unitRepository;
    }

    @Override
    public MySharedViewModel create() {
        return new MySharedViewModel(unitRepository);
    }
}
