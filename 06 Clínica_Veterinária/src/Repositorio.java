import java.util.ArrayList;

class Elemento<T>{
	public String id;
	public T value;
	
	public Elemento(String id, T value){
		this.id = id;
		this.value = value;
	}
	
	public String toString(){
		return this.id + ": "+ value;
	}
}

public class Repositorio<T> {
	private ArrayList<Elemento<T>> vet;
	private String tipo;
	
	public Repositorio(String tipo){
		this.tipo = tipo;
		this.vet = new ArrayList<Elemento<T>>();
	}
	
	public void add(String id, T t){
		for(Elemento<T> elem : vet){
			if(elem.id.equals(id))
				throw new RuntimeException("fail: " + tipo + " " + id + " ja existe");	
		}
		vet.add(new Elemento<T>(id, t));
	}
	
	public T get(String id){
		for(Elemento<T> elem : vet){
			if(elem.id.equals(id))
				return elem.value;
		}
		throw new RuntimeException("fail: " + tipo + " " + id + " nao existe");
	}
	
	public void remove(String id){
		for(int i = 0; i < vet.size(); i++){
			if(vet.get(i).id.equals(id))
				vet.remove(i);
		}
		throw new RuntimeException("fail: " + tipo + " " + id + " nao existe");
	}
	
	public ArrayList<T> getAll(){
		ArrayList<T> all = new ArrayList<T>();
		for(Elemento<T> elem : vet){
			all.add(elem.value);
		}
		return all;
	}
	
	public String toString(){
		String saida = "";
		for(Elemento<T> elem : vet){
			saida += elem.id + " "; 
		}
		return saida;
	}
}
