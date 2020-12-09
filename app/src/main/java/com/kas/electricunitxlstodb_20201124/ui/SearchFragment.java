package com.kas.electricunitxlstodb_20201124.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.kas.electricunitxlstodb_20201124.R;

public class SearchFragment extends Fragment {

    private static final String LOG_TAG = "#_SEARCH_FRAGMENT";
    private SearchViewModel searchViewModel;
    //private FragmentSearchBinding searchFragmentBinding;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        //searchFragmentBinding = DataBindingUtil.setContentView(getActivity(), R.layout.fragment_search);

        EditText searchField = view.findViewById(R.id.search_field);
        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String filter = charSequence.toString();
                if (!filter.isEmpty()) {
                    searchViewModel.getFilter().setValue(filter);

                    Log.d(LOG_TAG, "Search onChanged = " + filter);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}