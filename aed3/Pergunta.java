package aed3;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Pergunta implements RegistroArvoreBMais<Pergunta>{
    protected final int TAM_PERGUNTA=20;
    protected final int TAM_CHAVE=20;
    protected int idPergunta ;
    protected int idUsuario;
    protected long criacao;
    protected short nota;
    protected String pergunta;
    protected String palavrasChave;
    protected boolean ativa;
  
    public Pergunta (String p, String k, long c, short n,boolean a ) {
      if (p.length()>TAM_PERGUNTA) p=resizeL(p);
      else p=resizeU(p);
      this.idPergunta  = 0;
      this.idUsuario = -1;
      this.criacao = c;
      this.nota = n;
      this.pergunta = p;
      this.palavrasChave = k;
      this.ativa = a;
    }
    public Pergunta() {
      this("", "", (long)-1, (short)-1, false);
    }

    public void setID(int id){
      this.idPergunta=id;
    }

    public int getID(){
      return this.idPergunta;
    }

    public void setidUsuario(int id){
      this.idUsuario=id;
    }

    public int getidUsuario(){
      return this.idUsuario;
    }

    public void setcriacao(long criacao){
      this.criacao=criacao;
    }

    public long getcriacao(){
      return this.criacao;
    }

    public void setNota(short nota){
      this.nota=nota;
    }

    public short getNota(){
      return this.nota;
    }

    public void setPerguntas(String pergunta){
      this.pergunta=pergunta;
    }

    public String getPerguntas(){
      return this.pergunta;
    }

    public void setPalavrasChave(String palavrasChave){
      this.palavrasChave=palavrasChave;
    }

    public String getPalavrasChave(){
      return this.palavrasChave;
    }

    public void setAtiva(boolean ativa){
      this.ativa=ativa;
    }

    public Boolean getAtiva(){
      return this.ativa;
    }
    private String resizeL(String p){
      String ne="";
      for (int i=0; i < TAM_PERGUNTA; i++) {
        ne+=p.charAt(i);
      }
      return ne;
    }
    private String resizeU(String p){
      for (int i=p.length(); i < TAM_PERGUNTA; i++) {
        p+=" ";
      }
      return p;
    }
    public byte[] toByteArray() throws IOException {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      DataOutputStream dos = new DataOutputStream(baos);
      dos.writeInt(idPergunta);
      dos.writeInt(idUsuario);
      dos.writeLong(criacao);
      dos.writeShort(nota);
      dos.writeUTF(pergunta);
      dos.writeUTF(palavrasChave);
      dos.writeBoolean(ativa);
      return baos.toByteArray();
    }
  
    public void fromByteArray(byte[] ba) throws IOException {
      ByteArrayInputStream bais = new ByteArrayInputStream(ba);
      DataInputStream dis = new DataInputStream(bais);
      idPergunta=dis.readInt();
      idUsuario=dis.readInt();
      criacao=dis.readLong();
      nota=dis.readShort();
      pergunta=dis.readUTF();
      palavrasChave=dis.readUTF();
      ativa=dis.readBoolean();
    }
    public short size() {
      return 4+4+8+2+TAM_PERGUNTA*2+TAM_CHAVE*2+1;
    }
    public int compareTo(Pergunta obj) {
      int difer=0;
      if (idPergunta!=obj.idPergunta) difer+=idPergunta-obj.idPergunta;
      if (idUsuario!=obj.idUsuario) difer+=idUsuario-obj.idUsuario;
      if (criacao!=obj.criacao) difer+=criacao-obj.criacao;
      if (nota!=obj.nota) difer+=nota-obj.nota;
      difer+=pergunta.compareTo(obj.pergunta);
      difer+=palavrasChave.compareTo(obj.palavrasChave);
      if (ativa!=obj.ativa) difer+=ativa?10:-10;
      return difer;
    }
    public Pergunta clone(){
      return new Pergunta();
    }
  }