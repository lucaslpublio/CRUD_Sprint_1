package aed3;

import java.io.RandomAccessFile;
import java.util.Scanner;


public class ArquivoUsuario extends Crud<Usuario> {
	private RandomAccessFile IDEmail;
    private Scanner sc;
	private int ID_Usuario;
	

    public ArquivoUsuario(String nomeEntidade) throws Exception {
    	super(nomeEntidade, Usuario.class.getConstructor());
		IDEmail = new RandomAccessFile("dados/" + nomeEntidade + "/parIDE.db", "rw");
		
		sc= new Scanner(System.in);
    }
    /*private int idEmailTest(String email,int ID) throws Exception{
		int idS=emailID(email);
		if(-1 != idS){
			IDEmail.seek(IDEmail.length());
			IDEmail.writeInt(email.hashCode());
			IDEmail.writeInt(ID);
			idS=ID;
		}
		return idS;
	}*/
	private void idEmail(String email,int ID) throws Exception{
		IDEmail.seek(IDEmail.length());
		IDEmail.writeInt(email.hashCode());
		IDEmail.writeInt(ID);
	}
	private int emailID(String email) throws Exception{
		if (IDEmail.length()<1) return -1;
		IDEmail.seek(0);
		int hashS=email.hashCode();
		int hash;
		int id;
		boolean FOUND=false;
		do {
			hash=IDEmail.readInt();
			id=IDEmail.readInt();
			FOUND = hash == hashS;
		} while (!FOUND && IDEmail.getFilePointer()<IDEmail.length());
		if (!FOUND)	id=-1;
		return id;
	}
	@Override
	public int create(Usuario user) throws Exception {
		int id = super.create(user);
		idEmail(user.email, id);
		return id;
	}
	public Usuario read(String email) throws Exception {
		int id=emailID(email);
		Usuario obj=null;
		if (id != -1) obj=super.read(id);
		return obj;
	}
    // Lógica inicial do cabeçalho, damos as opcões ao usuario e
  	// a partir da sua decisão tomamos o caminho necessário
    private int MENU()throws Exception  {
    	Usuario user = new Usuario();
		int escolha;
		System.out.println("\nPERGUNTAS 1.0\n=============\n\nACESSO\n1) Acesso ao sistema\n2) Novo usuário (primeiro acesso)\n3) Alterar conta existente\n0) Sair\n\nOpção: ");
    	try {
			escolha=sc.nextInt();
		} catch (Exception e) {
			escolha=-1;
		}
    	switch (escolha) {
			case 1:
				user=Acesso();
				break;
			case 2:
				user=PrimeiroAcesso();
				if(-1 != emailID(user.email))	throw new Exception("Email ja existente");
				create(user);
				break;
			case 3:
				user=TrocarConta();
			case 0:
				break;
			default:
				System.out.println("\nCommando INVALIDO!");
				break;
		}
		return escolha;
    }

    public Usuario Acesso() throws Exception {
    	Usuario user;
    	System.out.println("\nACESSO AO SISTEMA\n============\n\nEmail: ");
    	String Email = sc.next();
    	user= read(Email);         // na leitura ira retornar o usu�rio para fazer as demais opera��es
    	if(user!=null) {
    		if(TesteSenha(user)){ 
					System.out.println("Bem vindo");
    		
			//		ID_Usuario= user.getID();
			//	perguntas.MenuPerguntas(ID_Usuario);
				
    		}
    	}else {
    		System.out.println("E-mail invalido");
    		for(int i = 0; i < 2; i++) {
				System.out.print(i + " Precione enter para continuar: ");
				
				sc.nextLine();
			}
		}
           
    	return user;
    }
	// L�gica do primeiro acesso, m�todo que n�o requer par�metros e
    // retorna o usu�rio novo
    public Usuario PrimeiroAcesso() throws Exception {
    	Usuario user = new Usuario();
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
    	return user;
    }
	public void start() throws Exception{
		int escolha=-1;
		do{
			escolha=MENU();
		}while(escolha!=0);
		sc.close();
	}   



	

	@Override
  public int update(Usuario user) throws Exception {
    int id=super.update(user);
    if (id != user.getID()){
		  IDEmail.seek(0);
      user.setID(id);
		int hashS=user.getEmail().hashCode();
		int hash;
		boolean FOUND=false;
		do {
			hash=IDEmail.readInt();
			id=IDEmail.readInt();
			FOUND = hash == hashS;
		} while (!FOUND && IDEmail.getFilePointer()<IDEmail.length());
		if (FOUND){
      IDEmail.seek(IDEmail.getFilePointer()-4);
      IDEmail.writeInt(id);
    }
    }
    return user.getID();
  }
  private Usuario TrocarConta() throws Exception{
    Usuario user;
    System.out.println("\nTROCAR INFORMACOES DA CONTA\n============\n\nEmail: ");
    String Email = sc.next();
    user= read(Email);         // na leitura ira retornar o usu�rio para fazer as demais opera��es
    if(user!=null) {
    	if(TesteSenha(user)) {  if (TesteFrase(user)){
                                user=updateConta(user);
                              }
                              
      }
    	else {
    		for(int i = 0; i < 2; i++) {
				System.out.print(i + " Precione enter para continuar: ");
				
				sc.nextLine();
			}
    	}
    }else {
    	System.out.println("E-mail invalido");
    	for(int i = 0; i < 5; i++) {
			System.out.print(i + " Precione enter para continuar: ");
			
			sc.nextLine();
		}
		}
    return user;
  }
  private Boolean TesteSenha(Usuario user){
    System.out.println("Senha: ");
    Boolean RESP = false;
    int senha1=sc.next().hashCode();
    int senha2=user.getHashSenha();  // ira buscar a senha do usu�rio e compara com a digitada
	RESP=senha1 == senha2;
    for (int i = 3; i > 0 && !RESP; i--) {
			System.out.println("Senha invalida\nTente novamente :"+i);
			senha1=sc.next().hashCode();
			RESP=senha1 == senha2;
		}  
    return RESP;
  }
  private Boolean TesteFrase(Usuario user){
    System.out.println("Frase de recuperação: "+"Qual o nome do seu PET?:");
    Boolean RESP = false;
    String senha1=sc.next();
    String senha2=user.getResposta();  // ira buscar a senha do usu�rio e compara com a digitada
	RESP=senha1.equals(senha2);
    for (int i = 3; i > 0 && !RESP; i--) {
			System.out.println("Resposta invalida\nTente novamente :"+i);
			senha1=sc.next();
			RESP=senha1.equals(senha2);
		}  
    return RESP;
  }
  private Usuario updateConta(Usuario user) throws Exception{
    System.out.println("Nome: ");
      user.setNome(sc.next());
      System.out.println("Senha: ");
      user.setHashSenha(sc.next().hashCode());
      System.out.println("Qual o nome do seu PET?: ");
      user.setResposta(sc.next());
	  user.setID(update(user));
    return user;
  }
  

}