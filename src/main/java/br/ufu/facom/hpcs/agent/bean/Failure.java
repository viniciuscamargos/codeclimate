package br.ufu.facom.hpcs.agent.bean;

public class Failure {

	private Integer eventIdentifier;
	private String productName;
	private String sourceName;

	/**
	 * Retorna o identificador do evento.
	 * @return Integer
	 */
	public final Integer getEventIdentifier() {
		return eventIdentifier;
	}

	/**
	 * Seta o identificador do evento.
	 * @param eventIdentifier
	 */
	public final void setEventIdentifier(final Integer eventIdentifier) {
		this.eventIdentifier = eventIdentifier;
	}

	/**
	 * Retorna o produdct name.
	 * @return String
	 */
	public final String getProductName() {
		return productName;
	}

	/**
	 * Seta o product name.
	 * @param productName
	 */
	public final void setProductName(final String productName) {
		this.productName = productName;
	}

	/**
	 * Retorna o source name.
	 * @return String
	 */
	public final String getSourceName() {
		return sourceName;
	}

	/**
	 * Seta o source name.
	 * @param sourceName
	 */
	public final void setSourceName(final String sourceName) {
		this.sourceName = sourceName;
	}

}
