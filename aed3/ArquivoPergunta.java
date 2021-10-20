package aed3;

public class ArquivoPergunta extends Crud<Pergunta> {
    private int ORDEM=5;
    private ArvoreBMais<IndicePerUsu> index;
    private int ID_Usuario;
    public ArquivoPergunta(String nomeEntidade,int ordem) throws Exception{
        super(nomeEntidade, Pergunta.class.getConstructor());
        ORDEM=ordem;
        index= new ArvoreBMais<>(IndicePerUsu.class.getConstructor(), ORDEM, "dados/" + nomeEntidade + "/indexPergunta.db");
    }
    private void createPergunta(String pergunta,String PalavrasChaves){
        Pergunta pergunta1=new Pergunta(pergunta, PalavrasChaves, c, n, a);
    
       boolean  id;
    
        pergunta1.setPerguntas(pergunta);
        pergunta1.setPalavrasChave(PalavrasChaves);
        pergunta1.setidUsuario(ID_Usuario);
        pergunta1.setAtiva(true);
        id=super.create(ID_Usuario);
    
    
        //return id;
    }
    
    
    
    
    // Inicio das Perguntas
    
    public void MenuPerguntas(int IdUser) throws Exception {
    IDG=IdUser;
    int escolha;
    System.out.println("\nPERGUNTAS 1.0\n=============\n\nINÍCIO\n1)Minha área\n2) Buscar perguntas \n\n0) Sair\n\nOpção: ");
    
    try {
        escolha=sc.nextInt();
    } catch (Exception e) {
        escolha=-1;
    }
    
    switch (escolha) {
        case 1:
            MinhaArea();
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
    //return escolha;
    }
    
    
    public void MinhaArea()throws Exception  {
    int escolha;
    System.out.println("\nPERGUNTAS 1.0\n=============\n\nINÍCIO > MINHA ÁREA\n1) Minhas perguntas\n2) Minhas respostas\n3) Meus votos em perguntas\n4) Meus votos em respostas\n\n0) Retornar ao menu anterior\n\nOpção: ");
    try {
        escolha=sc.nextInt();
    } catch (Exception e) {
        escolha=-1;
    }
    switch (escolha) {
        case 1:
            MinhasPerguntas();
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
        MenuPerguntas(IDG);
            break;
        default:
            System.out.println("\nCommando INVALIDO!");
            break;
    }
    //return escolha;
    }
    
    
    public void MinhasPerguntas()throws Exception  {
    int escolha;
    System.out.println("\nPERGUNTAS 1.0\n=============\n\nINÍCIO > MINHA ÁREA > MINHAS PERGUNTAS\n1) Listar\n2) Incluir\n3) Alterar\n4) Arquivar\n\n0) Retornar ao menu anterior\n\nOpção: ");
    try {
        escolha=sc.nextInt();
    } catch (Exception e) {
        escolha=-1;
    }
    switch (escolha) {
        //case 1:
        //	Listar();
            //break;
        case 2:
            IncluirPergunta();
            break;
        /*case 3:
            AlterarPergunta();
        case 4:
        ArquivarPergunta();
        break;*/
        case 0:
        MinhaArea();
            break;
        default:
            System.out.println("\nCommando INVALIDO!");
            break;
    }
    //return escolha;
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
    createPergunta(pergunta,PalavrasChaves);
    
    }
    
}
