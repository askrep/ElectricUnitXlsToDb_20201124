package com.kas.electricunitxlstodb_20201124.ui;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.kas.electricunitxlstodb_20201124.R;
import com.kas.electricunitxlstodb_20201124.databinding.SearchFragmentBinding;

public class SearchFragment extends Fragment {

    private static final String LOG_TAG = "#SEARCH FRAGMENT";
    private SearchViewModel searchViewModel;
    private SearchFragmentBinding searchFragmentBinding;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        searchFragmentBinding = DataBindingUtil.setContentView(getActivity(), R.layout.search_fragment);

        EditText searchField = searchFragmentBinding.searchField;
        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String filter = charSequence.toString();
                if (!filter.isEmpty()) {
                    searchViewModel.setFilter(filter);

                    Log.d(LOG_TAG, "Search onChanged = " + filter);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}