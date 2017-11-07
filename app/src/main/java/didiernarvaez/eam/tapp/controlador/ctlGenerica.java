package didiernarvaez.eam.tapp.controlador;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import didiernarvaez.eam.tapp.Entidades.AsyncResponse;
import didiernarvaez.eam.tapp.Entidades.DireccionIP;

/**
 * Created by Didier_Narváez on 26/09/2017.
 */

public class ctlGenerica extends AsyncTask<Void, String, Boolean> {

    public AsyncResponse delegate = null;

    //Variable que almacena el resultado del servidor
    private StringBuffer buffer = null;

    //Objeto
    private Object objeto;

    //Variable que almacena la acción que se realiza en el servidor
    private String accion;

    //URL en la que se realizará la petición
    private final String ruta = DireccionIP.getIp();

    //Referencia a la barra de carga
    // ProgressBar carga;

    public ctlGenerica(Object obj, String accion) {
        this.accion = accion;
        this.objeto = obj;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        //Se define un objeto para la conexión
        HttpURLConnection conn = null;
        //Se define un buffer para leer los resultados de la conexión
        BufferedReader reader = null;

        try {
            //Se crea la conexión
            //Se establece un objeto URL con la ruta definida
            URL url = new URL(ruta);
            //Se añada la URL a la conexión
            conn = (HttpURLConnection) url.openConnection();
            //Se define el tipo de conexión (GET o POST)
            conn.setRequestMethod("POST");

            Gson gson = new Gson();
            String strJson = gson.toJson(objeto);

            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("JSON", strJson)
                    .appendQueryParameter("type", accion);

            //Se codificacan los datos añadidos con
            String query = builder.build().getEncodedQuery();

            Log.e("query", query);

            //Se define un OtputStream para añadir los datos definidos a la conexión
            OutputStream os = conn.getOutputStream();

            //Se pasan a un Buffer
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8")
            );
            // Se añaden
            writer.write(query);

            //Se confirma
            writer.flush();

            //Se cierra la edición
            writer.close();
            os.close();

            //Se conecta, recibe datos y los lee
            //Se ejecuta la conexión

            //publishProgress("Se envian los datos");
            conn.connect();

            //con un input stream se obtienen los datos de la conexión
            InputStream stream = conn.getInputStream();

            //Se define un reader para leer los datos, asociandolo al input stream
            reader = new BufferedReader(new InputStreamReader(stream));

            //se inicializa el buffer para almacenar como string los resultados
            buffer = new StringBuffer();

            //Variable temporal para concatenar los datos leidos
            String line;

            //Mientras lo que lea es diferente de vacio
            while ((line = reader.readLine()) != null) {
                //Añadalos al buffer global
                buffer.append(line);
            }


        } catch (MalformedURLException e) {
            //publishProgress("Error mal estructura URL " + e.getMessage());
            e.printStackTrace();
            return false;
        } catch (IOException i) {
            //publishProgress("Error IO " + i.getMessage());
            i.printStackTrace();
            return false;
        } finally {
            //Desconecta la conexión activa
            if (conn != null) {
                conn.disconnect();
            }
            try {
                //Cerramos los readers
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                //publishProgress("Error al final " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    /**
     * @Override protected void onProgressUpdate(String... values) {
     * Toast.makeText(activity, values[0], Toast.LENGTH_SHORT).show();
     * }
     **/
    @Override
    protected void onPostExecute(Boolean result) {
        if (result) {
            try {
                String res = buffer.toString();
                JSONObject jsonRes = new JSONObject(res);

                if (jsonRes.has("registro")) {

                    delegate.processFinish(jsonRes);

                } else {

                    JSONArray jsonArray = new JSONArray(buffer.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject json = jsonArray.getJSONObject(i);
                        delegate.processFinish(json);
                    }

                }
            } catch (JSONException e) {

            }
        }

    }
    // Se oculta la barra de carga
    //carga.setVisibility(View.INVISIBLE);
    /**try {
     if (result) {

     if (accion.equals("registrar")) {
     //Como el resultado obtenido es un array JSON, se pasa el string JSON al array
     JSONObject jsonObject = new JSONObject(buffer.toString());
     String res = jsonObject.getString("registro");
     if (res.equals("1")) {
     Toast.makeText(activity, "Registro exitoso", Toast.LENGTH_SHORT).show();
     } else {
     Toast.makeText(activity, "La cuenta ya existe", Toast.LENGTH_SHORT).show();
     }
     }

     if (accion.equals("eliminar")) {
     //Como el resultado obtenido es un array JSON, se pasa el string JSON al array
     JSONObject jsonObject = new JSONObject(buffer.toString());
     String res = jsonObject.getString("eliminar");
     if (res.equals("1")) {
     Toast.makeText(activity, "Se ha eliminado exitosamente", Toast.LENGTH_SHORT).show();
     } else {
     Toast.makeText(activity, "No existe", Toast.LENGTH_SHORT).show();
     }
     }

     if (accion.equals("editar")) {
     //Como el resultado obtenido es un array JSON, se pasa el string JSON al array
     JSONObject jsonObject = new JSONObject(buffer.toString());
     String res = jsonObject.getString("editado");
     if (res.equals("1")) {
     Toast.makeText(activity, "Se ha consignado exitosamente", Toast.LENGTH_SHORT).show();
     } else {
     Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show();
     }
     }

     /**  if (accion.equals("buscar")) {
     //Como el resultado obtenido es un array json, se pasa
     JSONArray jsonArray = new JSONArray(buffer.toString());
     // en string json a JSONArray
     if (jsonArray.length() == 0) {
     Toast.makeText(activity, "La cuenta no existe", Toast.LENGTH_SHORT).show();
     Consignar.encontrado = false;
     } else {
     //Se saca el objeto del array y se pasa a un objeto JSON
     JSONObject row = jsonArray.getJSONObject(0);
     Log.e("row", row.toString());
     //Se saca las variables del objeto

     double monto = row.getDouble("monto");
     Consignar.montoAcutal = monto;
     Consignar.encontrado = true;

     }
     }

     if (accion.equals("listar")) {

     JSONArray jsonArray = new JSONArray(buffer.toString());
     //ArrayList<Cuenta> cuentas = new ArrayList<>();

     for (int i = 0; i < jsonArray.length(); i++) {
     JSONObject row = jsonArray.getJSONObject(i);

     int cod = row.getInt("codigo");
     double monto = row.getDouble("monto");
     String tipo = row.getString("tipo");

     /**Cuenta c = new Cuenta(cod, monto, tipo);
     cuentas.add(c);
     }

     }

     } else {
     Toast.makeText(activity, "Error en la operación", Toast.LENGTH_SHORT).show();
     }
     } catch (JSONException e) {
     Toast.makeText(activity, "Error tratando el resultado " + e.getMessage(),
     Toast.LENGTH_SHORT).show();
     e.printStackTrace();
     }
     } **/

}
