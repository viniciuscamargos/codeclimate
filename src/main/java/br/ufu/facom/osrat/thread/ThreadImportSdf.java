package br.ufu.facom.osrat.thread;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import br.ufu.facom.hpcs.agent.bean.RecordBean;
import br.ufu.facom.hpcs.agent.bean.StabilityMetricBean;
import br.ufu.facom.hpcs.agent.sdf.SDFReader;
import br.ufu.facom.hpcs.controller.LogUnknowController;
import br.ufu.facom.hpcs.controller.PersistenceController;
import br.ufu.facom.hpcs.entity.Sdf;
import br.ufu.facom.hpcs.entity.SdfGroup;
import br.ufu.facom.osrat.model.Node;
import br.ufu.facom.osrat.model.TypeNode;
import br.ufu.facom.osrat.persist.dao.SdfDAO;
import br.ufu.facom.osrat.persist.dao.SdfDAOJPA;
import br.ufu.facom.osrat.persist.dao.SdfGroupDAO;
import br.ufu.facom.osrat.persist.dao.SdfGroupDAOJPA;
import br.ufu.facom.osrat.reader.ReadRecords;
import br.ufu.facom.osrat.ui.LogUnknowFrame;
import br.ufu.facom.osrat.ui.MainFrame;

public class ThreadImportSdf extends PersistenceController implements Runnable  {

	private File[] sdfs;

	private List<RecordBean> unknows = new ArrayList<RecordBean>();
	
	private SdfGroupDAO sdfGroupDAO;
	private SdfDAO sdfDAO;

	public ThreadImportSdf(final File[] sdfs) {
		loadPersistenceContext();
		this.sdfs = sdfs;
		sdfGroupDAO = new SdfGroupDAOJPA(getPersistenceContext());
		sdfDAO = new SdfDAOJPA(getPersistenceContext());
	}

	@Override
	public final void run() {
		initProcess();
		importSdfs();
		endProcess();
	}

	private void importSdfs() {
		MainFrame.clean();

		final Node node = new Node("SDF(s)", TypeNode.GROUPS, null);
		MainFrame.createTree(node);

		// create root group e save in database
		SdfGroup group = new SdfGroup();
		group.setName("SDF(s)");
		group.setDate(new Date());
		group = sdfGroupDAO.saveOrUpdate(group);

		//linka o grupo do no
		node.setGroup(group);

		DefaultTreeModel model = (DefaultTreeModel) MainFrame.getTree().getModel();
		DefaultMutableTreeNode noRoot = (DefaultMutableTreeNode) model.getRoot();
		node.setModel(noRoot);
		
		for (File sdf : sdfs) {
						
			if (!sdf.getName().endsWith(".sdf") && !sdf.isDirectory()) {
				continue;
			}
			
			mountTreeFiles(sdf, noRoot, group);
/*			for (final File sdf : sdfs) {
				if (!sdf.getName().endsWith(".sdf") && !sdf.isDirectory()) {
					continue;
				}
			}
*/			model.reload();
	
		}
		
		
		
		if (!getUnknows().isEmpty()) {
			LogUnknowController controller = new LogUnknowController(new LogUnknowFrame(getUnknows(), false));
			controller.getFrame().setLocationRelativeTo(null);
			controller.getFrame().setVisible(true);
		}

		// liberar menus
		MainFrame.getMntExport().setEnabled(true);
		MainFrame.getMnView().setEnabled(true);
		MainFrame.getMntClean().setEnabled(true);

	}

	private void mountTreeFiles(final File fileRoot, final DefaultMutableTreeNode dftNodePai, final SdfGroup parentGroup) {
		final DefaultTreeModel model = (DefaultTreeModel) MainFrame.getTree().getModel();
		if (fileRoot.isDirectory()) {

			Node nParent = null;
			if (dftNodePai.getUserObject() != null) {
				nParent = (Node) dftNodePai.getUserObject();
			}
			Node node = new Node(fileRoot.getName(), TypeNode.GROUP, nParent, fileRoot.getAbsolutePath());

			DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(node);
			node.setModel(childNode);
			model.insertNodeInto(childNode, dftNodePai, dftNodePai.getChildCount());

			//salva o grupo no banco
			SdfGroup group = new SdfGroup();
			group.setName(fileRoot.getName());
			group.setDate(new Date());
			group.setParent(parentGroup);
			group = sdfGroupDAO.saveOrUpdate(group);

			//linka o grupo ao nó
			node.setGroup(group);
			
			final File[] files = fileRoot.listFiles();
			for (final File file : files) {
				mountTreeFiles(file, childNode, group);
			}
			
		} else if(fileRoot.getName().endsWith(".sdf")){

			final Node node = new Node(fileRoot.getName(), TypeNode.MACHINE, fileRoot.getAbsolutePath());

			final DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(node);
			node.setModel(childNode);
			model.insertNodeInto(childNode, dftNodePai, dftNodePai.getChildCount());

			//salva o sdf no banco
			Sdf sdf = new Sdf(fileRoot.getName(), new Date(), parentGroup);
			sdf = sdfDAO.saveOrUpdate(sdf);
			
			//linka o sdf ao nó
			node.setSdf(sdf);

			readSdf(fileRoot, node, sdf);

			getUnknows().addAll(node.getUnknows());

		}
	}

	private void readSdf(final File file, final Node node, final Sdf sdf) {
		final List<RecordBean> records = SDFReader.getRecords(file.getAbsolutePath());
		node.setRecords(records);

		final List<StabilityMetricBean> indexes = SDFReader.getStabilityMetrics(file.getAbsolutePath());
		node.setIndexes(indexes);

		ReadRecords readRecords = new ReadRecords();
		
		readRecords.read(records, node);

		// save records in database
		/*RecordDAO dao = new RecordDaoJDBC();
		for (RecordBean bean : records) {
			bean.setIdSdf(sdf.getId());
			dao.save(bean);
		}*/
	}

	/**
	 * Configurações antes de iniciar o processo de leitura dos SDFS.
	 */
	private void initProcess() {
		MainFrame.getScrollTree().setVisible(false);
		MainFrame.getProgressBar().setVisible(true);
		MainFrame.getProgressBar().setIndeterminate(true);
	}

	/**
	 * Configurações após a leitura dos SDFS.
	 */
	private void endProcess() {
		MainFrame.getProgressBar().setIndeterminate(false);
		MainFrame.getProgressBar().setVisible(false);
		MainFrame.getScrollTree().setVisible(true);

		MainFrame.getScrollTree().getViewport().add(MainFrame.getTree());
	}

	public File[] getSdfs() {
		return sdfs;
	}

	public void setSdfs(File[] sdfs) {
		this.sdfs = sdfs;
	}

	/**
	 * Retorna a lista de logs desconhecidos.
	 * @return List
	 */
	private List<RecordBean> getUnknows() {
		if (unknows == null) {
			setUnknows(new ArrayList<RecordBean>());
		}
		return unknows;
	}

	/**
	 * Seta a lista de logs desconhecidos.
	 * @param unknows
	 */
	private void setUnknows(final List<RecordBean> unknows) {
		this.unknows = unknows;
	}

}
