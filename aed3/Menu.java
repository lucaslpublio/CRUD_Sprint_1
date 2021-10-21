package aed3;

import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Menu {
    private Scanner sc;
    public ArquivoUsuario au;
    public ArquivoPergunta ap;
    private Usuario usuario;
    private boolean logado;
    private RandomAccessFile manter_logado = new RandomAccessFile("client/conta.cl", "rw");
    public Menu(String nomeEntidade, int ordem) throws Exception{
        sc= new Scanner(System.in);
        au=new ArquivoUsuario(nomeEntidade);
        ap=new ArquivoPergunta(nomeEntidade+"P", ordem);
        logado=false;
    }
    // Lógica inicial do cabeçalho, damos as opcões ao usuario e
  	// a partir da sua decisão tomamos o caminho necessário
      private int MENU()throws Exception  {
    	Usuario user = new Usuario();
		int escolha;
		System.out.println("\nPERGUNTAS 1.0\n=============\n\nACESSO\n1) Acesso ao sistema\n2) Novo usuário (primeiro acesso)\n3) Alterar conta existente\n0) Sair\n\nOpção: ");
    	try {
			escolha=Integer.parseInt(sc.nextLine());
		} catch (Exception e) {
			escolha=-1;
		}
    	switch (escolha) {
			case 1:
				user=Acesso();
				break;
			case 2:
				user=PrimeiroAcesso();
				if(null != au.read(user.email))	throw new Exception("Email ja existente");
				au.create(user);
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
    	String Email = sc.nextLine();
    	user= au.read(Email);         // na leitura ira retornar o usu�rio para fazer as demais opera��es
    	if(user!=null) {
    		if(TesteSenha(user)){ 
					System.out.println("Bem vindo");
					usuario=user;
          ap.setUsuario(user);
					logado=true;
          startMinhaArea();
    		}
    	}else {
    		System.out.println("E-mail invalido");
    		System.out.println("\n\nPressione qualquer tecla para continuar");
        sc.nextLine();
		}
           
    	return user;
    }
	// L�gica do primeiro acesso, m�todo que n�o requer par�metros e
    // retorna o usu�rio novo
    public Usuario PrimeiroAcesso() throws Exception {
    	Usuario user = new Usuario();
    	System.out.println("\nNOVO USUARIO\n============\n\nEmail: ");
    	String NovoEmail = sc.nextLine();
     	if(au.read(NovoEmail) != null) {
       		System.out.println("Email já Cadastrado!");
     	} else {
       		user.email=NovoEmail;  
       		System.out.println("Nome: ");
       		user.setNome(sc.nextLine());
       		System.out.println("Senha: ");
       		user.setHashSenha(sc.nextLine().hashCode());
       		System.out.println("Qual o nome do seu PET?: ");
       		user.setResposta(sc.nextLine());
		}
    	return user;
    }
	public void start() throws Exception{
		int escolha=-1;
    logado=false;
    if (manter_logado.length() != 0){
      byte login[] = new byte[(int)manter_logado.length()];
      try {
        manter_logado.read(login);
        Usuario user = new Usuario();
        user.fromByteArray(login);
        Usuario aux = au.read(user.email);
        if (aux.compareTo(user) == 0){
          usuario=user;
          ap.setUsuario(user);
          System.out.println(usuario.toString());
          logado=true;
        } 
      } catch (Exception e) {}
    }
		do{
			if(logado)  MinhaArea();
      else  escolha=MENU();
		}while(escolha!=0);
    if (usuario != null){
      System.out.println("\nDeslogar [0] ou Fechar [1]");
    int s=0;
    try {
      s = Integer.parseInt(sc.nextLine());
    } catch (Exception e) {}
    manter_logado.seek(0);
    if (s == 1) manter_logado.write(usuario.toByteArray());
    else manter_logado.setLength(0);
    }
    else manter_logado.setLength(0);
		sc.close();
	}   
    private Usuario TrocarConta() throws Exception{
        Usuario user;
        System.out.println("\nTROCAR INFORMACOES DA CONTA\n============\n\nEmail: ");
        String Email = sc.nextLine();
        user= au.read(Email);         // na leitura ira retornar o usu�rio para fazer as demais opera��es
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
        int senha1=sc.nextLine().hashCode();
        int senha2=user.getHashSenha();  // ira buscar a senha do usu�rio e compara com a digitada
        RESP=senha1 == senha2;
        for (int i = 3; i > 0 && !RESP; i--) {
                System.out.println("Senha invalida\nTente novamente :"+i);
                senha1=sc.nextLine().hashCode();
                RESP=senha1 == senha2;
            }  
        return RESP;
      }
      private Boolean TesteFrase(Usuario user){
        System.out.println("Frase de recuperação: "+"Qual o nome do seu PET?:");
        Boolean RESP = false;
        String senha1=sc.nextLine();
        String senha2=user.getResposta();  // ira buscar a senha do usu�rio e compara com a digitada
        RESP=senha1.equals(senha2);
        for (int i = 3; i > 0 && !RESP; i--) {
                System.out.println("Resposta invalida\nTente novamente :"+i);
                senha1=sc.nextLine();
                RESP=senha1.equals(senha2);
            }  
        return RESP;
      }
      private Usuario updateConta(Usuario user) throws Exception{
        System.out.println("Nome: ");
          user.setNome(sc.nextLine());
          System.out.println("Senha: ");
          user.setHashSenha(sc.nextLine().hashCode());
          System.out.println("Qual o nome do seu PET?: ");
          user.setResposta(sc.nextLine());
          user.setID(au.update(user));
        return user;
      }
      public void showU() throws Exception{
        au.show();
      }
      public int MenuPerguntas() throws Exception {
        int escolha;
        System.out.println("\nPERGUNTAS 1.0\n=============\n\nINÍCIO > MINHA ÁREA > MINHAS PERGUNTAS\n1) Listar\n2) Incluir\n3) Alterar \n4) Arquivar \n\n0) Sair\n\nOpção: ");
        
        try {
            escolha=Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            escolha=-1;
        }
        
        switch (escolha) {
            case 1:
                escolha=-1;
                MinhasPerguntas();
                break;
            case 2:
                InserirPergunta();
                break;
            case 3:
                AlterarPergunta();
                break;
            case 4:
                ArquivarPergunta();
                break;
            case 0:
                break;
            default:
                System.out.println("\nCommando INVALIDO!");
                break;
        }
        return escolha;
        }
        
        
        public int MinhaArea()throws Exception  {
        int escolha;
        System.out.println("\nPERGUNTAS 1.0\n=============\n\nINÍCIO > MINHA ÁREA\n1) Minhas perguntas\n2) Minhas respostas\n3) Meus votos em perguntas\n4) Meus votos em respostas\n\n0) Retornar ao menu anterior\n\nOpção: ");
        try {
            escolha=Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            escolha=-1;
        }
        switch (escolha) {
            case 1:
                escolha= MenuPerguntas();
                break;
            /*case 2:
                MinhasRespostas();
                break;
            case 3:
                MeusVotosPerguntas();
            case 4:
            MeusVotosRespostas();
            break;*/
            case 0:
                logado=false;
                break;
            default:
                System.out.println("\nCommando INVALIDO!");
                break;
        }
        return escolha;
        }
      public void ArquivarPergunta() throws Exception{
        System.out.println("\nPERGUNTAS 1.0\n=============\n\nINÍCIO > MINHA ÁREA > MINHAS PERGUNTAS > ARQUIVAR");
          ArrayList<Pergunta> pergs = ap.read(usuario);
            for (int i = 0; i < pergs.size(); i++) {
                System.out.println(pergs.get(i).toString());
            }
            System.out.println("\n\nDigite o ID da pargunta a ser arquivada");
            int ID = 0;
            try {
              ID = Integer.valueOf(sc.nextLine());
            } catch (Exception e) {}
            if (ID == 0) return;
            Pergunta pergunta = ap.read(ID);
            if (pergunta == null)   return;
            else  if (!pergunta.ativa){
                    System.out.println("Pergunta ja apagada!");
                    return;
                  }
                  else{
              System.out.println(pergunta.toString()+"\nDeseja Arquivar essa Pergunta (essa acao nao pode ser desfeita)? (s/n)");
              char confirmacao='n';
              try {
                confirmacao=Character.toLowerCase(sc.nextLine().charAt(0));
              } catch (Exception e) {}
              if (confirmacao == 's'){
                ap.delete(pergunta);
                System.out.println("\nPergunta Arquivada");
              }
            }

      }
        
        public void MinhasPerguntas()throws Exception  {
            System.out.println("\nPERGUNTAS 1.0\n=============\n\nINÍCIO > MINHA ÁREA > MINHAS PERGUNTAS > Lista");
            ArrayList<Pergunta> pergs = ap.read(usuario);
            for (int i = 0; i < pergs.size(); i++) {
                System.out.println(pergs.get(i).toString());
            }
            System.out.println("\n\nPressione qualquer tecla para continuar");
            sc.nextLine();
        }


        public void InserirPergunta() throws Exception{
          System.out.println("\nPERGUNTAS 1.0\n=============\n\nINÍCIO > MINHA ÁREA > MINHAS PERGUNTAS > ADICIONAR");
          System.out.println("\n\nEscreva sua pergunta:");
          String pergunta=sc.nextLine();
          String PalavrasChaves="";
          
          if(pergunta == null || pergunta.equals("")){
              InserirPergunta();
          }else{
              System.out.println("Palavras Chaves ou 0 para sair:");
              String aux;
              do {
                 aux = sc.nextLine();
                if (aux.charAt(0)!='0') PalavrasChaves+=(aux.trim()+";");
              } while (aux.charAt(0)!='0');
          
              System.out.println("Inserir a pergunta? (s/n)");
              char confirmacao=sc.nextLine(). charAt(0);
          
              if(confirmacao == 'n'){
                  return;
              }else{
                pergunta = pergunta.trim();
                if (pergunta.charAt(pergunta.length()-1)!='?')  pergunta+="?";
                ap.create(new Pergunta(pergunta, PalavrasChaves, new Date().getTime(), (short)(pergunta.hashCode()%100), true));
              }
          }
        }
        public void AlterarPergunta() throws Exception{
          System.out.println("\nPERGUNTAS 1.0\n=============\n\nINÍCIO > MINHA ÁREA > MINHAS PERGUNTAS > ALTERAR");
          ArrayList<Pergunta> pergs = ap.read(usuario);
            for (int i = 0; i < pergs.size(); i++) {
                System.out.println(pergs.get(i).toString());
            }
            System.out.println("\n\nDigite o ID da pargunta a ser alterada");
            int ID = 0;
            try {
              ID = Integer.valueOf(sc.nextLine());
            } catch (Exception e) {}
            if (ID == 0) return;
            Pergunta pergunta = ap.read(ID);
            Pergunta nova=new Pergunta();
            if (pergunta == null)   return;
            else  if (!pergunta.ativa){
                    System.out.println("Pergunta ja apagada!");
                    return;
                  }
                  else{
              System.out.println(pergunta.toString()+"\nDeseja Alterar a Pergunta? (s/n)");
              char confirmacao='n';
              try {
                confirmacao=Character.toLowerCase(sc.nextLine().charAt(0));
              } catch (Exception e) {}
              if (confirmacao == 's'){
                System.out.println("\nDigite a nova Pergunta");
                String aux = sc.nextLine();
                if (aux == null || aux.length()==0) return;
                
                aux = aux.trim();
                  if (aux.charAt(aux.length()-1)!='?')  aux+="?";
                  nova.setPergunta(aux);
                  
                  System.out.println(pergunta.toString()+"\nDeseja Alterar as Chaves? (s/n)");
                  confirmacao='n';
                  try {
                    confirmacao=Character.toLowerCase(sc.nextLine().charAt(0));
                  } catch (Exception e) {}
                  if (confirmacao != 'n'){
                    System.out.println("\nDigite as novas Chaves ou 0 para sair:");
                    String chaves="";
                    String aux2;
                  do {
                  aux2 = sc.nextLine();
                  if (aux2.charAt(0)!='0') chaves+=(aux2.trim()+";");
                } while (aux2.charAt(0)!='0');
                nova.setPalavrasChave(chaves);
                System.out.println("Alterar a pergunta? (s/n)");
                confirmacao='n';
                  try {
                    confirmacao=Character.toLowerCase(sc.nextLine().charAt(0));
                  } catch (Exception e) {}
                if (confirmacao == 'n') return;
                nova.setcriacao(new Date().getTime());
                ap.update(pergunta,nova);
                  }
                }
              }
            
        }

          public void startMinhaArea() throws Exception{
            int escolha=-1;
            do{
              escolha=MinhaArea();
            }while(escolha!=0);
          }   
}
