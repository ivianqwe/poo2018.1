import java.util.Scanner;

class Controller{
	Repositorio<Usuario> usuarios;
	Repositorio<Grupo> grupos;

    public Controller() {
		usuarios = new Repositorio<Usuario>("usuario");
		grupos = new Repositorio<Grupo>("grupo");
    }
    
    //nossa funcao oraculo que recebe uma pergunta e retorna uma resposta
    public String oracle(String line){
        String ui[] = line.split(" ");

        if(ui[0].equals("help")){
            return "addUser_idUser, allUsers, newChat_idUser_idGrupo, chats_idUser, "
            		+ "\ninvite_idUser_idNewUser_IdGrupo, users_idGrupo, leave_idUser_idGrupo";
        }
        else if(ui[0].equals("addUser")){
        	usuarios.add(ui[1], new Usuario(ui[1]));
        }
        else if(ui[0].equals("allUsers")){
        	String saida = "";
        	for (Usuario user : usuarios.getAll()) {
				saida += user + " ";
			}
        	System.out.println("[ " + saida + "]"); 
        }
        else if(ui[0].equals("newChat")){
        	usuarios.get(ui[1]).addGrupo(usuarios.get(ui[1]), new Grupo(usuarios.get(ui[1]), ui[2]));
        	grupos.add(usuarios.get(ui[1]).getGrupos().get(ui[2]).getId_grupo(), usuarios.get(ui[1]).getGrupos().get(ui[2]));
        }
        else if(ui[0].equals("chats")){
        	System.out.println(usuarios.get(ui[1]).toStringGrupos());
        }
        else if(ui[0].equals("invite")){
        	grupos.get(ui[3]).inviteUser(usuarios.get(ui[2]), grupos.get(ui[3]));
        }
        else if(ui[0].equals("users")){
        	System.out.println(grupos.get(ui[1]).toStringUsuarios());
        }
        else if(ui[0].equals("leave")){//erro
        	grupos.get(ui[2]).getUsuarios().remove(ui[1]);
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
addUser ivian
addUser yara
allUsers
newChat ivian teste
newChat yara teste2
chats ivian
chats yara
invite ivian yara teste
chats yara
users teste
chats ivian

*/