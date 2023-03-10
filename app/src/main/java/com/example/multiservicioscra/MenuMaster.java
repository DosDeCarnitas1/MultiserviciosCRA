package com.example.multiservicioscra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MenuMaster extends AppCompatActivity {

    ImageButton ibtntickets, ibtntecnicos, ibtnrefacciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_master);

        ibtntickets = findViewById(R.id.ibtntickets);
        ibtntecnicos = findViewById(R.id.ibtntecnicos);
        ibtnrefacciones = findViewById(R.id.ibtnrefacciones);

        ibtntecnicos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent v = new Intent(getApplicationContext(), CrudTecnicos.class);
                startActivity(v);
            }
        });

        ibtntickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent v = new Intent(getApplicationContext(), CrudTickets.class);
                startActivity(v);
            }
        });

        ibtnrefacciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent v = new Intent(getApplicationContext(), CrudRefacciones.class);
                startActivity(v);
            }
        });
    }
}