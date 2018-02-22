package br.ufu.facom.osrat.util;

import java.util.Comparator;
import java.util.Map;

/**
 * Classe que compara os valores para ranking.
 *
 */
class ValueComparator implements Comparator<String> {

    private Map<String, Integer> base;

    /**
     * Construtor.
     * @param base
     */
    public ValueComparator(final Map<String, Integer> base) {
        this.base = base;
    }

	public int compare(final String a, final String b) {
        if (base.get(a) >= base.get(b)) {
            return -1;
        } else {
            return 1;
        }
    }
}
