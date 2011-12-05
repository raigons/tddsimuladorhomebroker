package br.com.homebroker.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class DataDays{

	@Id
	@GeneratedValue
	private Long id;
	@Column(name="maximo")
	private Double max;
	@Column(name="minimo")
	private Double min;
	@Column(name="fechamento")
	private Double close;
	@Column(name="abertura")
	private Double open;

	private Long volume;

	@ManyToOne
	@JoinColumn(name="id_ativo")
	private Active active;
	
	@Column(name="data")
	private Date date;
	
	public Date getDate() {
		return date;
	}

	public DataDays(Double max, Double min, Double close, Double open,
			Long volume, Active active, Date date) {
		this.max = max;
		this.min = min;	
		this.close = close;
		this.open = open;
		this.volume = volume;
		this.active = active;
		this.date = date;
	}
	
	public DataDays(){
		
	}
	
	public void setId(Long id){
		this.id = id;
	}
	
	
	public Long getId() {
		return this.id;
	}
	
	public Double getMax() {
		return max;
	}

	public Double getMin() {
		return min;
	}

	public Double getClose() {
		return this.close;
	}

	public Double getOpen() {
		return this.open;
	}

	public Long getVolume() {
		return this.volume;
	}

	public String getActiveCode() {
		return this.active.getCode();
	}
}
