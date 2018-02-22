package br.ufu.facom.osrat.model;

/**
 * @author vinicius
 *
 */
public class SourceProductName {

	/**
	 * Source name.
	 */
	private String source;

	/**
	 * Product name.
	 */
	private String product;

	/**
	 * Total de registros encontrados.
	 */
	private Long total;

	/**
	 * Tipo do log.
	 */
	private TypeLog typeLog;

	/**
	 * @param source
	 * @param product
	 */
	public SourceProductName(final String source, final String product,
			final TypeLog typeLog) {
		this.source = source;
		this.product = product;
		this.typeLog = typeLog;
		this.total = 1L;
	}

	/**
	 * Retorna o source name.
	 * @return String
	 */
	public final String getSource() {
		return source;
	}

	/**
	 * @param source
	 */
	public final void setSource(final String source) {
		this.source = source;
	}

	public final String getProduct() {
		return product;
	}

	/**
	 * @param product
	 */
	public final void setProduct(final String product) {
		this.product = product;
	}

	/**
	 * REtorna o total de registros encontrados.
	 * @return Long
	 */
	public final Long getTotal() {
		if (total == null) {
			total = 0L;
		}
		return total;
	}

	/**
	 * @param total
	 */
	public final void setTotal(final Long total) {
		this.total = total;
	}

	/**
	 * adiciona mai 1.
	 */
	public final void add() {
		total += 1;
	}

	/**
	 * Retorna o tipo do log.
	 * @return TypeLog
	 */
	public final TypeLog getTypeLog() {
		return typeLog;
	}

	/**
	 * Seta o tipo do log.
	 * @param typeLog
	 */
	public final void setTypeLog(final TypeLog typeLog) {
		this.typeLog = typeLog;
	}

}
