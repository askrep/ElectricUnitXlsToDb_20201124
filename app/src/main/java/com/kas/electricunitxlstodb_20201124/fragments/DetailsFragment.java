package com.kas.electricunitxlstodb_20201124.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.kas.electricunitxlstodb_20201124.R;
import com.kas.electricunitxlstodb_20201124.dao.UnitEntry;
import com.kas.electricunitxlstodb_20201124.data.PreferencesUtil;
import com.kas.electricunitxlstodb_20201124.databinding.DetailsFragmentBinding;
import com.kas.electricunitxlstodb_20201124.viewmodels.PreferencesViewModel;
import com.kas.electricunitxlstodb_20201124.viewmodels.SharedViewModel;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DetailsFragment extends Fragment {

    public static final String EXTRA_UNIT_ID = "extraUnitId";
    private static final int DEFAULT_UNIT_ID = -1;
    private int unitId = DEFAULT_UNIT_ID;

    private static final String TAG = "#_DETAILS_FRAGMENT";
    private SharedViewModel sharedViewModel;
    private DetailsFragmentBinding binding;

    private boolean isEditMode;
    private Button detailsButton;
    private Button deleteButton;
    private EditText location;
    private EditText title;
    private EditText description;
    private EditText cabinet;

    @Inject
    public DetailsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DetailsFragmentBinding.inflate(inflater, container, false);
        View inflate = binding.getRoot();

        detailsButton = binding.detailsButton;
        deleteButton = binding.deleteButton;
        detailsButton.setOnClickListener(view -> onCommonButtonClicked());
        deleteButton.setOnClickListener(view -> onDeleteButtonClicked());
        location = binding.location;
        cabinet = binding.cabinet;
        title = binding.title;
        description = binding.thirdField;

        return inflate;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Intent intent = getActivity().getIntent();

        sharedViewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);
        PreferencesViewModel preferencesViewModel = new ViewModelProvider(getActivity()).get(PreferencesViewModel.class);

        isEditMode = PreferencesUtil.isEditMode(this.getContext());

        if (null != intent && intent.hasExtra(EXTRA_UNIT_ID)) {
            unitSelectedFromList(intent);
        }
    }

    private void unitSelectedFromList(Intent intent) {
        detailsButton.setText(R.string.update_button);
        deleteButton.setVisibility(View.VISIBLE);

        if (isEditMode) {
            isEditModeConfig();
        } else {
            isOnlyReadModeConfig();
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

    private void isEditModeConfig() {
        location.setEnabled(true);
        cabinet.setEnabled(true);
        title.setEnabled(true);
        description.setEnabled(true);
        deleteButton.setClickable(true);
        detailsButton.setClickable(true);
        Log.d(TAG, "isEditModeConfig: on");
    }

    private void isOnlyReadModeConfig() {
        location.setEnabled(false);
        cabinet.setEnabled(false);
        title.setEnabled(false);
        description.setEnabled(false);
        //description.setTextColor(Color.BLUE);
        deleteButton.setClickable(false);
        detailsButton.setClickable(false);
        //todo replace deprecated method
        deleteButton.setBackgroundColor(getResources().getColor(R.color.gray_deactivated));
        detailsButton.setBackgroundColor(getResources().getColor(R.color.gray_deactivated));
        Log.d(TAG, "isOnlyReadModeConfig: off");
    }

    private void fillingUi(UnitEntry unitEntry) {
        if (unitEntry == null) return;
        location.setText(unitEntry.getLocation());
        cabinet.setText(unitEntry.getCabinet());
        title.setText(unitEntry.getTitle());
        description.setText(unitEntry.getDescription());
    }

    public void onCommonButtonClicked() {
        String entryLocation = location.getText().toString();
        String entryCabinet = cabinet.getText().toString();
        String entryTitle = title.getText().toString();
        String entryDescription = description.getText().toString();
        UnitEntry unitEntry = new UnitEntry(entryLocation, entryCabinet, entryTitle, entryDescription);

        if (unitId == DEFAULT_UNIT_ID) {
            sharedViewModel.insertUnit(unitEntry);
        } else {
            unitEntry.setId(unitId);
            sharedViewModel.updateUnit(unitEntry);
        }
        getActivity().finish();
    }

    public void onDeleteButtonClicked() {
        sharedViewModel.deleteUnit(unitId); //database.unitDao().deleteUnit(unitId);
        getActivity().finish();
    }
}