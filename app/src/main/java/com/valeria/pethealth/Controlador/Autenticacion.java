package com.valeria.pethealth.Controlador;

import static androidx.constraintlayout.widget.Constraints.TAG;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Autenticacion {

    boolean succes;
    FirebaseAuth auth;
    boolean acceso;

    public void ComprobarAcceso(){
        FirebaseUser UsuarioActual = auth.getCurrentUser();
        if(UsuarioActual != null){
            UsuarioActual.reload();
        }
    }

    public FirebaseAuth CrearInstacia(){
        auth = FirebaseAuth.getInstance();
        return auth;
    }

    public void CrearUsuario(String correo , String Contraseña, Context contexto){
        auth.createUserWithEmailAndPassword(correo, Contraseña)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = auth.getCurrentUser();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());

                        }
                    }
                });
    }




}
