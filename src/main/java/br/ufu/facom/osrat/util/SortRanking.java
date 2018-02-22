package br.ufu.facom.osrat.util;

import java.util.HashMap;
import java.util.TreeMap;

/**
 * Classe responsável por ordenar o ranking.
 *
 */
public final class SortRanking {

	private SortRanking() { }

	/**
	 * Ordena o ranking.
	 * @param map
	 * @return TreeMap
	 */
	public static TreeMap<String, Integer> sort(final HashMap<String, Integer> map) {
		ValueComparator bvc = new ValueComparator(map);
		TreeMap<String, Integer> sortedMap = new TreeMap<String, Integer>(bvc);
		sortedMap.putAll(map);
		return sortedMap;
	}

}
