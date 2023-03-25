package com.example.multiservicioscra;

import static com.example.multiservicioscra.MainActivity.usuarioIdParaConsultas;
import static com.example.multiservicioscra.MainActivity.usuarioTipoParaConsultas;
import static com.example.multiservicioscra.MainActivity.ip;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
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
import java.util.List;

public class activity_tickets_tecnicos extends AppCompatActivity {
    Bundle b;
//    String ip = "192.168.100.9";
//    String ip = "192.168.90.111";
    RequestQueue requestQueue;
    ArrayAdapter arrayAdapter;
    List<ListElemento> tickets;
    RecyclerView recyclerView;
    TextView tvTicketsTecnico;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets_tecnicos);
        requestQueue = Volley.newRequestQueue(this);

        tvTicketsTecnico = findViewById(R.id.tvTicketsTecnicos);

        recyclerView = findViewById(R.id.rvTicketsTecnicos);

        b = getIntent().getExtras();

        tvTicketsTecnico.setText(b.getString("Titulo"));
        System.out.println("Este es el id del usuario: "+usuarioIdParaConsultas);
        System.out.println("Este es el tipo del usuario: "+usuarioTipoParaConsultas);
        System.out.println("http://"+ip+"/MCRAAndroidphps/consultarTickets.php?tipoUsuario="+usuarioTipoParaConsultas+"&id_usuario="+usuarioIdParaConsultas);

        desplegarTickets("http://"+ip+"/MCRAAndroidphps/consultarTickets.php?tipoUsuario="+usuarioTipoParaConsultas+"&id_usuario="+usuarioIdParaConsultas,
                         b.getString("Estado"));



        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(activity_tickets_tecnicos.this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        try {
                            String json = jsonObject.toString();
//                            String toaste = "posicion: "+position;
//                            String posicion = Integer.toString(position);
//                            System.out.println(jsonObject.getJSONObject(posicion).getString("created_at"));
//                            Toast.makeText(getApplicationContext(),toaste,Toast.LENGTH_LONG).show();

                            Intent v = new Intent(activity_tickets_tecnicos.this, DetalleTickets.class);

                            for (int i = 0; i < jsonObject.length(); i++) {
                                System.out.println("Actual:" +jsonObject.getJSONObject(""+i).getInt("id"));
                                System.out.println("Seleccionado: "+tickets.get(position).getId());
                                System.out.println(" ");
                                System.out.println("descAct: "+jsonObject.getJSONObject(""+i).getString("descripcion"));
                                System.out.println("descSelecc: "+tickets.get(position).getDescripcion());
                                if(jsonObject.getJSONObject(""+i).getInt("id") == tickets.get(position).getId()){
                                    v.putExtra("ticket",jsonObject.getJSONObject(""+i).toString());
                                    break;
                                }
                            }

                            startActivity(v);

                        }catch (Exception e){
                            System.out.println(e);
                        }
                    }
                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
    }
    private JSONObject jsonObject= null;

    private void desplegarTickets(String url, String estado){
        JsonArrayRequest jsonarrayRequest = new JsonArrayRequest
                (url, new Response.Listener<JSONArray>() {
                    //respuesta exitosa por parte del servidor
                    @Override
                    public void onResponse(JSONArray response) {
                        String filtro = estado;
                        try {

                            for (int i = 0; i < response.length(); i++) {
                                jsonObject = response.getJSONObject(i);
                            }

//                            System.out.println("respopnse: "+response.getJSONObject(0));
//                            System.out.println("lenght: "+response.length());
//                            System.out.println("is: "+response.getJSONObject(0).getJSONObject("0").getString("estado")+" == "+filtro);
                            //se le resta uno a la longitud del json porque el primer campo son los "estados" los cuales no son tickets
                            //cosa que se presenta cuando se hace un cambio en el spiner, o mas bien se filtra
                            tickets = new ArrayList<>();
                            for (int i = 0; i < jsonObject.length()-1; i++) {
                                //bro estos de aqui son para que tu verifiques por la consola si si sale lo esperado
                                System.out.println(i);
                                String index = Integer.toString(i);
                                System.out.println(jsonObject.getJSONObject(index));

                                //meto todos las concidencias en la lista
                                if(jsonObject.getJSONObject(index).getString("estado").equals(filtro)){
                                    //esta parte creo que va a ser la mas dificil de hacer, tenemos que ver cuales
                                    //fueron los campos que definiste, porque yo te devuelvo todas las columnas por cada registro
                                    //tendriamos que elejir que columnas se pasan, para que cuando lo metamos a el listadapter, los
                                    //datos que devuelve el php coincidan con los campos que tu definiste
                                    System.out.println("titulo: "+jsonObject.getJSONObject(index).getString("titulo"));
                                    System.out.println("descr: "+jsonObject.getJSONObject(index).getString("descripcion"));
                                    System.out.println("estado: "+jsonObject.getJSONObject(index).getString("estado"));
                                    System.out.println("estado: "+jsonObject.getJSONObject(index).getInt("id"));

                                    tickets.add(new ListElemento(jsonObject.getJSONObject(index).getString("titulo"),
                                                                 jsonObject.getJSONObject(index).getString("descripcion"),
                                                                 jsonObject.getJSONObject(index).getString("estado"),
                                                                 jsonObject.getJSONObject(index).getInt("id")));
                                }
                            }
                            //EN ESTA parte intente meter lo que el chavo mete en el minuto 27:39.
                            //Puede estar mal, pero mas o menos espero que sirva como guia para ver si jala xd
                            //van a aparecer muchos errores si no me equivoco, es por que las clases son las que
                            //definio el chavo
                            if(!tickets.isEmpty()){
                                ListAdaptero listAdapter = new ListAdaptero(tickets, activity_tickets_tecnicos.this);
                                recyclerView = findViewById(R.id.rvTicketsTecnicos);
                                recyclerView.setHasFixedSize(true);
                                recyclerView.setLayoutManager(new LinearLayoutManager(activity_tickets_tecnicos.this));
                                recyclerView.setAdapter(listAdapter);
                            }


                        }catch (Exception e){
                            System.out.println(e);
                        }
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
    }//cierre del método llenarSpinnerEstados
}