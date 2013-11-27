package br.ufscar.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
 
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
 
import android.os.AsyncTask;
import android.util.Log;
 
public class Consulta extends AsyncTask<String, Void, Boolean> {
 
    @Override
    protected Boolean doInBackground(String... params) {
 
        String linha = "";
        Boolean Erro = true;
        
        String URL = "http://api.openweathermap.org/data/2.5/weather?q=";  
        String result = "";  
        String deviceId = "xxxxx" ;  
        final String tag = "Your Logcat tag: ";  
    	
        
        
 
        if (params.length > 0)
            // faço qualquer coisa com os parâmetros
 
            try {
//            	HttpClient httpclient = new DefaultHttpClient();
//                 HttpGet request = new HttpGet(URL + params[0]);  
//                 request.addHeader("deviceId", deviceId);  
//                 ResponseHandler<String> handler = new BasicResponseHandler();  
//                 try {  
//                     result = httpclient.execute(request, handler);  
//                 } catch (ClientProtocolException e) {  
//                     e.printStackTrace();  
//                 } catch (IOException e) {  
//                     e.printStackTrace();  
//                 }  
//                 httpclient.getConnectionManager().shutdown();  
//                 Log.i(tag, result);  
//            	
            	
            	
                HttpClient client = new DefaultHttpClient();
                HttpGet requisicao = new HttpGet();
                requisicao.setHeader("Content-Type",
                        "text/plain; charset=utf-8");
                requisicao.setURI(new URI(URL + params[0]));
                HttpResponse resposta = client.execute(requisicao);
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        resposta.getEntity().getContent()));
                StringBuffer sb = new StringBuffer("");
 
                while ((linha = br.readLine()) != null) {
                    sb.append(linha);
                }
 
                br.close();
 
                linha = sb.toString();
                Log.i(tag,linha);
                Erro = false;
 
            } catch (Exception e) {
                Erro = true;
            }
 
        return Erro;
    }
}