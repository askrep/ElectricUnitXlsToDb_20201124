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
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import com.kas.electricunitxlstodb_20201124.AppExecutors;
import com.kas.electricunitxlstodb_20201124.R;
import com.kas.electricunitxlstodb_20201124.dao.AppDatabase;
import com.kas.electricunitxlstodb_20201124.dao.UnitEntry;
import com.kas.electricunitxlstodb_20201124.databinding.DetailsFragmentBinding;

public class DetailsFragment extends Fragment {

    public static final String EXTRA_UNIT_ID = "extraUnitId";
    private static final int DEFAULT_UNIT_ID = -1;
    private static final String TAG = "#_DETAILS_FRAGMENT";

    private DetailsViewModel detailsViewModel;
    private DetailsFragmentBinding binding;
    private AppDatabase database;
    private int unitId = DEFAULT_UNIT_ID;

    private Button detailsButton;
    private Button deleteButton;
    private boolean isChanging;
    private SharedViewModel sharedViewModel;

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

        isChanging = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean(getString(R.string.pref_edit_enable), false);
        if (isChanging) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            Log.d(TAG, "onCreate: NIGHT MODE");
        }
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Intent intent = getActivity().getIntent();
        sharedViewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);
        if (null != intent && intent.hasExtra(EXTRA_UNIT_ID)) {
            unitSelectedFromList(intent);
        }
    }

    private void unitSelectedFromList(Intent intent) {
        detailsButton.setText(R.string.update_button);
        deleteButton.setVisibility(View.VISIBLE);

        if (isChanging) {
            deleteButton.setClickable(true);
        } else {
            deleteButton.setClickable(false);
            detailsButton.setClickable(false);
            deleteButton.setActivated(false);
            detailsButton.setActivated(false);
            deleteButton.setBackgroundColor(getResources().getColor(R.color.gray_deactivated));
            detailsButton.setBackgroundColor(getResources().getColor(R.color.gray_deactivated));
        }

        if (unitId == DEFAULT_UNIT_ID) {
            unitId = intent.getIntExtra(EXTRA_UNIT_ID, DEFAULT_UNIT_ID);

            sharedViewModel.getUnitEntry(unitId).observe(getViewLifecycleOwner(), unitEntry -> {
                if (null != unitEntry) {
                    fillingUi(unitEntry);
                }
            });
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
        UnitEntry unitEntry = new UnitEntry(title, description);

        AppExecutors.getInstance().diskIO().execute(() -> {
            if (unitId == DEFAULT_UNIT_ID) {
                sharedViewModel.insertUnit(unitEntry);
            } else {
                unitEntry.setId(unitId);
                sharedViewModel.updateUnit(unitEntry);
            }
            getActivity().finish();
        });
    }

    public void onDeleteButtonClicked() {
        AppExecutors.getInstance().diskIO().execute(() -> {
            sharedViewModel.deleteUnit(unitId); //database.unitDao().deleteUnit(unitId);
            getActivity().finish();
        });
    }
}