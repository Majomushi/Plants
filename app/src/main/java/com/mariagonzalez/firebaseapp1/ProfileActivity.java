package com.mariagonzalez.firebaseapp1;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity{


    FragmentTransaction transaction;
    FirebaseAuth mAuth; //Objeto que nos brinda el paquete de Auth
    FragmentWelcome fragmentWelcome;
    FragmentSearch fragmentSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Toolbar Reference
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance(); //Intanciamiento del Objeto Auth

        fragmentWelcome = new FragmentWelcome();
        fragmentSearch = new FragmentSearch();



        getSupportFragmentManager().beginTransaction().add(R.id.containerFragment, fragmentWelcome).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        transaction = getSupportFragmentManager().beginTransaction();
        int id = item.getItemId();
        switch (id){
            case R.id.search:
                transaction.replace(R.id.containerFragment,fragmentSearch).commit();
                transaction.addToBackStack(null); //Para que no termine completamente la aplicacion
                break;
            case R.id.btnSignout:
                        mAuth.signOut();//Metodo de Auth para el Cierre de Sesión
                        startActivity(new Intent(ProfileActivity.this, MainActivity.class));//Redireccionamiento a la pagina principal
                        finish();
                break;
        }
        return true;
    }
}