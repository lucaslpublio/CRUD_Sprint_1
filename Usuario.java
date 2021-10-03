import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

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

  // Lógica inicial do cabeçalho, damos as opcões ao usuario e
  // a partir da sua decisão tomamos o caminho necessário
  public int cabecalho() {
    Usuario user = new Usuario();
    Scanner sc = new Scanner (System.in);

    System.out.println("
    PERGUNTAS 1.0
    =============

    ACESSO

    1) Acesso ao sistema
    2) Novo usuário (primeiro acesso)

    0) Sair

    Opção: ")

    int escolha = sc.nextInt();

    if(escolha == 1) {
      user.Acesso();
    } else 
      user.PrimeiroAcesso();

    return escolha;

  }

  // Lógica do primeiroc acesso, método que não requer parâmetros e
  // retorna o usuário novo
  public Usuario PrimeiroAcesso() {
    Usuario user = new Usuario();
    Scanner sc = new Scanner (System.in);
    System.out.println("
    NOVO USUARIO
    ============

    Email: ");

    String NovoEmail = sc.next();

    if(user.read(NovoEmail) != null) {
      System.out.println("Email já Cadastrado!");
    } else {
      System.out.println("Nome: ");
      String name = sc.next();
      System.out.println("Senha: ");
      int psw = sc.next().hashCode();
      System.out.println("Qual o nome do seu PET?: ");
      String asw = asw.next();

      Usuario novo = new Usuario(name, NovoEmail, psw, asw);

      return novo;

    }
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