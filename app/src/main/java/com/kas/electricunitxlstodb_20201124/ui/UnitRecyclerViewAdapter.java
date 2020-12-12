package com.kas.electricunitxlstodb_20201124.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.kas.electricunitxlstodb_20201124.R;
import com.kas.electricunitxlstodb_20201124.dao.UnitEntry;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link UnitEntry}.
 * TODO: Replace the implementation with code for your data type.
 */
public class UnitRecyclerViewAdapter extends RecyclerView.Adapter<UnitRecyclerViewAdapter.ViewHolder> {

    private List<UnitEntry> units;
    private final ItemClickListener itemClickListener;
    private Context context;

    /********** INNER ViewHolder CLASS ************/
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private static final String TAG = "#_VIEW_HOLDER";
        public final View view;
        public final TextView title;
        public final TextView description;
        public UnitEntry item;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            title =  view.findViewById(R.id.unit_title);
            description = view.findViewById(R.id.unit_description);
            view.setOnClickListener(this);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + description.getText() + "'";
        }

        @Override
        public void onClick(View view) {
            int elementId = units.get(getAdapterPosition()).getId();
            itemClickListener.onItemClickListener(elementId);
        }
    }/********** END VIEW HOLDER CLASS ************/

    /********** INNER ItemClick INTERFACE ************/
    public interface ItemClickListener {
        void onItemClickListener(int itemId);
    }

    public UnitRecyclerViewAdapter(Context context, ItemClickListener itemClickListener) {
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_unit_triple, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        UnitEntry unitEntry = units.get(position);
        String title = unitEntry.getTitle();
        String description = unitEntry.getDescription();

        holder.item = units.get(position);
        holder.title.setText(title);
        holder.description.setText(description);
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