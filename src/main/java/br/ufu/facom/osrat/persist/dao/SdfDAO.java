package br.ufu.facom.osrat.persist.dao;

import java.util.List;

import br.ufu.facom.hpcs.entity.Sdf;

public interface SdfDAO {

	Sdf save(Sdf sdf);

	void remove(Sdf sdf);

	List<Sdf> getAll();

	Sdf findById(Integer id);
	
	Sdf saveOrUpdate(Sdf sdf);

}
