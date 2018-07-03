import java.util.ArrayList;
import java.util.Scanner;

class Nota{
	private String titulo;
	private String texto;
	
	public Nota(String titulo, String texto){
		this.titulo = titulo;
		this.texto = texto;
	}

	public String getTitulo() {
		return titulo;
	}
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String toString(){
		return this.titulo + " " + this.texto;
	}
}

class Usuario implements Comparable<Usuario>{
	private String username;
	private String pass;
	ArrayList<Nota> notas;
	
	public Usuario(String username, String pass){
		this.username = username;
		this.pass = pass;
		this.notas = new ArrayList<Nota>();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public void addNote(String titulo, String texto){
		for(Nota n : notas){
			if(n.getTitulo().equals(titulo)){
				throw new RuntimeException("fail: Já existe uma nota com esse título.");
			}
		}
		Nota nota = new Nota(titulo, texto);
		notas.add(nota);
	}

	public void rmNote(String titulo){
		for(int i = 0; i < notas.size(); i++){
			if(notas.get(i).getTitulo().equals(titulo))
				notas.remove(i);
		}
		throw new RuntimeException("fail: Titulo não existe.");
	}

	public void changerPass(String oldPass, String newPass){
		if(this.pass.equals(oldPass));
			this.pass = newPass;
	}

	public boolean testaPass(String pass){
		return this.pass.equals(pass);
	}

	public void removerNota(String titulo){
		for(int i = 0; i < notas.size(); i++)
			if(notas.get(i).equals(titulo))
				notas.remove(i);
		throw new RuntimeException("fail: Não encontrada nota com o titulo " + titulo + ".");
	}

	public String mostrarNotas(){
		String saida = "";
		for(Nota n : notas)
			saida = n + "\n";
		return saida;
	}

	public String toString(){
		return this.username;
	}
	
	public int compareTo(Usuario arg0) {
		return this.username.compareTo(arg0.username);
	}
}

class Gerenciador_login{
	private Repositorio <Usuario> usuarios;
	private Usuario user;

	public Gerenciador_login(Repositorio <Usuario> usuario){
		this.usuarios = usuario;
		this.user = null;
	}

	public void login(String username, String pass){
		if(user != null){
			throw new RuntimeException("fail: Já existe alguem logado");
		}

		if(!usuarios.get(username).testaPass(pass))
			throw new RuntimeException("fail: senha invalida");
		this.user = usuarios.get(username);
	}

	public void logout(){
		if(this.user == null)
			throw new RuntimeException("fail: ninguem logado");
		this.user = null;
	}

	public Usuario getUsuario(){
		if(user == null)
			throw new RuntimeException("fail: ninguem logado");
		return user;
	}
}


class Controller{
	Repositorio<Usuario> usuarios;
	Gerenciador_login gerlog;

    public Controller() {
    	this.usuarios = new Repositorio<Usuario>("Usuario");
    	this.gerlog = new Gerenciador_login(usuarios);
    }
    //nossa funcao oraculo que recebe uma pergunta e retorna uma resposta
    public String oracle(String line){
        String ui[] = line.split(" ");

        if(ui[0].equals("help")){
            return "show, addUser _username _senha, showUsers, login _username _senha, "
            + "logout, changePass _senhaAntiga _senhaNova, addNote _titulo _texto,"
            + "addNote _titulo _texto, rmNote _titulo, showNotes";
        }
        
        else if(ui[0].equals("addUser")){
        	this.usuarios.add(ui[1], new Usuario(ui[1], ui[2]));
        }

        else if(ui[0].equals("showUsers")){
        	return this.usuarios.toString();
        }
        else if(ui[0].equals("login")){
        	this.gerlog.login(ui[1], ui[2]);
        }
        else if(ui[0].equals("logout")){
        	this.gerlog.logout();
        }
        else if(ui[0].equals("changePass")){
        	if(gerlog.getUsuario().testaPass(ui[1]))
        		gerlog.getUsuario().setPass(ui[2]);
        }

        else if(ui[0].equals("addNote")){
        	Usuario user = gerlog.getUsuario();
        	String texto = "";
        	for(int i = 2; i < ui.length; i++)
        		texto += ui[i] + " ";
        	user.addNote(ui[1], texto);
        }
        else if(ui[0].equals("rmNote")){
        	Usuario user = gerlog.getUsuario();
        	user.removerNota(ui[1]);
        }
        else if(ui[0].equals("showNotes")){
        	Usuario user = gerlog.getUsuario();
        	return user.mostrarNotas();
        }
        else{
            return "comando invalido";
        }
        return "done";
    }
}

public class Servico_Anitacoes {
    //cria um objeto scan para ler strings do teclado
    static Scanner scan = new Scanner(System.in);
    
    //aplica um tab e retorna o texto tabulado com dois espaços
    static private String tab(String text){
        return "  " + String.join("\n  ", text.split("\n"));
    }
    
    public static void main(String[] args) {
        Controller cont = new Controller();
        System.out.println("Digite um comando ou help:");
        while(true){
            String line = scan.nextLine();

            try {
                //se não der problema, faz a pergunta e mostra a resposta

                System.out.println(tab(cont.oracle(line)));
            }catch(Exception e) {
                //se der problema, mostre o erro que deu
                System.out.println(tab(e.getMessage()));
            }
        }
    }
}
