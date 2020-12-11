package com.kas.electricunitxlstodb_20201124.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.kas.electricunitxlstodb_20201124.AppExecutors;
import com.kas.electricunitxlstodb_20201124.DetailsViewModelFactory;
import com.kas.electricunitxlstodb_20201124.R;
import com.kas.electricunitxlstodb_20201124.dao.AppDatabase;
import com.kas.electricunitxlstodb_20201124.dao.UnitEntry;
import com.kas.electricunitxlstodb_20201124.databinding.DetailsFragmentBinding;

public class DetailsFragment extends Fragment {

    public static final String EXTRA_UNIT_ID = "extraUnitId";
    private static final int DEFAULT_UNIT_ID = -1;
    private static final String LOG_TAG = "#_DETAILS_FRAGMENT";

    private DetailsViewModel detailsViewModel;
    private DetailsFragmentBinding binding;
    private AppDatabase database;
    private int unitId = DEFAULT_UNIT_ID;

    private Button detailsButton;
    private Button deleteButton;
    private boolean isChanging;

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

        detailsButton = binding.detailsButton;
        deleteButton = binding.deleteButton;

        detailsButton.setOnClickListener(view -> onCommonButtonClicked());
        deleteButton.setOnClickListener(view -> onDeleteButtonClicked());
        Log.d(LOG_TAG, "On Create View");
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Intent intent = getActivity().getIntent();

        if (null != intent && intent.hasExtra(EXTRA_UNIT_ID)) {

            detailsButton.setText(R.string.update_button);
            deleteButton.setVisibility(View.VISIBLE);
            deleteButton.setClickable(true);

            if (unitId == DEFAULT_UNIT_ID) {
                unitId = intent.getIntExtra(EXTRA_UNIT_ID, DEFAULT_UNIT_ID);
                Log.d(LOG_TAG, "ID==" + unitId);
                DetailsViewModelFactory viewModelFactory = new DetailsViewModelFactory(database, unitId);
                detailsViewModel = new ViewModelProvider(this, viewModelFactory).get(DetailsViewModel.class);

                detailsViewModel.getUnitEntry().observe(getViewLifecycleOwner(), unitEntry -> {
                    Log.d(LOG_TAG, "On Changed unitEntry");
                    if (null != unitEntry) {
                        fillingUi(unitEntry);
                    }
                });
            }
        }
    }

    private void fillingUi(UnitEntry unitEntry) {
        if (unitEntry == null) return;
        String title = unitEntry.getTitle();
        String description = unitEntry.getDescription();
        binding.title.setText(title);
        binding.unitDescription.setText(description);
    }

    public void onCommonButtonClicked() {
        String title = binding.title.getText().toString();
        String description = binding.unitDescription.getText().toString();
        final UnitEntry unitEntry = new UnitEntry(title, description);

        AppExecutors.getInstance().diskIO().execute(() -> {
            if (unitId == DEFAULT_UNIT_ID) {
                database.unitDao().insertUnit(unitEntry);
            } else {
                unitEntry.setId(unitId);
                database.unitDao().updateUnit(unitEntry);
            }
            getActivity().finish();
        });
    }

    public void onDeleteButtonClicked() {
        AppExecutors.getInstance().diskIO().execute(() -> {
            Log.d(LOG_TAG, "On Delete Clicked, ID==" + unitId);
            database.unitDao().deleteUnit(unitId);
            getActivity().finish();
        });
    }
}