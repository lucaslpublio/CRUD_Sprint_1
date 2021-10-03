import aed3.ArquivoIndexado;
import aed3.HashExtensivel;

public class ArquivoUsuarios extends ArquivoIndexado<Usuario> {
    HashExtensivel<ParEmailID> indiceEmail;

    public ArquivoUsuarios(String nomeEntidade, Constructor<T> c) throws Exception {
        super(nomeEntidade, c);
        indiceEmail = new HashExtensivel<>(ParEmailID.class.getConstructor(), 4,
        "dados/" + nomeEntidade + "/indiceEmail_d.db", "dados/" + nomeEntidade + "/indiceEmail_c.db");
    }

    @Override
    public int create(Usuario user) {
        int id = super.create(u);
        ParEmailID par = new ParEmailID(id, u.getEmail());
        indiceEmail.create(par);
    }
    

    @Override
    public Usuario read(String email) {
        // Utilizamos o método já criado, read(int) na classe ArquivoIndexado
        // para procurar o hash code do email
        ParEmailID par = indiceEmail.read(email.hashCode());
        int idProcurado = par.getID();
        return super.read(idProcurado);
    }
}