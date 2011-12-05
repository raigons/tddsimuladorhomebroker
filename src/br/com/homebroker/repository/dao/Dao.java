package br.com.homebroker.repository.dao;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.caelum.vraptor.ioc.Component;
import br.com.homebroker.model.Active;
import br.com.homebroker.repository.RepositoryInterface;
import br.com.homebroker.util.SearchParams;

@Component
public abstract class Dao<E> implements RepositoryInterface<E>{
	
	protected Session session;
	
	private Class<E> clazz;
	
	public Dao(){
		  
	}
	
	@SuppressWarnings("unchecked")
	public Dao(Session session) {
		super();
		this.session = session;
		this.clazz = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	public E search(SearchParams searcher){
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	public E search(Long id){
		E result = (E) session.createCriteria(getObjectClass()).add(Restrictions.eq("id", id)).uniqueResult();
		if(result != null)
			return result;
		return null;
	}

	public E seach(String code){
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<E> search(Active active) {
		active = (Active) this.session.load(Active.class, active.getCode());
		if(active != null)
			return session.createCriteria(this.getObjectClass()).add(Restrictions.eq("active", active)).list();
		return new ArrayList<E>();
	}
	
	public boolean delete(E toDelete) {
		// TODO Auto-generated method stub
		return true;
	}

	@SuppressWarnings("unchecked")
	public List<E> search(Active active, Date begin, Date end) {
		active = (Active) this.session.load(Active.class, active.getCode());
		if(active != null){
			Criteria criteria = this.session.createCriteria(getObjectClass());
			criteria.add(Restrictions.eq("active", active));
			criteria.add(Restrictions.between("date", begin, end));
			List<E> list = criteria.list();
			return list;	
		}
		return new ArrayList<E>();
	}

	@Override
	public String getObjectClassName() {
		return this.clazz.getSimpleName();
	}
	
	@Override
	public Class<E> getObjectClass(){
		return this.clazz;
	}

	public boolean save(E object) {
		this.session.save(object);
		try{
			this.session.beginTransaction().commit();
			return true;
		}catch(HibernateException exception){
			return false;
		}
	}
}
