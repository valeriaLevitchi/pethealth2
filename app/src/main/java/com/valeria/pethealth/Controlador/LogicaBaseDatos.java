package com.valeria.pethealth.Controlador;



import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.valeria.pethealth.Modelo.Usarios;
import com.valeria.pethealth.Modelo.Mascota;
import com.valeria.pethealth.R;

import java.util.ArrayList;


public class LogicaBaseDatos  extends AppCompatActivity {
    String Usuario , contraseñaglobal, id,  nombreUsario;;

    FichaMascota FM = new FichaMascota();

    private DatabaseReference referencia_bd, referencia_UsarioId;
    private Autenticacion auth = new Autenticacion();


    private FirebaseDatabase basedatos;
    public Usarios nuevo_usuario;



    public void CrearInstacia() {

        basedatos = FirebaseDatabase.getInstance();
        referencia_UsarioId = basedatos.getReference("Usario_ID");

    }






    // Crear Usuario

    public void IntroducirUsuario(){

             DatabaseReference referenica2 = basedatos.getReference();
        nombreUsario = Usuario.replaceAll("[.@ !#$_%&*()={}\\[\\]<>,\"]", "");

             id = referenica2.push().getKey();
            referencia_bd = basedatos.getReference(nombreUsario);
            nuevo_usuario = new Usarios(Usuario, contraseñaglobal, id);
            referencia_bd.setValue(nuevo_usuario);
            TablaUsuariosId();


    }

    public void TablaUsuariosId(){


        referencia_UsarioId.child(nombreUsario).setValue(id);

    }






    public void RegistrarUsuario(String usario , String Contraseña, Context contexto){

        auth.CrearInstacia();
        auth.ComprobarAcceso();
        Usuario = usario;
        contraseñaglobal = Contraseña;
        auth.CrearUsuario(usario, Contraseña, contexto);
    }

    public void LoginUsario(String usuario , String Contraseña){

        auth.CrearInstacia();
        auth.ComprobarAcceso();




    }

    //Apartado para registrar Mascotas
    public void obtenerTipo(String tipo, Mascota mascota){

        mascota.setTipo(tipo);
    }












}
