package com.kas.electricunitxlstodb_20201124.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kas.electricunitxlstodb_20201124.DetailsActivity;
import com.kas.electricunitxlstodb_20201124.R;
import com.kas.electricunitxlstodb_20201124.viewmodels.SharedViewModel;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ListFragment extends Fragment implements UnitRecyclerViewAdapter.UnitClickListener {

    private static final String LOG_TAG = "#_LIST_FRAGMENT";

    private SharedViewModel sharedViewModel;
    private RecyclerView recyclerView;
    private UnitRecyclerViewAdapter adapter;

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;

    @Inject
    public ListFragment() {    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        sharedViewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_unit_list, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            adapter = new UnitRecyclerViewAdapter(this.getContext(), this);
            adapter.setUnits(sharedViewModel.getAllUnitsLiveData().getValue());
            recyclerView.setAdapter(adapter);
        }
        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), VERTICAL);
        recyclerView.addItemDecoration(decoration);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sharedViewModel.getUnitsLiveData().observe(getActivity(), filteredUnits -> {
            adapter.setUnits(filteredUnits);
        });
    }

    @Override
    public void onUnitClick(int unitId) {
        Intent intent = new Intent(getActivity().getBaseContext(), DetailsActivity.class);
        intent.putExtra(DetailsFragment.EXTRA_UNIT_ID, unitId);
        startActivity(intent);
    }
}