package br.ufu.facom.osrat.wmi;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import br.ufu.facom.hpcs.agent.bean.RecordBean;
import br.ufu.facom.hpcs.agent.bean.StabilityMetricBean;
import br.ufu.facom.osrat.util.Helper;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.EnumVariant;
import com.jacob.com.Variant;

public final class WMIReader {

	private WMIReader() { }

	public static List<RecordBean> getRecords() {
		String host = "localhost"; //Technically you should be able to connect to other hosts, but it takes setup
		String connectStr = String.format("winmgmts:\\\\%s\\root\\CIMV2", host);
		String query = "SELECT * FROM Win32_ReliabilityRecords"; //Started = 1 means the service is running.
		ActiveXComponent axWMI = new ActiveXComponent(connectStr);
		//Execute the query
		Variant vCollection = axWMI.invoke("ExecQuery", new Variant(query));

		List<RecordBean> records = new LinkedList<RecordBean>();

		// to adjust wmi time to localtime
		Date d = new Date();
		int offset = Calendar.getInstance().getTimeZone().getOffset(d.getTime());

		//Our result is a collection, so we need to work though the.
		EnumVariant enumVariant = new EnumVariant(vCollection.toDispatch());
		Dispatch item = null;
		while (enumVariant.hasMoreElements()) {
			item = enumVariant.nextElement().toDispatch();
			RecordBean record = new RecordBean();

			record.setComputerName(Dispatch.call(item, "ComputerName").changeType(Variant.VariantString).getString());
			record.setEventIdentifier(Dispatch.call(item, "EventIdentifier").changeType(Variant.VariantInt).getInt());
			record.setLogfile(Dispatch.call(item, "Logfile").changeType(Variant.VariantString).getString());
			record.setMessage(Dispatch.call(item, "Message").changeType(Variant.VariantString).getString());
			record.setProductName(Dispatch.call(item, "ProductName").changeType(Variant.VariantString).getString());
			record.setRecordNumber(Dispatch.call(item, "RecordNumber").changeType(Variant.VariantLongInt).getLong());
			record.setSourceName(Dispatch.call(item, "SourceName").changeType(Variant.VariantString).getString());
			record.setTimeGenerated(Helper.stringToMilliseconds(
					Dispatch.call(item, "TimeGenerated")
							.changeType(Variant.VariantString).getString(),
					offset));
			record.setUser(Dispatch.call(item, "user").changeType(Variant.VariantString).getString());

			String[] strings = Dispatch.call(item, "InsertionStrings").toSafeArray().toStringArray();
			for (String s : strings) {
				record.addInsertionString(s);
			}

			records.add(record);
		}

		return records;
	}

	public static List<StabilityMetricBean> getStabilityMetrics() {
		String host = "localhost"; //Technically you should be able to connect to other hosts, but it takes setup
		String connectStr = String.format("winmgmts:\\\\%s\\root\\CIMV2", host);
		String query = "SELECT * FROM Win32_ReliabilityStabilityMetrics"; //Started = 1 means the service is running.
		ActiveXComponent axWMI = new ActiveXComponent(connectStr);
		//Execute the query
		Variant vCollection = axWMI.invoke("ExecQuery", new Variant(query));

		List<StabilityMetricBean> indexes = new LinkedList<StabilityMetricBean>();

		// to adjust wmi time to localtime
		Date d = new Date();
		int offset = Calendar.getInstance().getTimeZone().getOffset(d.getTime());

		//Our result is a collection, so we need to work though the.
		EnumVariant enumVariant = new EnumVariant(vCollection.toDispatch());
		Dispatch item = null;
		while (enumVariant.hasMoreElements()) {
			item = enumVariant.nextElement().toDispatch();
			StabilityMetricBean index = new StabilityMetricBean();

			index.setEndMeasurementDate(Helper.stringToMilliseconds(
					Dispatch.call(item, "EndMeasurementDate")
							.changeType(Variant.VariantString).getString(),
					offset));
			index.setRelID(Dispatch.call(item, "RelID").changeType(Variant.VariantString).getString());
			index.setStartMeasurementDate(Helper.stringToMilliseconds(
					Dispatch.call(item, "StartMeasurementDate")
							.changeType(Variant.VariantString).getString(),
					offset));
			index.setSystemStabilityIndex(Dispatch.call(item, "SystemStabilityIndex").changeType(Variant.VariantDouble).getDouble());
			index.setTimeGenerated(Helper.stringToMilliseconds(
					Dispatch.call(item, "TimeGenerated")
							.changeType(Variant.VariantString).getString(),
					offset));

			indexes.add(index);
		}

		return indexes;
	}

}

