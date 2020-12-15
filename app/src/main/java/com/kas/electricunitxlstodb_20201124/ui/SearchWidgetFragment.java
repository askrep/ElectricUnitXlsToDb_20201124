package com.kas.electricunitxlstodb_20201124.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.kas.electricunitxlstodb_20201124.databinding.FragmentSearchWidgetBinding;

public class SearchWidgetFragment extends Fragment {
    private SharedViewModel sharedViewModel;
    private static final String TAG = "#_SEARCH_WIDGET";
    FragmentSearchWidgetBinding searchWidgetBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        searchWidgetBinding = FragmentSearchWidgetBinding.inflate(inflater, container, false);
        View view = searchWidgetBinding.getRoot();

        sharedViewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);

        SearchView searchWidget = searchWidgetBinding.searchWidget;
        initSearchWidget(searchWidget);
        return view;
    }

    private void initSearchWidget(SearchView searchWidget) {
        searchWidget.setActivated(true);
        searchWidget.setQueryHint("Enter units filter");
        searchWidget.onActionViewExpanded();
        searchWidget.setIconified(false);
        searchWidget.clearFocus();
        searchWidget.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                sharedViewModel.setFilterLiveData(newText);
                if (newText != null || !newText.isEmpty()) {
                    sharedViewModel.setFilterLiveData(newText);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
