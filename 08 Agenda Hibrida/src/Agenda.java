import java.util.ArrayList;

abstract class Entrada{
	private String idEnt;
	private boolean favoritado;
	
	public Entrada(String idEnt) {
		this.idEnt = idEnt;
		this.favoritado = false;
	}
	
	public String getIdEnt() {
		return idEnt;
	}
	public void setIdEnt(String idEnt) {
		this.idEnt = idEnt;
	}
	public boolean isFavoritado() {
		return favoritado;
	}
	public void setFavoritado(boolean favoritado) {
		this.favoritado = favoritado;
	}
	
	public abstract String toString();
}

class Fone{
	private String id;
	private String numero;
	static int indice = 0;
	int serial;
	
	public Fone(String id, String numero){
		this.id = id;
		this.numero = numero;
		Fone.indice++;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	public String toString(){
		return this.id + ":" + this.numero; 
	}
}

class Contato extends Entrada{
	private RepContinuo<Fone> fones;
	public boolean favorito;
	private String operadora;
	private String numero;
	
	public Contato(String id, String operadora, String numero){
		super(id);
		this.fones = new RepContinuo<Fone>("fone");
		this.operadora = operadora;
		this.numero = numero;
	}
	
	public RepContinuo<Fone> getFones() {
		return fones;
	}

	public void setFones(RepContinuo<Fone> fones) {
		this.fones = fones;
	}
	
	public String getOperadora() {
		return operadora;
	}

	public void setOperadora(String operadora) {
		this.operadora = operadora;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public void addFone(Fone fone){
		for(Fone f : fones.getAll()){
			if(f.getId().equals(fone.getId()))
				throw new RuntimeException("fail: já existe fone com id" + f.getId() + ".");
		}
		fones.add(fone);
	}
	
	public void removeFone(String id){
		for(int i = 0; i < fones.getAll().size(); i++){
			if(fones.getAll().get(i).getId().equals(id))
				fones.getAll().remove(i);
		}
		throw new RuntimeException("fail: fone não encontrado");
	}

	public String toString() {
		ArrayList<Fone> afones = fones.getAll();
		ArrayList<String> aind = fones.getKeys();

		String saida = this.favorito ? "@ " : "- ";
		for(int i = 0; i < afones.size(); i++){
			saida += "["+ aind.get(i)+ ":" + afones.get(i) + "]";
		}
		return super.getIdEnt() + "" + saida;
	}	
}

class Nota extends Entrada{
	private String texto;
	
	public Nota(String id, String texto){
		super(id);
		this.texto = texto;
	}
	
	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String toString(){
		return "N " + this.texto;
	}
}

public class Agenda {
	private RepContinuo<Entrada> entradas;
	private RepContinuo<Entrada> favaritos;
	
	public Agenda(){
		entradas = new RepContinuo<Entrada>("entrada");
		this.favaritos = new RepContinuo<Entrada>("favorito");
	}

	public Repositorio<Entrada> getEntradas() {
		return entradas;
	}

	public Repositorio<Entrada> getFavariotos() {
		return favaritos;
	}

	public void setFavariotos(RepContinuo<Entrada> favariotos) {
		this.favaritos = favariotos;
	}
	
	public void addEntrada(Entrada entrada){
		for(Entrada ent : entradas.getAll()){
			if(ent.getIdEnt().equals(entrada.getIdEnt()))
				throw new RuntimeException("fail: " + ent.getIdEnt() + " já existe essa entrada");
		}
		entradas.add(entrada);
	}
	
	public void removeEntrada(String idEntrada){
		for(int i = 0; i < entradas.getAll().size(); i++){
			if(entradas.getAll().get(i).getIdEnt().equals(idEntrada))
				if(entradas.getAll().get(i).isFavoritado())
					entradas.getAll().get(i).setFavoritado(false);
					entradas.getAll().remove(i);
					favaritos.getAll().remove(i);
		}
		throw new RuntimeException("fail: entrada não encontrada");
	}
	
	public void favoritar(Entrada entrada) {
		if(!entrada.isFavoritado())
			entrada.setFavoritado(true);
		this.favaritos.add(entrada);
	}
	
	public void desfavoritar(Entrada entrada) {
		if(!entrada.isFavoritado())
			entrada.setFavoritado(false);
		this.favaritos.remove(entrada.getIdEnt());
	}
	
	public String toStringFavoritos() {
		String saida="";
		for(Entrada t : favaritos.getAll())
			saida += t.toString() + "\n";
		return saida;
	}
	
	public String toString(){
		String saida = "";
		for(Entrada entrada : entradas.getAll()){
			if(entrada.isFavoritado())
				saida += "@" + entrada + "\n";
			else
				saida += "-" + entrada + "\n";
		}
		return saida;
	}
}

class MasterAgenda{
	Agenda agenda;
	private String senhaMestra;
	private RepIndexado<Contato> contatos;
	private RepIndexado<Nota> notas;
	
	public MasterAgenda(Agenda agenda){
		this.agenda = agenda;
		this.senhaMestra = "123";
		this.contatos = new RepIndexado<Contato>("contato");
		this.notas = new RepIndexado<Nota>("nota");
	}

	public Repositorio<Contato> getContatos() {
		return contatos;
	}

	public Repositorio<Nota> getNotas() {
		return notas;
	}

	public void setSenhaMestra(String senhaMestra) {
		this.senhaMestra = senhaMestra;
	}

	public void addContato(Contato contato){
		for(Contato c : contatos.getAll()){
			if(c.getIdEnt().equals(contato.getIdEnt()))
				throw new RuntimeException("fail: contato " + contato.getIdEnt() + " já existe");
		}
		contatos.add(contato.getIdEnt(),contato);
		agenda.addEntrada(contato);
	}
	
	public void addNota(Nota nota){
		for(Nota n : notas.getAll()){
			if(n.getIdEnt().equals(nota.getIdEnt()))
				throw new RuntimeException("fail: nota " + nota.getIdEnt() + " já existe");
		}
		notas.add(nota.getIdEnt(), nota);
		agenda.addEntrada(nota);
	}
	
	public void removeContato(String id){
		for(int i = 0; i < contatos.getAll().size(); i++){
			if(contatos.getAll().get(i).getIdEnt().equals(id))
				contatos.getAll().remove(i);
		}
		agenda.removeEntrada(id);
	}
	
	public void removeNota(String id){
		for(int i = 0; i < notas.getAll().size(); i++){
			if(notas.getAll().get(i).getIdEnt().equals(id))
				notas.getAll().remove(i);
		}
		agenda.removeEntrada(id);
	}
}










