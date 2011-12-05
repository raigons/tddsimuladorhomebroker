package br.com.homebroker.util.exceptions;

public class NothingFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NothingFoundException(){
		super("Nenhum resultado encontrado");
	}
	
}
