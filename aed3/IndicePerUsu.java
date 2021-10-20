package aed3;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class IndicePerUsu implements RegistroArvoreBMais<IndicePerUsu> {
    Pergunta pergunta;
    public IndicePerUsu(Pergunta pergunta){
        this.pergunta=pergunta;
    }
    public IndicePerUsu(int idUsuario){
        this(-2, idUsuario);
    }
    public IndicePerUsu(int idPergunta, int idUsuario){
        this.pergunta=new Pergunta();
        pergunta.idUsuario=idUsuario;
        pergunta.idPergunta=idPergunta;
    }
    public IndicePerUsu(){
        this.pergunta=null;
    }


public short size() {
    return 8;
}





public byte[] toByteArray() throws IOException {
    if (pergunta.idPergunta == -2) return null;
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(baos);
    dos.writeInt(pergunta.idUsuario);
    dos.writeInt(pergunta.idPergunta);
    return null;
}





public void fromByteArray(byte[] ba) throws IOException {
    ByteArrayInputStream bais = new ByteArrayInputStream(ba);
    DataInputStream dis = new DataInputStream(bais);
    pergunta.idUsuario = dis.readInt();
    pergunta.idPergunta = dis.readInt();
}




public int compareTo(IndicePerUsu obj) {
    if (pergunta.idUsuario != obj.pergunta.idUsuario) return pergunta.idUsuario-obj.pergunta.idUsuario;
    else {  if (pergunta.idPergunta == -2)  return 0;}
    return pergunta.idPergunta-obj.pergunta.idPergunta;
}




public IndicePerUsu clone() {
    return new IndicePerUsu(pergunta.idPergunta, pergunta.idUsuario);
}

}
