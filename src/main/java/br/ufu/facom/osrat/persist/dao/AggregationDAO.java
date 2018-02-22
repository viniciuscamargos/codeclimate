package br.ufu.facom.osrat.persist.dao;

import java.util.List;

import br.ufu.facom.hpcs.entity.Aggregation;
import br.ufu.facom.hpcs.entity.AggregationField;

public interface AggregationDAO {

	Aggregation save(Aggregation aggregation);

	void remove(Aggregation aggregation);

	List<Aggregation> getAll();

	Aggregation findById(Integer id);

	List<AggregationField> getFields(Aggregation aggregation);
}
