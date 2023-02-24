package com.example.multiservicioscra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuMaster extends AppCompatActivity {

    Button btntickets, btntecnicos, btnrefacciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_master);

        btntickets = findViewById(R.id.btntickets);
        btntecnicos = findViewById(R.id.btntecnicos);
        btnrefacciones = findViewById(R.id.btnrefacciones);

        btntecnicos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent v = new Intent(getApplicationContext(), CrudTecnicos.class);
                startActivity(v);
            }
        });

        //btntickets.setOnClickListener(new View.OnClickListener() {
          //  @Override
           // public void onClick(View view) {
              //  Intent v = new Intent(getApplicationContext(), CrudTickets.class);
              //  startActivity(v);
          //  }
       // });

       // btnrefacciones.setOnClickListener(new View.OnClickListener() {
         //   @Override
         //   public void onClick(View view) {
         //       Intent v = new Intent(getApplicationContext(), CrudRefacciones.class);
         //       startActivity(v);
          //  }
       // });
    }
}