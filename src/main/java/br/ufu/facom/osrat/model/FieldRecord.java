package br.ufu.facom.osrat.model;

public enum FieldRecord {

	COMPUTER_NAME("Computer Name", "computer_name"),
	EVENT_IDENT("Event Identifier", "event_ident"),
	INSERTION_STRING("Insertion String", "insertion_string"),
	LOG_FILE("Log file", "log_file"),
	MESSAGE("Message", "message"),
	PRODUCT_NAME("Product Name", "product_name"),
	SOURCE_NAME("Source Name", "source_name");
	
	private String text;
	private String field;
	
	private FieldRecord(final String text, final String field){
		this.text = text;
		this.field = field;
	}
	
	public String getText() {
		return text;
	}
	
	public String getField() {
		return field;
	}
	
}
