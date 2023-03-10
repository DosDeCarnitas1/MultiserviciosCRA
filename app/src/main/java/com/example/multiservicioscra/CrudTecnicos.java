package com.example.multiservicioscra;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CrudTecnicos extends AppCompatActivity {

    EditText etnombreEmp, etapellidoEmp, etpuesto, etusuarioEmp, etcontraseñaEmp;

    ImageButton ibtnregistrar, ibtnconsultar, ibtnmodificar, ibtneliminar;

    RequestQueue requestQueue;

    String ip = "192.168.100.9";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_tecnicos);

        etnombreEmp = findViewById(R.id.etnombreEmp);
        etapellidoEmp = findViewById(R.id.etapellidoEmp);
        etpuesto = findViewById(R.id.etpuesto);
        etusuarioEmp = findViewById(R.id.etusuarioEmp);
        etcontraseñaEmp = findViewById(R.id.etcontraseñaEmp);

        ibtnregistrar = findViewById(R.id.ibtnregistrar);
        ibtnconsultar = findViewById(R.id.ibtnconsultar);
        ibtnmodificar = findViewById(R.id.ibtnmodificar);
        ibtneliminar = findViewById(R.id.ibtneliminar);

        requestQueue = Volley.newRequestQueue(this);


        ibtnconsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                consulta("http://" + ip + "/MCRAAndroidphps/consultaUsuario.php?codigo=" + etusuarioEmp.getText());
            }
        });//cierre del onclick consulta

        ibtnregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                operaciones("http://" + ip + "/MCRAAndroidphps/registroTecnico.php");
            }
        });//cierre del onclick registrar

        ibtnmodificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                operaciones("http://" + ip + "/ejemplomovil/modificaEmp.php");
            }
        });//cierre del onclick modificar

        ibtneliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                operaciones("http://" + ip + "/ejemplomovil/eliminaEmp.php");
            }
        });//cierre del onclick eliminar
    }//cierre del metodo oncreate

    private void operaciones(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) { //en caso de una respuesta exitosa
                System.out.println(response);
                if (response.equals("1")) {
                    Toast.makeText(getApplicationContext(), "Operación exitosa", Toast.LENGTH_LONG).show();
                    etnombreEmp.setText("");
                    etapellidoEmp.setText("");
                    etpuesto.setText("");
                    etusuarioEmp.setText("");
                    etcontraseñaEmp.setText("");
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
                parametros.put("nombre", etnombreEmp.getText().toString());
                parametros.put("apellido", etapellidoEmp.getText().toString());
                parametros.put("puesto", etpuesto.getText().toString());
                parametros.put("usuario", etusuarioEmp.getText().toString());
                parametros.put("password", etcontraseñaEmp.getText().toString());
                parametros.put("tipo", "Empleado");
                return parametros;
            }
        };
        //remove caching
        stringRequest.setShouldCache(false);
        // Wait 30 seconds and don't retry more than once
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(stringRequest);
    }//cierre del método operaciones

    private void consulta(String url){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (url, new Response.Listener<JSONArray>() {
                    //respuesta exitosa por parte del servidor
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONObject jsonObject = null;
                        for (int i = 0; i < response.length(); i++) {
                            try {//extrayendo el objecto json de cada una de las posiciones del arreglo
                                jsonObject = response.getJSONObject(i);

                                etnombreEmp.setText(jsonObject.getString("nombre"));
                                etapellidoEmp.setText(jsonObject.getString("apellido"));
                                etpuesto.setText(jsonObject.getString("puesto"));
                                etusuarioEmp.setText(jsonObject.getString("usuario"));
                                etcontraseñaEmp.setText(jsonObject.getString("contraseña"));

                            }//cierre del try
                            catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }//cierre del for
                    }//cierre del onResponse
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        System.out.println(error);
                        Toast.makeText(getApplicationContext(),"Empleado no encontrado",Toast.LENGTH_LONG).show();

                        etnombreEmp.setText("");
                        etapellidoEmp.setText("");
                        etpuesto.setText("");
                        etusuarioEmp.setText("");
                        etcontraseñaEmp.setText("");
                    }

                });
        requestQueue.add(jsonArrayRequest);
    }//cierre del método consulta
}//cierre de la clase