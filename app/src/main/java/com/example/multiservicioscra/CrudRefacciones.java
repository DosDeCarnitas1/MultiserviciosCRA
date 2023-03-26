package com.example.multiservicioscra;

import static com.example.multiservicioscra.MainActivity.usuarioIdParaConsultas;
import static com.example.multiservicioscra.MainActivity.usuarioTipoParaConsultas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.List;

public class CrudRefacciones extends AppCompatActivity {

    Spinner sprefaccion;

    RequestQueue requestQueue;

    ArrayAdapter arrayAdapter;

    List<ListRefaccion> refacciones;

    String ip = "192.168.100.9";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_refacciones);

        requestQueue = Volley.newRequestQueue(this);
        sprefaccion = findViewById(R.id.sprefaccion);

        System.out.println("Este es el id del usuario: "+usuarioIdParaConsultas);
        System.out.println("Este es el tipo del usuario: "+usuarioTipoParaConsultas);
        System.out.println("http://"+ip+"/MCRAAndroidphps/consultarRefacciones.php?tipoUsuario="+usuarioTipoParaConsultas+"&id_usuario="+usuarioIdParaConsultas);

        llenarSpinnerTipos("http://"+ip+"/MCRAAndroidphps/consultarRefacciones.php?tipoUsuario="+usuarioTipoParaConsultas+"&id_usuario="+usuarioIdParaConsultas);

        sprefaccion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if(position != 0){
                    String filtrado = sprefaccion.getSelectedItem().toString();
                    try{
                        ArrayList<String> elements = new ArrayList<String>();
                        for (int i = 0; i < jsonObject.length()-1; i++) {
                            System.out.println(i);
                            String index = Integer.toString(i);
                            System.out.println(jsonObject.getJSONObject(index));

                            if(jsonObject.getJSONObject(index).getString("Refacciones").equals(filtrado)){
                                refacciones.add(new ListRefaccion(jsonObject.getJSONObject(index).getString("nombre"),
                                                                jsonObject.getJSONObject(index).getInt("cantidad")));
                            }
                        }
                        if(!refacciones.isEmpty()){
                            ListAdapter2 listAdapter2 = new ListAdapter2(refacciones, CrudRefacciones.this);
                            RecyclerView recyclerView = findViewById(R.id.rvrefaccion);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(CrudRefacciones.this));
                            recyclerView.setAdapter(listAdapter2);
                        }
                    }catch (Exception e){
                        System.out.println(e);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private JSONObject jsonObject= null;

    private void llenarSpinnerTipos(String url){
        JsonArrayRequest jsonarrayRequest = new JsonArrayRequest
                (url, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONObject jsonObject = null;
                        System.out.println("veamos que es esto: " + jsonObject);
                        for (int i = 0; i < response.length(); i++) {
                            try {//extrayendo el objecto json de cada una de las posiciones del arreglo
                                jsonObject = response.getJSONObject(i);

                                //llenamos el spinner segun los estados que esten disponibles para el usuario
                                ArrayList<String> filtros = new ArrayList<String>();
                                filtros.add("Elije una opcion");
                                for (int j = 0; j < jsonObject.getJSONArray("Estados").length(); j++) {
                                    filtros.add(jsonObject.getJSONArray("Estados").getString(j));
                                }

                                arrayAdapter = new ArrayAdapter<String>(CrudRefacciones.this, android.R.layout.simple_spinner_item, filtros);
                                sprefaccion.setAdapter(arrayAdapter);

                            } catch (JSONException e) {
                                e.printStackTrace();
                                System.out.println("hubo un error mi shavo: " + e);
                            }
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        System.out.println(error);
                        //Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(),"Usuario y contraseña incorrectos",Toast.LENGTH_LONG).show();
                    }
                });
        requestQueue.add(jsonarrayRequest);

    }
}