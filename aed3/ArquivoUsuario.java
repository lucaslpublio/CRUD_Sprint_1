package aed3;

import java.io.RandomAccessFile;
import java.util.Scanner;

public class ArquivoUsuario extends Crud<Usuario> {
    RandomAccessFile IDEmail;
    private Scanner sc;

    public ArquivoUsuario(String nomeEntidade) throws Exception {
    	super(nomeEntidade, Usuario.class.getConstructor());
		IDEmail = new RandomAccessFile("dados/" + nomeEntidade + "/parIDE.db", "rw");
		sc= new Scanner(System.in);
    }
    private int idEmailTest(String email,int ID) throws Exception{
		int idS=emailID(email);
		if(-1 != idS){
			IDEmail.seek(IDEmail.length());
			IDEmail.writeInt(email.hashCode());
			IDEmail.writeInt(ID);
			idS=ID;
		}
		return idS;
	}
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
		if(-1 != emailID(user.email))	throw new Exception("Email ja existente");
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
		System.out.println("\nPERGUNTAS 1.0\n=============\n\nACESSO\n1) Acesso ao sistema\n2) Novo usuário (primeiro acesso)\n0) Sair\n\nOpção: ");
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
				create(user);
				break;
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
    		System.out.println("Senha: ");
    		int senha1=sc.next().hashCode();
    		int senha2=user.getHashSenha();  // ira buscar a senha do usu�rio e compara com a digitada
    		if(senha2 == senha1)	System.out.println("Bem vindo");
    		else {
    			Boolean RESP=false;
				for (int i = 3; i > 0 && !RESP; i--) {
					System.out.println("Senha invalida\nTente novamente :"+i);
					senha1=sc.next().hashCode();
					RESP=senha1 == senha2;
				}  
    			System.out.print(" Precione enter para voltar ao Menu: ");
    			sc.nextLine();
    		}
    	}else {
    		System.out.println("E-mail invalido");
    		System.out.print(" Precione enter para voltar ao Menu: ");
    		sc.nextLine();
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
}