package br.ufu.facom.osrat.reader;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import br.ufu.facom.hpcs.agent.bean.RecordBean;
import br.ufu.facom.hpcs.controller.PersistenceController;
import br.ufu.facom.osrat.model.Node;
import br.ufu.facom.osrat.model.TypeLog;
import br.ufu.facom.osrat.persist.dao.RecordDAO;
import br.ufu.facom.osrat.persist.dao.RecordDAOJPA;

/**
 * Classe responsável pela leitura dos sdfs.
 *
 */
public class ReadRecords extends PersistenceController{
	
	private RecordDAO recordDAO;

	public ReadRecords() { 
		loadPersistenceContext();
		this.recordDAO = new RecordDAOJPA(getPersistenceContext());
		
	}

	/** Lê os sdfs.
	 * @param records
	 * @param node
	 */
	public void read(final List<RecordBean> records, final Node node) {
		
		boolean initDate = false;
		Calendar cm = Calendar.getInstance();
		Calendar cM = Calendar.getInstance();

		for (RecordBean bean : records) {

			int ei = bean.getEventIdentifier();
			String sn = bean.getSourceName();
			String pn = bean.getProductName();

			String name = null;

			String s1 = (ei + sn).replaceAll("\\s", "");
			String s2 = (ei + sn + pn).replaceAll("\\s", "");

			Set<String> keys = ReadFailures.getMapLogs().keySet();
			for (String key : keys) {
				List<String> list = ReadFailures.getMapLogs().get(key);
				if (list.contains(s1) || list.contains(s2)) {
					name = key;
					break;
				}
			}

			if(name == null){
				bean.setSdf(node.getSdf());
				node.getUnknows().add(bean);
				continue;
			}
			
			bean.setTypeLog(TypeLog.valueOf(name));
			
			if(TypeLog.IGNORE.equals(bean.getTypeLog())){
				continue;
			}

			// ===== indica a menor e a maior data =====
			if (!initDate) {
				cm.setTimeInMillis(bean.getTimeGenerated());
				cM.setTimeInMillis(bean.getTimeGenerated());
				initDate = true;
			}

			// pega menor data e maior data
			Calendar dt = Calendar.getInstance();
			dt.setTimeInMillis(bean.getTimeGenerated());

			if (dt.after(cm)) {
				cM = dt;
			} else if (dt.before(cm)) {
				cm = dt;
			}
			
			bean.setSdf(node.getSdf());
			recordDAO.saveOrUpdate(bean);
		}

		// ===== salvando as datas =====
		SimpleDateFormat dtFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

		Date dtInit = new Date(cm.getTimeInMillis());
		node.setInitDate(dtFormat.format(dtInit));

		Date dtFinal = new Date(cM.getTimeInMillis());
		node.setFinalDate(dtFormat.format(dtFinal));

		// Calcula a quantidade de dias entre as datas
		long diff = cM.getTimeInMillis() - cm.getTimeInMillis();

		// Quantidade de milissegundos em um dia
		int timeDay = 1000 * 60 * 60 * 24;

		int diffDays = (int) (diff / timeDay);
		node.setNumberDays(diffDays == 0 ? 1 : diffDays);
	}
}
