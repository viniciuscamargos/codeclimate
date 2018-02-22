package br.ufu.facom.osrat.thread;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.apache.log4j.Logger;

import br.ufu.facom.hpcs.agent.bean.RecordBean;
import br.ufu.facom.hpcs.agent.bean.StabilityMetricBean;
import br.ufu.facom.hpcs.controller.LogUnknowController;
import br.ufu.facom.osrat.model.Node;
import br.ufu.facom.osrat.model.TypeNode;
import br.ufu.facom.osrat.reader.ReadRecords;
import br.ufu.facom.osrat.ui.LogUnknowFrame;
import br.ufu.facom.osrat.ui.MainFrame;
import br.ufu.facom.osrat.wmi.WMIReader;

/**
 * Thread responsável pela leitura dos sdfs da máquina local.
 *
 */
public class ThreadImportLocalSdf implements Runnable {

	/**
	 * LOG.
	 */
	private static final Logger LOG = Logger
			.getLogger(ThreadImportLocalSdf.class);

	/**
	 * Lista dos logs desconhecidos.
	 */
	private List<RecordBean> unknows;

	@Override
	public final void run() {
		initProcess();
		importLocalSdfs();
		endProcess();

	}

	/**
	 * Importa os sdfs da máquina local.
	 */
	private void importLocalSdfs() {
		try {
			MainFrame.clean();

			final Node node = new Node("LOCAL", TypeNode.MACHINE, null);
			MainFrame.createTree(node);

			final DefaultTreeModel model = (DefaultTreeModel) MainFrame
					.getTree().getModel();
			final DefaultMutableTreeNode noRoot = (DefaultMutableTreeNode) model
					.getRoot();
			node.setModel(noRoot);

			readSdfs(node);
			getUnknows().addAll(node.getUnknows());
			model.reload();

			if (!getUnknows().isEmpty()) {
				LogUnknowController controller = new LogUnknowController(new LogUnknowFrame(getUnknows(), true));
				controller.getFrame().setLocationRelativeTo(null);
				controller.getFrame().setVisible(true);
			}

			// liberar menus
			MainFrame.getMntExport().setEnabled(true);
			MainFrame.getMnView().setEnabled(true);
			MainFrame.getMntClean().setEnabled(true);
		} catch (Exception e) { 	
			endProcess();
			throw e;
		}

	}

	/**
	 * Leitura dos sdfs.
	 * @param node
	 */
	private void readSdfs(final Node node) {
		if (LOG.isInfoEnabled()) {
			LOG.info("Reading records from WMI");
		}
		final List<RecordBean> records = WMIReader.getRecords();

		if (LOG.isInfoEnabled()) {
			LOG.info("Reading indexes from WMI");
		}

		/*RecordDAO dao = new RecordDaoJDBC();
		for (RecordBean bean : records) {
			dao.save(bean);
		}*/

		final List<StabilityMetricBean> indexes = WMIReader
				.getStabilityMetrics();

		node.setRecords(records);
		node.setIndexes(indexes);

		ReadRecords readRecords = new ReadRecords();
		readRecords.read(records, node);
	}

	/**
	 * Configurações antes de inicialiar a leitura.
	 */
	private void initProcess() {
		MainFrame.getScrollTree().setVisible(false);
		MainFrame.getProgressBar().setVisible(true);
		MainFrame.getProgressBar().setIndeterminate(true);
	}

	/**
	 * Configurações após a leitura.
	 */
	private void endProcess() {
		MainFrame.getProgressBar().setIndeterminate(false);
		MainFrame.getProgressBar().setVisible(false);
		MainFrame.getScrollTree().setVisible(true);

		MainFrame.getScrollTree().getViewport().add(MainFrame.getTree());
	}

	/**
	 * Retorna os logs desconhecidos.
	 * @return List
	 */
	private List<RecordBean> getUnknows() {
		if (unknows == null) {
			setUnknows(new ArrayList<RecordBean>());
		}
		return unknows;
	}

	/**
	 * Seta os logs desconhecidos.
	 * @param unknows
	 */
	public final void setUnknows(final List<RecordBean> unknows) {
		this.unknows = unknows;
	}

}
