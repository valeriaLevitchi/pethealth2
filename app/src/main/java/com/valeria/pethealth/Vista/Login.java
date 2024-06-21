package com.valeria.pethealth.Vista;

import static androidx.constraintlayout.widget.Constraints.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.valeria.pethealth.Controlador.Autenticacion;
import com.valeria.pethealth.Controlador.LogicaBaseDatos;
import com.valeria.pethealth.R;

public class Login extends AppCompatActivity
{
    public LogicaBaseDatos Controlador = new LogicaBaseDatos();
    EditText texto1, contraseña;

    TextView tv;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        btn = (Button) findViewById(R.id.submit2);
        tv = (TextView) findViewById(R.id.titulo) ;

        texto1 = (EditText) findViewById(R.id.nombre);
        contraseña = (EditText) findViewById(R.id.password);
        Controlador.CrearInstacia();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = String.valueOf(texto1.getText());
                String contra = String.valueOf(contraseña.getText());

                String nombreUsario = text.replaceAll("[.@ !#$_%&*()={}\\[\\]<>,\"]", "");
                FirebaseAuth auth;
                Autenticacion ath = new Autenticacion();
                auth = ath.CrearInstacia();
                Controlador.LoginUsario(text, contra);


                auth.signInWithEmailAndPassword(text, contra)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    Toast.makeText(Login.this, "LoginSuccesful", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(Login.this, Mostrar_Mascotas.class);
                                    intent.putExtra("nombreUsuario", nombreUsario);
                                    startActivity(intent);



                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(Login.this, "Login no Succesful", Toast.LENGTH_LONG).show();
                                    Toast.makeText(Login.this, "Usuario o contraseña incorrecta." , Toast.LENGTH_LONG).show();



                                }

                            }

                        });





            }
        });

    }

}
