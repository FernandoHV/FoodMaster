package com.foodmaster.fernandoh.foodmaster;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by FernandoH on 30/09/2015.
 */
public class WebServicePost extends AsyncTask<WebServicePost.ParamentrosPost, Integer, String> {
    public WebServicePost(){

    }
    private static String retorno;


    @Override
    protected String doInBackground(ParamentrosPost... params) {
        // TODO Auto-generated method stub
        try {
          retorno =  retornaDados(params);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return retorno;
    }

    protected void onPostExecute(String result){
        //setRespostaServidor(retorno);
    }

    protected void onProgressUpdate(Integer... progress){
        // pb.setProgress(progress[0]);

    }

    HttpURLConnection conn;
    private String retornaDados(WebServicePost.ParamentrosPost... p) throws IOException {
        StringBuilder result = new StringBuilder();
        String response = "";

        try {
            URL link = new URL(p[0].url);
            conn = (HttpURLConnection) link.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            HashMap <String,String> map = new HashMap<>();
            for (int i = 0; i < p[0].nomeCampo.size(); i ++) {
                map.put(p[0].nomeCampo.get(i).toString(),p[0].valorCampo.get(i).toString());
            }


            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(map));

            writer.flush();
            writer.close();
            os.close();
            int responseCode=conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response+=line;
                }
            }
            else {
                response="";

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return response;
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }



    public static class ParamentrosPost {
        ArrayList<String> nomeCampo;
        ArrayList<String> valorCampo;
        String url;
        ParamentrosPost(String url, ArrayList nomeCampo, ArrayList valorCampo) {
            this.nomeCampo = nomeCampo;
            this.valorCampo = valorCampo;
            this.url = url;
        }
    }

}
