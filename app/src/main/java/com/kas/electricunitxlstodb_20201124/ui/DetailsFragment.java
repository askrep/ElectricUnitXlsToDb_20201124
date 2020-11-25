package com.kas.electricunitxlstodb_20201124.ui;

import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.kas.electricunitxlstodb_20201124.AppExecutors;
import com.kas.electricunitxlstodb_20201124.DetailsViewModelFactory;
import com.kas.electricunitxlstodb_20201124.R;
import com.kas.electricunitxlstodb_20201124.dao.AppDatabase;
import com.kas.electricunitxlstodb_20201124.dao.UnitEntry;
import com.kas.electricunitxlstodb_20201124.databinding.DetailsFragmentBinding;
import com.kas.electricunitxlstodb_20201124.ui.dummy.DummyContent;

import java.util.List;

public class DetailsFragment extends Fragment {

    public static final String EXTRA_UNIT_ID = "extraUnitId";
    private static final int DEFAULT_UNIT_ID = -1;
    private static final String LOG_TAG = "# DETAILS FRAGMENT";

    private DetailsViewModel viewModel;
    private DetailsFragmentBinding binding;
    private List<DummyContent.DummyItem> ITEMS = DummyContent.ITEMS;
    private AppDatabase database;
    private int unitId = DEFAULT_UNIT_ID;


    public static DetailsFragment newInstance() {
        return new DetailsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DetailsFragmentBinding.inflate(inflater, container, false);
        View inflate = binding.getRoot();
        Context context = getContext();
        database = AppDatabase.getInstance(context);

        Intent intent = getActivity().getIntent();

        if (null != intent && intent.hasExtra(EXTRA_UNIT_ID)) {

            unitId = intent.getIntExtra(EXTRA_UNIT_ID, DEFAULT_UNIT_ID);
            DummyContent.DummyItem dummyItem = ITEMS.get(unitId);
            binding.unit.setText(dummyItem.title);
            binding.unitDescription.setText(dummyItem.description);
        }

        Button save = binding.detailsSaveBtn;
        save.setOnClickListener(view -> onSaveButtonClicked());
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // viewModel = new ViewModelProvider(this).get(DetailsViewModel.class);
        DetailsViewModelFactory viewModelFactory = new DetailsViewModelFactory(database, unitId);
        viewModel = new ViewModelProvider(this, viewModelFactory).get(DetailsViewModel.class);
        // TODO: Use the ViewModel

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void onSaveButtonClicked() {
        String title = binding.unit.getText().toString();
        String description = binding.unitDescription.getText().toString();

        final UnitEntry unitEntry = new UnitEntry(title, description);

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (unitId == DEFAULT_UNIT_ID) {
                    database.unitDao().insertUnit(unitEntry);
                } else {
                    unitEntry.setId(unitId);
                    database.unitDao().updateUnit(unitEntry);
                }

            }
        });
    }
}