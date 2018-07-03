import java.util.ArrayList;

class Usuario{
	private String iduser;
	private Repositorio<Usuario> seguidos;
	private Repositorio<Usuario> seguidores;
	private Repositorio<Tweet> mytweets;
	private Repositorio<Tweet> timeline;
	public int naolidos;

	public Usuario(String iduser) {
		this.iduser = iduser;
		seguidores = new Repositorio<Usuario>("seguidores");
		seguidos = new Repositorio<Usuario>("seguidos");
		mytweets = new Repositorio<Tweet>("mytweets");
	    timeline = new Repositorio<Tweet>("timeline");
	    this.naolidos = 0;
	}

	public String getIduser() {
		return iduser;
	}

	public void setIduser(String iduser) {
		this.iduser = iduser;
	}

	public Repositorio<Usuario> getSeguidores() {
		return seguidores;
	}

	public void setSeguidores(Repositorio<Usuario> seguidores) {
		this.seguidores = seguidores;
	}

	public Repositorio<Usuario> getSeguidos() {
		return seguidos;
	}

	public void setSeguidos(Repositorio<Usuario> seguidos) {
		this.seguidos = seguidos;
	}

	public Repositorio<Tweet> getMytweets() {
		return mytweets;
	}

	public void setMytweets(Repositorio<Tweet> mytweets) {
		this.mytweets = mytweets;
	}

	public Repositorio<Tweet> getTimeline() {
		return timeline;
	}

	public void setTimeline(Repositorio<Tweet> timeline) {
		this.timeline = timeline;
	}
	
	public void seguir(Usuario other){
		other.seguidores.add(this.getIduser(), this);
		this.seguidos.add(other.getIduser(), other);
	}
	
	public void twittar(Tweet t){
		this.mytweets.add("" + t.getIdTw(), t);
		for(Usuario user : seguidores.getAll()){
				user.timeline.add("" + t.getIdTw(), t);
				user.naolidos++;
		}
	}

	public void darLike (int idTw){
		for(Tweet t : timeline.getAll()){
			if(t.getIdTw() == idTw){
				if(!t.isLike()){
					t.setLike(true);
					//t.setTexto(t.getTexto() + "{" + this.iduser + "}");
					t.darLiike(this.iduser);
					return;
				}
			}
		}
		throw new RuntimeException("fail: você não possui esse tweet");
	}
	
	public String unread(){
    	String saida = "";
    	if(this.naolidos > 0) {
    		for(int i = timeline.getAll().size(); i > naolidos; i--) {
    			System.out.println(timeline.getAll().size() + " = " + naolidos);
    			saida += " " + timeline.getAll().get(i);
    		}
    	}
    	this.naolidos = 0;
    	return "	" + saida;
	}
	
	public String toStringNaoLidas(){
		String saida = "";
			for(Tweet t : timeline.getAll()){
				if(this.naolidos > 0)
					saida += t + "\n";
			}
		this.naolidos = 0;
		return saida;
	}
	
	public String toStringMyTweets(){
		String saida = "";
		for(Tweet t : mytweets.getAll()){
			saida += " " + t.getIdTw() + " " + t.getUser().getIduser() + ": " + t.getTexto() + "\n"; 
		}
		return saida;
	}
	
	public String toStringTimeLine(){
    	String saida = "";
    	for(Tweet t : timeline.getAll()) {
    		   saida += " " + t.getIdTw() + " " + t.getUser().getIduser() + ": " + t.getTexto() + "\n"; 
    	}
    	this.naolidos = 0;
    	return "	" + saida;
    }
	
	public String toStringSeguidos(){
		String saida = "";
		for(Usuario user : seguidos.getAll()){
			saida += user.getIduser();
		}
		return "[" + saida + "]";
	}
	
	public String toStringSeguidores(){
		String saida = "";
		for(Usuario user : seguidores.getAll()){
			saida += user.getIduser(); 
		}
		return "[" + saida + "]";
	}
}

public class Tweet {
	private int idTw;
	Usuario user;
	private String texto;
	private boolean isLike;
	private ArrayList<String> seguidores;
	
	public Tweet(int id, Usuario user, String texto){
		this.idTw = id;
		this.user = user;
		this.texto = texto;
		this.user = user;
		this.seguidores = new ArrayList<String>();
	}

	public int getIdTw() {
		return idTw;
	}

	public void setIdTw(int idTw) {
		this.idTw = idTw;
	}

	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public boolean isLike() {
		return isLike;
	}

	public void setLike(boolean isLike) {
		this.isLike = isLike;
	}

	public ArrayList<String> getQuantidadedelikes() {
		return seguidores;
	}

	public void setQuantidadedelikes(ArrayList<String> quantidadedelikes) {
		this.seguidores = quantidadedelikes;
	}

	public void darLiike(String user){
		this.seguidores.add(user);
	}
	
	public String seguidores(){
		String saida = "";
		for(String like : seguidores){
			saida += like + ", \n";
		}
		return saida;
	}
	
	public String toString(){
		String saida = "";
		if(seguidores.size() <= 0)
			saida += "	" + this.idTw + " " + this.user.getIduser() + ": " + texto;
		else
			saida += "	" + this.idTw + " " + this.user.getIduser() + ": " + texto + " " + "[" + seguidores() + "]";
		return saida;
	}
}

class TweetGerenciador {
	private Repositorio<Tweet> tweets;
	int nextTweet;
	
	public TweetGerenciador(Repositorio<Tweet> t){
		this.tweets = t;
	}

	public Repositorio<Tweet> getTweets() {
		return tweets;
	}

	public void setTweets(Repositorio<Tweet> tweets) {
		this.tweets = tweets;
	}
	
	public Tweet novoTweet(Usuario user, String texto){
		Tweet tweet = new Tweet(nextTweet, user, texto);
		nextTweet++;
		return tweet;
	}

	public String showTweets(){
		String saida = "";
		for(Tweet t : tweets.getAll()){
			saida += t.toString() + "\n";
		}
		return saida;
	}
}


