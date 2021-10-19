package aed3;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Pergunta implements Registro {
    protected int idPergunta ;
    protected int idUsuario;
    protected long criacao;
    protected short nota;
    protected String perguntas;
    protected String palavrasChave;
    protected boolean ativa;
  
    public Pergunta (String n, String e, long s, short r,boolean b ) {
      this.idPergunta  = 0;
      this.idUsuario = -1;
      this.criacao = s;
      this.nota = r;
      this.perguntas = n;
      this.palavrasChave = e;
      this.ativa = b;
    }
    public Pergunta() {
      this.idPergunta  = 0;
      this.idUsuario = -1;
      this.criacao = -1;
      this.nota = -1;
      this.perguntas = "";
      this.palavrasChave = "";
      this.ativa = false;
    }

    public void setID(int id){
      this.idPergunta=id;
    }

    public int getID(){
      return this.idPergunta;
    }

    public void setidUsuario(int id){
      this.idUsuario=id;
    }

    public int getidUsuario(){
      return this.idUsuario;
    }

    public void setcriacao(long criacao){
      this.criacao=criacao;
    }

    public long getcriacao(){
      return this.criacao;
    }

    public void setNota(short nota){
      this.nota=nota;
    }

    public short getNota(){
      return this.nota;
    }

    public void setPerguntas(String perguntas){
      this.perguntas=perguntas;
    }

    public String getPerguntas(){
      return this.perguntas;
    }

    public void setPalavrasChave(String palavrasChave){
      this.palavrasChave=palavrasChave;
    }

    public String getPalavrasChave(){
      return this.palavrasChave;
    }

    public void setAtiva(boolean ativa){
      this.ativa=ativa;
    }

    public Boolean getAtiva(){
      return this.ativa;
    }

    public byte[] toByteArray() throws IOException {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      DataOutputStream dos = new DataOutputStream(baos);
      dos.writeInt(idUsuario);
      dos.writeUTF(perguntas);
      dos.writeUTF(palavrasChave);
      return baos.toByteArray();
    }
  
    public void fromByteArray(byte[] ba) throws IOException {
      ByteArrayInputStream bais = new ByteArrayInputStream(ba);
      DataInputStream dis = new DataInputStream(bais);
      idUsuario = dis.readInt();
      perguntas = dis.readUTF();
      palavrasChave = dis.readUTF();
    }
  }