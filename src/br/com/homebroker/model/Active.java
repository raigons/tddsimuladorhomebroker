package br.com.homebroker.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ativo")
public class Active implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="codigo")
	private String code;
	@Column(name="nome_pregao")
	private String pregao;
	@Column(name="nome_companhia")
	private String company;

	public Active() {		
	}
	
	public Active(String code, String pregao, String company) {
		this.code = code;
		this.pregao = pregao;
		this.company = company;
	}

	public String getPregao(){
		return this.pregao;
	}

	public void setPregao(String pregao){
		this.pregao = pregao;
	}
	
	public String getCompany() {
		return this.company;
	}
	
	public void setCompany(String company){
		this.company = company;
	}
	
	public void setCode(String code){
		this.code = code;
	}
	
	public String getCode() {
		return this.code;
	}

}
