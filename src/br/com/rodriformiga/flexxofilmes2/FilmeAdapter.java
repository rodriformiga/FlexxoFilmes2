package br.com.rodriformiga.flexxofilmes2;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.rodriformiga.flexxofilmes2.entities.Filme;

public class FilmeAdapter extends BaseAdapter {

   private List<Filme> filmes;
   private Context context;

   public FilmeAdapter(Context context, List<Filme> filmes) {
      this.context = context;
      this.filmes = filmes;
   }

   @Override
   public int getCount() {
      return filmes.size();
   }

   @Override
   public Object getItem(int position) {
      return filmes.get(position);
   }

   @Override
   public long getItemId(int position) {
      return filmes.get(position).id;
   }

   @Override
   public View getView(int position, View view, ViewGroup group) {
      LayoutInflater inflater = (LayoutInflater) context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      View layout = inflater.inflate(R.layout.lista_filme, null);

      ImageView img = (ImageView) layout.findViewById(R.id.img01);
      TextView txtDesc = (TextView) layout.findViewById(R.id.txtDesc);
      TextView txtNome = (TextView) layout.findViewById(R.id.txtNome);
      Filme filme = filmes.get(position);
      txtDesc.setText(filme.descricao.toString());
      txtNome.setText(filme.nome.toString());
      carregarFotoClick(filme.imagem, img);

      return layout;
   }

   private void carregarFotoClick(String nome, ImageView img) {
      InputStream foto;
      try {
         foto = context.getAssets().open(nome);
         img.setImageBitmap(BitmapFactory.decodeStream(foto));
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

}
