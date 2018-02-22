package br.ufu.facom.osrat.model;

import java.util.Comparator;

public class NodeComp implements Comparator<Node> {

	@Override
	public final int compare(final Node o1, final Node o2) {
		return o1.getName().compareTo(o2.getName());
	}

}
