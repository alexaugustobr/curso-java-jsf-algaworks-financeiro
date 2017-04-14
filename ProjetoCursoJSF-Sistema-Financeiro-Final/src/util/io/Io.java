package util.io;

public class Io {
	private static final String ANSI_RESET = "\u001B[0m";
	private static final String ANSI_BLACK = "\u001B[30m";
	private static final String ANSI_RED = "\u001B[31m";
	private static final String ANSI_GREEN = "\u001B[32m";
	private static final String ANSI_YELLOW = "\u001B[33m";
	private static final String ANSI_BLUE = "\u001B[34m";
	private static final String ANSI_PURPLE = "\u001B[35m";
	private static final String ANSI_CYAN = "\u001B[36m";
	private static final String ANSI_WHITE = "\u001B[37m";

	//para modificar a visualização de mensagens altere essas variaveis
	private static boolean mensagensPrioritarias=true;
	private static boolean mensagensErros=true;
	private static boolean mensagensAvisos=false;
	private static boolean mensagensGenericas=false;
	
	public static void out(String mensagem) {
		if(mensagensGenericas==true){
			System.out.println("[MENSAGEM] [ORIGEM: NULL] - "+mensagem);
		}
	}
	
	public static void out(String mensagem, String origem) {
		if(origem==null||origem.equals("")){
			origem="[ORIGEM: NULL] - "; 
		} else {
			origem="[ORIGEM: "+origem+"] - ";
		}
		if(mensagensGenericas==true){
			System.out.println("[MENSAGEM] "+origem+mensagem);
		}
	}

	public static void out(String mensagem, IoTipoMensagem tipoMensagem, String origem) {
		if(origem==null||origem.equals("")){
			origem="";
		} else {
			origem="[ORIGEM: "+origem+"] - ";
		}
		if(tipoMensagem==IoTipoMensagem.Avisos&&mensagensAvisos==true){
			System.out.println("[AVISO] "+origem+mensagem);
		}
		if(tipoMensagem==IoTipoMensagem.Erros&&mensagensErros==true){
			System.out.println("[ERRO] "+origem+mensagem);
		}
		if(tipoMensagem==IoTipoMensagem.Prioritarias&&mensagensPrioritarias==true){
			System.out.println("[MENSAGEM PRIORITARIA] "+origem+mensagem);
		}
		if(tipoMensagem==IoTipoMensagem.Genericas&&mensagensGenericas==true){
			System.out.println("[MENSAGEM] "+origem+mensagem);
		}
		if(tipoMensagem==null&&mensagensGenericas==true){
			System.out.println("[MENSAGEM] "+origem+mensagem);
		}
			
	}

}
