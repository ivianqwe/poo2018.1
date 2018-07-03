class Animal{
	public int idSta;
	public String idAni;
	public Cliente dono;
	public String especie;
	
	public Animal(Cliente dono, String idAni, String especie){
		this.dono = dono;
		this.idAni = idAni;
		this.especie = especie;
	}
	
	public String toString(){
		return idSta +":"+ idAni + ":" + especie;
	}
}

class Cliente{
	public Repositorio<Animal> animais;
	public String nome;
	public String idcli;
	
	public Cliente(String id, String nome){
		this.nome = nome;
		this.idcli = id;
		animais = new Repositorio<Animal>("animais");
	}
	
	public String toString(){
		return idcli + ": " + nome;
	}
}

class Venda{
	public int idSta;
	public String idAni;
	public String idCli;
	public String idSer;
	
	public Venda(String idAni, String idCli, String idSer){
		this.idAni = idAni;
		this.idCli = idCli;
		this.idSer = idSer;
	}
	
	public String toString(){
		return idSta + " " + this.idAni + ":" + this.idCli + ":" + this.idSer; 
	}
}

public class Servico {
	public String idser;
	public float valor;
	
	public Servico(String id, float valor){
		this.idser = id;
		this.valor = valor;
	}
	
	public String toString(){
		return idser + " " + valor;
	}
}
