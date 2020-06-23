package com.mariagonzalez.firebaseapp1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter {

    List<Planta> lista = new ArrayList<>();
    Context context;
    public CustomAdapter(@NonNull Context context, int resource, List<Planta> objeto) {
        super(context, resource);
        this.context = context;
        this.lista = objeto;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.list_view,null);
        TextView textView = (TextView) view.findViewById(R.id.textViewLista);
        textView.setText(lista.get(position).getNombreComun());
        return view;
    }

    @Override
    public int getCount() {
        return lista.size();
    }
}
