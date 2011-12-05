package br.com.homebroker.repository.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.homebroker.model.Active;
import br.com.homebroker.model.Diary;

public class DaoDataDays<E> extends Dao<E> {
	
	public DaoDataDays(Session session) {
		super(session);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<E> search(Active active, Date begin, Date end){
		active = (Active) this.session.load(Active.class, active.getCode());
		if(active != null){
			Criteria criteria = this.session.createCriteria(super.getObjectClass());
			criteria.add(Restrictions.eq("active", active));
			criteria.add(Restrictions.between("date", begin, end));
			List<E> list = criteria.list();
			return list;	
		}
		return new ArrayList<E>();
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	private List<E> criterialist(){
		System.out.println("I'm here");
		Calendar calendar = Calendar.getInstance();
		
		List<E> list = new ArrayList<E>();
		list.add(((E) new Diary(1.0, 2.0, 2.0, 1.0, 220L, new Active("2A", "pregao", "company"), calendar.getTime())));
		return list;
	}

	@Override
	public List<E> listAll() {
		throw new RuntimeException("Intradays e Diários devem ser filtrados de acordo com um ativo e/ou um período");
	}
}
