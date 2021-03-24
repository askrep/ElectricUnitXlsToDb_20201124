package com.kas.electricunitxlstodb_20201124.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.kas.electricunitxlstodb_20201124.dao.UnitEntry;
import com.kas.electricunitxlstodb_20201124.databinding.FragmentThreeFieldsBinding;

import java.util.List;

public class UnitRecyclerViewAdapter extends RecyclerView.Adapter<UnitRecyclerViewAdapter.ViewHolderTriple> {

    private List<UnitEntry> units;
    private final UnitClickListener unitClickListener;
    private Context context;

/*    public class ViewHolderDouble extends RecyclerView.ViewHolder implements View.OnClickListener {
        private static final String LOG_TAG = "# VIEW HOLDER";
        //    public final View view;
        public final TextView titleView;
        public final TextView descriptionView;
        public UnitEntry unit;
        FragmentUnitDoubleBinding doubleBinding;
        FragmentThreeFieldsBinding tripleBinding;

        public ViewHolderDouble(FragmentUnitDoubleBinding binding) {
            super(binding.getRoot());
            doubleBinding = binding;

            titleView = binding.;
            descriptionView = binding.unitDescription;
            binding.getRoot().setOnClickListener((View.OnClickListener) this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            int elementId = units.get(adapterPosition).getId();
            unitClickListener.onUnitClick(elementId);
        }
    }*/

    /********** INNER ViewHolderTriple CLASS ************/
    public class ViewHolderTriple extends RecyclerView.ViewHolder implements View.OnClickListener {
        private static final String LOG_TAG = "# VIEW HOLDER";

        private final TextView firstField;
        private final TextView secondField;
        private final TextView thirdField;

        public UnitEntry unit;

        public ViewHolderTriple(FragmentThreeFieldsBinding binding) {
            super(binding.getRoot());

            firstField = binding.firstField;
            secondField = binding.secondField;
            thirdField = binding.thirdField;
            binding.getRoot().setOnClickListener((View.OnClickListener) this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            int elementId = units.get(adapterPosition).getId();
            unitClickListener.onUnitClick(elementId);
        }
    }/********** END VIEW HOLDER CLASS ************/

    /********** INNER ItemClick INTERFACE ************/
    public interface UnitClickListener {
        void onUnitClick(int itemId);
    }

    public UnitRecyclerViewAdapter(Context context, UnitClickListener unitClickListener) {
        this.context = context;
        this.unitClickListener = unitClickListener;
    }

    @Override
    public ViewHolderTriple onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        //ViewHolderTriple Data bindings
        FragmentThreeFieldsBinding binding = FragmentThreeFieldsBinding.inflate(inflater, parent, false);
        return new ViewHolderTriple(binding);
    }

    @Override
    public void onBindViewHolder(final ViewHolderTriple holder, int position) {
        UnitEntry unitEntry = units.get(position);

        holder.unit = units.get(position);
        holder.firstField.setText(unitEntry.getLocation());
        holder.secondField.setText(unitEntry.getTitle());
        holder.thirdField.setText(unitEntry.getDescription());
    }

    @Override
    public int getItemCount() {
        if (units == null) return 0;
        return units.size();
    }

    public void setUnits(List<UnitEntry> units) {
        this.units = units;
        notifyDataSetChanged();
    }
}