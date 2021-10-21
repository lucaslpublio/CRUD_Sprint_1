import java.text.Normalizer;
import java.util.Date;

import aed3.Menu;
import aed3.Pergunta;

public class Main {
  public static void main(String[] args) throws Exception {
	  Menu menu = new Menu("crud", 10);
    //menu.ap.setUsuario(menu.au.read(1));
    //menu.ap.create(new Pergunta("Porque o ceu é azul?", "ceu;cor;ciencia;", new Date().getTime(), (short)10, true));
    //menu.ap.create(new Pergunta("Voce esta bem?", "bem querer;", new Date().getTime(), (short)10, true));
    //menu.ap.create(new Pergunta("Qual sua cor favorita?", "cor;preferencia;", new Date().getTime(), (short)10, true));
    //menu.ap.create(new Pergunta("Vc quer de sim ou não?", "sim;não;", new Date().getTime(), (short)10, true));
    //menu.ap.show();
	  menu.start();
    //menu.showU();
    //String a = "gay12345?";
    //System.out.println(a.charAt(a.length()-1));

  }
}
