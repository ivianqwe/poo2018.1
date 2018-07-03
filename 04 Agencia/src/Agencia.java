import java.util.ArrayList;

class Gerenciador_login{
	private Repositorio <Cliente> clientes;
	private Cliente clien;

	public Gerenciador_login(Repositorio<Cliente> cliente){
		this.clientes = cliente;
		this.clien = null;
	}

	public void login(String username){
		if(clien != null){
			throw new RuntimeException("fail: Já existe alguem logado");
		}
		this.clien = clientes.get(username);
	}

	public void logout(){
		if(this.clien == null)
			throw new RuntimeException("fail: ninguem logado");
		this.clien = null;
	}

	public Cliente getUsuario(){
		if(clien == null)
			throw new RuntimeException("fail: ninguem logado");
		return clien;
	}
}

class Agencia {
	
	private Repositorio<Cliente> clientes;
	
	public Agencia(){
		this.clientes = new Repositorio<Cliente>("cliente");
	}
	
	public Repositorio<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(Repositorio<Cliente> clientes) {
		this.clientes = clientes;
	}

	public boolean abrirConta(String Cpf){
		return true;
	}
	public String procuraConta(int id){
		String saida = "";
		for(Cliente c : clientes.getAll()){
			for(Conta conta : c.getContas())
				if(conta.getNumero() == id)
					saida = c.getIdCliente();
		}
		return saida;
	}
	public String toString(){
		String saida = "";
		for(Cliente c : clientes.getAll()){
			saida += c.getIdCliente() + " [" + c.toString() + "]\n";
		}
		return saida;
	}
	
}

class Cliente implements Comparable<Cliente>{	
	private String id;
	private Repositorio<Conta> contas;
	
	public Cliente(String idCliente){
		this.id = idCliente;
		this.contas = new Repositorio<Conta>("conta");
	}
	
	public boolean addConta(Conta conta){
		for(Conta c : contas.getAll()){
			if(c.getNumero() == conta.getNumero())
				throw new RuntimeException("Conta já cadastrada");
		}
		
		if(conta == null){
			throw new RuntimeException("Conta não encontrada");
		}
		
		int i = 0;
		for(Conta c : contas.getAll()){
			if(c.isAtiva()){
				i++;
			}
		}
		
		if(i >= 2){
			throw new RuntimeException("Limite maximo de conta atingido");
		}
		
		this.contas.getAll().add(conta);
		return true;
	}
	
	public boolean encerarConta(int numero){
		for(Conta c : contas.getAll()){
			if(c.getNumero() == numero){
				if(c.getSaldo() == 0){
					c.encerrar();
					return true;
				}
			}
		}
		return false;
	}

	public String getIdCliente() {
		return id;
	}

	public void setIdCliente(String idCliente) {
		this.id = idCliente;
	}

	public ArrayList<Conta> getContas() {
		return contas.getAll();
	}
	
	public Repositorio<Conta> getRepContas() {
		return contas;
	}

	public void setContas(Repositorio<Conta> contas) {
		this.contas = contas;
	}
	
	public String toString(){
		String saida = "";
		for(Conta c : contas.getAll()){
			saida += c.getNumero() + " ";
		}
		return " " + saida;
	}

	
	public int compareTo(Cliente arg0) {
		return this.id.compareTo(arg0.id);
	}
}

class Conta {
	public static int ultConta = 0;
	private double saldo;
	private int numero;
	private ArrayList<Operacao> operacoes;
	private boolean ativa;
	
	public Conta(int numero){
		this.numero = numero;
		this.saldo = 0;
		this.operacoes = new ArrayList<>();
		this.ativa = true;
	}
	
	public boolean depositar(double valor){
		if(valor <= 0){
			return false;
		}
		this.saldo += valor;
		this.operacoes.add(new Operacao("deposito", valor, saldo));
		return true;
	}
	
	public boolean sacar(double valor){
		if(valor <= 0){
			throw new RuntimeException("Valor negativo");
		}
		if(valor > saldo){
			throw new RuntimeException("Valor maior que saldo");
		}
		
		this.saldo -= valor;
		this.operacoes.add(new Operacao("saque", valor, saldo));
		return true;
	}
	
	public boolean transferir(Conta other, double valor){
		if(!other.ativa){
			throw new RuntimeException("A conta destino não esta ativa");
		}
		
		if(this.sacar(valor)){
			other.depositar(valor);
			return true;
		}
		return false;
	}
	
	public void encerrar(){
		this.ativa = false;
	}
	
	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public ArrayList<Operacao> getOperacoes() {
		return operacoes;
	}

	public void setOperacoes(ArrayList<Operacao> operacoes) {
		this.operacoes = operacoes;
	}

	public boolean isAtiva() {
		return ativa;
	}

	public void setAtiva(boolean ativa) {
		this.ativa = ativa;
	}
	
	public String extrado(){
		String saida = "";
		for(Operacao op : operacoes){
			saida += op + "\n";
		}
		return saida;
	}
	
	public String toString(){
		return "Conta: " + this.numero + ", Saldo: " + this.saldo + ", Status: " + this.ativa;
	}
}

class Operacao {
	private String descricao;
	private double valor;
	private double saldoParcial;
	
	public Operacao(String descricao, double valor, double saldoParcial){
		this.descricao = descricao;
		this.valor = valor;
		this.saldoParcial = saldoParcial;
	}
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}
	
	public double getSaldoParcial() {
		return saldoParcial;
	}

	public void setSaldoParcial(double saldoParcial) {
		this.saldoParcial = saldoParcial;
	}
	
	public String toString(){
		return "Descrção: " + this.descricao + ", value: " + this.valor + ", saldo: " + this.saldoParcial;
	}
}
