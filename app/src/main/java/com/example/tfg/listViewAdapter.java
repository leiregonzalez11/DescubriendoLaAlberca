package com.example.tfg;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class listViewAdapter extends BaseAdapter {

    private final Context context;
    private final int layout;
    private final ArrayList<String> names;

    public listViewAdapter(Context context, int layout, ArrayList<String> names){
        this.context = context;
        this.layout = layout;
        this.names = names;
    }

    @Override
    public int getCount() {
        return this.names.size();
    }

    @Override
    public Object getItem(int position) {
        return this.names.get(position);
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
        view = layoutInflater.inflate(R.layout.list_item, null);

        // Valor actual según la posición
        String currentName  = names.get(position);

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
