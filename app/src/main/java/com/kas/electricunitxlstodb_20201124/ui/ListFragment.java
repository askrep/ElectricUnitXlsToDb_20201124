package com.kas.electricunitxlstodb_20201124.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.kas.electricunitxlstodb_20201124.dao.AppDatabase;

/**
 * A fragment representing a list of Items.
 */
public class ListFragment extends Fragment implements UnitRecyclerViewAdapter.ItemClickListener {

    private static final String LOG_TAG = "#_LIST_FRAGMENT";
    private ListViewModel listViewModel;
    private SharedViewModel sharedViewModel;

    private AppDatabase database;

    private static final String ARG_COLUMN_COUNT = "column-count";

    private int mColumnCount = 1;
    private RecyclerView recyclerView;
    private UnitRecyclerViewAdapter adapter;

    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;

    public ListFragment() {
        super(R.layout.fragment_unit_list);
        Log.d(LOG_TAG, "CONSTRUCTOR");
    }

    public static ListFragment newInstance(int columnCount) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        sharedViewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);
        listViewModel = new ViewModelProvider(getActivity()).get(ListViewModel.class);

        Log.d(LOG_TAG, "OnCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_unit_list, container, false);

        database = AppDatabase.getInstance(getContext());

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            adapter = new UnitRecyclerViewAdapter(this.getContext(), this);
            recyclerView.setAdapter(adapter);

        }
        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), VERTICAL);
        recyclerView.addItemDecoration(decoration);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        sharedViewModel.getFilterLiveData().observe(getActivity(), filter -> {
            sharedViewModel.setFilterLiveData(filter);

            Log.d(LOG_TAG, "onActivityCreated: setFilter " + filter);
        });

        sharedViewModel.getFilteredUnitsLiveData().observe(getActivity(), filteredUnits -> {
            adapter.setUnits(filteredUnits);

            Log.d(LOG_TAG, "onActivityCreated: adapter.setUnits " + filteredUnits.size());
        });

        Log.d(LOG_TAG, "OnActivityCreated");
    }

    @Override
    public void onItemClickListener(int unitId) {
        Intent intent = new Intent(getActivity().getBaseContext(), DetailsActivity.class);
        intent.putExtra(DetailsFragment.EXTRA_UNIT_ID, unitId);
        startActivity(intent);

        Log.d(LOG_TAG, "ON_ITEM_CLICKED, ID==" + unitId);
    }
}