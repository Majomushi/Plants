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

public class LoginActivity extends AppCompatActivity {

    //Obtener las Referencias de los componentes en activity_login
    private EditText mEditTextEmail;
    private EditText mEditTextPassword;
    private Button mButtonLogin;

    //Variables de los datos a registrar
    private String email = "";
    private String password = "";

    FirebaseAuth mAuth;//Objeto que nos brinda el paquete de Auth

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();//Intanciamiento del Objeto Auth

    /*Instanciamiento de los componentes a los que se le pasamos
         las referencias establecidas en el xml*/
        mEditTextEmail = (EditText) findViewById(R.id.editTextEmail);
        mEditTextPassword = (EditText) findViewById(R.id.editTextPassword);
        mButtonLogin = (Button) findViewById(R.id.btnInicioSesion);

        //Validacion para saber si el usuario ingreso valores a los EditText.
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = mEditTextEmail.getText().toString();
                password = mEditTextPassword.getText().toString();

                if (!email.isEmpty() && !password.isEmpty()){
                    loginUser(); //Metodo para Login
                }else{
                    Toast.makeText(LoginActivity.this, "Complete los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loginUser(){
        //Metodo para Incio de Sesion a traves de Email y Password con una validacion para pasar al siguiente screen
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
                    finish();
                }else {
                    Toast.makeText(LoginActivity.this, "No se pudo Iniciar Sesion, verifique sus datos.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}