import java.io.File;

import aed3.ArquivoIndexado;
import aed3.Usuario;

public class Main {
  public static void main(String[] args) {

    ArquivoIndexado<Usuario> arqUsuarios;

    try {

      Usuario c1 = new Usuario("José Couves", "couves@gmail.com", 12345, "R1");
      Usuario c2 = new Usuario("Ana Maria Couves", "aninha@gmail.com", 54321, "R2");
      Usuario c3 = new Usuario("Pedro Augusto Couves", "pcouves@gmail.com", 01245, "R3");

      // ESCRITA
      arqUsuarios = new ArquivoIndexado<>("usuarios", Usuario.class.getConstructor());

      arqUsuarios.create(c1);
      arqUsuarios.create(c2);
      arqUsuarios.create(c3);

      Usuario c0 = arqUsuarios.read(1);
      System.out.println(c0);

      Usuario c01 = arqUsuarios.read(2);
      System.out.println(c01);

      arqUsuarios.delete(2);
      System.out.println("\n" + arqUsuarios.read(2));

      c0.setNome("José das Couves");
      arqUsuarios.update(c0);
      System.out.println("\n" + arqUsuarios.read(1));

    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}
