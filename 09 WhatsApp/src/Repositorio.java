import java.util.ArrayList;

class Elemento<T>{
	public String id;
	public T value;
	
	public Elemento(String id, T value){
		this.id = id;
		this.value = value;
	}
	
	public String toString(){
		return id + ":" + value;
	}
}

public class Repositorio<T> {
	ArrayList<Elemento<T>> elemens;
	public String tipo;
	
	public Repositorio(String tipo){
		this.tipo = tipo;
		elemens = new ArrayList<Elemento<T>>();
	}
	
	public void add(String id, T t){
		for(Elemento<T> elem : elemens){
			if(elem.id.equals(id))
				throw new RuntimeException("fail: " + tipo + " " + id + " já existe.");
		}
		elemens.add(new Elemento<T>(id, t));
	}
	
	public T get(String id){
		for(Elemento<T> elem : elemens){
			if(elem.id.equals(id))
				return elem.value;
		}
		throw new RuntimeException("fail: " + tipo + " " + id + " não encontrado.");
	}
	
	public void remove(String id){
		for(int i = 0; i < elemens.size(); i++){
			if(elemens.get(i).id.equals(id))
				elemens.remove(i);
		}
		throw new RuntimeException("fail: " + tipo + " " + id + " não encontrado.");
	}
	
	public ArrayList<T> getAll(){
		ArrayList<T> all = new ArrayList<T>();
		for(Elemento<T> elem : elemens){
			all.add(elem.value);
		}
		return all;
	}
	
	public String toString(){
		String saida = "";
		for(Elemento<T> elem : elemens){
			saida += elem.id + " ";
		}
		return saida;
	}
}
