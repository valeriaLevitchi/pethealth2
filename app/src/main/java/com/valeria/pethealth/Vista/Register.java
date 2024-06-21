package com.valeria.pethealth.Vista;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.valeria.pethealth.Controlador.LogicaBaseDatos;
import com.valeria.pethealth.R;

public class Register extends AppCompatActivity
{
    public LogicaBaseDatos Controlador = new LogicaBaseDatos();
    EditText texto1, contraseña;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        btn = (Button) findViewById(R.id.submit);
        Controlador.CrearInstacia();

        texto1 = (EditText) findViewById(R.id.nombre);
        contraseña = (EditText) findViewById(R.id.password);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = String.valueOf(texto1.getText());
                String contra = String.valueOf(contraseña.getText());

                String nombreUsario = text.replaceAll("[.@ !#$_%&*()={}\\[\\]<>,\"]", "");

                Controlador.RegistrarUsuario(text, contra, Register.this);
                Controlador.IntroducirUsuario();

                Intent intent = new Intent(Register.this, Mostrar_Mascotas.class);
                intent.putExtra("nombreUsuario" , nombreUsario);
                startActivity(intent);
            }
        });

    }

}
