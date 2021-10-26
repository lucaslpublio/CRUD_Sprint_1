package aed3;


public class ArquivoUsuario extends Crud<Usuario> {
	private HashExtensivel<ParEmailID> IDEmail;
    public ArquivoUsuario(String nomeEntidade) throws Exception {
    	super(nomeEntidade, Usuario.class.getConstructor());
		IDEmail = new HashExtensivel<>(ParEmailID.class.getConstructor(), 4, "dados/" + nomeEntidade + "/hash.dir", "dados/" + nomeEntidade + "/hash.nes");
    }
	@Override
	public int create(Usuario user) throws Exception {
		int id=-1;
		try {
			id=super.create(user);
			IDEmail.create(new ParEmailID(user.email, id));
		} catch (Exception e) {}
		return id;
	}
	public Usuario read(String email) throws Exception {
		ParEmailID aux = null;
		try {
			aux = IDEmail.read(email.hashCode());
		} catch (Exception e) {}
		Usuario obj = null;
		if (aux!=null){
			try {
				obj = super.read(aux.getId());
			} catch (Exception e) {}

		}
		return obj;
	}
	@Override
  	public int update(Usuario user) throws Exception {
		int id=super.update(user);
    	if (id != user.getID())	IDEmail.update(new ParEmailID(user.email, user.getID()));
    	return user.getID();
  	}
}