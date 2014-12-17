package br.com.rodriformiga.flexxofilmes2.db;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import br.com.rodriformiga.flexxofilmes2.IOHelper;

@SuppressLint("DefaultLocale")
public class FilmesDatabaseHelper extends SQLiteOpenHelper {

   private static final String DB_NAME = "Filmes.db";
   private static final int DB_VERSION = 2;
   private Context context;

   public FilmesDatabaseHelper(Context context) {
      super(context, DB_NAME, null, DB_VERSION);
      this.context = context;
   }

   @Override
   public void onCreate(SQLiteDatabase db) {
      for (int i = 1; i <= DB_VERSION; i++) {
         aplicarAlteracoes(db, i);
      }
   }

   @Override
   public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      aplicarAlteracoes(db, newVersion);
   }

   private void aplicarAlteracoes(SQLiteDatabase db, int version) {
      String fileName = String.format("migration/%d.sql", version);
      String sql = IOHelper.getStringFromAsset(context, fileName);
      db.execSQL(sql);
   }

}
