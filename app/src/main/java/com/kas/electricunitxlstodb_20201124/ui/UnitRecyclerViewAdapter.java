package com.kas.electricunitxlstodb_20201124.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.kas.electricunitxlstodb_20201124.R;
import com.kas.electricunitxlstodb_20201124.dao.UnitEntry;
import com.kas.electricunitxlstodb_20201124.databinding.FragmentUnitDoubleBinding;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link UnitEntry}.
 * TODO: Replace the implementation with code for your data type.
 */
public class UnitRecyclerViewAdapter extends RecyclerView.Adapter<UnitRecyclerViewAdapter.ViewHolder> {

    private List<UnitEntry> units;
    private final UnitClickListener unitClickListener;
    private Context context;

    /********** INNER ViewHolder CLASS ************/
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private static final String LOG_TAG = "# VIEW HOLDER";
        public final View view;
        public final TextView titleView;
        public final TextView descriptionView;
        public UnitEntry item;
        FragmentUnitDoubleBinding doubleBinding;

        public ViewHolder(View view) {
            super(view);
            this.view = view.findViewById(R.id.fragment_unit_double);
            titleView = (TextView) view.findViewById(R.id.unit_title);
            descriptionView = (TextView) view.findViewById(R.id.unit_description);
            view.setOnClickListener(this);
    
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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_unit_double, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        UnitEntry unitEntry = units.get(position);

        String title = unitEntry.getTitle();
        String description = unitEntry.getDescription();
        holder.item = units.get(position);
        holder.titleView.setText(title);
        holder.descriptionView.setText(description);
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