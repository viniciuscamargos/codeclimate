package br.ufu.facom.osrat.reader;

import java.util.Date;
import java.util.List;

import br.ufu.facom.hpcs.agent.bean.RecordBean;

public final class ReadRanking {

	private ReadRanking() { }

	public static long getTotal(final List<RecordBean> records, final Date dtInit,
			final Date dtEnd) {
		if (dtInit == null && dtEnd == null) {
			return records.size();
		} else {
			long total = 0;

			for (RecordBean bean : records) {
				Date dtGenerated = new Date(bean.getTimeGenerated());

				if (dtInit != null && dtGenerated.compareTo(dtInit) < 0) {
					continue;
				}
				if (dtEnd != null && dtGenerated.compareTo(dtEnd) > 0) {
					continue;
				}

				total++;
			}

			return total;
		}
	}
}
