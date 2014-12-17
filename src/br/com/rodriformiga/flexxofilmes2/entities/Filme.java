package br.com.rodriformiga.flexxofilmes2.entities;

public class Filme {
   public int id;
   public String nome;
   public String descricao;
   public int ano;
   public int lancamento;
   public String imagem;
   
   @Override
   public String toString() {
      return nome;
   }
}
