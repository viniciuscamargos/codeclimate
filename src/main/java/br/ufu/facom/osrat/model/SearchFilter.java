package br.ufu.facom.osrat.model;

import java.util.Date;

import br.ufu.facom.hpcs.entity.Sdf;
import br.ufu.facom.hpcs.entity.SdfGroup;

public class SearchFilter {

	private SdfGroup group;
	private Sdf sdf;
	private TypeLog typeLog;
	private Date periodStart;
	private Date periodEnd;

	public SdfGroup getGroup() {
		return group;
	}

	public void setGroup(SdfGroup group) {
		this.group = group;
	}

	public Sdf getSdf() {
		return sdf;
	}

	public void setSdf(Sdf sdf) {
		this.sdf = sdf;
	}

	public TypeLog getTypeLog() {
		return typeLog;
	}

	public void setTypeLog(TypeLog typeLog) {
		this.typeLog = typeLog;
	}

	public Date getPeriodStart() {
		return periodStart;
	}

	public void setPeriodStart(Date periodStart) {
		this.periodStart = periodStart;
	}

	public Date getPeriodEnd() {
		return periodEnd;
	}

	public void setPeriodEnd(Date periodEnd) {
		this.periodEnd = periodEnd;
	}

}
