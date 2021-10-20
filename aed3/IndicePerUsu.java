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
    public IndicePerUsu(int idPergunta, int idUsuario){
        this.pergunta=new Pergunta();
        pergunta.idUsuario=idUsuario;
        pergunta.idPergunta=idPergunta;
    }



public short size() {
    return 8;
}





public byte[] toByteArray() throws IOException {
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
    return pergunta.idUsuario-obj.pergunta.idUsuario+pergunta.idPergunta-obj.pergunta.idPergunta;
}




public IndicePerUsu clone() {
    return new IndicePerUsu(pergunta.idPergunta, pergunta.idUsuario);
}

}
