import java.io.File;

import aed3.ArquivoIndexado;

public class Main {
  public static void main(String[] args) {

    ArquivoIndexado<Cliente> arqUsuarios;

    try {

      Cliente c1 = new Cliente("José Couves", "couves@gmail.com", 12345, "R1");
      Cliente c2 = new Cliente("Ana Maria Couves", "aninha@gmail.com", 54321, "R2");
      Cliente c3 = new Cliente("Pedro Augusto Couves", "pcouves@gmail.com", 01245, "R3");

      // ESCRITA
      arqIndexado = new ArquivoIndexado<>("usuarios", Usuario.class.getConstructor());

      arqIndexado.create(c1);
      arqIndexado.create(c2);
      arqIndexado.create(c3);

      Cliente c0 = arqIndexado.read(1);
      System.out.println(c0);

      Cliente c01 = arqIndexado.read(2);
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