import java.util.ArrayList;

class Elemento<T>{
	public String id;
	public T value;
	
	public Elemento(String id, T value){
		this.id = id;
		this.value = value;
	}
	
	public String toString(){
		return this.id + ": " + this.value;
	}
}

public class Repositorio<T> {
	public ArrayList<Elemento<T>> elemens;
	public String tipo;
	
	public Repositorio(String tipo){
		this.elemens = new ArrayList<Elemento<T>>();
		this.tipo = tipo;
	}
	
	public void add(String id, T t){
		for(Elemento<T> elem : elemens){
			if(elem.id.equals(id))
				throw new RuntimeException("fail: " + this.tipo + " " + id + " já existe.");
		}
		elemens.add(new Elemento<T>(id, t));
	}
	
	public void remove(String id){
		for(int i = 0; i < elemens.size(); i++){
			if(elemens.get(i).id.equals(id))
				elemens.remove(i);
		}
		throw new RuntimeException("fail: " + tipo + " " + id + " não encontrado.");
	}
	
	public T get(String id){
		for(Elemento<T> elem : elemens){
			if(elem.id.equals(id))
				return elem.value;
		}
		throw new RuntimeException("fail: " + tipo + " " + id + " não encontrado.");
	}
	
	public ArrayList<T> getAll(){
		ArrayList<T> getAll = new ArrayList<T>();
		for(Elemento<T> elem : elemens){
			getAll.add(elem.value);
		}
		return getAll;
	}
	
	public String toString(){
		String saida = " ";
		for(Elemento<T> elem : elemens){
			saida += elem.id + " ";
		}
		return saida;
	}
}
