import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import aed3.Registro;

public class Usuario implements Registro {
  protected int idUsuario;
  protected String nome;
  protected String email;
  protected int HashSenha;
  protected String resposta;

  public Usuario(String n, String e, String s, String p) {
    this.idUsuario = -1;
    this.nome = n;
    this.email = e;
    this.HashSenha = s;
    this.resposta = p;
  }

  public Usuario() {
    this.idUsuario = -1;
    this.nome = "";
    this.email = "";
    this.HashSenha = "";
    this.resposta = "";
  }

  public void setID(int id) {
    this.idUsuario = id;
  }

  public int getID() {
    return this.idUsuario;
  }

  public void setNome(String n) {
    this.nome = n;
  }

  public String getNome() {
    return this.nome;
  }

  public void setEmail(String e) {
    this.email = e;
  }

  public String getEmail() {
    return this.email;
  }

  public void setHashSenha(String s) {
    this.HashSenha = s;
  }

  public String getHashSenha() {
    return this.HashSenha;
  }

  public void setResposta(String p) {
    this.resposta = p;
  }

  public String getResposta() {
    return this.resposta;
  }

  public String toString() {

    return "\nID....: " + this.idUsuario + "\nNome: " + this.nome + "\nEmail.: " + this.email +
       "\nSenha.: " + this.HashSenha + "\nResposta.: " + this.resposta+ ;
   }

  public byte[] toByteArray() throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(baos);
    dos.writeInt(idUsuario);
    dos.writeUTF(nome);
    dos.writeUTF(email);
    dos.writeInt(HashSenha);
    dos.writeUTF(resposta);
    return baos.toByteArray();
  }

  public void fromByteArray(byte[] ba) throws IOException {
    ByteArrayInputStream bais = new ByteArrayInputStream(ba);
    DataInputStream dis = new DataInputStream(bais);
    idUsuario = dis.readInt();
    nome = dis.readUTF();
    email = dis.readUTF();
    HashSenha = dis.readInt();
    resposta = dis.readUTF();
  }

}