package br.ufu.facom.osrat.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.tree.DefaultMutableTreeNode;

import br.ufu.facom.hpcs.agent.bean.RecordBean;
import br.ufu.facom.hpcs.agent.bean.StabilityMetricBean;
import br.ufu.facom.hpcs.entity.Sdf;
import br.ufu.facom.hpcs.entity.SdfGroup;

public class Node {

	// informações gerais
	private DefaultMutableTreeNode model;
	private String path;
	private String name;
	private TypeNode typeNode;

	// grupo ou sdf
	private SdfGroup group;
	private Sdf sdf;
	
	// informações por sdf
	private List<RecordBean> unknows;
	private List<RecordBean> ignores;
	private List<RecordBean> kernels;
	private List<RecordBean> services;
	private List<RecordBean> osApps;
	private List<RecordBean> userApps;

	private String initDate;
	private String finalDate;
	private Integer numberDays;

	// informações por grupo
	private List<Double> listCT;
	private List<Double> listNF;

	public Node(final String name, final TypeNode typeNode, final String path) {
		this.name = name;
		this.typeNode = typeNode;
		this.path = path;
	}

	public Node(final String name, final TypeNode typeNode, final Node nodeParent, final String path) {
		this.name = name;
		this.typeNode = typeNode;
		this.nodeParent = nodeParent;
		this.path = path;
	}

	public final DefaultMutableTreeNode getModel() {
		return model;
	}

	public final void setModel(final DefaultMutableTreeNode model) {
		this.model = model;
	}

	public final String getPath() {
		return path;
	}

	public final void setPath(final String path) {
		this.path = path;
	}

	public final List<RecordBean> getUnknows() {
		if (unknows == null) {
			unknows = new ArrayList<RecordBean>();
		}
		return unknows;
	}

	public final void setUnknows(final List<RecordBean> unknows) {
		this.unknows = unknows;
	}

	public final List<RecordBean> getIgnores() {
		if (ignores == null) {
			ignores = new ArrayList<RecordBean>();
		}
		return ignores;
	}

	public final void setIgnores(final List<RecordBean> ignores) {
		this.ignores = ignores;
	}

	public final List<RecordBean> getKernels() {
		if (kernels == null) {
			kernels = new ArrayList<RecordBean>();
		}
		return kernels;
	}

	public final void setKernels(final List<RecordBean> kernels) {
		this.kernels = kernels;
	}

	public final List<RecordBean> getServices() {
		if (services == null) {
			services = new ArrayList<RecordBean>();
		}
		return services;
	}

	public final void setServices(final List<RecordBean> services) {
		this.services = services;
	}

	public final List<RecordBean> getOsApps() {
		if (osApps == null) {
			osApps = new ArrayList<RecordBean>();
		}
		return osApps;
	}

	public final void setOsApps(final List<RecordBean> osApps) {
		this.osApps = osApps;
	}

	public final List<RecordBean> getUserApps() {
		if (userApps == null) {
			userApps = new ArrayList<RecordBean>();
		}
		return userApps;
	}

	public final void setUserApps(final List<RecordBean> userApps) {
		this.userApps = userApps;
	}

	// principal
	private List<RecordBean> records;
	private List<StabilityMetricBean> indexes;

	// informações gerais
	private Node nodeParent;

	private String sheetFileName;
	private String sheetIdxName;
	private String shhetRanking;

	// informações para sdf
	private Integer totalService;
	private Integer totalKernel;
	private Integer totalApp;
	private Integer totalUser;
	private Integer totalFails;

	// informações para grupo de sdf
	private Map<String, Object[]> mapGroup = new HashMap<String, Object[]>();
	private Map<String, Object[]> mapRanking = new HashMap<String, Object[]>();

	// ambos
	private List<Double> timesService;
	private List<Double> timesKernel;
	private List<Double> timesApp;

	public final List<RecordBean> getRecords() {
		return records;
	}

	public final void setRecords(final List<RecordBean> records) {
		this.records = records;
	}

	public final List<StabilityMetricBean> getIndexes() {
		return indexes;
	}

	public final void setIndexes(final List<StabilityMetricBean> indexes) {
		this.indexes = indexes;
	}

	public final String getName() {
		return name;
	}

	public final void setName(final String name) {
		this.name = name;
	}

	public final TypeNode getTypeNode() {
		return typeNode;
	}

	public final void setTypeNode(final TypeNode typeNode) {
		this.typeNode = typeNode;
	}
	
	public SdfGroup getGroup() {
		return group;
	}
	
	public void setGroup(SdfGroup group) {
		this.group = group;
	}
	
	public Sdf getSdf() {
		return sdf;
	}
	
	public void setSdf(Sdf sdf) {
		this.sdf = sdf;
	}

	public final Node getNodeParent() {
		return nodeParent;
	}

	public final void setNodeParent(final Node nodeParent) {
		this.nodeParent = nodeParent;
	}

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

	public final String getShhetRanking() {
		return shhetRanking;
	}

	public final void setShhetRanking(final String shhetRanking) {
		this.shhetRanking = shhetRanking;
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
		if (totalFails == null) {
			this.totalFails = 0;
		}
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

	public final Map<String, Object[]> getMapGroup() {
		if (mapGroup == null) {
			this.mapGroup = new HashMap<String, Object[]>();
		}
		return mapGroup;
	}

	public final void setMapGroup(final Map<String, Object[]> mapGroup) {
		this.mapGroup = mapGroup;
	}

	public final Map<String, Object[]> getMapRanking() {
		if (mapRanking == null) {
			this.mapRanking = new HashMap<String, Object[]>();
		}
		return mapRanking;
	}

	public final void setMapRanking(final Map<String, Object[]> mapRanking) {
		this.mapRanking = mapRanking;
	}

	public final List<Double> getListCT() {
		if (listCT == null) {
			this.listCT = new ArrayList<Double>();
		}
		return listCT;
	}

	public final void setListCT(final List<Double> listCT) {
		this.listCT = listCT;
	}

	public final List<Double> getListNF() {
		if (listNF == null) {
			this.listNF = new ArrayList<Double>();
		}
		return listNF;
	}

	public final void setListNF(final List<Double> listNF) {
		this.listNF = listNF;
	}

	public final List<Double> getTimesService() {
		if (timesService == null) {
			this.timesService = new ArrayList<Double>();
		}
		return timesService;
	}

	public final void setTimesService(final List<Double> timesService) {
		this.timesService = timesService;
	}

	public final List<Double> getTimesKernel() {
		if (timesKernel == null) {
			this.timesKernel = new ArrayList<Double>();
		}
		return timesKernel;
	}

	public final void setTimesKernel(final List<Double> timesKernel) {
		this.timesKernel = timesKernel;
	}

	public final List<Double> getTimesApp() {
		if (timesApp == null) {
			this.timesApp = new ArrayList<Double>();
		}
		return timesApp;
	}

	public final void setTimesApp(final List<Double> timesApp) {
		this.timesApp = timesApp;
	}

	@Override
	public final String toString() {
		return name;
	}

}
