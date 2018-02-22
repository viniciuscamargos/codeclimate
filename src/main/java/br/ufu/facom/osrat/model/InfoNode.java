package br.ufu.facom.osrat.model;

import java.util.Map;

public class InfoNode {

	// informações gerais
	private String sheetFileName;
	private String sheetIdxName;

	// informações para sdf
	private Integer totalService;
	private Integer totalKernel;
	private Integer totalApp;
	private Integer totalUser;
	private Integer totalFails;

	private String initDate;
	private String finalDate;
	private Integer numberDays;

	// informações para grupo de sdf
	private Integer minCT;
	private Integer maxCT;
	private Integer avgCT;
	private Integer mdnCT;
	private Integer sdCT;

	private Integer minNF;
	private Integer maxNF;
	private Integer avgNF;
	private Integer mdnNF;
	private Integer sdNF;

	private Map<String, Integer> mapGroup;

	public final String getSheetFileName() {
		return sheetFileName;
	}

	public final void setSheetFileName(final String sheetFileName) {
		this.sheetFileName = sheetFileName;
	}

	public final String getSheetIdxName() {
		return sheetIdxName;
	}

	public final void setSheetIdxName(final String sheetIdxName) {
		this.sheetIdxName = sheetIdxName;
	}

	public final Integer getMinCT() {
		return minCT;
	}

	public final void setMinCT(final Integer minCT) {
		this.minCT = minCT;
	}

	public final Integer getMaxCT() {
		return maxCT;
	}

	public final void setMaxCT(final Integer maxCT) {
		this.maxCT = maxCT;
	}

	public final Integer getAvgCT() {
		return avgCT;
	}

	public final void setAvgCT(final Integer avgCT) {
		this.avgCT = avgCT;
	}

	public final Integer getMdnCT() {
		return mdnCT;
	}

	public final void setMdnCT(final Integer mdnCT) {
		this.mdnCT = mdnCT;
	}

	public final Integer getSdCT() {
		return sdCT;
	}

	public final void setSdCT(final Integer sdCT) {
		this.sdCT = sdCT;
	}

	public final Integer getMinNF() {
		return minNF;
	}

	public final void setMinNF(final Integer minNF) {
		this.minNF = minNF;
	}

	public final Integer getMaxNF() {
		return maxNF;
	}

	public final void setMaxNF(final Integer maxNF) {
		this.maxNF = maxNF;
	}

	public final Integer getAvgNF() {
		return avgNF;
	}

	public final void setAvgNF(final Integer avgNF) {
		this.avgNF = avgNF;
	}

	public final Integer getMdnNF() {
		return mdnNF;
	}

	public final void setMdnNF(final Integer mdnNF) {
		this.mdnNF = mdnNF;
	}

	public final Integer getSdNF() {
		return sdNF;
	}

	public final void setSdNF(final Integer sdNF) {
		this.sdNF = sdNF;
	}

	public final Integer getTotalService() {
		return totalService;
	}

	public final void setTotalService(final Integer totalService) {
		this.totalService = totalService;
	}

	public final Integer getTotalKernel() {
		return totalKernel;
	}

	public final void setTotalKernel(final Integer totalKernel) {
		this.totalKernel = totalKernel;
	}

	public final Integer getTotalApp() {
		return totalApp;
	}

	public final void setTotalApp(final Integer totalApp) {
		this.totalApp = totalApp;
	}

	public final Integer getTotalUser() {
		return totalUser;
	}

	public final void setTotalUser(final Integer totalUser) {
		this.totalUser = totalUser;
	}

	public final Integer getTotalFails() {
		return totalFails;
	}

	public final void setTotalFails(final Integer totalFails) {
		this.totalFails = totalFails;
	}

	public final String getInitDate() {
		return initDate;
	}

	public final void setInitDate(final String initDate) {
		this.initDate = initDate;
	}

	public final String getFinalDate() {
		return finalDate;
	}

	public final void setFinalDate(final String finalDate) {
		this.finalDate = finalDate;
	}

	public final Integer getNumberDays() {
		return numberDays;
	}

	public final void setNumberDays(final Integer numberDays) {
		this.numberDays = numberDays;
	}

	public final Map<String, Integer> getMapGroup() {
		return mapGroup;
	}

	public final void setMapGroup(final Map<String, Integer> mapGroup) {
		this.mapGroup = mapGroup;
	}

}
