package br.ufu.facom.osrat.exception;

public class PersistenceException extends RuntimeException {

	public PersistenceException(final String msg) {
		super(msg);
	}

	public PersistenceException(final Exception ex) {
		super(ex);
	}

	public PersistenceException(final String msg, final Exception ex) {
		super(msg, ex);
	}

}
