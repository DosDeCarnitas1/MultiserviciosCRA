package com.example.multiservicioscra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    public static int usuarioIdParaConsultas = 0;
    public static String usuarioTipoParaConsultas = "";
    public static String ip = "192.168.100.9";
    EditText etusuario,etcontraseña;
    Button btningresar;
    CheckBox cbxRecuerdame;
    RequestQueue requestQueue;

//    String ip = "192.168.100.76";
//    String ip = "192.168.100.9";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etusuario = findViewById(R.id.etusuario);
        etcontraseña = findViewById(R.id.etcontraseña);
        btningresar = findViewById(R.id.btningresar);
        cbxRecuerdame = findViewById(R.id.cbxRecuerdame);
        requestQueue = Volley.newRequestQueue(this);

        if(true){
            SharedPreferences shPf = getSharedPreferences("credenciales",MainActivity.MODE_PRIVATE);
            SharedPreferences.Editor editor = shPf.edit();
            editor.clear();
            editor.apply();
        }

        SharedPreferences shPf = getSharedPreferences("credenciales",MainActivity.MODE_PRIVATE);
        String usuario = shPf.getString("user", null);
        if(usuario != null){
            etusuario.setText(shPf.getString("user", null));
            etcontraseña.setText(shPf.getString("password", null));
            inicioSesion("http://"+ip+"/MCRAAndroidphps/consultaUsuario.php?usuario="+ etusuario.getText().toString());
        }

        btningresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //la ip 10.0.2.2 android la reconoce como localhost
                //inicioSesion("http://10.0.2.2:8080/ejemplomovil/validaentradaMovil.php?usuario="+ etusuario.getText().toString());
                //utilizando la ip de la laptop o computadora.
                //inicioSesion("http://192.168.100.76/MCRAAndroidphps/consultaUsuario.php?usuario="+ etusuario.getText().toString());
                inicioSesion("http://"+ip+"/MCRAAndroidphps/consultaUsuario.php?usuario="+ etusuario.getText().toString());
//                inicioSesion("http://192.168.90.78/MCRAAndroidphps/consultaUsuario.php?usuario="+ etusuario.getText().toString());
                //conexion desde el dispositivo movil deben usar un hosting (conexion segura https) o tener un certificado digital (SSL)
                //inicioSesion("https://ip o nombre_domino del hosting/ejemplomovil/validaentradaMovil.php?usuario="+ etusuario.getText().toString());
            }   
        });

    }//cierre del método oncreate

    /*Agregar métodos personalizados*/
    private void inicioSesion(String url){
        JsonArrayRequest jsonarrayRequest = new JsonArrayRequest
                (url, new Response.Listener<JSONArray>() {
                    //respuesta exitosa por parte del servidor
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONObject jsonObject= null;
                        System.out.println("veamos que es esto: "+jsonObject);
                        for(int i=0; i < response.length();i++){
                            try{//extrayendo el objecto json de cada una de las posiciones del arreglo
                                jsonObject = response.getJSONObject(i);

                                //la validación de si el password es correcto
                                if(jsonObject.getString("password").equals(etcontraseña.getText().toString())){
                                    Toast.makeText(getApplicationContext(),"Bienvenido",Toast.LENGTH_LONG).show();
                                    Intent ventana=null;
                                    usuarioIdParaConsultas = Integer.parseInt(jsonObject.getString("id"));
                                    usuarioTipoParaConsultas = jsonObject.getString("tipo");
                                    if(jsonObject.getString("tipo").equals("Empleado")) {
                                        Toast.makeText(getApplicationContext(),jsonObject.getString("puesto")+ " "+jsonObject.getString("nombre")+" "+jsonObject.getString("apellido"),Toast.LENGTH_LONG).show();
                                        ventana = new Intent(MainActivity.this, MenuTecnico.class);
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(),jsonObject.getString("nombre")+" "+jsonObject.getString("apellido"),Toast.LENGTH_LONG).show();
                                        ventana = new Intent(MainActivity.this, MenuMaster.class);
                                    }

                                    if(cbxRecuerdame.isChecked()){
                                        recuerdame();
                                    }
                                    startActivity(ventana);
                                }
                                else{
                                    Toast.makeText(getApplicationContext(),"Usuario y contraseña incorrectos",Toast.LENGTH_LONG).show();
                                }//cierre del else
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

    //<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
       // android:layout_width="match_parent"
       // android:layout_height="match_parent"
      //  android:orientation="vertica"

      //  android:x="@drawable/Fondo"

      //  android:padding="10dp">


    private void recuerdame(){
        SharedPreferences shPf = getSharedPreferences("credenciales",MainActivity.MODE_PRIVATE);

        String password = etcontraseña.getText().toString();
        String usuario = etusuario.getText().toString();

        SharedPreferences.Editor editor = shPf.edit();
        editor.putString("user", usuario);
        editor.putString("password", password);

        editor.commit();
    }
}