package com.example.myappapivolley;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class ComicsDataService {


    Context context;

    //Criar contexto desta classe para passar no Toast e Singleton
    public ComicsDataService(Context context) {
        this.context = context;
    }

    //Callback para garantir que o valor retornado (as comics) esteja correto -> não precisa retornar nada como parametro
    public interface VolleyResponseLinster{
        void onError(String message);
        void onResponse (String response);
    }


    public void getComics(VolleyResponseLinster volleyResponseLinster){

        //Tratamento para encontrar o hash MD5
        String publicKey = "770d3f8235585b20d276f0935dd0cc1d";
        String privateKey = "a5ce428380cef0c711a347ba64f7047a9a3fcb01";
        String ts1 = "" + System.currentTimeMillis()/1000;
        String s = ts1 + privateKey + publicKey;

        MD5 md5 = new MD5();
        String md5Res = "";

        try {
            md5Res = md5.getMD5(s);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        //Autenticação
        //http://gateway.marvel.com/v1/public/comics?ts=1&apikey=1234&hash=ffd275c51305
        //apikey = publickey
        String url ="https://gateway.marvel.com:443/v1/public/comics?apikey="+publicKey+"&ts="+ts1+"&hash="+md5Res;

        //Utilizando Volley...

        //Request a JSON
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //String comicID = "";
                //Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show();
                volleyResponseLinster.onResponse(response.toString()); //CALLBACK

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, "ERROR_JSON", Toast.LENGTH_LONG).show();
                volleyResponseLinster.onError("ERROR_JSON"); //CALLBACK
            }
        });

        // Add the request to the RequestQueue.

        //Singleton
        MySingleton.getInstance(context).addToRequestQueue(request);
    }


    public interface ForecastByIDResponse{
        void onError(String message);
        void onResponse (List<ComicsModel> comicsModels);
    }

    public void getComicsForecastByID(String id, ForecastByIDResponse forecastByIDResponse){
        //Tratamento para encontrar o hash MD5
        String publicKey = "770d3f8235585b20d276f0935dd0cc1d";
        String privateKey = "a5ce428380cef0c711a347ba64f7047a9a3fcb01";
        String ts1 = "" + System.currentTimeMillis()/1000;
        String s = ts1 + privateKey + publicKey;

        MD5 md5 = new MD5();
        String md5Res = "";

        try {
            md5Res = md5.getMD5(s);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        //Autenticação
        //http://gateway.marvel.com/v1/public/comics?ts=1&apikey=1234&hash=ffd275c51305
        //apikey = publickey
        String url ="https://gateway.marvel.com:443/v1/public/comics/"+id+"?apikey="+publicKey+"&ts="+ts1+"&hash="+md5Res;

        //List<ComicsModel> report = new ArrayList<>()

        List<ComicsModel> comicsReportModels = new ArrayList<>();
        //Request a JSON
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //String comicID = "";
                Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show();

                try {
                    JSONArray listaDados = response.getJSONArray("data");

                    //Capturar elementos no array
                    ComicsModel umaComic = new ComicsModel();

                    for (int i = 0; i < listaDados.length(); i++){

                        JSONObject comic1_api = (JSONObject) listaDados.get(i);
                        umaComic.setId(comic1_api.getInt("id"));
                        umaComic.setDigitalId(comic1_api.getInt("digitalId"));
                        umaComic.setTitle(comic1_api.getString("title"));
                        umaComic.setIssueNumber(comic1_api.getInt("issueNumber"));
                        umaComic.setVariantDescription(comic1_api.getString("variantDescription"));
                        umaComic.setDescription(comic1_api.getString("description"));
                        umaComic.setModified(comic1_api.getString("modified"));
                        umaComic.setIsbn(comic1_api.getString("isbn"));
                        umaComic.setUpc(comic1_api.getString("upc"));
                        umaComic.setDiamondCode(comic1_api.getString("diamondCode"));
                        umaComic.setEan(comic1_api.getString("ean"));
                        umaComic.setIssn(comic1_api.getString("issn"));
                        umaComic.setFormat(comic1_api.getString("format"));
                        umaComic.setPageCount(comic1_api.getInt("pageCount"));
                        umaComic.setResourceURI(comic1_api.getString("resourceURI"));

                        comicsReportModels.add(umaComic);
                    }

                    forecastByIDResponse.onResponse(comicsReportModels);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, "ERROR_JSON", Toast.LENGTH_LONG).show();
                //volleyResponseLinster.onError("ERROR_JSON"); //CALLBACK
                forecastByIDResponse.onError("ERROR...");
            }
        });

        //Singleton
        MySingleton.getInstance(context).addToRequestQueue(request);

    }


}
