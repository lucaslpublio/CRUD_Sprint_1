package aed3;

import java.util.Scanner;

public class ArquivoUsuario extends ArquivoIndexado<Usuario> {
    HashExtensivel<ParEmailID> indiceEmail;

    public ArquivoUsuario(String nomeEntidade) throws Exception {
        super(nomeEntidade, Usuario.class.getConstructor());
        indiceEmail = new HashExtensivel<>(ParEmailID.class.getConstructor(), 4,
        "dados/" + nomeEntidade + "/indiceEmail_d.db", "dados/" + nomeEntidade + "/indiceEmail_c.db");
    }

    @Override
    public int create(Usuario user) throws Exception {
        int id = super.create(user);
        ParEmailID par = new ParEmailID(user.getEmail(),id);
        indiceEmail.create(par);
        return id;
    }
    
    @Override
    public Usuario read(String email) throws Exception {
        // Utilizamos o método já criado, read(int) na classe ArquivoIndexado
        // para procurar o hash code do email
        ParEmailID par = indiceEmail.read(email.hashCode());
        int idProcurado = par.getID();
        return super.read(idProcurado);
    }
    // Lógica inicial do cabeçalho, damos as opcões ao usuario e
  // a partir da sua decisão tomamos o caminho necessário
  public int cabecalho() {
    Usuario user = new Usuario();
    Scanner sc = new Scanner (System.in);

    System.out.println("\nPERGUNTAS 1.0\n=============\n\nACESSO\n1) Acesso ao sistema\n2) Novo usuário (primeiro acesso)\n0) Sair\n\nOpção: ");

    int escolha = sc.nextInt();

    if(escolha == 1) {
      user=Acesso();
    } else 
      user=PrimeiroAcesso();

    return escolha;

  }

  // Lógica do primeiroc acesso, método que não requer parâmetros e
  // retorna o usuário novo
  public Usuario PrimeiroAcesso() throws Exception {
    Usuario user = new Usuario();
    Scanner sc = new Scanner (System.in);
    System.out.println("\nNOVO USUARIO\n============\n\nEmail: ");

    String NovoEmail = sc.next();

    if(read(NovoEmail) != null) {
      System.out.println("Email já Cadastrado!");
    } else {
      user.email=NovoEmail;  
      System.out.println("Nome: ");
      user.setNome(sc.next());
      System.out.println("Senha: ");
      user.setHashSenha(sc.next().hashCode());
      System.out.println("Qual o nome do seu PET?: ");
      user.setResposta(sc.next());

      

    }
    sc.close();
    return user;
  }
}