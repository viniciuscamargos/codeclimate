package br.ufu.facom.osrat.xls;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Colour;
import jxl.read.biff.BiffException;
import jxl.write.DateFormat;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import br.ufu.facom.hpcs.agent.bean.RecordBean;
import br.ufu.facom.hpcs.agent.bean.StabilityMetricBean;
import br.ufu.facom.osrat.model.TypeLog;
import br.ufu.facom.osrat.reader.ReadFailures;

/**
 * Leitura e criação de planilhas para exportação dos sdfs.
 *
 */
public final class XLSWriter {

	private static WritableCellFormat integerFormat;
	private static WritableCellFormat floatFormat;
	private static DateFormat customDateFormat;
	private static WritableCellFormat dateFormat;

	private static Map<String, List<String>> mapLogs = new HashMap<String, List<String>>();

	private XLSWriter() { }

	/**
	 * Leitura dos registros de eventos.
	 */
	public static void readLogs() {
		try {
			File inputWorkbook = new File("Failures.xls");
			Workbook w = Workbook.getWorkbook(inputWorkbook);
			Sheet[] sheets = w.getSheets();
			for (Sheet sheet : sheets) {
				String name = sheet.getName();
				List<String> list = new ArrayList<>();
				for (int i = 1; i < sheet.getRows(); i++) {
					Cell[] cells = sheet.getRow(i);
					String vlr = "";
					for (Cell cell : cells) {
						vlr += cell.getContents();
					}
					list.add(vlr.replaceAll("\\s", ""));
				}
				mapLogs.put(name, list);
			}
			w.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Escreve na planilha.
	 * @param workbook
	 * @param records
	 * @param sheetName
	 * @throws IOException
	 * @throws BiffException
	 * @throws WriteException
	 */
	public static void writeRecordsFile(final WritableWorkbook workbook,
			final List<RecordBean> records, final String sheetName) throws IOException,
			BiffException, WriteException {

		initFormats();

		List<RecordBean> unknows = new ArrayList<RecordBean>();

		WritableSheet sheet = createSheet(workbook, sheetName);
		Set<String> names = new HashSet<String>();

		WritableSheet namesSheet = workbook.getSheet("Names");
		if (namesSheet == null) {
			namesSheet = workbook.createSheet("Names", 0);
			namesSheet.addCell(createLabelCell("Filename", 0, 0));
			namesSheet.addCell(createLabelCell("Computer Name", 1, 0));
		}

		int col = 0;
		sheet.addCell(createLabelCell("ComputerName", col, 0));
		sheet.addCell(createLabelCell("EventIdentifier", ++col, 0));
		sheet.addCell(createLabelCell("InsertionStrings", ++col, 0));
		sheet.addCell(createLabelCell("Logfile", ++col, 0));
		sheet.addCell(createLabelCell("Message", ++col, 0));
		sheet.addCell(createLabelCell("ProductName", ++col, 0));
		sheet.addCell(createLabelCell("RecordNumber", ++col, 0));
		sheet.addCell(createLabelCell("SourceName", ++col, 0));
		sheet.addCell(createLabelCell("TimeGenerated", ++col, 0));
		sheet.addCell(createLabelCell("User", ++col, 0));
		// sheet.addCell(createLabelCell("EventType", 10, 0));

		int i = 1;
		for (RecordBean bean : records) {

			int ei = bean.getEventIdentifier();
			String sn = bean.getSourceName();
			String pn = bean.getProductName();

			Colour foundColour = encounter(ei, sn, pn);

			if (foundColour == null) {
				continue;
			}

			sheet.addCell(createLabelCell(bean.getComputerName(), 0, i));
			sheet.addCell(createIntCell(bean.getEventIdentifier(), 1, i));
			sheet.addCell(createLabelCell(join(bean.getInsertionStrings()), 2,
					i));
			sheet.addCell(createLabelCell(bean.getLogfile(), 3, i));
			sheet.addCell(createLabelCell(bean.getMessage(), 4, i));
			sheet.addCell(createLabelCell(bean.getProductName(), 5, i));
			sheet.addCell(createIntCell(bean.getRecordNumber(), 6, i));

			Label snLabel = createLabelCell(bean.getSourceName(), 7, i);
			WritableCellFormat snFormat = null;

			snFormat = new WritableCellFormat(snLabel.getCellFormat());
			snFormat.setBackground(foundColour);

			if (Colour.CORAL.equals(foundColour)) {
				unknows.add(bean);
			}

			if (snFormat != null) {
				snLabel.setCellFormat(snFormat);
			}

			sheet.addCell(snLabel);
			sheet.addCell(createDateCell(bean.getTimeGenerated(), 8, i));
			sheet.addCell(createLabelCell(bean.getUser(), 9, i));

			i++;

			names.add(bean.getComputerName());

		}

		i = namesSheet.getRows();
		for (String n : names) {
			namesSheet.addCell(createLabelCell(sheet.getName(), 0, i));
			namesSheet.addCell(createLabelCell(n, 1, i));
			i++;
		}

	}

	public static Colour encounter(final int ei, final String sn, final String pn) {
		try {

			String name = null;

			String s1 = (ei + sn).replaceAll("\\s", "");
			String s2 = (ei + sn + pn).replaceAll("\\s", "");

			mapLogs = ReadFailures.getMapLogs();

			Set<String> keys = mapLogs.keySet();
			for (String key : keys) {
				List<String> list = mapLogs.get(key);
				if (list.contains(s1) || list.contains(s2)) {
					name = key;
					break;
				}
			}

			if (TypeLog.IGNORE.name().equals(name)) {
				return null;
			} else if (TypeLog.KERNEL.name().equals(name)) {
				return Colour.RED;
			} else if (TypeLog.SERVICE.name().equals(name)) {
				return Colour.YELLOW;
			} else if (TypeLog.OS_APPLICATION.name().equals(name)) {
				return Colour.GOLD;
			} else if (TypeLog.USER_APPLICATION.name().equals(name)) {
				return Colour.SKY_BLUE;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Colour.CORAL;
	}

	/**
	 * Escreve a planilha de métricas.
	 * @param workbook
	 * @param indexes
	 * @param sheetName
	 * @throws IOException
	 * @throws BiffException
	 * @throws WriteException
	 */
	public static void writeStabilityMetricsFile(final WritableWorkbook workbook,
			final List<StabilityMetricBean> indexes, final String sheetName)
			throws IOException, BiffException, WriteException {
		initFormats();

		WritableSheet sheet = createSheet(workbook, sheetName);

		int col = 0;
		sheet.addCell(createLabelCell("EndMeasurementDate", col, 0));
		sheet.addCell(createLabelCell("RelID", ++col, 0));
		sheet.addCell(createLabelCell("StartMeasurementDate", ++col, 0));
		sheet.addCell(createLabelCell("SystemStabilityIndex", ++col, 0));
		sheet.addCell(createLabelCell("TimeGenerated", ++col, 0));

		int row = 1;
		for (StabilityMetricBean bean : indexes) {
			col = 0;
			sheet.addCell(createDateCell(bean.getEndMeasurementDate(), col, row));
			sheet.addCell(createLabelCell(bean.getRelID(), ++col, row));
			sheet.addCell(createDateCell(bean.getStartMeasurementDate(), ++col, row));
			sheet.addCell(createFloatCell(bean.getSystemStabilityIndex(), ++col, row));
			sheet.addCell(createDateCell(bean.getTimeGenerated(), ++col, row));

			row++;
		}

	}

	/**
	 * Cria ou abre uma planilha.
	 * @param filename
	 * @param append
	 * @return WritableWorkbook
	 * @throws IOException
	 * @throws BiffException
	 */
	public static WritableWorkbook openWorkbook(final String filename, final boolean append)
			throws IOException, BiffException {
		File file = new File(filename);
		File newFile = null;

		WritableWorkbook workbook = null;
		WorkbookSettings wbSetting = new WorkbookSettings();
		wbSetting.setUseTemporaryFileDuringWrite(true);

		if (!file.exists() || !append) {
			workbook = Workbook.createWorkbook(file, wbSetting);
		} else {
			Workbook original = Workbook.getWorkbook(file);
			newFile = File.createTempFile("rac-agent-", "");

			workbook = Workbook.createWorkbook(newFile, original, wbSetting);
		}

		return workbook;
	}

	/**
	 * Cria uma planilha.
	 * @param workbook
	 * @param sheetName
	 * @return WritableSheet
	 */
	private static WritableSheet createSheet(final WritableWorkbook workbook,
			final String sheetName) {
		return workbook.createSheet(validateSheetName(sheetName, workbook),
				workbook.getNumberOfSheets());
	}

	/**
	 * Valida o nome da planilha.
	 * @param name
	 * @param workbook
	 * @return String
	 */
	private static String validateSheetName(final String name,
			final WritableWorkbook workbook) {
		String newName = name;
		String[] sheets = workbook.getSheetNames();
		Random rand = new Random();
		while (Arrays.binarySearch(sheets, newName) >= 0) {
			newName = name + rand.nextInt();
		}

		return newName;
	}

	/**
	 * Cria uma célula para o formato texto.
	 * @param value
	 * @param c
	 * @param r
	 * @return Label
	 */
	private static Label createLabelCell(final String value, final int c, final int r) {
		return new Label(c, r, value);
	}

	/**
	 * Cria uma célula para o formato inteiro.
	 * @param value
	 * @param c
	 * @param r
	 * @return Number
	 */
	private static Number createIntCell(final long value, final int c, final int r) {
		return new Number(c, r, value, integerFormat);
	}

	/**
	 * Cria uma planilha para o formato decimal.
	 * @param value
	 * @param c
	 * @param r
	 * @return Number
	 */
	private static Number createFloatCell(final double value, final int c, final int r) {
		return new Number(c, r, value, floatFormat);
	}

	/**
	 * Cria uma célula para o formato data.
	 * @param value
	 * @param c
	 * @param r
	 * @return DateTime
	 */
	private static DateTime createDateCell(final long value, final int c, final int r) {
		Date date = new Date(value);
		return new DateTime(c, r, date, dateFormat);
	}

	private static String join(final List<String> values) {
		if (values.size() == 0) {
			return "";
		}

		StringBuilder sb = new StringBuilder(values.get(0));
		for (int i = 1; i < values.size(); i++) {
			sb.append(", ").append(
					values.get(i).replace("\r\n", "; ").replace("\r", "; ")
							.replace("\n", "; ").replace('\t', ' '));
		}

		return sb.toString();
	}

	// tem um bug na API que faz com que os formatos se percam entre uma
	// planilha e otura
	// por isso, antes de escrever uma planilha nova, os formatos sao
	// reiniciados
	/**
	 * Reinicia a formatação.
	 */
	private static void initFormats() {
		integerFormat = new WritableCellFormat(NumberFormats.INTEGER);
		floatFormat = new WritableCellFormat(NumberFormats.FLOAT);
		customDateFormat = new DateFormat("dd/MM/yyyy hh:mm:ss");
		dateFormat = new WritableCellFormat(customDateFormat);
	}

}
