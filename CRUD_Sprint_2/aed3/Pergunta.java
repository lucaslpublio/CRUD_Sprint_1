package aed3;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Pergunta implements Registro {
  protected int idPergunta;
  protected int idUsuario;
  protected long criacao;
  protected short nota;
  protected String pergunta;
  protected String palavrasChave;
  protected boolean ativa;


  public Pergunta(String n, String e, int s) {
    this.idPergunta = -1;
    this.idUsuario =-1;
    this.criacao = -1;
    this.nota = -1;
    this.pergunta = n;
    this.palavrasChave = e;
    this.ativa = false;
  }
  public Pergunta() {
    this.idPergunta = -1;
    this.idUsuario = -1;
    this.criacao = -1;
    this.nota = -1;
    this.pergunta = "";
    this.palavrasChave = "";
    this.ativa = false;
  }

  public void setIdPergunta(int idPergunta) {
    this.idPergunta = idPergunta;
  }

  public int getIdPergunta() {
    return this.idPergunta;
  }

  public void setIdUsuario(int idUsuario) {
    this.idUsuario = idUsuario;
  }

  public int getIdUsuario() {
    return this.idUsuario;
  }

  public void setCriacao(Long criacao) {
    this.criacao = criacao;
  }

  public Long getCriacao() {
    return this.criacao;
  }

  public void setNota(Short nota) {
    this.nota = nota;
  }

  public Short getNota() {
    return this.nota;
  }

  public void setPergunta(String n) {
    this.pergunta = n;
  }

  public String getPergunta() {
    return this.pergunta;
  }

  public void setPalavrasChave(String e) {
    this.palavrasChave = e;
  }

  public String getPalavrasChave() {
    return this.palavrasChave;
  }

  public void setAtiva (Boolean i) {
    this.ativa = i;
  }

  public boolean getAtiva () {
    return this.ativa ;
  }
  
  public String toString() {

    return "\nIdPergunta....: " + this.idPergunta + "\nidUsuario: " + this.idUsuario + "\nCriacao.: " + this.criacao +
       "\nNota.: " + this.nota + "\nPergunta.: " + this.pergunta + "\nPalavrasChave.: " + this.palavrasChave
       + "\nAtiva.: " + this.ativa;
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
  @Override
  public void setID(int ba) {
    // TODO Auto-generated method stub
    
  }
  @Override
  public int getID() {
    // TODO Auto-generated method stub
    return 0;
  }

}