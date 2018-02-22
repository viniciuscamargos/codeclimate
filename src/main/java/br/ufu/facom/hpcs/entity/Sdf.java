package br.ufu.facom.hpcs.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.ufu.facom.osrat.model.AbstractEntity;

@Entity
@Table(name = "sdf")
public class Sdf extends AbstractEntity {

	private String name;
	private Date date;
	private SdfGroup group;

	public Sdf() {
	}

	public Sdf(final String name, final Date date, final SdfGroup group) {
		this.name = name;
		this.date = date;
		this.group = group;
	}

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
	public SdfGroup getGroup() {
		return group;
	}
	
	public void setGroup(SdfGroup group) {
		this.group = group;
	}

}
