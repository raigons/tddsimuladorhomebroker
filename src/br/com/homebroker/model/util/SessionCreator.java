package br.com.homebroker.model.util;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.ComponentFactory;
import br.com.caelum.vraptor.ioc.RequestScoped;

@Component
@RequestScoped
public class SessionCreator implements ComponentFactory<Session>{

	private final SessionFactory factory;
	private Session session;
	
	public SessionCreator(SessionFactory factory) {
		this.factory = factory;
	}
	
	@PostConstruct
	public void create(){
		System.out.println("Abrindo sessao no Banco de Dados");
		this.session = factory.openSession();
	}
	
	@Override
	public Session getInstance() {
		return this.session;
	}

	@PreDestroy
	public void destroy(){
		System.out.println("Fechando sessão no Banco de Dados");
		this.session.close();
	}
}
