package com.example.multiservicioscra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MenuTecnico extends AppCompatActivity {

    ImageButton ibtnServiciosActivos, ibtnServicioPendientes, ibtnServiciosCompletados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_tecnico);

        ibtnServiciosActivos = findViewById(R.id.ibtnTecnicosSA);
        ibtnServicioPendientes = findViewById(R.id.ibtnTecnicosSP);
        ibtnServiciosCompletados = findViewById(R.id.ibtnTecnicosSC);

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
                v.putExtra("Estado", "Completado");
                v.putExtra("Titulo", "Tickets Completados");
                startActivity(v);
            }
        });
    }
}