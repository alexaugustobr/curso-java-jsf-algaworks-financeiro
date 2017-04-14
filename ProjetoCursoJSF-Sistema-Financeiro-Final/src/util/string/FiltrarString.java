package util.string;

public final class FiltrarString {

	/**
	 * Metodo remove todos os espaÃ§os duplicados, no comeÃ§o, fim e meio da
	 * frase.
	 * 
	 * @param string
	 *            Frase a ter os espaÃ§os removidos
	 *
	 * @return {@code String} Frase com seus espaÃ§os removidos
	 *
	 * 
	 */
	public static boolean frasePossuiPalavra(String frase, String palavra, boolean ignoraMaiuscula) {

		if (frase == null || palavra == null || palavra.isEmpty() || frase.isEmpty())
			return false;
		else {
			if (ignoraMaiuscula) {
				return frase.toLowerCase().indexOf(palavra.toLowerCase()) > -1;
			} else {
				return frase.indexOf(palavra) > -1;
			}
		}
	}
	
	/**
	 * Metodo remove todos os espaÃ§os duplicados, no comeÃ§o, fim e meio da
	 * frase.
	 * 
	 * @param string
	 *            Frase a ter os espaÃ§os removidos
	 *
	 * @return {@code String} Frase com seus espaÃ§os removidos
	 *
	 * 
	 */
	public static int palavraPosicaoNaFrase(String frase, String palavra, boolean ignoraMaiuscula) {

		if (frase == null || palavra == null || palavra.isEmpty() || frase.isEmpty())
			return -1;
		else {
			if (ignoraMaiuscula) {
				return frase.toLowerCase().indexOf(palavra);
			} else {
				return frase.indexOf(palavra);
			}
		}
	}
	
	
	

}