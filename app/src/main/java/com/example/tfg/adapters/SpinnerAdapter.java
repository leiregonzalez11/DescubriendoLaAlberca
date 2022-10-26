package com.example.tfg.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tfg.R;

import java.util.ArrayList;

public class SpinnerAdapter extends BaseAdapter {

    private final Context context;
    private final int layout;
    private final String[] names;

    public SpinnerAdapter(Context context, int layout, String [] names){
        this.context = context;
        this.layout = layout;
        this.names = names;
    }

    @Override
    public int getCount() {
        return this.names.length;
    }

    @Override
    public Object getItem(int position) {
        return this.names[position];
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override

    public View getView(int position, View convertView, ViewGroup viewGroup) {

        View view;

        //Inflamos la vista con el layout que querramos utilizar
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        view = layoutInflater.inflate(this.layout, null);

        // Valor actual según la posición
        String currentName  = names[position];

        // Referenciamos el elemento a modificar y lo rellenamos
        TextView textView = view.findViewById(R.id.textView);
        textView.setText(currentName);

        //Devolvemos la vista inflada
        return view;
    }

    public int getLayout() {
        return layout;
    }
}
