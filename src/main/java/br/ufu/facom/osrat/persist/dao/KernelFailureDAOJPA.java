package br.ufu.facom.osrat.persist.dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.ufu.facom.hpcs.entity.KernelFailure;

public class KernelFailureDAOJPA extends AbstractDAO<KernelFailure, Integer> implements KernelFailureDAO {

	public KernelFailureDAOJPA(EntityManager em) {
		super(em);
	}

	@Override
	public KernelFailure getKernelFailureByCode(String code) {
		if (code == null || code.isEmpty())
			return null;
		Query query = getPersistenceContext().createQuery("SELECT o FROM KernelFailure o WHERE o.code = :code");
		query.setParameter("code", code);
		return (KernelFailure) query.getSingleResult();
	}

}
