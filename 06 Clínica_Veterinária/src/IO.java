import java.util.Scanner;

class Controller{
	int nextVen = 0;
	int nextAni = 1;
	float saldo = 0;
	Repositorio<Cliente> clientes;
	Repositorio<Servico> servicos;
	Repositorio<Venda> vendas;
	Repositorio<Animal> animais;
   
    public Controller() {
    	vendas = new Repositorio<Venda>("Venda");
    	servicos = new Repositorio<Servico>("Servico");
    	clientes = new Repositorio<Cliente>("Cliente");
    	animais = new Repositorio<Animal>("Animal");
    }

    
    //nossa funcao oraculo que recebe uma pergunta e retorna uma resposta
    public String oracle(String line){
        String ui[] = line.split(" ");

        if(ui[0].equals("help")){
            return "nwcli_idCli_nome, lacli, addAni_idCli _idAni _especie";
        }
        else if(ui[0].equals("nwcli")){
        	String nome = "";
        	for(int i = 2; i < ui.length; i++){
        		nome += ui[i] + " ";
        	}
        	clientes.add(ui[1], new Cliente(ui[1], nome));
        }
        else if(ui[0].equals("lacli")){
        	String saida = "";
        	for(Cliente c : clientes.getAll()){
        		saida += "cli " + c + "\n";
        	}
        	System.out.println(saida);
        }
        else if(ui[0].equals("addAni")){
        	//clientes.get(ui[1]).animais.add(ui[2], new Animal(new Cliente(ui[1],""), ui[2], ui[3]));
        	//clientes.get(ui[1]).animais.get(ui[2]).idSta = nextAni++;
        	animais.add(ui[2], new Animal(clientes.get(ui[1]), ui[2], ui[3]));
        	animais.get(ui[2]).idSta = nextAni++;
        }
        else if(ui[0].equals("lscli")){
        	String saida = "";        	
        	for(Animal ani : animais.getAll()){
        		saida += "[" + ani + "]";
        	}
        	System.out.println("cli " + clientes.get(ui[1]) + " " + saida);
        }
        else if(ui[0].equals("laani")){
        	String saida = "";        	
        	for(Animal ani : animais.getAll()){
        		saida += "[" + ani + "]\n";
        	}
        	System.out.println(saida);
        }
        else if(ui[0].equals("nwser")){
        	servicos.add(ui[1], new Servico(ui[1], Float.parseFloat(ui[2])));
        }
        else if(ui[0].equals("laser")){
        	String saida = "";
        	for(Servico s : servicos.getAll()){
        		saida += "[" + s + "]\n";
        	}
        	System.out.println(saida);
        }
        else if(ui[0].equals("nwven")){
        	clientes.get(ui[1]);
        	animais.get(ui[2]);
        	servicos.get(ui[3]);
        	vendas.add("" + nextVen, new Venda(ui[1], ui[2], ui[3]));
        	vendas.get("" + nextVen).idSta = nextVen++;
        	saldo += servicos.get(ui[3]).valor;
        }
        else if(ui[0].equals("laven")){
        	String saida = "";
        	for(Venda v : vendas.getAll()){
        		saida += "[" + v + "]\n";
        	}
        	System.out.println(saida);
        }
        else if(ui[0].equals("saldo")){
        	System.out.println("Saldo " + saldo + " reais" );
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


/*
nwcli ivian Ivian do Carmo
addAni ivian xani gato
addAni ivian lessi cat
lscli ivian
nwcli yara Yara do Torquato
addAni yara rei leão
laani
nwser tosa 30
nwser banho 15
nwser tingimento 150
laser
nwven ivian xani tosa
nwven r2d2 rex banho
nwven luke xuxu banho
nwven luke rosinha castracao
laven
saldo

*/
