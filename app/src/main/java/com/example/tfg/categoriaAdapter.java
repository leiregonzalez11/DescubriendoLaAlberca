package com.example.tfg;
import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
        import android.widget.TextView;

/**
 * {@link BaseAdapter} para poblar coches en un grid view
 */

public class categoriaAdapter extends BaseAdapter {
    private Context context;

    public categoriaAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return Categoria.categorias.length;
    }

    @Override
    public Categoria getItem(int position) {
        return Categoria.categorias[position];
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.grid_item, viewGroup, false);
        }

        Button buttonCategoria = view.findViewById(R.id.button_categoria);

        final Categoria item = getItem(position);

        buttonCategoria.setText(item.getNombre());
        //imagenCoche.setImageResource(item.getIdDrawable());

        return view;
    }

}
