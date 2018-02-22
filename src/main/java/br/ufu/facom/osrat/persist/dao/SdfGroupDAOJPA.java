package br.ufu.facom.osrat.persist.dao;

import javax.persistence.EntityManager;

import br.ufu.facom.hpcs.entity.SdfGroup;

public class SdfGroupDAOJPA extends AbstractDAO<SdfGroup, Integer> implements SdfGroupDAO {

	public SdfGroupDAOJPA(EntityManager em) {
		super(em);
	}
}
