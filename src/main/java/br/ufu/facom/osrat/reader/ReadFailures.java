package br.ufu.facom.osrat.reader;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import br.ufu.facom.hpcs.agent.bean.Failure;

public final class ReadFailures {

	private static Map<String, List<String>> mapLogs = new HashMap<String, List<String>>();
	private static Map<String, List<Failure>> mapFailures = new HashMap<String, List<Failure>>();

	private ReadFailures() { }

	public static void read() {
		try {
			File inputWorkbook = new File("Failures.xls");
			Workbook w = Workbook.getWorkbook(inputWorkbook);
			Sheet[] sheets = w.getSheets();
			for (Sheet sheet : sheets) {
				String name = sheet.getName();

				List<String> list = new ArrayList<>();
				List<Failure> failures = new ArrayList<>();

				for (int i = 1; i < sheet.getRows(); i++) {
					Cell[] cells = sheet.getRow(i);

					if (cells[0].getContents().trim().isEmpty()) {
						continue;
					}

					String vlr = cells[0].getContents();
					vlr += cells[1].getContents();
					if (cells.length == 3) {
						vlr += cells[2].getContents();
					}
					list.add(vlr.replaceAll("\\s", ""));

					Failure failure = new Failure();
					failure.setEventIdentifier(Integer.parseInt(cells[0]
							.getContents().trim()));
					failure.setSourceName(cells[1].getContents());
					if (cells.length == 3) {
						failure.setProductName(cells[2].getContents());
					}
					failures.add(failure);
				}
				mapLogs.put(name, list);
				mapFailures.put(name, failures);
			}
			w.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Map<String, List<String>> getMapLogs() {
		if (mapLogs == null) {
			mapLogs = new HashMap<String, List<String>>();
		}
		return mapLogs;
	}

	public static Map<String, List<Failure>> getMapFailures() {
		if (mapFailures == null) {
			mapFailures = new HashMap<String, List<Failure>>();
		}
		return mapFailures;
	}

}
