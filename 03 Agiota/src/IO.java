import java.util.ArrayList;
import java.util.Scanner;

class Transacao{
	String clienteId;
	float valor;
	int idTrns;
	
	public Transacao(int idTrns, String clienteId, float valor){
		this.idTrns = idTrns;
		this.clienteId = clienteId;
		this.valor = valor;
	}
	
	public String toString(){
		return "\nid:" + this.idTrns + " [" + this.clienteId + " " + this.valor + "]";
	}
}

class Pessoa{
	ArrayList<Transacao> transacoes;
	String nome;
	float valor;
	boolean vida;
	String clienteId;
	float saldo;
	int idTrns;
	
	public Pessoa(String clienteId, String nome){
		this.transacoes = new ArrayList<Transacao>();
		this.vida = true;
		this.clienteId = clienteId;
		this.nome = nome;
		this.valor = 0;
		this.saldo = 0;
	}
	
	public String toString(){
		return "id:" + idTrns + "[" +this.clienteId + " "  + this.valor + "]\n";
	}
	
}

class Sistema{
	ArrayList<Pessoa> pessoas;
	public float valorInicial;
	int idTrns = 0;
	
	public Sistema(float valorinit){
		this.valorInicial = valorinit;
		this.pessoas = new ArrayList<Pessoa>();
	}
	
	public String toString(){
		String saida = " ";
			if(pessoas != null){
				for(Pessoa p : pessoas)
					saida += p;
			}	
		return saida;
	}
	
	public Pessoa buscar(String clienteId){
		for(Pessoa p : pessoas){
			if(p.clienteId.equals(clienteId)){
				return p;
			}
		}
		return null;
	}
	
	public void addCliente(Pessoa cliente){
		for(Pessoa p : pessoas){
			if(p.clienteId.equals(cliente.clienteId))
				throw new RuntimeException("fail: Cliente " + cliente.clienteId + " já existe.");
		}
		pessoas.add(cliente);
	}
	
	public void emprestar(String clienteId, float valor){
		if(this.buscar(clienteId) == null)
			throw new RuntimeException("fail: cliente " + clienteId + " não existe");
		
		for(int i = 0 ; i < pessoas.size(); i++)
			if ((pessoas.get(i).clienteId.equals(clienteId)) && (valor <= this.valorInicial)){							
					this.valorInicial -= valor;
					pessoas.get(i).valor = -valor;
					pessoas.get(i).saldo += (-valor);
					System.out.println("id:" + this.idTrns  + " [" + clienteId + " " + -valor + "]");
					System.out.println(pessoas.get(i).saldo);
					pessoas.get(i).idTrns = this.idTrns;
					pessoas.get(i).transacoes.add(new Transacao(pessoas.get(i).idTrns, pessoas.get(i).clienteId,
													-pessoas.get(i).valor));
					this.idTrns ++;
			}
		if(valor > this.valorInicial)
			throw new RuntimeException("fail: fundos insuficiente");
	}
	
	public String extrato(){
		String saida = "";
			for(int i = 0; i < pessoas.size(); i++){
				if(pessoas.get(i).saldo != 0)
					saida += pessoas.get(i).transacoes + "\n";
			}
		return saida;
	}
	public void mostrarCli(String clienteId){	
		for(int i = 0; i < pessoas.size(); i++){
			if(pessoas.get(i).clienteId.equals(clienteId)){
				for(Transacao t : pessoas.get(i).transacoes)
					System.out.println(t);
					System.out.println("saldo: " + pessoas.get(i).saldo);	
			}
		}
	}
	
	public void mostarAllCli(){
		for(Pessoa p : pessoas){
			System.out.println(p.clienteId + ":" + p.nome + ":" + p.saldo);
		}		
	}
	
	public void pagar(String clienteId, float valor){
			for(int i = 0; i < pessoas.size(); i++)
				if(pessoas.get(i).clienteId.equals(clienteId))
					if (-valor >= this.pessoas.get(i).saldo){
							this.pessoas.get(i).saldo += valor;
							System.out.println("id:" + this.idTrns + " [" + clienteId + " " + valor + "]");
							System.out.println(pessoas.get(i).saldo);
							pessoas.get(i).idTrns = this.idTrns;
							pessoas.get(i).saldo += valor;
							pessoas.get(i).transacoes.add(new Transacao(pessoas.get(i).idTrns, pessoas.get(i).clienteId,
															pessoas.get(i).valor));
							this.idTrns++;
							//transacoes.add(new Transacao(Pessoa.idTrns, clienteId, valor));
					}
					else{
							throw new RuntimeException("fail: valor maior que a divida");
					}
				else
					throw new RuntimeException("fail: cliente " + clienteId + "não existe");
	}
	
	public void matar(String clienteId){
		for(int i = 0; i < pessoas.size(); i++)
			if(pessoas.get(i).clienteId.equals(clienteId))
				pessoas.remove(i);
	}			
}

class Controller{
    Sistema sistema;
    static final float valorInit = 500;
   
    public Controller() {
    	this.sistema = new Sistema(valorInit);
    }
    
    //nossa funcao oraculo que recebe uma pergunta e retorna uma resposta
    public String oracle(String line){
        String ui[] = line.split(" ");

        if(ui[0].equals("help")){
            return "show, init _ValorInicial, nwcli _idPessoa _nomePessoa, \n"
            		+ " emprestar _clienteId _valor, latran, lscli _idPessoa,\n lacli, "
            		+ "receber _idPessoa _valor";
        }
        else if(ui[0].equals("show")){
        	System.out.println("Sistema iniciado com " + sistema.valorInicial + " reais.");
        }
        else if(ui[0].equals("init")){
            sistema = new Sistema(Float.parseFloat(ui[1]));
        }
        else if(ui[0].equals("nwcli")){
        	sistema.addCliente(new Pessoa(ui[1], ui[2]));
        }
        else if(ui[0].equals("emprestar")){
        	sistema.emprestar(ui[1], Float.parseFloat(ui[2]));
        }
        else if(ui[0].equals("latran")){
        	return sistema.extrato();
        }
        else if(ui[0].equals("lscli")){
        	sistema.mostrarCli(ui[1]);
        }
        else if(ui[0].equals("lacli")){
        	sistema.mostarAllCli();
        }
        else if(ui[0].equals("receber")){
        	sistema.pagar(ui[1],Float.parseFloat(ui[2]));
        }
        else if(ui[0].equals("matar")){
        	sistema.matar(ui[1]);
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