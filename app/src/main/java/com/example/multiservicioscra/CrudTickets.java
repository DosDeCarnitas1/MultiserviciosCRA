package com.example.multiservicioscra;

import static com.example.multiservicioscra.MainActivity.usuarioIdParaConsultas;
import static com.example.multiservicioscra.MainActivity.usuarioTipoParaConsultas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CrudTickets extends AppCompatActivity {

    Spinner spEstado;
    //pon tu ip aqui
    String ip = "192.168.100.9";
    RequestQueue requestQueue;
    ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_tickets);

        requestQueue = Volley.newRequestQueue(this);
        spEstado = findViewById(R.id.spestado);

        System.out.println("Este es el id del usuario: "+usuarioIdParaConsultas);
        System.out.println("Este es el tipo del usuario: "+usuarioTipoParaConsultas);
        System.out.println("http://"+ip+"/MCRAAndroidphps/consultarTickets.php?tipoUsuario="+usuarioTipoParaConsultas+"&id_usuario="+usuarioIdParaConsultas);

        llenarSpinnerEstados("http://"+ip+"/MCRAAndroidphps/consultarTickets.php?tipoUsuario="+usuarioTipoParaConsultas+"&id_usuario="+usuarioIdParaConsultas);


        spEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if(position != 0) {
                    String filtro = spEstado.getSelectedItem().toString();
                    try {
                        //se le resta uno a la longitud del json porque el primer campo son los "estados" los cuales no son tickets
                        //cosa que se presenta cuando se hace un cambio en el spiner, o mas bien se filtra
                        for (int i = 0; i < jsonObject.length()-1; i++) {
                            System.out.println(i);
                            String index = Integer.toString(i);
                            System.out.println(jsonObject.getJSONObject(index));
                        }

                    }catch (Exception e){
                        System.out.println(e);
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //do nothing
            }
        });
    }
    private JSONObject jsonObject= null;

    private void llenarSpinnerEstados(String url){
        JsonArrayRequest jsonarrayRequest = new JsonArrayRequest
                (url, new Response.Listener<JSONArray>() {
                    //respuesta exitosa por parte del servidor
                    @Override
                    public void onResponse(JSONArray response) {
//                        JSONObject jsonObject= null;
                        System.out.println("veamos que es esto: "+jsonObject);
                        for(int i=0; i < response.length();i++){
                            try{//extrayendo el objecto json de cada una de las posiciones del arreglo
                                jsonObject = response.getJSONObject(i);

                                //llenamos el spinner segun los estados que esten disponibles para el usuario
                                ArrayList<String> filtros = new ArrayList<String>();
                                filtros.add("Elije una opcion");
                                for (int j = 0; j < jsonObject.getJSONArray("Estados").length(); j++) {
                                    filtros.add(jsonObject.getJSONArray("Estados").getString(j));
                                }

                                arrayAdapter = new ArrayAdapter<String>(CrudTickets.this, android.R.layout.simple_spinner_item, filtros);
                                spEstado.setAdapter(arrayAdapter);

                            }//cierre del try
                            catch (JSONException e) {
                                e.printStackTrace();
                                System.out.println("hubo un error mi shavo: "+e);
                            }
                        }//cierre del for
                    }//cierre del onResponse

                }, //en caso de respuesta de error por parte del servidor
                        new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO: Handle error
                                System.out.println(error);
                                //Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                                Toast.makeText(getApplicationContext(),"Usuario y contraseña incorrectos",Toast.LENGTH_LONG).show();
                            }
                        });
        requestQueue.add(jsonarrayRequest);
    }//cierre del método iniciSesion
}