package br.ufu.facom.hpcs.agent.sdf;

import java.util.ArrayList;
import java.util.List;

import br.ufu.facom.hpcs.agent.bean.RecordBean;
import br.ufu.facom.hpcs.agent.bean.StabilityMetricBean;

public final class SDFReader {

	private static final int ERR_OK = 0;
	private static final int ERR_CONN = 1;
	private static final int ERR_SQL = 2;
	private static final int ERR_IO = 3;
	private static final int ERR_OTHER = 4;

	private static native int callGetRecords(final String sdfFilename,
			final ArrayList<RecordBean> result);

	private static native int callGetStabilityMetrics(final String sdfFilename,
			final ArrayList<StabilityMetricBean> result);

	private SDFReader() { }

	static {
		String arch = System.getProperty("os.arch");
		if (arch.contains("86")) {
			/*String dll = SDFReader.class.getClassLoader().getResource("dll/SdfReaderCPP-x86.dll").getFile();
			System.load(dll);*/
			System.loadLibrary("SdfReaderCPP-x86");
		} else if (arch.contains("64")) {
			/*String sdf = SDFReader.class.getClassLoader().getResource("dll/SdfReaderCPP-x64.dll").getFile();
			System.load(sdf);*/
			System.loadLibrary("SdfReaderCPP-x64");
		} else {
			throw new RuntimeException("Unsupported architecture: " + arch);
		}
	}

	public static List<RecordBean> getRecords(final String sdfFilename) {
		ArrayList<RecordBean> result = new ArrayList<RecordBean>();
		int r = callGetRecords(sdfFilename, result);

		switch (r) {
		case ERR_CONN:
			throw new RuntimeException("Connection error in SDF library");
		case ERR_SQL:
			throw new RuntimeException("SQL error in SDF library");
		case ERR_IO:
			throw new RuntimeException("IO error in SDF library");
		case ERR_OTHER:
			throw new RuntimeException("Unknown error in SDF library");
		default:
			break;
		}

		return result;
	}

	public static List<StabilityMetricBean> getStabilityMetrics(
			final String sdfFilename) {
		ArrayList<StabilityMetricBean> result = new ArrayList<StabilityMetricBean>();
		int r = callGetStabilityMetrics(sdfFilename, result);

		switch (r) {
		case ERR_CONN:
			throw new RuntimeException("Connection error in SDF library");
		case ERR_SQL:
			throw new RuntimeException("SQL error in SDF library");
		case ERR_IO:
			throw new RuntimeException("IO error in SDF library");
		case ERR_OTHER:
			throw new RuntimeException("Unknown error in SDF library");
		default:
			break;
		}

		return result;
	}
}
