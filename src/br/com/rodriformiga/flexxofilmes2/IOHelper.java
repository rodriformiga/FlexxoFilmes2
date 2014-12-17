package br.com.rodriformiga.flexxofilmes2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;

public class IOHelper {
   public static String getStringFromAsset(Context context, String fileName) {
      try {
         InputStream stream = context.getAssets().open(fileName);
         BufferedReader r = new BufferedReader(new InputStreamReader(stream));
         StringBuilder total = new StringBuilder();
         String line;
         while ((line = r.readLine()) != null) {
            total.append(line);
            total.append("\n");
         }
         return total.toString();
      } catch (IOException e) {
         e.printStackTrace();
         return "";
      }
   }

   public static String getString(InputStream stream) {
      try {
         BufferedReader r = new BufferedReader(new InputStreamReader(stream));
         StringBuilder total = new StringBuilder();
         String line;
         while ((line = r.readLine()) != null) {
            total.append(line);
            total.append("\n");
         }
         return total.toString();
      } catch (IOException e) {
         e.printStackTrace();
         return "";
      }
   }
}
