package com.foodmaster.fernandoh.foodmaster;

import android.os.AsyncTask;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by FernandoH on 30/09/2015.
 */
public class WebServiceGet extends AsyncTask<String, Integer, String> {
    public WebServiceGet(){

    }
    private static String retorno;

    @Override
    protected String doInBackground(String... params) {
        // TODO Auto-generated method stub
        try {
          retorno =  retornaDados(params[0]);
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

    HttpURLConnection urlConnection;
    private String retornaDados(String destino) throws IOException {
        StringBuilder result = new StringBuilder();

        try {
            URL url = new URL(destino);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }


        return result.toString();
    }




}
