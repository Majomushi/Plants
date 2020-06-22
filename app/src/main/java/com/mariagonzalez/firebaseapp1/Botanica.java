package com.mariagonzalez.firebaseapp1;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Botanica {
    //Backend-Parser de Trefle API para busqueda de Especies.
    //FALTA HACER PRUEBAS EN UNA APLICACION REAL.

    private String token = "NzNsNjRtaUVKc1BuajFmTjh1VlEydz09";
    private String endpoint = "https://trefle.io/api"; // Esta api esta sujeta a cambiar en el futuro proximo :v

    public List<Planta> buscar(String busqueda) throws NullPointerException{
        try{
            URL url = new URL(endpoint+"/species?q="+busqueda);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.addRequestProperty("Accept","application/json");
            connection.addRequestProperty("Authorization","Bearer "+token);
            connection.connect();

            int status = connection.getResponseCode();

            switch(status){
                case 200:
                case 201:
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while((line = bufferedReader.readLine()) != null){
                        stringBuilder.append(line+"\n");
                    }
                    bufferedReader.close();
                    //System.out.println(stringBuilder.toString());
                    JSONArray jsonArray = new JSONArray(stringBuilder.toString());
                    List<Planta> lista = new ArrayList<>();

                    for(int i = 0; i<jsonArray.length(); i++){
                        JSONObject temp = jsonArray.getJSONObject(i);
                        Planta planta = new Planta();

                        String nombreComun = temp.getString("common_name");
                        String nombreCientifico = temp.getString("scientific_name");
                        String familia = temp.getString("family_common_name");
                        boolean especiePrincipal = temp.getBoolean("is_main_species");

                        //Sanitizacion
                        if(nombreComun.equals("null")){
                            nombreComun = "Desconocido";
                        }
                        if(nombreCientifico.equals("null")){
                            nombreCientifico = "Desconocido";
                        }
                        if(familia.equals("null")){
                            familia = "Desconocida";
                        }

                        planta.setNombreCientifico(nombreCientifico);
                        planta.setNombreComun(nombreComun);
                        planta.setFamilia(familia);
                        planta.setEspeciePrincipal(especiePrincipal);
                        lista.add(planta);
                    }
                    return lista;
            }
            connection.disconnect();
        }catch(Exception e){
            System.out.println("Error al obtener datos. " + e.getMessage());
        }
        return null;
    }
}
