package br.ufu.facom.osrat.reader;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import br.ufu.facom.hpcs.agent.bean.RecordBean;
import br.ufu.facom.osrat.model.SourceProductName;
import br.ufu.facom.osrat.model.TypeLog;

public final class ReadSourceProductName {

	private ReadSourceProductName() { }

	/**
	 * Faz a leitura dos registros.
	 * @param records
	 * @param map
	 * @param dtInit
	 * @param dtEnd
	 * @param typeLog
	 */
	public static void read(final List<RecordBean> records,
			final ConcurrentHashMap<String, SourceProductName> map,
			final Date dtInit, final Date dtEnd, final TypeLog typeLog) {
		for (final RecordBean bean : records) {

			final Date dtGenerated = new Date(bean.getTimeGenerated());

			if (dtInit != null && dtGenerated.compareTo(dtInit) < 0) {
				continue;
			}

			if (dtEnd != null && dtGenerated.compareTo(dtEnd) > 0) {
				continue;
			}

			final String source = bean.getSourceName();
			final String product = bean.getProductName();

			final String key = (source + product).replaceAll("\\s", "");

			if (map.containsKey(key)) {
				final SourceProductName srcProd = map.get(key);
				srcProd.add();
			} else {
				map.put(key, new SourceProductName(source, product, typeLog));
			}
		}
	}
}
