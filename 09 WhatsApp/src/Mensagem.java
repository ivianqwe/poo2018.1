class Usuario{
	private String id_user;
	private Repositorio<Grupo> grupos;
	
	public Usuario(String id){
		this.id_user = id;
		this.grupos = new Repositorio<Grupo>("grupo");
	}

	public String getId_nome() {
		return id_user;
	}

	public void setId_nome(String id_nome) {
		this.id_user = id_nome;
	}

	public Repositorio<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(Repositorio<Grupo> grupos) {
		this.grupos = grupos;
	}
	
	public void addGrupo(Usuario user, Grupo grupo){
		user.grupos.add(grupo.getId_grupo(), grupo);
	}
	
	public String toStringGrupos(){
		String saida = "";
		for(Grupo g : grupos.getAll()){
			saida += g + " ";
		}
		return "[" + saida + "]";
	}
	
	public String toString(){
		return this.id_user;
	}
}

class Grupo{
	private String id_grupo;
	private Repositorio<Usuario> usuarios;
	
	public Grupo(Usuario user, String id){
		this.id_grupo = id;
		this.usuarios = new Repositorio<Usuario>("usuario");
		usuarios.add(user.getId_nome(), user);
	}

	public String getId_grupo() {
		return id_grupo;
	}

	public void setId_grupo(String id_grupo) {
		this.id_grupo = id_grupo;
	}
	
	public Repositorio<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Repositorio<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
	public void addUser(Usuario usuario){
		usuarios.add(usuario.getId_nome(), usuario);
	}
	
	public void inviteUser(Usuario newUser, Grupo grupo){
		this.addUser(newUser);
		newUser.addGrupo(newUser, grupo);
	}
	
	public void removeUserGrupo(Usuario user){
		for(Usuario u : usuarios.getAll()){
			if(u.getId_nome().equals(user.getId_nome()))
				usuarios.remove(u.getId_nome());
		}
	}
	
	public String toStringUsuarios(){
		String saida = "";
    	for(Usuario user : usuarios.getAll()){
    		saida += user + " ";
    	}
    	return "[ " + saida + "]";
	}
	
	public String toString(){
		return this.getId_grupo();
	}
}

public class Mensagem {
		private String idGrupo;
		private String idUser;
		private String texto;
		private boolean isLido;

		public Mensagem(String idUser, String idGrupo, String texto) {
			this.idGrupo = idGrupo;
			this.idUser = idUser;
			this.texto = texto;
			this.isLido = false;
		}

		public boolean isLido() {
			return isLido;
		}

		public void setLido(boolean isLido) {
			this.isLido = isLido;
		}
		
		public String getIndice() {
			return idGrupo;
		}

		public void setIndice(String indice) {
			this.idGrupo = indice;
		}

		public String getUser() {
			return idUser;
		}

		public void setUser(String user) {
			this.idUser = user;
		}

		public String getTexto() {
			return texto;
		}

		public void setTexto(String texto) {
			this.texto = texto;
		}

		public String toString() {
			return " " + idUser + ": " + texto;

		}
}
