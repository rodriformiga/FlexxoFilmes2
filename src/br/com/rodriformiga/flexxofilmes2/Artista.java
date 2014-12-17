package br.com.rodriformiga.flexxofilmes2;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import br.com.rodriformiga.flexxofilmes2.ws.FilmeTask;
import br.com.rodriformiga.flexxofilmes2.ws.ObjectCallback;

public class Artista extends Activity implements ObjectCallback {

   private String nome;
   private ProgressBar prog;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_artista);
      nome = getIntent().getExtras().getString("nome");
      new FilmeTask(this).execute(nome);
      ((TextView) findViewById(R.id.txtNomeFilme)).setText(nome);
      prog = (ProgressBar) findViewById(R.id.proBar);
   }

   @Override
   public void sucess(Object result) {

      JSONObject json = (JSONObject) result;
      if (json == null)
         return;
      String art;
      try {
         art = json.getString("Actors");
         ((TextView) findViewById(R.id.txtArtistas)).setText(art);
      } catch (JSONException e) {
         e.printStackTrace();
      }
      prog.setVisibility(View.GONE);
   }

   @Override
   public void error(Exception ex) {
      Toast.makeText(getApplicationContext(), ex.getMessage(),
            Toast.LENGTH_LONG).show();
      finish();
   }
}
