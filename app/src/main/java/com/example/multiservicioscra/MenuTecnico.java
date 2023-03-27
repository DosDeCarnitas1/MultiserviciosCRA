package com.example.multiservicioscra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MenuTecnico extends AppCompatActivity {

    ImageButton ibtnServiciosActivos, ibtnServicioPendientes, ibtnServiciosCompletados;
    Button btnCerrarSesionTecnico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_tecnico);

        ibtnServiciosActivos = findViewById(R.id.ibtnTecnicosSA);
        ibtnServicioPendientes = findViewById(R.id.ibtnTecnicosSP);
        ibtnServiciosCompletados = findViewById(R.id.ibtnTecnicosSC);
        btnCerrarSesionTecnico = findViewById(R.id.btnCerrarSesionTecnico);

        ibtnServicioPendientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent v = new Intent(getApplicationContext(), activity_tickets_tecnicos.class);
                v.putExtra("Estado", "Pendiente");
                v.putExtra("Titulo", "Tickets pendientes");
                startActivity(v);
            }
        });

        ibtnServiciosActivos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent v = new Intent(getApplicationContext(), activity_tickets_tecnicos.class);
                v.putExtra("Estado", "Activo");
                v.putExtra("Titulo", "Tickets Activos");
                startActivity(v);
            }
        });

        ibtnServiciosCompletados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent v = new Intent(getApplicationContext(), activity_tickets_tecnicos.class);
                v.putExtra("Estado", "Inactivo");
                v.putExtra("Titulo", "Tickets Completados");
                startActivity(v);
            }
        });


        btnCerrarSesionTecnico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences shPf = getSharedPreferences("credenciales",MainActivity.MODE_PRIVATE);
                SharedPreferences.Editor editor = shPf.edit();
                editor.clear();
                editor.apply();
                Intent v = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(v);
            }
        });
    }
}