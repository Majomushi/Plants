package com.mariagonzalez.firebaseapp1;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentWelcome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentWelcome extends Fragment {


    //Obtener las Referencias de los componentes en activity_profile
    private TextView mTextViewName;
    private TextView mTextViewEmail;

    FirebaseAuth mAuth; //Objeto que nos brinda el paquete de Auth
    DatabaseReference mDatabase; //Objeto para hacer uso de la Base de Datos RealTime de Firebase


    public FragmentWelcome() {
        // Required empty public constructor
    }


    public static FragmentWelcome newInstance(String param1, String param2) {
        FragmentWelcome fragment = new FragmentWelcome();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance(); //Intanciamiento del Objeto Auth
        //Instanciamiento de la Base de Datos,
        //donde se hace referencia al nodo principal de la base de datos
        mDatabase = FirebaseDatabase.getInstance().getReference();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);

        //Instanciamiento de los componentes a los que se le pasa
        //las referencias establecidas en el xml
        mTextViewName = (TextView) view.findViewById(R.id.textViewName);
        mTextViewEmail = (TextView) view.findViewById(R.id.textViewEmail);

        getUserInfo();//Metodo de la Info del usuario.
        return view;
    }

    //Metodo para la Obtencion de Datos de la Base de Datos
    private void getUserInfo(){
        String id = mAuth.getCurrentUser().getUid(); //Traer el Id del Usuario que tiene iniciada la Sesion.

        //Lectura de los Datos de los campos name y email, para Mostrarlo en la pantalla de Profile,
        // validando que estos existan
        mDatabase.child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String name = snapshot.child("name").getValue().toString();
                    String email = snapshot.child("email").getValue().toString();

                    mTextViewName.setText(name);
                    mTextViewEmail.setText(email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}