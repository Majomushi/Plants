package com.mariagonzalez.firebaseapp1;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

//Generar la lista, quise hacer un ejemplo, sin embargo tuve un problema con el adapter :D
//Para poder mostrar mis datos

public class FragmentSearch extends Fragment {

    SearchView mSearchView;
    ListView mList;
    Botanica botanica = new Botanica(); // POC
    List<Planta> list = new ArrayList<>();
    List<String> lista2 = new ArrayList<>();

    ArrayAdapter<String> adapter;

    public FragmentSearch() {

    }


    public static FragmentSearch newInstance(String param1, String param2) {
        FragmentSearch fragment = new FragmentSearch();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        * Desactiva cualquier metodo de seguridad de android para poder ejecutar la busqueda en el mismo hilo.
        * Intente hacerlo de la buena manera y de pura v**** sirvio :v PD: Eliminar este comentario antes de darselo al maestro, salu3.
        * */
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();  StrictMode.setThreadPolicy(policy);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        mList = (ListView) view.findViewById(R.id.myList);
        mSearchView = (SearchView) view.findViewById(R.id.searchView);
        List<Planta> list = new ArrayList<Planta>();

        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // AQUI VA EL CODIGO PARA MANDAR EL OTRO FRAGMENT.
                Planta planta = (Planta) parent.getAdapter().getItem(position);
                FragmentInfo fragmentInfo = new FragmentInfo();
                Bundle bundle = new Bundle();
                bundle.putSerializable("planta",planta);
                fragmentInfo.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(FragmentSearch.super.getId(),fragmentInfo);
                fragmentTransaction.addToBackStack(null);
               fragmentTransaction.commit();
            }
        });

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query) {
                ejecutarQuery();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //hace algo cuando el texto cambia, en este caso, nada.
                return false;
            }
        });
        return view;
    }

    private void actualizarLista(){
        CustomAdapter arrayAdapter = new CustomAdapter(getActivity(),R.layout.list_view,list);
        //arrayAdapter.notifyDataSetChanged();
        mList.setAdapter(arrayAdapter);
        System.out.println("Size: "+list.size());
    }

    private void ejecutarQuery(){
        String dato = mSearchView.getQuery().toString();
        list = botanica.buscar(dato);
        try {
            list = botanica.buscar(dato);
            actualizarLista();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}