package br.ufu.facom.osrat.model;

public class LogMessage {

	private TypeLogMessage typeLogMessage;
	private String fileName;
	private String message;

	private Integer eventIdentifier;
	private String productName;
	private String sourceName;

	public LogMessage(final TypeLogMessage typeLogMessage, final String fileName, final String message) {
		this.typeLogMessage = typeLogMessage;
		this.fileName = fileName;
		this.message = message;
	}

	public LogMessage(final TypeLogMessage typeLogMessage,
			final String fileName, final Integer eventIdentifier,
			final String sourceName, final String productName,
			final String message) {
		this.typeLogMessage = typeLogMessage;
		this.fileName = fileName;
		this.eventIdentifier = eventIdentifier;
		this.sourceName = sourceName;
		this.productName = productName;
		this.message = message;
	}

	public final TypeLogMessage getTypeLogMessage() {
		return typeLogMessage;
	}

	public final void setTypeLogMessage(final TypeLogMessage typeLogMessage) {
		this.typeLogMessage = typeLogMessage;
	}

	public final String getFileName() {
		return fileName;
	}

	public final void setFileName(final String fileName) {
		this.fileName = fileName;
	}

	public final String getMessage() {
		return message;
	}

	public final void setMessage(final String message) {
		this.message = message;
	}

	public final Integer getEventIdentifier() {
		return eventIdentifier;
	}

	public final void setEventIdentifier(final Integer eventIdentifier) {
		this.eventIdentifier = eventIdentifier;
	}

	public final String getProductName() {
		return productName;
	}

	public final void setProductName(final String productName) {
		this.productName = productName;
	}

	public final String getSourceName() {
		return sourceName;
	}

	public final void setSourceName(final String sourceName) {
		this.sourceName = sourceName;
	}

}
