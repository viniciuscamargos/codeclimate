package br.ufu.facom.hpcs.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import br.ufu.facom.osrat.model.AbstractEntity;

@Entity
@Table(name = "aggregation")
public class Aggregation extends AbstractEntity {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

}
