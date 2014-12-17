package br.com.rodriformiga.flexxofilmes2.ws;

import java.net.URL;
import java.net.URLConnection;

import org.json.JSONObject;

import android.os.AsyncTask;
import br.com.rodriformiga.flexxofilmes2.IOHelper;

public class FilmeTask extends AsyncTask<String, Integer, Object> {

   private ObjectCallback callback;
   private Exception err;

   public FilmeTask(ObjectCallback callback) {
      this.callback = callback;
   }

   @Override
   protected Object doInBackground(String... params) {
      try {
         String nome = params[0];
         nome = nome.replace(" ", "%20");
         URL url = new URL(String.format(
               "http://www.omdbapi.com/?t=%s&y=&plot=short&r=json", nome));
         URLConnection urlConnection = url.openConnection();
         String result = IOHelper.getString(urlConnection.getInputStream());
         return new JSONObject(result);
      } catch (Exception e) {
         // return null;
         // this.callback.error(e);
         err = e;
         return null;
      }
   }

   @Override
   protected void onPostExecute(Object result) {
      if (err != null)
         this.callback.error(err);
      else
         this.callback.sucess(result);
   }

}
