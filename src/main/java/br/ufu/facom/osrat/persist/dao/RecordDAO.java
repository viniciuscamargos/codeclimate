package br.ufu.facom.osrat.persist.dao;

import java.util.List;

import br.ufu.facom.hpcs.agent.bean.RecordBean;
import br.ufu.facom.osrat.model.SearchFilter;

public interface RecordDAO {

	RecordBean save(RecordBean record);
	
	void remove(RecordBean record);
	
	List<RecordBean> getAll();
	
	RecordBean findById(Integer id);

	List<RecordBean> findRecordBeans(SearchFilter filter);
	
	RecordBean saveOrUpdate(RecordBean record);

	List<Double> getInfoCT(Integer idGroup);

	List<Double> getInfoNF(Integer idGroup);

	List<Long> getTbfs(SearchFilter filter);

}
