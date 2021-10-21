package aed3;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class Pergunta implements Registro{
    protected int idPergunta ;
    protected int idUsuario ; 
    protected long criacao;
    protected short nota;
    protected String pergunta;
    protected String palavrasChave;
    protected boolean ativa;
  
    public Pergunta (String p, String k, long c, short n,boolean a ) {
      this.idPergunta  = 0;
      this.idUsuario = -1;
      this.criacao = c;
      this.nota = n;
      this.pergunta = p;
      this.palavrasChave = k;
      this.ativa = a;
    }
    public Pergunta() {
      this("", "", (long)-1, (short)-1, false);
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

    public void setPergunta(String pergunta){
      this.pergunta=pergunta;
    }

    public String getPergunta(){
      return this.pergunta;
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
      dos.writeInt(idPergunta);
      dos.writeInt(idUsuario);
      dos.writeLong(criacao);
      dos.writeShort(nota);
      dos.writeUTF(pergunta);
      dos.writeUTF(palavrasChave);
      dos.writeBoolean(ativa);
      return baos.toByteArray();
    }
  
    public void fromByteArray(byte[] ba) throws IOException {
      ByteArrayInputStream bais = new ByteArrayInputStream(ba);
      DataInputStream dis = new DataInputStream(bais);
      idPergunta = dis.readInt();
      idUsuario = dis.readInt();
      criacao = dis.readLong();
      nota = dis.readShort();
      pergunta = dis.readUTF();
      palavrasChave = dis.readUTF();
      ativa = dis.readBoolean();
    }
    public String toString() {
      Date data = new Date(criacao);
      SimpleDateFormat d = new SimpleDateFormat("dd/MM/yyyy hh:mm");
      return "\n"+this.idPergunta + ".\n" + d.format(data) + ".\n\"" + this.pergunta +
         "\"\nPalavras chave: " + this.palavrasChave + (ativa?"":"\nARQUIVADA");
    }
  }