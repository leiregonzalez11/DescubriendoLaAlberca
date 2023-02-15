package com.example.tfg.adapters;

import java.util.List;
import com.example.tfg.R;
import android.view.View;
import java.util.ArrayList;
import android.widget.Filter;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.TextView;
import android.widget.Filterable;
import android.widget.BaseAdapter;
import android.view.LayoutInflater;
import android.annotation.SuppressLint;

@SuppressWarnings("unchecked")
public class listViewAdapter extends BaseAdapter implements Filterable {

    List<String> mData;
    List<String> mStringFilterList;
    ValueFilter valueFilter;
    private final Context context;
    private final int layout;

    public listViewAdapter(Context context, int layout, List<String> names) {
        this.context = context;
        this.layout = layout;
        mData=names;
        mStringFilterList = names;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public String getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {

        View view;

        //Inflamos la vista con el layout que querramos utilizar
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        view = layoutInflater.inflate(this.layout, null);

        // Valor actual según la posición
        String currentName  = mData.get(position);

        // Referenciamos el elemento a modificar y lo rellenamos
        TextView textView = view.findViewById(R.id.textView);
        textView.setText(currentName);

        //Devolvemos la vista inflada
        return view;
    }

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                List<String> filterList = new ArrayList<>();
                for (int i = 0; i < mStringFilterList.size(); i++) {
                    if ((mStringFilterList.get(i).toUpperCase()).contains(constraint.toString().toUpperCase())) {
                        filterList.add(mStringFilterList.get(i));
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = mStringFilterList.size();
                results.values = mStringFilterList;
            }
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            mData = (List<String>) results.values;
            notifyDataSetChanged();
        }

    }

}
