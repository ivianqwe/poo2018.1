import java.util.ArrayList;

class Elemento<T>{
	public String key;
	public T value;

	public Elemento(String key, T value) {
		this.key = key;
		this.value = value;
	}
	public String toString() {

		return key;
	}
}

public class Repositorio <T>{	
	private ArrayList<Elemento<T>> vet;
	private String typename;

	public Repositorio(String typename) {
		vet = new ArrayList<Elemento<T>>();
		this.typename = typename;
	}

	public void add(String key, T t) {
		for(Elemento<T> elem : vet)
			if(elem.key.equals(key))
				throw new RuntimeException(typename + " " + key + " ja existe");
		vet.add(new Elemento<T>(key, t));
	}

	T get(String key){
		for(Elemento<T> elem : vet){
			if(elem.key.equals(key)){
				return elem.value;
			}
		}
		throw new RuntimeException("fail: " + typename + "" + key + "não existe.");
	}

	void remove(String key){
		for(int i = 0; i < vet.size(); i++){
			if(vet.get(i).key.equals(key)){
				vet.remove(i);
				return ;
			}
		}
		throw new RuntimeException("fail: " + typename + "" + key + "não existe.");
	}

	public String toString() {
		String saida = "";
		for(Elemento<T> elem : vet)
			saida += elem + "\n";
		return saida;
	}
}
