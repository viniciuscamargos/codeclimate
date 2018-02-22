package br.ufu.facom.hpcs.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.ufu.facom.osrat.model.AbstractEntity;

@Entity
@Table(name = "sdf_group")
public class SdfGroup extends AbstractEntity {

	private String name;
	private Date date;
	private SdfGroup parent;
	
	@Column
	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Column
	public Date getDate() {
		return date;
	}

	public void setDate(final Date date) {
		this.date = date;
	}

	@ManyToOne
	public SdfGroup getParent() {
		return parent;
	}
	
	public void setParent(SdfGroup parent) {
		this.parent = parent;
	}

}
