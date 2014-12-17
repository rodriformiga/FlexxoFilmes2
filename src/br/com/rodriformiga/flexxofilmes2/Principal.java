package br.com.rodriformiga.flexxofilmes2;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import br.com.rodriformiga.flexxofilmes2.db.FilmesDatabaseHelper;
import br.com.rodriformiga.flexxofilmes2.entities.Filme;

public class Principal extends Activity implements OnItemClickListener {

   SQLiteDatabase db;
   List<Filme> filmes;
   private ListView listaFilme;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_principal);

      db = new FilmesDatabaseHelper(getApplicationContext())
            .getWritableDatabase();

      //insereFilmes(); ///////  SÓ GERAR ESTAS INFORMAÇÕES NA PRIMEIRA EXECUÇÃO ////////////
      mostraFilmes();
      buscaFilmes();

      FilmeAdapter adap = new FilmeAdapter(this, filmes);

      listaFilme = (ListView) findViewById(R.id.lista);
      listaFilme.setAdapter(adap);
      listaFilme.setOnItemClickListener(this);
   }

   public void insereFilmes() {
      db.execSQL("DELETE from filmes;");
      db.execSQL("DELETE from notas;");
      String sql = IOHelper.getStringFromAsset(getApplicationContext(),
            "seed/1.sql");
      String[] v = sql.split("\n");
      for (int i = 0; i < v.length; i++) {
         db.execSQL(v[i]);
      }
   }

   public void mostraFilmes() {
      Cursor cursor = db.rawQuery("SELECT nome FROM filmes", null);
      cursor.moveToFirst();
      while (!cursor.isAfterLast()) {
         Log.d("FLEXXO", cursor.getString(0));
         cursor.moveToNext();
      }
   }

   public void buscaFilmes() {
      filmes = new ArrayList<Filme>();
      Cursor cur = db.rawQuery("SELECT * FROM filmes", null);
      cur.moveToFirst();
      while (!cur.isAfterLast()) {
         Filme filme = new Filme();
         filme.id = cur.getInt(0);
         filme.nome = cur.getString(1);
         filme.descricao = cur.getString(2);
         filme.ano = cur.getInt(3);
         filme.lancamento = cur.getInt(4);
         filme.imagem = cur.getString(5);

         filmes.add(filme);

         cur.moveToNext();
      }
   }

   @Override
   public void onItemClick(AdapterView<?> parent, View view, int position,
         long id) {
      Filme fil = filmes.get(position);
      Intent i = new Intent(this, Detalhe.class);
      i.putExtra("id", fil.id);
      startActivity(i);
   }

}
