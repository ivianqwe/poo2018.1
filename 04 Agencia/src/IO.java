import java.util.Scanner;

class Controller{
	Agencia agencia;
	Gerenciador_login io;
	
    public Controller() {
    	agencia = new Agencia();
    	io = new Gerenciador_login(agencia.getClientes());
    }
    
    //nossa funcao oraculo que recebe uma pergunta e retorna uma resposta
    public String oracle(String line){
        String ui[] = line.split(" ");

        if(ui[0].equals("help")){
            return "addCliente_idcliente, abrirConta_idcliente, encerrarConta_idcliente_idconta, showAll, "
            		+ "login_idcliente, logout_idcliente, show, saldo_idconta, saque_idconta_valor, deposito_idconta_valor, "
            		+ "extrato_idconta, ";
        }
        else if(ui[0].equals("addCliente")){
        	agencia.getClientes().add(ui[1], new Cliente(ui[1]));
        	agencia.getClientes().get(ui[1]).addConta(new Conta(Conta.ultConta++));
        	System.out.println("conta " + Conta.ultConta + " adicionada ao cliente " );
        }
        else if(ui[0].equals("abrirConta")){
        	agencia.getClientes().get(ui[1]).addConta(new Conta(Conta.ultConta));
        	System.out.println("conta " + Conta.ultConta + " adicionada ao cliente " );
        }
        else if(ui[0].equals("encerrarConta")){
        	agencia.getClientes().get(ui[1]).encerarConta(Integer.parseInt(ui[2]));
        }
        else if(ui[0].equals("showAll")){
        	System.out.println(agencia);
        }
        else if(ui[0].equals("login")){
        	io.login(agencia.getClientes().get(ui[1]).getIdCliente());
        }
        else if(ui[0].equals("logout")){
        	io.logout();
        }
        else if(ui[0].equals("show")){
        	System.out.println("Cliente: " + io.getUsuario().getIdCliente() + "\n");
        	String saida = "";
        	double saldo = 0;
        	for(Conta c : io.getUsuario().getContas()){
        		saida += c.toString() + "\n";
        		saldo += c.getSaldo();
        	}
        	System.out.println(saida);
        	System.out.println("Saldo total: " + saldo);
        }
        else if(ui[0].equals("saldo")){
        	io.getUsuario().getContas().get(Integer.parseInt(ui[1])).getSaldo();
        }
        else if(ui[0].equals("saque")){
        	io.getUsuario().getContas().get(Integer.parseInt(ui[1])).sacar(Double.parseDouble(ui[2]));
        }
        else if(ui[0].equals("deposito")){
        	io.getUsuario().getContas().get(Integer.parseInt(ui[1])).depositar(Double.parseDouble(ui[2]));
        }
        else if(ui[0].equals("extrato")){
        	io.getUsuario().getContas().get(Integer.parseInt(ui[1])).extrado();
        }
        else if(ui[0].equals("transf")){
        	String other = agencia.procuraConta(Integer.parseInt(ui[2]));
        	io.getUsuario().getContas().get(Integer.parseInt(ui[1])).transferir(
        			agencia.getClientes().get(other).getRepContas().get(ui[2]), Double.parseDouble(ui[3]));
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
addCliente Ivian
abrirConta Ivian
showAll
addCliente Yara
abrirConta Yara
showAll
login Ivian
show
deposito 1 550
extrato 1


 */
