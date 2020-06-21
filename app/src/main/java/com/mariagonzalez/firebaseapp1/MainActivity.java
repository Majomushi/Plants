package com.mariagonzalez.firebaseapp1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    //Obtener las Referencias de los componentes en activity_main
    private EditText mEditTextName;
    private EditText mEditTextEmail;
    private EditText mEditTextPassword;
    private Button mButtonResgister;
    private Button mButtonSendToLogin;

    //Variables de los datos que se van registrar
    private String name = "";
    private String email = "";
    private String password = "";

    FirebaseAuth mAuth; //Objeto que nos brinda el paquete de Auth
    DatabaseReference mDatabase; //Objeto para hacer uso de la Base de Datos RealTime de Firebase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance(); //Intanciamiento del Objeto Auth
        /*Instanciamiento de la Base de Datos,
        donde se hace referencia al nodo principal de la base de datos*/
        mDatabase = FirebaseDatabase.getInstance().getReference();

    /*Instanciamiento de los componentes a los que se le pasa
         las referencias establecidas en el xml*/
        mEditTextName = (EditText) findViewById(R.id.editTextName);
        mEditTextEmail = (EditText) findViewById(R.id.editTextEmail);
        mEditTextPassword = (EditText) findViewById(R.id.editTextPassword);
        mButtonResgister = (Button) findViewById(R.id.btnRegistrar);
        mButtonSendToLogin = (Button) findViewById(R.id.btnSendToLogin);

//Validacion de Datos del Registro, con un Evento del Boton Registro.
        mButtonResgister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            /*Aqui se le asigna a las variables de registro lo que se ingreso en
                en los EditText*/
                name = mEditTextName.getText().toString();
                email = mEditTextEmail.getText().toString();
                password = mEditTextPassword.getText().toString();

            //Validacion para saber si el usuario ingreso valores a los EditText y si la contraseña es correcta
                if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()){
                    if(password.length() >= 6){//Google exige que las contraseñas sean de mas de 6 caracteres.
                        registerUser(); //Si cumple con los requisito va al Metodo de Registrar el Usuario.
                    }else{
                        Toast.makeText(MainActivity.this,"El Campo debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this,"Debe completar los Campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Acción del Boton de Ya tengo Cuenta para redireccionamiento.
        mButtonSendToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
    }

//Metodo del Registro del Usuario que cumple con las validaciones.
    private void registerUser(){
        //Metodo para crear el usuario y su validación.
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){ //Validacion si el registro fue correcto

                    //Mapa de Valores con los Datos y los campos que se crean en la base de datos.
                    Map<String, Object> map = new HashMap<>();
                    map.put("name", name);
                    map.put("email", email);
                    map.put("password", password);

                    String id = mAuth.getCurrentUser().getUid();//Obtencio del Id de Autentificacion.

                    //Creacion del Nodo Hijo llamado User, con el ID de Firebase del usuario con un Mapa de valores.
                    mDatabase.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override //Luego se Realiza una validacion para poder enviar al usuario a la pantalla de Profile.
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()){
                                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                                finish();
                            }else{
                                Toast.makeText(MainActivity.this,"No se pudieron crear los datos correctamente", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }else{
                    Toast.makeText(MainActivity.this,"No se pudo registar el Usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        if(mAuth.getCurrentUser() != null){
            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            finish();
        }
    }
}