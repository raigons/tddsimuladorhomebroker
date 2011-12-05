package br.com.homebroker.util;

public class Param<E> {

	private E param;
	
	public Param(E param){
		this.param = param;
	}

	public E getParam() {
		return this.param;
	}

}
