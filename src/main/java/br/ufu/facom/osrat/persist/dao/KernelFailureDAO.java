package br.ufu.facom.osrat.persist.dao;

import java.util.List;

import br.ufu.facom.hpcs.entity.KernelFailure;


public interface KernelFailureDAO {

	KernelFailure save(KernelFailure kernelFailure);
	
	void remove(KernelFailure kernelFailure);
	
	List<KernelFailure> getAll();
	
	KernelFailure getKernelFailureByCode(String code);
	
	KernelFailure findById(Integer id);

}
