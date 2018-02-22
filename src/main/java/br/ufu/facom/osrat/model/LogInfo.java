package br.ufu.facom.osrat.model;

import java.util.Date;

public class LogInfo {

	private Integer kernel = 0;
	private Integer app = 0;
	private Integer service = 0;
	private Integer eventLog = 0;
	private Integer totalFails = 0;
	private Date dtInit;
	private Date dtFinal;
	private Integer nroDays = 0;

	public final Integer getKernel() {
		if (kernel == null) {
			return 0;
		}
		return kernel;
	}

	public final void addKernel() {
		this.kernel += 1;
	}

	public final Integer getApp() {
		if (kernel == null) {
			return 0;
		}
		return app;
	}

	public final void addApp() {
		this.app += 1;
	}

	public final Integer getService() {
		if (service == null) {
			return 0;
		}
		return service;
	}

	public final void addService() {
		this.service += 1;
	}

	public final Integer getEventLog() {
		if (eventLog == null) {
			return 0;
		}
		return eventLog;
	}

	public final void addEventLog() {
		this.eventLog += 1;
	}

	public final Integer getTotalFails() {
		if (totalFails == null) {
			return 0;
		}
		return totalFails;
	}

	public final void addTotalFails() {
		this.totalFails += 1;
	}

	public final Date getDtInit() {
		return dtInit;
	}

	public final void setDtInit(final Date dtInit) {
		this.dtInit = dtInit;
	}

	public final Date getDtFinal() {
		return dtFinal;
	}

	public final void setDtFinal(final Date dtFinal) {
		this.dtFinal = dtFinal;
	}

	public final Integer getNroDays() {
		return nroDays;
	}

	public final void setNroDays(final Integer nroDays) {
		this.nroDays = nroDays;
	}

}
