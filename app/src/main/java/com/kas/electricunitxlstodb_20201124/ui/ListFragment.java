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
import com.kas.electricunitxlstodb_20201124.dao.UnitEntry;

import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class ListFragment extends Fragment implements UnitRecyclerViewAdapter.ItemClickListener {

    private static final String LOG_TAG = "# LIST FRAGMENT";
    private ListViewModel listViewModel;
    private MainViewModel mainViewModel;
   // private FragmentUnitListBinding binding;
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
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        listViewModel = new ViewModelProvider(this).get(ListViewModel.class);

        Log.d(LOG_TAG, "OnCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //binding = FragmentUnitListBinding.inflate(inflater, container, false);
        View view = inflater.inflate(R.layout.fragment_unit_list, container, false);
        //View view = binding.getRoot();

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

        Log.d(LOG_TAG, "OnCreateView");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listViewModel.getUnitsLiveData().observe(getViewLifecycleOwner(), (List<UnitEntry> units) -> {
            Log.d(LOG_TAG, "Updating list of tasks from LiveData in ViewModel");
            adapter.setUnits(units);
        });

        Log.d(LOG_TAG, "OnActivityCreated");
    }

    @Override
    public void onItemClickListener(int unitId) {
        Log.d(LOG_TAG, "ON ITEM CLICKED, ID==" + unitId);
        Intent intent = new Intent(getActivity().getBaseContext(), DetailsActivity.class);

        intent.putExtra(DetailsFragment.EXTRA_UNIT_ID, unitId);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}