package br.com.homebroker.repository.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.homebroker.model.Active;

public class DaoActive extends Dao<Active> {
	
	public DaoActive(Session session) {
		super(session);
	}

	@Override
	public List<Active> search(Active active, Date begin, Date end) {
		throw new RuntimeException("Ativos não podem ser buscados por intervalo de período");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Active> listAll() {
		Criteria criteria = this.session.createCriteria(Active.class);
		criteria.addOrder(Order.asc("code"));
		return criteria.list();
	}
	
	@Override
	public Active seach(String code) {
		return (Active) session.createCriteria(Active.class).add(Restrictions.eq("code", code)).uniqueResult();
	}
	
	@Override
	public Active search(Long id) {
		return null;
	}
}
