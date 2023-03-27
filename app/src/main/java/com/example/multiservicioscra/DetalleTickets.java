package com.example.multiservicioscra;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import static com.example.multiservicioscra.MainActivity.ip;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetalleTickets extends AppCompatActivity {

    JSONObject jsonObj;
    TextView tvTituloTicket, tvFechaCreado, tvFechaActualizado, tvDescripcion, tvZona, tvTecnicoAsig;
    Button btnCambiarEstado;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_tickets);

        tvTituloTicket = findViewById(R.id.tvTituloTicket);
        tvFechaCreado = findViewById(R.id.tvFechaCreado);
        tvFechaActualizado = findViewById(R.id.tvFechaActualizado);
        tvDescripcion = findViewById(R.id.tvDescripcion);
        tvZona = findViewById(R.id.tvZona);
        tvTecnicoAsig = findViewById(R.id.tvTecnicoAsig);

        btnCambiarEstado = findViewById(R.id.btnCambiarEstado);

        requestQueue = Volley.newRequestQueue(this);

        String toaste = null;
        try {
            jsonObj = new JSONObject(getIntent().getStringExtra("ticket"));
            System.out.println("json:"+jsonObj);

            toaste = "Titulo: "+jsonObj.getString("titulo")+
                  "\n FechaCreado: "+jsonObj.getString("created_at")+
                  "\n FechaActu: "+jsonObj.getString("updated_at")+
                  "\n Descripcion: "+jsonObj.getString("descripcion")+
                  "\n Zona: "+jsonObj.getString("zona")+
                  "\n Tecnico: "+jsonObj.getString("tecnicoAsignado");

            tvTituloTicket.setText(jsonObj.getString("titulo"));
            tvFechaCreado.setText(jsonObj.getString("created_at"));
            tvFechaActualizado.setText(jsonObj.getString("updated_at"));
            tvDescripcion.setText(jsonObj.getString("descripcion"));
            tvZona.setText(jsonObj.getString("zona"));
            tvTecnicoAsig.setText(jsonObj.getString("tecnicoAsignado"));

            actualizarBoton();
//            switch (jsonObj.getString("estado")){
//                case "Pendiente":
//                    btnCambiarEstado.setText("Cambiar a Inactivo(Completado)");
//                    break;
//                case "Activo":
//                    btnCambiarEstado.setText("Cambiar a Pendiente");
//                    break;
//                case "Inactivo":
//                    btnCambiarEstado.setText(jsonObj.getString("estado"));
//                    btnCambiarEstado.setEnabled(false);
//                    break;
//            }

        } catch (JSONException e) {
            System.out.println(e);
        }

        System.out.println(toaste);

        btnCambiarEstado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] estados = {"Activo", "Pendiente","Inactivo"};
                String estadoTemp = "Inactivo";
                String sigEstado = btnCambiarEstado.getText().toString();
                String estado = "";
//                int index = sigEstado.indexOf(estadoTemp);
                System.out.println(sigEstado);
//                System.out.println(index);
//                System.out.println("de sigEstado: "+sigEstado.substring(index, index+estadoTemp.length()));

                for (int i = 0; i < estados.length; i++) {
                    int index = sigEstado.indexOf(estados[i]);
                    if(index != -1){
                        if(sigEstado.substring(index, index+estados[i].length()).equals(estados[i])){
                            System.out.println("si \n"+
                                    "substring: "+sigEstado.substring(index, index+estados[i].length())+
                                    "\nestados: "+estados[i]);
                            String estadO = sigEstado.substring(index, index+estados[i].length());

                            try {
                                if(estadO.equals("Inactivo")){
                                    actualizarEstado("http://"+ip+"/MCRAAndroidphps/actualizarTicket(estado).php",
                                                     estadO,
                                                     jsonObj.getString("id"));

                                }else if(estadO.equals("Pendiente")){
                                    actualizarEstado("http://"+ip+"/MCRAAndroidphps/actualizarTicket(estado)2.php",
                                            estadO,
                                            jsonObj.getString("id"));
                                }

                            }catch (Exception e){
                                System.out.println(e);
                            }
                        }
                    }
                }

//                actualizarEstado("http://"+ip+"/MCRAAndroidphps/actualizarTicket.php",
//                                );
            }
        });
    }

    private void actualizarBoton() throws JSONException {
        switch (jsonObj.getString("estado")){
            case "Pendiente":
                System.out.println("estado: "+jsonObj.getString("estado"));
                btnCambiarEstado.setText("Cambiar a Inactivo(Completado)");
                break;
            case "Activo":
                System.out.println("estado: "+jsonObj.getString("estado"));
                btnCambiarEstado.setText("Cambiar a Pendiente");
//                btnCambiarEstado.setEnabled(false);
                break;
            case "Inactivo":
                System.out.println("estado: "+jsonObj.getString("estado"));
                btnCambiarEstado.setText("Inactivo");
                btnCambiarEstado.setEnabled(false);
                break;
        }
    }

    private void actualizarEstado(String url, String estado, String ticket_id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) { //en caso de una respuesta exitosa
                System.out.println(response);
                if (response.equals("1")) {
                    try {
//                        jsonObj.put("estado", "Inactivo");
//                        btnCambiarEstado.setText("Inactivo");
                        conssultarElmMaliditoTicketRecienActualizado("http://"+ip+"/MCRAAndroidphps/consultarUnTicket.php?ticket_id="+jsonObj.getString("id"));
                        actualizarBoton();
                    }catch (Exception e){
                        System.out.println(e);
                    }
                    Toast.makeText(getApplicationContext(), "Operación exitosa", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(getApplicationContext(), "Operación no exitosa", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) { //cuando se ejecuta la petición en el servidor y hay un error respondera con un errorRespose
                Toast.makeText(getApplicationContext(), "Error" + error.toString(), Toast.LENGTH_LONG).show();
                System.out.println("Error Volley: " + error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("estado", estado);
                parametros.put("ticket_id", ticket_id);
                return parametros;
            }
        };
        //remove caching
        stringRequest.setShouldCache(false);
        // Wait 30 seconds and don't retry more than once
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(stringRequest);
    }//cierre del método operaciones


    private void conssultarElmMaliditoTicketRecienActualizado(String url){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (url, new Response.Listener<JSONObject>() {
                    //respuesta exitosa por parte del servidor
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
//                            for (int i = 0; i < response.length(); i++) {
                                jsonObj = response;
                                System.out.println("jsonNuevo: "+jsonObj);
                                actualizarBoton();

//                            }

                        }catch (Exception e){
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
        requestQueue.add(jsonObjectRequest);
    }//cierre del método llenarSpinnerEstados
}