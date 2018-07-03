import java.util.Scanner;

class Controller{
	MasterAgenda magenda;
	Agenda agenda;
	Repositorio<Entrada> entradas;
	
    public Controller() {
    	agenda = new Agenda();
    	this.magenda = new MasterAgenda(agenda);
    	entradas = new RepContinuo<Entrada>("entrada");
    }
    
    //nossa funcao oraculo que recebe uma pergunta e retorna uma resposta
    public String oracle(String line){
        String ui[] = line.split(" ");

        if(ui[0].equals("help")){
            return "addContato_idUser_id_numero, lacont, rmFone_idUser_idTelefone,\n"
            		+ "";
        }
        else if(ui[0].equals("addContato")){
        	Contato contato = new Contato(ui[1], ui[2], ui[3]);
        	for(int i = 2; i < ui.length; i++){
        		String dados[] = ui[i].split(":");
        		Fone fone = new Fone(dados[0], dados[1]);
        		contato.getFones().add(fone);
        	}
        	magenda.addContato(contato);
        }
        else if(ui[0].equals("lacont")){
        	String saida ="";
        	for(Contato c : magenda.getContatos().getAll()){
        		saida += c + "\n"; 
        	}
        	System.out.println(saida);
        }
        else if(ui[0].equals("rmFone")){
        	magenda.getContatos().get(ui[1]).removeFone(ui[2]);
        }
        else if(ui[0].equals("fav")){
        	magenda.agenda.favoritar(magenda.getContatos().get(ui[1]));
        }
        else if(ui[0].equals("lafav")){
        	System.out.println(magenda.agenda.toStringFavoritos());
        }
        else if(ui[0].equals("addNote")){
        	String texto = "";
        	for(int i = 2; i < ui.length; i++){
        		texto += ui[i] + " ";
        	}
        	Nota nota = new Nota(ui[1], texto);
        	magenda.addNota(nota);
        }
        else if(ui[0].equals("laNote")){
        	String saida ="";
        	for(Nota n : magenda.getNotas().getAll()){
        		saida += n + "\n"; 
        	}
        	System.out.println(saida);
        }
        else if(ui[0].equals("la")){
        	System.out.println(magenda.agenda.toString());
        }
        else if(ui[0].equals("setMasterPass")){
        	magenda.setSenhaMestra(ui[1]);
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
addContato ivian oi:88 tim:99
addContato yara oi:88 tim:99
lacont
rmFone ivian 0
fav ivian
la

*/
