import java.util.ArrayList;
import java.util.Scanner;

class Espiral{
	String nome;
	int qnt;
	float preco;
	
	public Espiral(){
		this.nome = "-";
		this.qnt = 0;
		this.preco = 0;
	}
	
	public String toString() {
		return "["+ this.nome + " : "+  this.qnt + " U" + " : " + this.preco + " RS]";
	}
}

class Maquina{
	float saldo;
	float lucro;
	ArrayList<Espiral> espirais;
	int qtdEspirais;
	int maxProdutos;
	
	public Maquina(int qtdEspirais, int maxProdutos){
		this.qtdEspirais = qtdEspirais;
		this.maxProdutos = maxProdutos;
		this.saldo = 0;
		this.lucro = 0;
		this.espirais = new ArrayList<Espiral>();
		for(int i = 0; i < qtdEspirais; i++)
			this.espirais.add(new Espiral());
	}
	
	public String toString() {
		String saida = "";
		for(int i = 0; i < espirais.size(); i++)
			saida += i + " " + espirais.get(i) + "\n";
		return saida;
	}
	
	public void alterarEspiral(int indice, String nome, float preco){
		if(this.espirais.get(indice).qnt > maxProdutos){
			throw new RuntimeException("fail: limite de produtos e " + maxProdutos + " por espiral");
		}
		this.espirais.get(indice).nome = nome;
		this.espirais.get(indice).preco = preco;
	}
	
	public void limpar(int indice){
		this.espirais.get(indice).nome = "-";
		this.espirais.get(indice).preco = 0;
		this.espirais.get(indice).qnt = 0;
	}
	
	public void dinheiro(float valor){
		this.saldo += valor;
	}
	
	public void comprar(int indice){
		this.saldo -= this.espirais.get(indice).preco;
		if(this.espirais.get(indice).qnt > 0){
			this.espirais.get(indice).qnt -= 1;
		}
		else{
			throw new RuntimeException("fail: espiral sem produtos");
		}
		if(this.espirais.get(indice).preco >= this.saldo){
			this.saldo -= this.espirais.get(indice).preco;
			System.out.println("comprou um xaverde. Saldo: " + this.saldo);
		}
		else{
			throw new RuntimeException(this.espirais.get(indice).nome + " valor " + 
										this.espirais.get(indice).preco + ", saldo insuficiente");
		}
	}
	
	public double troco(){
		return Double.parseDouble("voce recebeu " + saldo);
	}
	
	public double saldo(){
		return this.saldo;
	}
	
}

class Controller{
    Maquina maq;
    static final int DEFAULT_ESPIRAIS = 3;
    static final int DEFAULT_MAX = 5;
    public Controller() {
        maq = new Maquina(DEFAULT_ESPIRAIS, DEFAULT_MAX);
    }
    
    //nossa funcao oraculo que recebe uma pergunta e retorna uma resposta
    public String oracle(String line){
        String ui[] = line.split(" ");

        if(ui[0].equals("help")){
        	if(maq.saldo != 0.0)
        		System.out.println(maq.saldo);
            return "show, init _espirais _maximo";
        }
        else if(ui[0].equals("iniciar")){
            maq = new Maquina(Integer.parseInt(ui[1]), Integer.parseInt(ui[2]));
        }
        else if(ui[0].equals("show")){
            return "" + maq;
        }
        else if(ui[0].equals("set")){ // 0- set," ", 1- indice," ", 2- nome," ", 3- qnt, " ", 4- preco 
        	maq.alterarEspiral(Integer.parseInt(ui[1]), ui[2], Float.parseFloat(ui[4]));
        	maq.espirais.get(Integer.parseInt(ui[1])).qnt = Integer.parseInt(ui[3]);
        }
        else if(ui[0].equals("limpar")){
        	maq.limpar(Integer.parseInt(ui[1]));
        }
        else if(ui[0].equals("dinheiro")){
        	maq.dinheiro(Float.parseFloat(ui[1]));
        }
        else if(ui[0].equals("troco")){
        	maq.troco();
        }
        else if(ui[0].equals("comprar")){
        	maq.comprar(Integer.parseInt(ui[1]));
        }
        else if(ui[0].equals("saldo")){
        	System.out.println(maq.saldo());
        }
        else{
            return "comando invalido";
        }
        return "done";
    }
}

public class IO {
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

