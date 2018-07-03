import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

class Telefone {
	String idFone;
	int numero;
	
	public Telefone(String idFone, int numero){
		this.idFone = idFone;
		this.numero = numero;
	}
	
	public static ArrayList<String> split(String str, String separator){
		ArrayList<String> arraystr = new ArrayList<String>();
		Collections.addAll(arraystr, str.split(separator));
		return arraystr;
	}
	
	public String ToString(){
		return this.idFone + ":" + this.numero;
	}
	
}

class Contato{
	String nome;
	ArrayList<Telefone> telefones;
	
	public Contato(String nome){
		this.nome = nome;
		this.telefones = new ArrayList<Telefone>();
	}
	
	public void addFone(Telefone fone){
		for(Telefone t : telefones){
			if(t.idFone.equals(fone.idFone))
				System.out.println("Telefone não adicionado");
		}
		telefones.add(fone);
	}
	
	public boolean rmFone(String idFone){
		for(int i = 0; i < telefones.size(); i++){
			if(telefones.get(i).idFone.equals(idFone)){
				telefones.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public String toString(){
		String saida = "";
		for(Telefone t : telefones){
			saida += "[" + t.ToString() + "]";
		}
		return saida;
	}
}

public class IO {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner entrada = new Scanner(System.in);

		Contato c = new Contato(null);
		
		while(true){
			System.out.println("Digite um comando");
			String user = entrada.nextLine();
			ArrayList<String> arraystr = new ArrayList<String>();
			arraystr = Telefone.split(user, " ");
			
			if(arraystr.get(0).equals("init")){
				c.nome = arraystr.get(1);
				System.out.println("done");
			}
			
			else if(arraystr.get(0).equals("show")){
				System.out.println(c.nome + " " + c.toString());
			}
			
			else if(arraystr.get(0).equals("addFone")){
				c.addFone(new Telefone(arraystr.get(1), Integer.parseInt(arraystr.get(2))));
				System.out.println("done");
			}
			else if(arraystr.get(0).equals("rmFone")){
				if(c.rmFone(arraystr.get(1)))
					System.out.println("done");
				else
					System.out.println("Telefone não removido");
			}
		}

	}

}

