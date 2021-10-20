package aed3;

import java.util.ArrayList;
import java.util.Scanner;

public class ArquivoPergunta extends Crud<Pergunta> {
    private int ORDEM=5;
    private ArvoreBMais<IndicePerUsu> index;
    private Usuario usuario;
    private Scanner sc;
    public ArquivoPergunta(String nomeEntidade,int ordem) throws Exception{
        super(nomeEntidade, Pergunta.class.getConstructor());
        ORDEM=ordem;
        index= new ArvoreBMais<>(IndicePerUsu.class.getConstructor(), ORDEM, "dados/" + nomeEntidade + "/indexPergunta.db");
        sc = new Scanner(System.in);
    }
    @Override
	public int create(Pergunta pergunta) throws Exception {
		int id = super.create(pergunta);
		index.create(new IndicePerUsu(id, usuario.getID()));
		return id;
	}
	public Pergunta read(Pergunta pergunta) throws Exception {
		int id=pergunta.getID();
		Pergunta obj=null;
		if (id != -1) obj=super.read(id);
		return obj;
	}
    public ArrayList<Pergunta> read(Usuario usuario) throws Exception {
		ArrayList<IndicePerUsu> al = index.read(new IndicePerUsu(usuario.getID()));
        ArrayList<Pergunta> resp= new ArrayList<>(al.size());
        for (int i = 0; i < al.size(); i++) {
            resp.add(i, super.read(al.get(i).pergunta.idPergunta));
        }
        return resp;
       
	}
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    
    
    // Inicio das Perguntas
    
    public int MenuPerguntas() throws Exception {
    int escolha;
    System.out.println("\nPERGUNTAS 1.0\n=============\n\nINÍCIO > MINHA ÁREA > MINHAS PERGUNTAS\n1) Listar\n2) Incluir\n3) Alterar \n4) Arquivar \n\n0) Sair\n\nOpção: ");
    
    try {
        escolha=sc.nextInt();
    } catch (Exception e) {
        escolha=-1;
    }
    
    switch (escolha) {
        case 1:
            escolha=-1;
            MinhasPerguntas();
            break;
        //case 2:
            //user=BuscarPerguntas();
            //break;
        case 0:
            break;
        default:
            System.out.println("\nCommando INVALIDO!");
            break;
    }
    return escolha;
    }
    
    
    public int MENU()throws Exception  {
    int escolha;
    System.out.println("\nPERGUNTAS 1.0\n=============\n\nINÍCIO > MINHA ÁREA\n1) Minhas perguntas\n2) Minhas respostas\n3) Meus votos em perguntas\n4) Meus votos em respostas\n\n0) Retornar ao menu anterior\n\nOpção: ");
    try {
        escolha=sc.nextInt();
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
            escolha=0;
            break;
        default:
            System.out.println("\nCommando INVALIDO!");
            break;
    }
    return escolha;
    }
    
    
    public void MinhasPerguntas()throws Exception  {
        System.out.println("\nPERGUNTAS 1.0\n=============\n\nINÍCIO > MINHA ÁREA > MINHAS PERGUNTAS > Lista");
        ArrayList<Pergunta> pergs = read(usuario);
        for (int i = 0; i < pergs.size(); i++) {
            System.out.println(pergs.get(i).toString());
        }
        System.out.println("Pressione qualquer tecla para continuar");
        System.in.read();
    }
    
    public void IncluirPergunta() throws Exception{
    
    System.out.println("Escreva sua pergunta:");
    String pergunta=sc.next();
    String PalavrasChaves="";
    
    if(pergunta == null){
        MinhasPerguntas();
    }else{
        System.out.println("Palavras Chaves:");
         PalavrasChaves=sc.next();
    
        System.out.println("Incluir a pergunta? (s/n)");
        char confirmacao=sc.next(). charAt(0);
    
        if(confirmacao == 'n'){
            MinhasPerguntas();
        }else{
    
        }
    }
    //createPergunta(pergunta,PalavrasChaves);
    
    }
    public void start() throws Exception{
		int escolha=-1;
		do{
			escolha=MENU();
		}while(escolha!=0);
	} 
}
