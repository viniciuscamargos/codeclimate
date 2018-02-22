package br.ufu.facom.hpcs.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.ufu.facom.osrat.model.AbstractEntity;

@Entity
@Table(name = "aggregation_field")
public class AggregationField extends AbstractEntity {

	private String opLogic;
	private String field;
	private String opCond;
	private String vlrField;
	private Aggregation aggregation;

	public String getOpLogic() {
		return opLogic;
	}

	public void setOpLogic(final String opLogic) {
		this.opLogic = opLogic;
	}

	public String getField() {
		return field;
	}

	public void setField(final String field) {
		this.field = field;
	}

	public String getOpCond() {
		return opCond;
	}

	public void setOpCond(final String opCond) {
		this.opCond = opCond;
	}

	public String getVlrField() {
		return vlrField;
	}

	public void setVlrField(final String vlrField) {
		this.vlrField = vlrField;
	}
	
	@ManyToOne
	public Aggregation getAggregation() {
		return aggregation;
	}
	
	public void setAggregation(Aggregation aggregation) {
		this.aggregation = aggregation;
	}

}
