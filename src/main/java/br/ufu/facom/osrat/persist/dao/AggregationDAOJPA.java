package br.ufu.facom.osrat.persist.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.criterion.Restrictions;

import br.ufu.facom.hpcs.entity.Aggregation;
import br.ufu.facom.hpcs.entity.AggregationField;

public class AggregationDAOJPA extends AbstractDAO<Aggregation, Integer> implements AggregationDAO {

	public AggregationDAOJPA(EntityManager em) {
		super(em);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AggregationField> getFields(Aggregation aggregation ) {
		return getSession().createCriteria(AggregationField.class)
				.add(Restrictions.eq("aggregation", aggregation))
				.list();
	}

}
