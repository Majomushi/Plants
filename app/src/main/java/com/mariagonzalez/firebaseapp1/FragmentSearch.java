package com.mariagonzalez.firebaseapp1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

//Generar la lista, quise hacer un ejemplo, sin embargo tuve un problema con el adapter :D
//Para poder mostrar mis datos

public class FragmentSearch extends Fragment {

    SearchView mSearchView;
    ListView mList;

    ArrayList<String> list;
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




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        mSearchView = (SearchView) view.findViewById(R.id.searchView);
        mList = (ListView) view.findViewById(R.id.myList);

        return view;   }
}