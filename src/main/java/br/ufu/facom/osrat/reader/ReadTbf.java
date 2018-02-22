package br.ufu.facom.osrat.reader;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.ufu.facom.hpcs.agent.bean.RecordBean;

/**
 * Classe responável pela leitura dos tbfs.
 *
 */
public final class ReadTbf {

	private ReadTbf() { }

	/**
	 * Lê os tbfs.
	 * @param records
	 * @param dtInit
	 * @param dtEnd
	 * @return List
	 */
	public static List<Long> read(final List<RecordBean> records, final Date dtInit,
			final Date dtEnd) {
		List<Long> tbfs = new ArrayList<Long>();
		boolean init = false;
		Long before = 0L;

		if (records.size() > 0) {
			before = records.get(0).getTimeGenerated();
		}

		for (RecordBean bean : records) {
			Date dtGenerated = new Date(bean.getTimeGenerated());

			if (dtInit != null && dtGenerated.compareTo(dtInit) < 0) {
				continue;
			}
			if (dtEnd != null && dtGenerated.compareTo(dtEnd) > 0) {
				continue;
			}

			if (!init) {
				init = true;
				continue;
			}

			Long tbf = before - bean.getTimeGenerated();
			before = bean.getTimeGenerated();

			tbfs.add(tbf);

		}

		return tbfs;

	}
}
