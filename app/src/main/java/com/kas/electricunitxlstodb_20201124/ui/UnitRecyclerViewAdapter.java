package com.kas.electricunitxlstodb_20201124.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.kas.electricunitxlstodb_20201124.dao.UnitEntry;
import com.kas.electricunitxlstodb_20201124.databinding.FragmentUnitDoubleBinding;
import com.kas.electricunitxlstodb_20201124.databinding.FragmentUnitTripleBinding;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link UnitEntry}.
 * TODO: Replace the implementation with code for your data type.
 */
public class UnitRecyclerViewAdapter extends RecyclerView.Adapter<UnitRecyclerViewAdapter.ViewHolderTriple> {

    private List<UnitEntry> units;
    private final UnitClickListener unitClickListener;
    private Context context;

    /********** INNER ViewHolderTriple CLASS ************/
    public class ViewHolderDouble extends RecyclerView.ViewHolder implements View.OnClickListener {
        private static final String LOG_TAG = "# VIEW HOLDER";
        //    public final View view;
        public final TextView titleView;
        public final TextView descriptionView;
        public UnitEntry unit;
        FragmentUnitDoubleBinding doubleBinding;
        FragmentUnitTripleBinding tripleBinding;

        public ViewHolderDouble(FragmentUnitDoubleBinding binding) {
            super(binding.getRoot());
            doubleBinding = binding;

            titleView = binding.unitTitle;
            descriptionView = binding.unitDescription;
            binding.getRoot().setOnClickListener((View.OnClickListener) this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            int elementId = units.get(adapterPosition).getId();
            unitClickListener.onUnitClick(elementId);
        }
    }

    /********** END VIEW HOLDER CLASS ************/
    public class ViewHolderTriple extends RecyclerView.ViewHolder implements View.OnClickListener {
        private static final String LOG_TAG = "# VIEW HOLDER";

        private final TextView unitLocation;
        public final TextView titleView;
        public final TextView descriptionView;

        public UnitEntry unit;
        FragmentUnitTripleBinding tripleBinding;

        public ViewHolderTriple(FragmentUnitTripleBinding binding) {
            super(binding.getRoot());
            tripleBinding = binding;
            unitLocation = binding.unitLocation;
            titleView = binding.unitTitle;
            descriptionView = binding.unitDescription;
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
        FragmentUnitTripleBinding binding = FragmentUnitTripleBinding.inflate(inflater, parent, false);
        return new ViewHolderTriple(binding);
    }

    @Override
    public void onBindViewHolder(final ViewHolderTriple holder, int position) {
        UnitEntry unitEntry = units.get(position);

        holder.unit = units.get(position);
        holder.unitLocation.setText(unitEntry.location);
        holder.titleView.setText(unitEntry.title);
        holder.descriptionView.setText(unitEntry.description);
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