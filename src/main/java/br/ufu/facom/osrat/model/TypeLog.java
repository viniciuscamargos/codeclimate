package br.ufu.facom.osrat.model;

public enum TypeLog {
	IGNORE("Ignore", "IGNORE"),
	SERVICE("Service", "SERVICE"),
	KERNEL("Kernel", "KERNEL"),
	OS_APPLICATION("Os Application", "OS_APPLICATION"),
	USER_APPLICATION("User Application", "USER_APPLICATION"),
	ALL("All", "All");

	private String title;
	private String planilha;

	private TypeLog(final String title, final String planilha) {
		this.title = title;
		this.planilha = planilha;
	}
	
	public String getTitle() {
		return title;
	}

	public String getPlanilha() {
		return planilha;
	}
	
	public static TypeLog[] getFailureLogs(){
		TypeLog[] types = new TypeLog[5];
		types[0] = TypeLog.ALL;
		types[1] = TypeLog.SERVICE;
		types[2] = TypeLog.KERNEL;
		types[3] = TypeLog.OS_APPLICATION;
		types[4] = TypeLog.USER_APPLICATION;
		return types;
	}
}
