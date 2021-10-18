package aed3;

import java.io.RandomAccessFile;
import java.util.Scanner;

public class ArquivoPergunta extends Crud<Pergunta> {
    RandomAccessFile IDEmail;
    private Scanner sc;

    public ArquivoPergunta(String nomeEntidade) throws Exception {
    	super(nomeEntidade, Pergunta.class.getConstructor());
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
	public int create(Pergunta user) throws Exception {
		int id = super.create(user);
		idEmail(user.email, id);
		return id;
	}
	public Pergunta read(String email) throws Exception {
		int id=emailID(email);
		Pergunta obj=null;
		if (id != -1) obj=super.read(id);
		return obj;
	}
    // Lógica inicial do cabeçalho, damos as opcões ao usuario e
  	// a partir da sua decisão tomamos o caminho necessário
    private int MENU()throws Exception  {
    	Pergunta user = new Pergunta();
		int escolha;
		System.out.println("\nPERGUNTAS 1.0\n=============\n\nINÍCIO\n1) Minha área\n2) Buscar perguntas \n0) Sair\n\nOpção: ");
    	try {
			escolha=sc.nextInt();
		} catch (Exception e) {
			escolha=-1;
		}
    	switch (escolha) {
			case 1:
				user=MinhaArea();
				break;
			case 2:
				user=BuscarPerguntas();
				break;
			case 0:
				break;
			default:
				System.out.println("\nCommando INVALIDO!");
				break;
		}
		return escolha;
    }
    public int MinhaArea() throws Exception {
    	Pergunta user;
		int escolha;
    	System.out.println("\nPERGUNTAS 1.0 \n============\nINÍCIO > MINHA ÁREA\n\n1) Minhas perguntas\n2) Minhas respostas\n3) Meus votos em perguntas\n4) Meus votos em respostas \n\n 0) Retornar ao menu anterior");
    	try {
			escolha=sc.nextInt();
		} catch (Exception e) {
			escolha=-1;
		}
    	switch (escolha) {
			case 1:
				user=MinhasPerguntas();
				break;
			case 2:
				user=MinhasRespostas();
				break;
			case 3:
				user=VotosPerguntas();
				break;
			case 4:
				user=VotosRespostas();
				break;
			case 0:
				break;
			default:
				System.out.println("\nCommando INVALIDO!");
				break;
		}
		return escolha;
    }

	public void MinhasPerguntas() throws Exception {

	}
	public void MinhasRespostas() throws Exception {
		
	}
	public void VotosPerguntas() throws Exception {
		
	}
	public void VotosRespostas() throws Exception {
		
	}

    public int BuscarPerguntas() throws Exception {
    	Pergunta user = new Pergunta();
		int escolha;
    	System.out.println("\nPERGUNTAS 1.0\n============\n\nINÍCIO > MIMHA ÁREA > MINHAS PERGUNTAS \n\n 1) Listar \n2) Incluir \n3) Alterar \n4) Arquivar \n\n 0) Retornar ao menu anterior \n\n Opção:");
		try {
			escolha=sc.nextInt();
		} catch (Exception e) {
			escolha=-1;
		}
    	switch (escolha) {
			case 1:
				user=Listar();
				break;
			case 2:
				user=Incluir();
				break;
			case 3:
				user=Alterar();
				break;
			case 4:
				user=Arquivar();
				break;
			case 0:
				break;
			default:
				System.out.println("\nCommando INVALIDO!");
				break;
		}
		return escolha;
    }

	public void Listar() throws Exception {

	}
	public void Incluir() throws Exception {
		
	}
	public void Alterar() throws Exception {
		
	}
	public void Arquivar() throws Exception {
		
	}
	public void start() throws Exception{
		int escolha=-1;
		do{
			escolha=MENU();
		}while(escolha!=0);
		sc.close();
	}   

	@Override
  public int update(Pergunta user) throws Exception {
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
  private Pergunta TrocarConta() throws Exception{
    Pergunta user;
    System.out.println("\nTROCAR INFORMACOES DA CONTA\n============\n\nEmail: ");
    String Email = sc.next();
    user= read(Email);         // na leitura ira retornar o usu�rio para fazer as demais opera��es
    if(user!=null) {
    	if(TesteSenha(user)) {  if (TesteFrase(user)){
                                user=updateConta(user);
                              }
                              
      }
    	else {
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
  private Boolean TesteSenha(Pergunta user){
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
  private Boolean TesteFrase(Pergunta user){
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
  private Pergunta updateConta(Pergunta user) throws Exception{
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