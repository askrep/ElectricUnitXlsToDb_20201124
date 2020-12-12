package com.kas.electricunitxlstodb_20201124.ui;

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
import com.kas.electricunitxlstodb_20201124.R;
import com.kas.electricunitxlstodb_20201124.dao.UnitEntry;
import com.kas.electricunitxlstodb_20201124.databinding.DetailsFragmentBinding;

public class DetailsFragment extends Fragment {
    
    private SharedViewModel sharedViewModel;
    
    public static final String EXTRA_UNIT_ID = "extraUnitId";
    private static final int DEFAULT_UNIT_ID = -1;
    private static final String TAG = "#_DETAILS_FRAGMENT";
    private int unitId = DEFAULT_UNIT_ID;
    
    private DetailsViewModel detailsViewModel;
    private DetailsFragmentBinding binding;
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
        detailsButton = binding.detailsButton;
        deleteButton = binding.deleteButton;
        detailsButton.setOnClickListener(view -> onCommonButtonClicked());
        deleteButton.setOnClickListener(view -> onDeleteButtonClicked());
        
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
        deleteButton.setClickable(true);
        
        if (unitId == DEFAULT_UNIT_ID) {
            unitId = intent.getIntExtra(EXTRA_UNIT_ID, DEFAULT_UNIT_ID);
            Log.d(TAG, "ID==" + unitId);
            
            sharedViewModel.getUnitEntry(unitId).observe(getViewLifecycleOwner(), unitEntry -> {
                Log.d(TAG, "On Changed unitEntry");
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
        final UnitEntry unitEntry = new UnitEntry(title, description);
        Log.d(TAG, "onCommonButtonClicked: " + title + " " + description + " id=" + unitId + " unit=" + unitEntry);
        
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