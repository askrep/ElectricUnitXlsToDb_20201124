package com.kas.electricunitxlstodb_20201124.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.kas.electricunitxlstodb_20201124.R;
import com.kas.electricunitxlstodb_20201124.dao.UnitEntry;

import java.util.List;

public class UnitRecyclerViewAdapter extends RecyclerView.Adapter<UnitRecyclerViewAdapter.ViewHolderTriple> {

    private List<UnitEntry> units;
    private final UnitClickListener unitClickListener;
    private Context context;

    /********** INNER ItemClick INTERFACE ************/
    public interface UnitClickListener {
        void onUnitClick(int itemId);
    }

    public UnitRecyclerViewAdapter(Context context, UnitClickListener unitClickListener) {
        this.context = context;
        this.unitClickListener = unitClickListener;
    }

    public void setUnits(List<UnitEntry> units) {
        this.units = units;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolderTriple onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId;

        switch (viewType) {
            case UnitEntry.VIEW_TYPE_SIMPLE: {
                layoutId = R.layout.fragment_unit_others;
                break;
            }
            case UnitEntry.VIEW_TYPE_SELECTED: {
                layoutId = R.layout.fragment_unit_selected;
                break;
            }
            default:
                throw new IllegalArgumentException("Invalid view type, value of " + viewType);
        }

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(layoutId, parent, false);
        //ViewHolderTriple Data bindings
        //FragmentThreeFieldsBinding binding = FragmentThreeFieldsBinding.inflate(inflater, parent, false);
        return new ViewHolderTriple(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolderTriple holder, int position) {
        UnitEntry unitEntry = units.get(position);
        String description = unitEntry.getDescription();

        holder.unit = units.get(position);
        holder.firstField.setText(unitEntry.getLocation());
        holder.secondField.setText(unitEntry.getCabinet());
        holder.thirdField.setText(unitEntry.getTitle());

        if (!description.isEmpty()) {
            holder.fourthFiled.setText(description);
        }
    }

    @Override
    public int getItemCount() {
        if (units == null) return 0;
        return units.size();
    }

    @Override
    public int getItemViewType(int position) {
    /*    if (position == 0) {
            return VIEW_TYPE_SELECTED;
        } else return VIEW_TYPE_OTHERS;*/
        return units.get(position).getType();

    }

    /********** INNER ViewHolderTriple CLASS ************/
    public class ViewHolderTriple extends RecyclerView.ViewHolder implements View.OnClickListener {
        private static final String LOG_TAG = "# VIEW HOLDER";

        private final TextView firstField;
        private final TextView secondField;
        private final TextView thirdField;
        private final TextView fourthFiled;

        public UnitEntry unit;

        public ViewHolderTriple(View view) {
            super(view);

            firstField = view.findViewById(R.id.first_field);
            secondField = view.findViewById(R.id.second_field);
            thirdField = view.findViewById(R.id.third_field);
            fourthFiled = view.findViewById(R.id.fourth_field);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            int elementId = units.get(adapterPosition).getId();

            unit = units.get(adapterPosition);
            unit.setExpanded(!unit.isExpanded());
            notifyItemChanged(getAdapterPosition());

            //unitClickListener.onUnitClick(elementId);
        }
    }/********** END VIEW HOLDER CLASS ************/
}