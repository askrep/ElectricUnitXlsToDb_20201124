package com.kas.electricunitxlstodb_20201124.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kas.electricunitxlstodb_20201124.DetailsActivity;
import com.kas.electricunitxlstodb_20201124.dao.AppDatabase;
import com.kas.electricunitxlstodb_20201124.dao.UnitEntry;
import com.kas.electricunitxlstodb_20201124.databinding.FragmentUnitListBinding;
import com.kas.electricunitxlstodb_20201124.ui.recycler.UnitRecyclerViewAdapter;

import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class ListFragment extends Fragment implements UnitRecyclerViewAdapter.ItemClickListener {

    private static final String LOG_TAG = "# LIST FRAGMENT";
    private ListViewModel listViewModel;
    private FragmentUnitListBinding binding;
    private AppDatabase database;

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private RecyclerView recyclerView;
    private UnitRecyclerViewAdapter adapter;

    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUnitListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        database = AppDatabase.getInstance(getContext());
        // Set the adapter
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
        listViewModel = new ViewModelProvider(this).get(ListViewModel.class);
        // TODO: Use the ViewModel
        listViewModel.getTasks().observe(getViewLifecycleOwner(), (List<UnitEntry> unitEntries) -> {
            Log.d(LOG_TAG, "Updating list of tasks from LiveData in ViewModel");
            adapter.setUnits(unitEntries);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClickListener(int unitId) {
        Log.d(LOG_TAG, "ON ITEM CLICKED, ID==" + unitId);
        Intent intent = new Intent(getActivity().getBaseContext(), DetailsActivity.class);

        intent.putExtra(DetailsFragment.EXTRA_UNIT_ID, unitId);
        startActivity(intent);
    }
}