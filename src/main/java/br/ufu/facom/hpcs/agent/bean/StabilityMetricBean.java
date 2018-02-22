package br.ufu.facom.hpcs.agent.bean;


public class StabilityMetricBean {

	private long endMeasurementDate;
	private String relID;
	private long startMeasurementDate;
	private double systemStabilityIndex;
	private long timeGenerated;

	public StabilityMetricBean() {
		setEndMeasurementDate(-1);
		setRelID("");
		setStartMeasurementDate(-1);
		setSystemStabilityIndex(-1.0);
		setTimeGenerated(-1);
	}

	public final long getEndMeasurementDate() {
		return endMeasurementDate;
	}

	public final void setEndMeasurementDate(final long endMeasurementDate) {
		this.endMeasurementDate = endMeasurementDate;
	}

	public final String getRelID() {
		return relID;
	}

	public final void setRelID(final String relID) {
		this.relID = relID.toLowerCase();

		if (!this.relID.startsWith("{")) {
			this.relID = "{" + this.relID;
		}

		if (!this.relID.endsWith("}")) {
			this.relID = this.relID + "}";
		}
	}

	public final long getStartMeasurementDate() {
		return startMeasurementDate;
	}

	public final void setStartMeasurementDate(final long startMeasurementDate) {
		this.startMeasurementDate = startMeasurementDate;
	}

	public final double getSystemStabilityIndex() {
		return systemStabilityIndex;
	}

	public final void setSystemStabilityIndex(final double systemStabilityIndex) {
		this.systemStabilityIndex = systemStabilityIndex;
	}

	public final long getTimeGenerated() {
		return timeGenerated;
	}

	public final void setTimeGenerated(final long timeGenerated) {
		this.timeGenerated = timeGenerated;
	}
}
