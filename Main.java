import java.io.File;

import aed3.ArquivoIndexado;

public class Main {
  public static void main(String[] args) {

    ArquivoIndexado<Usuario> arqUsuarios;

    try {

      Usuario c1 = new Usuario("José Couves", "couves@gmail.com", 12345, "R1");
      Usuario c2 = new Usuario("Ana Maria Couves", "aninha@gmail.com", 54321, "R2");
      Usuario c3 = new Usuario("Pedro Augusto Couves", "pcouves@gmail.com", 01245, "R3");

      // ESCRITA
      arqIndexado = new ArquivoIndexado<>("usuarios", Usuario.class.getConstructor());

      arqIndexado.create(c1);
      arqIndexado.create(c2);
      arqIndexado.create(c3);

      Usuario c0 = arqIndexado.read(1);
      System.out.println(c0);

      Usuario c01 = arqIndexado.read(2);
      System.out.println(c01);

      arqIndexado.delete(2);
      System.out.println("\n" + arqIndexado.read(2));

      c0.nome = "José das Couves";
      arqIndexado.update(c0);
      System.out.println("\n" + arqIndexado.read(1));

    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}