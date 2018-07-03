import java.util.Scanner;

class Controller{
	Repositorio<Usuario> usuarios;
	Repositorio<Tweet> tweets;
	TweetGerenciador ger;

    public Controller() {
		usuarios = new Repositorio<Usuario>("Usuario");
		tweets = new Repositorio<Tweet>("tweet");
		ger = new TweetGerenciador(tweets);
    }
    
    //nossa funcao oraculo que recebe uma pergunta e retorna uma resposta
    public String oracle(String line){
        String ui[] = line.split(" ");

        if(ui[0].equals("help")){
            return "nwuser_idCli_nome, lauser, ";
        }
        else if(ui[0].equals("nwuser")){
        	usuarios.add(ui[1], new Usuario(ui[1]));
        }
        else if(ui[0].equals("lauser")){
        	String saida = "";
        	for(Usuario user : usuarios.getAll()){
        		saida += user.getIduser() + "\n" + "	seguidores"  + user.toStringSeguidores() + "\n" + "	seguidos" + user.toStringSeguidos() + "\n";
        	}
        	System.out.println(saida);
        }
        else if(ui[0].equals("seguir")){
        	usuarios.get(ui[1]).seguir(usuarios.get(ui[2]));
        }
        else if(ui[0].equals("twittar")){
        	Usuario user = usuarios.get(ui[1]);
        	String texto = "";
        	for(int i = 2 ; i<ui.length; i++){
 	    	   texto += ui[i] + " ";
 	        }
        	user.twittar(ger.novoTweet(usuarios.get(ui[1]), texto));	
        }
        else if(ui[0].equals("timeline")){
        	System.out.println("	timeline " + usuarios.get(ui[1]).getIduser());
        	System.out.println(usuarios.get(ui[1]).toStringTimeLine());
        }
        else if(ui[0].equals("myTweets")){
			System.out.println(usuarios.get(ui[1]).toStringMyTweets());        
		}
        else if(ui[0].equals("unread")){
        	System.out.println(usuarios.get(ui[1]).unread());
        }
        else if(ui[0].equals("like")){
			usuarios.get(ui[1]).darLike(Integer.parseInt(ui[2]));
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
                //se der probema, mostre o erro que deu
                System.out.println(tab(e.getMessage()));
            }
        }
    }
}

/*
nwuser ivian
nwuser yara
lauser 
seguir ivian yara
lauser
twittar ivian fazendo tabalho poo
twittar yara estou estudando para af
timeline ivian
myTweets yara
twittar yara estou ajudando para af do ivian
twittar yara estou ajudando para af do ivian2
unread ivian
timeline ivian
like ivian 2
timeline ivian

 */