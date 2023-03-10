package com.example.multiservicioscra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class activity_tickets extends AppCompatActivity {

    ImageButton ibtnprueba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets);

        ibtnprueba = findViewById(R.id.ibtnprueba);

        ibtnprueba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent v = new Intent(getApplicationContext(), CrudTecnicos.class);
                startActivity(v);
            }
        });

    }
}