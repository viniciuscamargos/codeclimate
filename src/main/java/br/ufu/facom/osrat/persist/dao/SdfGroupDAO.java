package br.ufu.facom.osrat.persist.dao;

import java.util.List;

import br.ufu.facom.hpcs.entity.SdfGroup;


public interface SdfGroupDAO {

	SdfGroup save(SdfGroup sdfGroup);
	
	void remove(SdfGroup sdfGroup);
	
	List<SdfGroup> getAll();
	
	SdfGroup findById(Integer id);
	
	SdfGroup saveOrUpdate(SdfGroup sdfGroup);

}
