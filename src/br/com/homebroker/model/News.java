package br.com.homebroker.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="noticia")
public class News{

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	@JoinColumn(name="id_do_ativo")
	private Active active;
	@Column(name="data")
	private Date date;
	@Column(name="manchete")
	private String headline;
	@Column(name="conteudo")
	private String text;
	
	private String link;

	public News(){
		
	}
	

	public News(Long id){
		this.id = id;
	}
	
	public News(Active active, Date date, String headline, String text, String link){
		this.active = active;
		this.date = date;
		this.headline = headline;
		this.text = text;
		this.link = link;
	}
	
	public Long getId() {
		return id;
	}

	public Active getActive() {
		return active;
	}

	public String getHeadline() {
		return headline;
	}

	public String getText() {
		return text;
	}

	public String getLink() {
		return link;
	}	
	
	public Date getDate() {
		return date;
	}

	public String getActiveCode() {
		return this.active.getCode();
	}

	/*hibernate set methods*/
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setActive(Active active) {
		this.active = active;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setLink(String link) {
		this.link = link;
	}
}
