package br.com.rodriformiga.flexxofilmes2;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import br.com.rodriformiga.flexxofilmes2.db.FilmesDatabaseHelper;

public class Detalhe extends Activity {

   private TextView txtNome;
   private ImageView img01;
   private TextView txtDesc;
   private RatingBar ratBar;
   private SQLiteDatabase db;
   private TextView txtPonto;
   private int id;
   private String nome;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_detalhe);
      db = new FilmesDatabaseHelper(getApplicationContext())
            .getWritableDatabase();
      img01 = (ImageView) findViewById(R.id.img01);
      txtNome = (TextView) findViewById(R.id.txtNome);
      txtDesc = (TextView) findViewById(R.id.txtDesc);
      txtPonto = (TextView) findViewById(R.id.txtPonto);
      ratBar = (RatingBar) findViewById(R.id.ratBar);

      id = getIntent().getExtras().getInt("id");
      String[] param = { Integer.toString(id) };

      Cursor cur = db.rawQuery("SELECT * FROM filmes WHERE id = ?", param);
      cur.moveToFirst();
      if (!cur.isAfterLast()) {
         nome = cur.getString(1);
         txtNome.setText(nome);
         txtDesc.setText(cur.getString(2));
         if (!cur.getString(5).isEmpty()) {
            carregarFotoClick(cur.getString(5));
         }
      }

      cur = db
            .rawQuery(
                  "SELECT COUNT(1) cont, SUM(nota) nota FROM notas WHERE filme_id = ?",
                  param);
      cur.moveToFirst();
      if (!cur.isAfterLast()) {
         float avg = (cur.getInt(0) == 0) ? 0f : (float)(cur.getInt(1) / (float)cur.getInt(0));
         txtPonto.setText(String.format("Média: %.2f (%d/%d)", avg, cur.getInt(1), cur.getInt(0)));
      }

   }

   public void carregarFotoClick(String nome) {
      InputStream foto;
      try {
         foto = getAssets().open(nome);
         img01.setImageBitmap(BitmapFactory.decodeStream(foto));
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   public void OkClick(View view) {
      // Toast.makeText(getApplicationContext(), "rato: "+ratBar.getProgress(),
      // Toast.LENGTH_SHORT).show();
      if (ratBar.getProgress() > 0) {
         String[] param = { Integer.toString(id),
               Integer.toString(ratBar.getProgress()) };
         db.execSQL("INSERT INTO notas (filme_id, nota) VALUES (?,?);", param);
      }
      finish();
   }
   public void ArtistaClick(View view) {
      Intent i = new Intent(getApplicationContext(), Artista.class);
      i.putExtra("nome", nome);
      startActivity(i);
   }
}
