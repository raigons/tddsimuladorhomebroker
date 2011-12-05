package br.com.homebroker.model.util;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.ComponentFactory;

@Component
@ApplicationScoped
public class SesssionFactoryCreator implements ComponentFactory<SessionFactory>{

	private SessionFactory factory;
	
	@SuppressWarnings("deprecation")
	@PostConstruct
	public void create(){
		System.out.println("Abrindo fábrica de Sessões");
		AnnotationConfiguration config = new AnnotationConfiguration();
		config.configure();
		//allowReconstructDatabase(config);
		factory = config.buildSessionFactory();
	}
	
	@SuppressWarnings("deprecation")
	private void allowReconstructDatabase(AnnotationConfiguration config){
		SchemaExport sc = new SchemaExport(config);
		sc.create(true, true);
	}
	
	@Override
	public SessionFactory getInstance() {
		return factory;
	}
	
	@PreDestroy
	public void destroy(){
		System.out.println("Fechando fábrica de sessões");
		factory.close();
	}

}
