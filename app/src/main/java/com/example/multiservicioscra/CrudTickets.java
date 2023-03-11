package com.example.multiservicioscra;

import static com.example.multiservicioscra.MainActivity.usuarioIdParaConsultas;
import static com.example.multiservicioscra.MainActivity.usuarioTipoParaConsultas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
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
                        ArrayList<String> tickets = new ArrayList<String>();
                        for (int i = 0; i < jsonObject.length()-1; i++) {
                            //bro estos de aqui son para que tu verifiques por la consola si si sale lo esperado
                            System.out.println(i);
                            String index = Integer.toString(i);
                            System.out.println(jsonObject.getJSONObject(index));

                            //meto todos las concidencias en la lista
                            if(jsonObject.getJSONObject(index).getString("Estado").equals(filtro)){
                                //esta parte creo que va a ser la mas dificil de hacer, tenemos que ver cuales
                                //fueron los campos que definiste, porque yo te devuelvo todas las columnas por cada registro
                                //tendriamos que elejir que columnas se pasan, para que cuando lo metamos a el listadapter, los
                                //datos que devuelve el php coincidan con los campos que tu definiste

                                tickets.add(jsonObject.getJSONObject(index).toString());
                            }
                        }
                        //EN ESTA parte intente meter lo que el chavo mete en el minuto 27:39.
                        //Puede estar mal, pero mas o menos espero que sirva como guia para ver si jala xd
                        //van a aparecer muchos errores si no me equivoco, es por que las clases son las que
                        //definio el chavo
                        if(!tickets.isEmpty()){
                            ListAdapter listAdapter = new ListAdapter(tickets, this);
                            RecyclerView recyclerView = findViewById(R.id.listRecyclerView);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(this));
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