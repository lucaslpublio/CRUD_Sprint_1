package aed3;
import java.io.RandomAccessFile;
import java.io.File;
import java.lang.reflect.Constructor;

public class Crud<T extends Registro> {

    final int TAMANHO_CABECALHO = 8;
    RandomAccessFile arquivo;
    RandomAccessFile ind;
    RandomAccessFile log;
    Constructor<T> construtor;
    T sObj;
    long sPot;
    String logR;
    public Crud(String nomeArquivo, Constructor<T> c) throws Exception {
        File f = new File("dados");
        if (!f.exists())    f.mkdir();
        f = new File("dados/" + nomeArquivo);
        if (!f.exists())    f.mkdir();
        arquivo = new RandomAccessFile("dados/" + nomeArquivo + "/arquivo.db", "rw");
        ind = new RandomAccessFile("dados/" + nomeArquivo + "/indice.db", "rw");
        log = new  RandomAccessFile("dados/" + nomeArquivo + "/log.db", "rw");
        log.seek(log.length());
        logR = new String("\n\""); 
        construtor = c;
        if (ind.length() == 0) {
            ind.writeInt(0);
            ind.writeInt(0);
        }
    }

    public int create(T obj) throws Exception{
        log("CREATE");
        ind.seek(0);
        int ID=ind.readInt();
        int LIXO=ind.readInt();
        log("ID:"+ID+"|LIXO:"+LIXO);
        if (LIXO>0) {
            fillInd(obj);
            log("|FILL");
        }
        else {
            ind.seek(0);
            ind.writeInt(++ID);
            obj.setID(ID);
            byte OBJ[]=obj.toByteArray();
            ind.seek(ind.length());
            ind.writeInt(ID);
            log("|NEW OBJ ID:"+ID);
            arquivo.seek(arquivo.length());
            log("|NEW|COD:"+arquivo.getFilePointer()+"|IND:  "+ind.getFilePointer());
            ind.writeLong(arquivo.getFilePointer());
            arquivo.writeByte(' ');
            arquivo.writeInt(OBJ.length);
            arquivo.write(OBJ);
        }
        log();
        return obj.getID();
    }
    public int update(T obj) throws Exception{
        log("UPDATE");
        byte OBJ[]=obj.toByteArray();
        arquivo.seek(seek(obj.getID())+1);
        int tam=arquivo.readInt();
        log("|TAM:"+tam+"&"+OBJ.length);
        if (tam == OBJ.length){
            log("|SAME:"+arquivo.getFilePointer()+"+"+OBJ.length);
            arquivo.write(OBJ);
            log();
        }
        else {
            log("|DIFF");
            delete(obj.getID());
            obj.setID(create(obj)); 
        }
        return obj.getID();
    }
    public void delete(int id) throws Exception{
        log("DELETE:"+id);
        arquivo.seek(seek(id));
        arquivo.writeByte('!');
        ind.seek(seekInd(id));
        ind.writeInt(-1);
        ind.seek(4);
        int lixo=ind.readInt()+1;
        ind.seek(4);
        ind.writeInt(lixo);
        log();
    }
    public T read(int id) throws Exception{
        arquivo.seek(seek(id));
        byte test=arquivo.readByte();
        if (test == '!') throw new Exception("ID Apagado");
        if (test != ' ') throw new Exception("Hash Falha");
        int tam=arquivo.readInt();
        byte OBJ[]= new byte[tam];
        arquivo.read(OBJ);
        T obj = construtor.newInstance();
        obj.fromByteArray(OBJ);
        return obj;
    }
    public void show() throws Exception{
        byte sp;
        int size;
        T obj = construtor.newInstance();
        byte[] item;
        boolean end=false;
        arquivo.seek(0);
        while (arquivo.getFilePointer()<arquivo.length()&&!end){
            log("SHOW:"+arquivo.getFilePointer());
            sp=arquivo.readByte();
            size=arquivo.readInt();
            log("|LAPIDE:"+sp+"|SIZE:"+size);
            if (sp == ' '){
                sPot=arquivo.getFilePointer();
                item=new byte[size];
                arquivo.read(item);
                obj.fromByteArray(item);
                System.out.println(obj.toString());
                log("|PRINT");
            } else  arquivo.skipBytes(size);
            log();
        }
    }
    public void showInd() throws Exception{
        log("SHOWIND:"+ind.length());
        int id;
        long cod;
        ind.seek(TAMANHO_CABECALHO);
        while (ind.getFilePointer()<ind.length()){
            id=ind.readInt();
            cod=ind.readLong();
            if (id>0){
                System.out.println("ID :"+id+"  |COD :"+cod);    
            }
        }
        log();
    }
    private long seek(int id) throws Exception{
        ind.seek(TAMANHO_CABECALHO);
        int ID=ind.readInt();
        long pont=ind.readLong();
        boolean found=false;
        boolean fim=false;
        while (!found && !fim){
            if (ID == id)   found=true;
            else {  if (ind.getFilePointer()<ind.length()){
                        ID=ind.readInt();
                        pont=ind.readLong();
                } 
                else    fim=true;   
            }
        }
        if (!found && fim) throw new Exception("ID nao encontrada");
        return pont;

    }
    private long seekInd(int id) throws Exception{
        ind.seek(TAMANHO_CABECALHO);
        long pont=ind.getFilePointer();
        int ID=ind.readInt();
        boolean found=false;
        boolean fim=false;
        while (!found && !fim){
            if (ID == id)   found=true;
            else {  if (ind.getFilePointer()<ind.length()){
                        ind.skipBytes(8);
                        pont=ind.getFilePointer();
                        ID=ind.readInt();
                } 
                else    fim=true;   
            }
        }
        if (!found && fim) throw new Exception("ID nao encontrada");
        return pont;

    }
    private void fillInd(T obj) throws Exception{
        ind.seek(0);
        int id=ind.readInt()+1;
        ind.seek(0);
        obj.setID(id);
        ind.writeInt(id);
        ind.seek(4);
        int LIXO=ind.readInt();
        int contador=0;
        id=ind.readInt();
        long code=ind.readLong();
        log("|LIXO:"+LIXO+"|CONTADOR:"+contador+"|ID:"+id+"|CODE:"+code);
        boolean found=false;
        byte OBJ[]=obj.toByteArray();
        while (!found && contador<LIXO){
            if (id == -1){
                contador++;
                arquivo.seek(code+1);
                if (arquivo.readInt()==OBJ.length){
                   long pos=ind.getFilePointer()-12;
                   ind.seek(4);
                   ind.writeInt(LIXO-1);
                   ind.seek(pos);
                   ind.writeInt(obj.getID());
                   arquivo.seek(code);
                   arquivo.writeByte(' '); 
                   arquivo.writeInt(OBJ.length);
                   arquivo.write(OBJ);
                   found=true;
                   log("|FOUND");
                }
            }
            else log("|LIXO:"+LIXO+"|CONTADOR:"+contador+"|ID:"+id+"|CODE:"+code);
            if (ind.getFilePointer()<ind.length()) {
                id=ind.readInt();
                code=ind.readLong();
            }
            else    contador=LIXO;
        }
        if (!found){
            log("|CREATE");
            ind.seek(ind.length());
            ind.writeInt(obj.getID());
            arquivo.seek(arquivo.length());
            log("|ID:"+id+"CODE:"+arquivo.getFilePointer());
            ind.writeLong(arquivo.getFilePointer());
            arquivo.writeByte(' ');
            arquivo.writeInt(OBJ.length);
            arquivo.write(OBJ);
        }
    }
    private void log(String text){
        logR+="|"+text;
    }
    private void log() throws Exception{
        logR+="\"\n";
        log.writeUTF(logR);
        logR=new String("\n\"");
    }
}
