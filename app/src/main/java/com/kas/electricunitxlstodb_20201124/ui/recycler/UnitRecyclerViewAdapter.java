package com.kas.electricunitxlstodb_20201124.ui.recycler;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kas.electricunitxlstodb_20201124.R;
import com.kas.electricunitxlstodb_20201124.dao.UnitEntry;
import com.kas.electricunitxlstodb_20201124.databinding.FragmentUnitListBinding;


import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link UnitEntry}.
 * TODO: Replace the implementation with code for your data type.
 */
public class UnitRecyclerViewAdapter extends RecyclerView.Adapter<UnitRecyclerViewAdapter.ViewHolder> {

    private List<UnitEntry> units;
    private final ItemClickListener itemClickListener;
    private Context context;
    // private final List<UnitEntry> unitEntries;
    private FragmentUnitListBinding binding;


    /********** INNER ViewHolder CLASS ************/
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private static final String LOG_TAG = "# VIEW HOLDER";
        public final View view;
        public final TextView titleView;
        public final TextView descriptionView;
        public UnitEntry item;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            titleView = (TextView) view.findViewById(R.id.unit_title);
            descriptionView = (TextView) view.findViewById(R.id.unit_description);

            view.setOnClickListener(this);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + descriptionView.getText() + "'";
        }

        @Override
        public void onClick(View view) {
            int elementId = units.get(getAdapterPosition()).getId();
            Log.d("# VIEW HOLDER", "On Click ID==" + elementId);
            itemClickListener.onItemClickListener(elementId);
        }
    }/********** END VIEW HOLDER CLASS ************/


    /********** INNER ItemClick INTERFACE ************/
    public interface ItemClickListener {
        void onItemClickListener(int itemId);
    }

    /********** END INNER ItemClick INTERFACE ************/


    public UnitRecyclerViewAdapter(Context context, ItemClickListener itemClickListener) {
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_unit, parent, false);
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