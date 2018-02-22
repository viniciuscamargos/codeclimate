package br.ufu.facom.osrat.model;

/**
 * Tipos de nó da árvore.
 *
 */
public enum TypeNode {

	GROUP("Group", "Group", "group.png"), GROUPS(
			"Groups", "Groups", "groups.png"), MACHINE(
			"Computer", "Computer", "comp.png");

	private String title;
	private String toolTip;
	private String path;

	private TypeNode(final String title, final String toolTip, final String path) {
		this.title = title;
		this.toolTip = toolTip;
		this.path = path;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public String getToolTip() {
		return toolTip;
	}

	public String getPath() {
		return path;
	}

}