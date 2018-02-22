package br.ufu.facom.osrat.model;

public class Ranking {

	private String name;
	private Long total;

	public Ranking(final String name, final Long total) {
		this.name = name;
		this.total = total;
	}

	public final String getName() {
		return name;
	}

	public final void setName(final String name) {
		this.name = name;
	}

	public final Long getTotal() {
		return total;
	}

	public final void setTotal(final Long total) {
		this.total = total;
	}

}
