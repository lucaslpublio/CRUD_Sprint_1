package aed3;

import java.text.Normalizer;
import java.util.ArrayList;

public class ArquivoPergunta extends Crud<Pergunta> {
    private int ORDEM=5;
    private ArvoreBMais<IndicePerUsu> index;
    private Usuario usuario;
    public ListaInvertida chaves;
    public ArquivoPergunta(String nomeEntidade,int ordem) throws Exception{
        super(nomeEntidade, Pergunta.class.getConstructor());
        ORDEM=ordem;
        index= new ArvoreBMais<>(IndicePerUsu.class.getConstructor(), ORDEM, "dados/" + nomeEntidade + "/indexPergunta.db");
        chaves = new ListaInvertida(ORDEM, "dados/" + nomeEntidade + "/chave.dic", "dados/" + nomeEntidade + "/chave.blo");
    }
    @Override
	public int create(Pergunta pergunta) throws Exception {
		int id = super.getNextID();
        boolean created = index.create(new IndicePerUsu(id, usuario.getID()));    
        if (created){
            super.create(pergunta);
            String chave = Normalizer.normalize(pergunta.palavrasChave, Normalizer.Form.NFKD);
            chave = chave.replaceAll("[^\\p{ASCII}]", "");
            chave = chave.trim();
            chave = chave.toLowerCase();
            String aux[] = chave.split(";");
            for (String string : aux) {
                chaves.create(string, id);    
            }
        }    
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
    public void update(Pergunta antiga,Pergunta nova) throws Exception{
        antiga.setAtiva(false);
        super.update(antiga);
        String aux[] = antiga.getPalavrasChave().split(";");
        for (String string : aux) {
            chaves.delete(string, antiga.getID());
        }
        nova.setidUsuario(antiga.getidUsuario());
        nova.setAtiva(true);
        create(nova);
    }
    public void delete(Pergunta pergunta) throws Exception{
        pergunta.setAtiva(false);
        super.update(pergunta);
    }
}
