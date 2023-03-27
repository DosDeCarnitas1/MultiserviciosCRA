package com.example.multiservicioscra;

import static com.example.multiservicioscra.MainActivity.usuarioIdParaConsultas;
import static com.example.multiservicioscra.MainActivity.usuarioTipoParaConsultas;
import static com.example.multiservicioscra.MainActivity.ip;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
    private SwipeRefreshLayout swipeContainer;

//    String ip = "192.168.100.9";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_refacciones);

        requestQueue = Volley.newRequestQueue(this);
        sprefaccion = findViewById(R.id.sprefaccion);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        System.out.println("Este es el id del usuario: "+usuarioIdParaConsultas);
        System.out.println("Este es el tipo del usuario: "+usuarioTipoParaConsultas);
        System.out.println("http://"+ip+"/MCRAAndroidphps/consultarRefacciones.php");

        llenarSpinnerTipos("http://"+ip+"/MCRAAndroidphps/consultarRefacciones.php");

        sprefaccion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
//                if(position != 0){
//                    llenarSpinnerTipos("http://"+ip+"/MCRAAndroidphps/consultarRefacciones.php");
                    filtrar();
//                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                int indexSp = sprefaccion.getSelectedItemPosition();
                llenarSpinnerTipos("http://"+ip+"/MCRAAndroidphps/consultarRefacciones.php", true);
                sprefaccion.setSelection(indexSp);
                filtrar();
                System.out.println("indexSP: "+indexSp);
                swipeContainer.setRefreshing(false);

            }
        });

    }
    private JSONObject jsonObject= null;

    private void llenarSpinnerTipos(String url){
        JsonArrayRequest jsonarrayRequest = new JsonArrayRequest
                (url, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
//                        JSONObject jsonObject = null;
//                        System.out.println("veamos que es esto: " + jsonObject);
                        for (int i = 0; i < response.length(); i++) {
                            try {//extrayendo el objecto json de cada una de las posiciones del arreglo
                                jsonObject = response.getJSONObject(i);

                                //llenamos el spinner segun los estados que esten disponibles para el usuario
                                ArrayList<String> filtros = new ArrayList<String>();
                                filtros.add("Todas las refacciones");
                                for (int j = 0; j < jsonObject.getJSONArray("refaccionTipo").length(); j++) {
                                    filtros.add(jsonObject.getJSONArray("refaccionTipo").getString(j));
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
                        Toast.makeText(getApplicationContext(),"eror de volley",Toast.LENGTH_LONG).show();
                    }
                });
        requestQueue.add(jsonarrayRequest);
    }


    private void llenarSpinnerTipos(String url, boolean activar){
        JsonArrayRequest jsonarrayRequest = new JsonArrayRequest
                (url, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
//                        JSONObject jsonObject = null;
//                        System.out.println("veamos que es esto: " + jsonObject);
                        for (int i = 0; i < response.length(); i++) {
                            try {//extrayendo el objecto json de cada una de las posiciones del arreglo
                                jsonObject = response.getJSONObject(i);
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
                        Toast.makeText(getApplicationContext(),"eror de volley",Toast.LENGTH_LONG).show();
                    }
                });
        requestQueue.add(jsonarrayRequest);
    }


    ListAdapter2 listAdapter2;
    RecyclerView recyclerView;
    private void filtrar(){
        String filtrado = sprefaccion.getSelectedItem().toString();
        try{
            refacciones = new ArrayList<>();
            refacciones.clear();
//            System.out.println("FiltradoVar: "+filtrado);
            for (int i = 0; i < jsonObject.length()-1; i++) {
//                System.out.println(i);
                String index = Integer.toString(i);
//                System.out.println(jsonObject.getJSONObject(index));

                if(jsonObject.getJSONObject(index).getString("tipoRefaccion").equals(filtrado)){
//                    System.out.println("nombre: "+jsonObject.getJSONObject(index).getString("nombre")+
//                            "cantidad: "+jsonObject.getJSONObject(index).getInt("cantidad")+
//                            "imagenes: "+jsonObject.getJSONObject(index).getString("imagenes"));
                    refacciones.add(new ListRefaccion(jsonObject.getJSONObject(index).getString("nombre"),
                            jsonObject.getJSONObject(index).getInt("cantidad"),
                            jsonObject.getJSONObject(index).getInt("id")));
                } else if ("Todas las refacciones".equals(filtrado)) {
                    refacciones.add(new ListRefaccion(jsonObject.getJSONObject(index).getString("nombre"),
                            jsonObject.getJSONObject(index).getInt("cantidad"),
                            jsonObject.getJSONObject(index).getInt("id")));
                }
            }
//            System.out.println("empty?:"+refacciones.isEmpty());
//            System.out.println("refaccion"+refacciones.get(0));
            if(!refacciones.isEmpty()){
//                emptyView.setVisibility(View.GONE);
                listAdapter2 = new ListAdapter2(refacciones, CrudRefacciones.this);
                recyclerView = findViewById(R.id.rvrefaccion);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(CrudRefacciones.this));
                recyclerView.setAdapter(listAdapter2);
                recyclerView.setVisibility(View.VISIBLE);
            }else{
                System.out.println("gone");
                recyclerView.setVisibility(View.GONE);
//                emptyView.setVisibility(View.VISIBLE);
            }
//            }
        }catch (Exception e){
            System.out.println(e);
        }
    }
}