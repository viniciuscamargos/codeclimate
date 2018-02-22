package br.ufu.facom.osrat.persist.dao;

import javax.persistence.EntityManager;

import br.ufu.facom.hpcs.entity.Sdf;

public class SdfDAOJPA extends AbstractDAO<Sdf, Integer> implements SdfDAO {

	public SdfDAOJPA(EntityManager em) {
		super(em);
	}

	
}
