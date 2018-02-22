package br.ufu.facom.osrat.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class GetSpecificAnalysis {

	private GetSpecificAnalysis() { }

	private static ArrayList<Integer> eventIDEventLog = new ArrayList<Integer>();
	private static ArrayList<String> sourcenameEventLog = new ArrayList<String>();
	private static ArrayList<String> productnameEventLog = new ArrayList<String>();
	private static ArrayList<String> messagesEventLog = new ArrayList<String>();
	private static ArrayList<String> logfilesEventLog = new ArrayList<String>();
	private static ArrayList<String> pcnamesEventLog = new ArrayList<String>();
	private static ArrayList<Long> recordnumbersEventLog = new ArrayList<Long>();
	private static ArrayList<Integer> linesEventLog = new ArrayList<Integer>();
	private static ArrayList<Date> timesEventLog = new ArrayList<Date>();
	private static ArrayList<List<String>> insertionstringsEventLog = new ArrayList<List<String>>();

	private static ArrayList<Integer> eventID = new ArrayList<Integer>();
	private static ArrayList<String> sourcename = new ArrayList<String>();
	private static ArrayList<String> productname = new ArrayList<String>();
	private static ArrayList<String> messages = new ArrayList<String>();
	private static ArrayList<String> logfiles = new ArrayList<String>();
	private static ArrayList<String> pcnames = new ArrayList<String>();
	private static ArrayList<Long> recordnumbers = new ArrayList<Long>();
	private static ArrayList<Integer> lines = new ArrayList<Integer>();
	private static ArrayList<Date> times = new ArrayList<Date>();
	private static ArrayList<List<String>> insertionstrings = new ArrayList<List<String>>();

	public static void insertValuesLogFiles(final int ei, final String sn, final String pn,
			final String message, final String logfile, final String pcname,
			final List<String> insertionstring, final long recordnumber, final int i, final Date time)
			throws IOException {

		eventIDEventLog.add(ei);
		sourcenameEventLog.add(sn);
		productnameEventLog.add(pn);
		logfilesEventLog.add(logfile);
		messagesEventLog.add(message);
		pcnamesEventLog.add(pcname);
		recordnumbersEventLog.add(recordnumber);
		linesEventLog.add(i);
		timesEventLog.add(time);
		insertionstringsEventLog.add(insertionstring);
	}

	public static void setArchive(final String name) throws IOException {
		File arq = new File("/workspace/ReliabilityProgram/Txts/UniubeAdm/"
				+ name + ".txt");
		FileWriter fw;
		BufferedWriter bw;
		if (!arq.exists()) {
			arq.createNewFile();
			fw = new FileWriter(arq);
			bw = new BufferedWriter(fw);
		} else {
			arq = new File("/workspace/ReliabilityProgram/Txts/" + name
					+ ".txt");
			fw = new FileWriter(arq);
			bw = new BufferedWriter(fw);
		}

		int i, j;

		for (i = 0; i < eventIDEventLog.size(); i++) {
			int numberline = linesEventLog.get(i);

			for (j = 0; j < eventID.size(); j++) {
				if (timesEventLog.get(i) == times.get(j)) {
					break;
				}
			}
			if (j == eventID.size() - 1) {
				bw.append("The Event log is the last error");
			} else {
				bw.append("                                  Event before the Event Log ");
				bw.newLine();
				bw.newLine();
				bw.append("Event Identifier: " + eventID.get(j + 1));
				bw.newLine();
				bw.append("Source Name : " + sourcename.get(j + 1));
				bw.newLine();
				bw.append("Product Name : " + productname.get(j + 1));
				bw.newLine();
				bw.append("Log file : " + logfiles.get(j + 1));
				bw.newLine();
				bw.newLine();
				bw.append("Messages : ");
				bw.newLine();
				bw.append(messages.get(j + 1));
				bw.newLine();
				bw.newLine();
				bw.append("Pc name : " + pcnames.get(j + 1));
				bw.newLine();
				bw.append("Record Number : " + recordnumbers.get(j + 1));
				bw.newLine();
				bw.append("Line in the sheet : " + lines.get(j + 1));
				bw.newLine();
				bw.newLine();

				SimpleDateFormat dateformat = new SimpleDateFormat(
						"dd/MM/yyyy hh:mm:ss a");
				String t = dateformat.format(times.get(j + 1));

				bw.append("Time Generated : " + t);

				bw.newLine();
				bw.newLine();
				bw.append("Insertions Strings : ");
				List<String> aux = insertionstrings.get(j + 1);
				for (String s : aux) {
					bw.append(s);
					bw.append(",");
				}
				bw.newLine();
				bw.newLine();
				bw.newLine();
				bw.append(" ################################################################## ");
				bw.newLine();
				bw.newLine();
				bw.newLine();
			}

			bw.append("                                     Event Log ");
			bw.newLine();
			bw.newLine();
			bw.append("Event Identifier: " + eventIDEventLog.get(i));
			bw.newLine();
			bw.append("Source Name : " + sourcenameEventLog.get(i));
			bw.newLine();
			bw.append("Product Name : " + productnameEventLog.get(i));
			bw.newLine();
			bw.append("Log file : " + logfilesEventLog.get(i));
			bw.newLine();
			bw.newLine();
			bw.append("Messages : " + messagesEventLog.get(i));
			bw.newLine();
			bw.newLine();
			bw.append("Pc name : " + pcnamesEventLog.get(i));
			bw.newLine();
			bw.append("Record Number : " + recordnumbersEventLog.get(i));
			bw.newLine();
			bw.append("Line in the sheet : " + linesEventLog.get(i));
			bw.newLine();
			bw.newLine();

			SimpleDateFormat dateformat = new SimpleDateFormat(
					"dd/MM/yyyy hh:mm:ss a");
			String t = dateformat.format(timesEventLog.get(i));

			bw.append("Time Generated : " + t);

			bw.newLine();
			bw.newLine();
			bw.append("Insertions Strings : ");
			bw.newLine();
			bw.newLine();

			List<String> aux = insertionstringsEventLog.get(i);
			for (String s : aux) {
				bw.append(s);
				bw.append(",");
			}
			bw.newLine();
			bw.newLine();
			bw.append(" --------------------------------------------------------------------- ");
			bw.newLine();
			bw.newLine();
			bw.newLine();

		}

		destroyAllLists();
		bw.close();
		fw.close();
	}

	private static void destroyAllLists() {
		eventIDEventLog.clear();
		sourcenameEventLog.clear();
		productnameEventLog.clear();
		logfilesEventLog.clear();
		messagesEventLog.clear();
		pcnamesEventLog.clear();
		recordnumbersEventLog.clear();
		linesEventLog.clear();
		timesEventLog.clear();
		eventID.clear();
		sourcename.clear();
		productname.clear();
		logfiles.clear();
		messages.clear();
		pcnames.clear();
		recordnumbers.clear();
		lines.clear();
		times.clear();

	}

	public static void insertValues(final int ei, final String sn, final String pn,
			final String message, final String logfile, final String pcname, final long recordnumber,
			final int i, final Date time, final List<String> insertionstring) {

		eventID.add(ei);
		sourcename.add(sn);
		productname.add(pn);
		logfiles.add(logfile);
		messages.add(message);
		pcnames.add(pcname);
		recordnumbers.add(recordnumber);
		lines.add(i);
		times.add(time);
		insertionstrings.add(insertionstring);
	}

}
