package br.ufu.facom.osrat.model;

public enum ConditionalOperator {

	EQUAL("="),
	NOT_EQUAL("<>"),
	LESS("<"),
	GREATER(">"),
	EQUAL_LESS("<="),
	EQUAL_GREATER(">="),
	LIKE("like");
	
	private String code;
	
	private ConditionalOperator(final String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}
	
	@Override
	public String toString() {
		return code;
	}
	
}
