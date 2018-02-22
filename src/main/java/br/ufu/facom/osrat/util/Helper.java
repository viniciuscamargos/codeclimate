package br.ufu.facom.osrat.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public final class Helper {

	private Helper() { }

	public static String getIndexFilenamexls(final String filename) {
		return filename.substring(0, filename.length() - 4) + "-Idx.xls";
	}

	public static String getIndexFilenamecsv(final String filename) {
		return filename.substring(0, filename.length() - 4) + "-Idx.csv";
	}

	public static long stringToMilliseconds(final String str, final int offset) {
		try {
			SimpleDateFormat formatIn = new SimpleDateFormat(
					"yyyyMMddHHmmss.000000-000");
			return formatIn.parse(str).getTime() + offset;
		} catch (ParseException e) {
			return 0;
		}
	}

	public static String normalizeCsv(final String fileName) {
		return fileName + ".csv";
	}

	public static String normalizeXls(final String fileName) {
		return fileName + ".xls";
	}

}
