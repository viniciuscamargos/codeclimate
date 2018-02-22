package br.ufu.facom.osrat.thread;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jxl.write.WritableWorkbook;

import org.apache.log4j.Logger;

import br.ufu.facom.hpcs.agent.bean.RecordBean;
import br.ufu.facom.hpcs.agent.bean.StabilityMetricBean;
import br.ufu.facom.hpcs.agent.sdf.SDFReader;
import br.ufu.facom.osrat.model.LogMessage;
import br.ufu.facom.osrat.model.TypeLogMessage;
import br.ufu.facom.osrat.ui.MainFrame;
import br.ufu.facom.osrat.xls.XLSWriter;

public class ThreadExportSdfToXls implements Runnable {

	private String fileName = "";
	private String idxFilename = "";

	private File fOrg;
	private File fDest;

	private WritableWorkbook workbookRecord;
	private WritableWorkbook workbookIndex;

	private List<LogMessage> listLog = new ArrayList<LogMessage>();

	private static final Logger LOG = Logger
			.getLogger(ThreadExportSdfToXls.class);

	public ThreadExportSdfToXls(final File fOrg, final File fDest) {
		this.fOrg = fOrg;
		this.fDest = fDest;
	}

	@Override
	public final void run() {
		initProcess();
		importSdfs();
		endProcess();
	}

	private void importSdfs() {
		try {

			File[] files = fOrg.listFiles();
			if (files.length > 0) {

				SimpleDateFormat dtFormat = new SimpleDateFormat(
						"ddMMyyyy_HHmmss");

				// cria as planilhas
				String pathDestino = fDest.getPath();
				fileName = pathDestino + "_" + dtFormat.format(new Date())
						+ ".xls";
				idxFilename = fileName.replaceAll(".xls", "-Idx.xls");
				workbookRecord = XLSWriter.openWorkbook(fileName, true);
				workbookIndex = XLSWriter.openWorkbook(idxFilename, true);

				for (File file : files) {
					if (!file.getName().endsWith(".sdf")) {
						continue;
					}
					createXls(file);
				}

				// fecha as planilhas
				workbookRecord.write();
				workbookRecord.close();
				workbookIndex.write();
				workbookIndex.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e);
		}

	}

	private void createXls(final File file) {

		try {
			String name = file.getName().substring(0,
					file.getName().length() - 4);

			List<RecordBean> records = SDFReader.getRecords(file
					.getAbsolutePath());
			XLSWriter.writeRecordsFile(workbookRecord, records, name);

			List<StabilityMetricBean> indexes = SDFReader
					.getStabilityMetrics(file.getAbsolutePath());
			XLSWriter.writeStabilityMetricsFile(workbookIndex, indexes, name
					+ "-Idx");
		} catch (Exception e) {
			listLog.add(new LogMessage(TypeLogMessage.ERROR, file.getName(), e
					.getMessage()));
			LOG.error("Erro ao gerar xls: " + e);
		}

	}

	private void initProcess() {
		MainFrame.getScrollTree().setVisible(false);
		MainFrame.getProgressBar().setVisible(true);
		MainFrame.getProgressBar().setIndeterminate(true);
	}

	private void endProcess() {
		MainFrame.getProgressBar().setIndeterminate(false);
		MainFrame.getProgressBar().setVisible(false);
		MainFrame.getScrollTree().setVisible(true);

		MainFrame.getScrollTree().getViewport().add(MainFrame.getTree());
	}

}
