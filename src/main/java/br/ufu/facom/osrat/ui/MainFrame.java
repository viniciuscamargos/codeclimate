package br.ufu.facom.osrat.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import br.ufu.facom.hpcs.controller.DistributionController;
import br.ufu.facom.hpcs.controller.SdfGroupNodeController;
import br.ufu.facom.hpcs.controller.SdfNodeController;
import br.ufu.facom.hpcs.controller.SearchController;
import br.ufu.facom.osrat.component.ButtonTabComponent;
import br.ufu.facom.osrat.component.MyRenderer;
import br.ufu.facom.osrat.model.Node;
import br.ufu.facom.osrat.model.TypeNode;
import br.ufu.facom.osrat.thread.ThreadExportSdfToXls;
import br.ufu.facom.osrat.thread.ThreadImportLocalSdf;
import br.ufu.facom.osrat.thread.ThreadImportSdf;
import br.ufu.facom.osrat.util.OsratUtil;

public class MainFrame extends JFrame {

	// TODO - verificar duplicadas
	// TODO - informaï¿½ï¿½es contato

	/**
	 * Tela principal.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Progressbar.
	 */
	private static JProgressBar progressBar;

	/**
	 * Panel das abas.
	 */
	private static JTabbedPane jTabPane;

	/**
	 * Panel principal (E / D).
	 */
	private static JSplitPane splitPane;

	/**
	 * Panel da Ã¡rvore.
	 */
	private static JPanel pnlTree;

	/**
	 * Scroll da Ã¡rvore.
	 */
	private static JScrollPane scrollTree;

	/**
	 * Ã�rvore hierÃ¡rquica.
	 */
	private static JTree tree;

	/**
	 * DiretÃ³rio principal dos sdfs.
	 */
	private static File[] sdfs;

	/**
	 * Menu Principal.
	 */
	private JMenuBar menuBar;

	/**
	 * Menu visÃ£o.
	 */
	private static JMenu mnView;

	/**
	 * Menu arquivos.
	 */
	private JMenu mnFile;

	/**
	 * Menu exportaÃ§Ã£o.
	 */
	private static JMenuItem mntExport;

	/**
	 * Menu limpar.
	 */
	private static JMenuItem mntClean;

	/**
	 * LOG.
	 */
	private static final Logger LOG = Logger.getLogger(MainFrame.class);

	/**
	 * Create the application.
	 */
	public MainFrame() {
		super();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setBounds(100, 100, 450, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setIconImage(OsratUtil.getMainIcon().getImage());
		this.setTitle(OsratUtil.getTitleSystem()
				+ " - Operating System Reability Analysis Tool ");
		this.getContentPane().setLayout(new GridBagLayout());

		getSplitPane().setDividerLocation(200);

		menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);

		menuBar.add(getMnFile());
		menuBar.add(getMnView());

		final GridBagConstraints gbcSplitPane = new GridBagConstraints();
		gbcSplitPane.weighty = 1;
		gbcSplitPane.weightx = 1;
		gbcSplitPane.insets = new Insets(0, 0, 5, 0);
		gbcSplitPane.fill = GridBagConstraints.BOTH;
		gbcSplitPane.gridx = 0;
		gbcSplitPane.gridy = 0;
		this.getContentPane().add(getSplitPane(), gbcSplitPane);

		getSplitPane().setRightComponent(getjTabPane());

		getSplitPane().setLeftComponent(getPnlTree());

		final GridBagConstraints gbcPg = new GridBagConstraints();
		gbcPg.anchor = GridBagConstraints.CENTER;
		gbcPg.gridx = 0;
		gbcPg.gridy = 0;
		getPnlTree().add(getProgressBar(), gbcPg);
	}

	/**
	 * Exorta os sdfs para uma planilha xls.
	 */
	private static void exportXls() {
		final JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(new FileNameExtensionFilter("Excel Workbook",
				"xls"));
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			final File fDest = chooser.getSelectedFile();
			final Thread thread = new Thread(new ThreadExportSdfToXls(
					null, fDest));
			thread.start();
		}
	}

	/**
	 * Retorna o frame principal.
	 * @return JFrame
	 */
	public final JFrame getFrame() {
		return this;
	}

	/**
	 * Retonra o scrool da Ã¡rvore.
	 * @return JScrollPane
	 */
	public static JScrollPane getScrollTree() {
		if (scrollTree == null) {
			scrollTree = new JScrollPane();
		}
		return scrollTree;
	}

	/**
	 * Abre uma nova aba.
	 * @param tela
	 * @param titulo
	 * @throws IOException
	 */
	public static void openPanelAba(final JPanel tela, final String titulo)
			throws IOException {
		int count = getjTabPane().getTabCount();
		for (int i = 0; i < count; i++) {
			final String title = getjTabPane().getTitleAt(i);
			if (titulo.equalsIgnoreCase(title)) {
				getjTabPane().setSelectedIndex(i);
				return;
			}
		}

		final int tabNumber = getjTabPane().getTabCount();
		getjTabPane().addTab(titulo, tela);
		getjTabPane().setMnemonicAt(tabNumber, KeyEvent.VK_1);
		getjTabPane().setTabComponentAt(tabNumber,
				new ButtonTabComponent(getjTabPane()));
		getjTabPane().setSelectedIndex(tabNumber);

	}

	/**
	 * Cria a Ã¡rvore hierÃ¡rquica.
	 * @param noRoot
	 */
	public static void createTree(final Node noRoot) {
		tree = new JTree();
		tree.addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(final TreeSelectionEvent treeSelectEvent) {
				try {
					final DefaultMutableTreeNode dfNode = (DefaultMutableTreeNode) tree
							.getLastSelectedPathComponent();

					if (dfNode == null) {
						return;
					}

					final Node node = (Node) dfNode.getUserObject();

					if (TypeNode.MACHINE.equals(node.getTypeNode())) {
						SdfNodeController controller = new SdfNodeController(node);
						openPanelAba(controller.getFrame(), node.getName());
					} else {
						SdfGroupNodeController controller = new SdfGroupNodeController(node);
						openPanelAba(controller.getFrame(), node.getName());
					}

				} catch (IOException e1) {
					LOG.error(e1);
				}
			}
		});

		tree.addMouseListener(new MouseAdapter() {
			public void mousePressed(final MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e)) {
					final TreePath path = tree.getPathForLocation(e.getX(),
							e.getY());
					final DefaultMutableTreeNode dfNode = (DefaultMutableTreeNode) path
							.getLastPathComponent();
					final Node node = (Node) dfNode.getUserObject();
					if (TypeNode.MACHINE.equals(node.getTypeNode())) {
						return;
					}

					Rectangle pathBounds = tree.getUI().getPathBounds(tree,
							path);
					if (pathBounds != null
							&& pathBounds.contains(e.getX(), e.getY())) {

						JPopupMenu menu = new JPopupMenu();

						final JMenuItem mnAddSdf = new JMenuItem("Add Sdf(s)");
						mnAddSdf.addActionListener(new ActionListener() {
							public void actionPerformed(final ActionEvent e) {
								try {
									JFileChooser chooser = new JFileChooser();
									chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

									if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
										File sdfs = chooser.getSelectedFile();
										if (sdfs == null) {
											return;
										}

										File dir = new File(node.getPath());
										if (sdfs.isDirectory()) {
											FileUtils.copyDirectoryToDirectory(sdfs, dir);
										} else {
											FileUtils.copyFileToDirectory(sdfs,
													dir);
										}

										final Thread thread = new Thread(
												new ThreadImportSdf(getSdfs()));
										thread.start();
									}

								} catch (Exception e2) {
									e2.printStackTrace();
								}
							}
						});

						menu.add(mnAddSdf);
						menu.show(tree, pathBounds.x, pathBounds.y
								+ pathBounds.height);
					}
				}
			}
		});

		tree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.setModel(new DefaultTreeModel(new DefaultMutableTreeNode(noRoot)));

		tree.setCellRenderer(new MyRenderer());
	}

	/**
	 * Retorna a Ã¡rvore.
	 * @return JTree
	 */
	public static JTree getTree() {
		if (tree == null) {
			tree = new JTree();
		}
		return tree;
	}

	/**
	 * Retorna o split pane.
	 * @return JSplitPane
	 */
	public static JSplitPane getSplitPane() {
		if (splitPane == null) {
			splitPane = new JSplitPane();
			splitPane.setDividerLocation(300);
		}
		return splitPane;
	}

	/**
	 * Retorna o progressbar.
	 * @return JProgressBar
	 */
	public static JProgressBar getProgressBar() {
		if (progressBar == null) {
			progressBar = new JProgressBar();
		}
		return progressBar;
	}

	/**
	 * Retona o panel da Ã¡rvore.
	 * @return JPanel
	 */
	public static JPanel getPnlTree() {
		if (pnlTree == null) {
			pnlTree = new JPanel(new GridBagLayout());

			final GridBagConstraints gbcTree = new GridBagConstraints();
			gbcTree.fill = GridBagConstraints.BOTH;
			gbcTree.gridx = 0;
			gbcTree.gridy = 0;
			gbcTree.weightx = 1;
			gbcTree.weighty = 1;
			pnlTree.add(getScrollTree(), gbcTree);
		}
		return pnlTree;
	}

	public static File[] getSdfs() {
		return sdfs;
	}

	public static JMenuItem getMnView() {
		if (mnView == null) {
			mnView = new JMenu("View");
			mnView.setEnabled(false);

			final JMenuItem mnDistributions = new JMenuItem("Distributions");
			mnDistributions.addActionListener(new ActionListener() {
				public void actionPerformed(final ActionEvent e) {
					try {
						DistributionController controller = new DistributionController();
						openPanelAba(controller.getFrame(), "Distributions");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			});
			mnView.add(mnDistributions);

			final JMenuItem mnRanking = new JMenuItem("Ranking");
			mnRanking.addActionListener(new ActionListener() {
				public void actionPerformed(final ActionEvent e) {
					try {
						openPanelAba(new RankingFrame(), "Ranking");
					} catch (IOException e1) {
						LOG.error(e1);
					}
				}
			});
			mnView.add(mnRanking);

			final JMenuItem mnSrceProd = new JMenuItem("Search");
			mnSrceProd.addActionListener(new ActionListener() {
				public void actionPerformed(final ActionEvent e) {
					try {
						SearchController controller = new SearchController();
						openPanelAba(controller.getFrame(), "Search");
					} catch (IOException e1) {
						LOG.error(e1);
					}
				}
			});
			mnView.add(mnSrceProd);
		}
		return mnView;
	}

	public final JMenu getMnFile() {
		if (this.mnFile == null) {
			mnFile = new JMenu("File");

			JMenuItem mntImport = new JMenuItem("Import logs");
			mntImport.addActionListener(new ActionListener() {
				public void actionPerformed(final ActionEvent e) {
					importSdf();
				}
			});
			mnFile.add(mntImport);

			JMenuItem mntImportLocal = new JMenuItem("Import local logs");
			mntImportLocal.addActionListener(new ActionListener() {
				public void actionPerformed(final ActionEvent e) {
					try {
						final Thread thread = new Thread(
								new ThreadImportLocalSdf());
						thread.start();
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			});
			mnFile.add(mntImportLocal);

			mnFile.addSeparator();

			mnFile.add(getMntExport());
			mnFile.add(getMntClean());

			final JMenuItem mntFilter = new JMenuItem("Filter");
			mntFilter.addActionListener(new ActionListener() {
				public void actionPerformed(final ActionEvent e) {
					try {
						openPanelAba(new FilterFrame(), "Filter Failures");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			});
			mnFile.add(mntFilter);

			mnFile.addSeparator();

			final JMenuItem mntClose = new JMenuItem("Close");
			mntClose.addActionListener(new ActionListener() {
				public void actionPerformed(final ActionEvent e) {
					getFrame().dispose();
					System.exit(1);
				}
			});
			mnFile.add(mntClose);

		}
		return mnFile;
	}

	private void importSdf() {
		try {
			JFileChooser chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			chooser.setMultiSelectionEnabled(true);
			chooser.setFileFilter(new FileNameExtensionFilter("SQL Server Compact",
					"sdf"));
			File file = new File(System.getProperty("user.home") + System.getProperty("file.separator")+ "Desktop");
			chooser.setCurrentDirectory(file);

			if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				sdfs = chooser.getSelectedFiles();
				if (sdfs == null) {
					return;
				}
				
				long total = 0;
				for (File sdf : sdfs) {
					if(sdf.isDirectory()){
						total += folderSize(sdf);
					}else{						
						total += sdf.length();
					}
				}

				final long fileSizeInMB = (long)(total / Math.pow(1024, 2));

				if (fileSizeInMB > 100) {
					int x = JOptionPane
							.showConfirmDialog(
									null,
									"The files has more than 100 MB. The import may take a few minutes. "
									+ "Want to continue?",
									"Save", JOptionPane.YES_NO_OPTION);
					if (x == JOptionPane.NO_OPTION) {
						return;
					}
				}

				final Thread thread = new Thread(new ThreadImportSdf(sdfs));
				thread.start();
			}

		} catch (Exception e2) {
			e2.printStackTrace();
		}

	}
	
	private static long folderSize(File directory) {
	    long length = 0;
	    for (File file : directory.listFiles()) {
	        if (file.isFile())
	            length += file.length();
	        else
	            length += folderSize(file);
	    }
	    return length;
	}

	public static JMenuItem getMntExport() {
		if (mntExport == null) {
			mntExport = new JMenuItem("Export xls");
			mntExport.setEnabled(false);
			mntExport.addActionListener(new ActionListener() {
				public void actionPerformed(final ActionEvent e) {
					exportXls();
				}
			});
		}
		return mntExport;
	}

	public static JMenuItem getMntClean() {
		if (mntClean == null) {
			mntClean = new JMenuItem("Clean");
			mntClean.setEnabled(false);
			mntClean.addActionListener(new ActionListener() {
				public void actionPerformed(final ActionEvent e) {
					clean();
				}
			});
		}
		return mntClean;
	}

	public static void clean() {
		cleanTree();

		MainFrame.getScrollTree().getViewport().removeAll();

		getPnlTree().revalidate();
		getPnlTree().repaint();
		getPnlTree().updateUI();

		getjTabPane().removeAll();

		getMnView().setEnabled(false);
		getMntExport().setEnabled(false);
		getMntClean().setEnabled(false);
	}

	/**
	 * Limpa a Ã¡rvore.
	 */
	public static void cleanTree() {
		if (getTree() != null) {
			final DefaultTreeModel model = (DefaultTreeModel) MainFrame
					.getTree().getModel();
			final DefaultMutableTreeNode root = (DefaultMutableTreeNode) model
					.getRoot();
			if (root != null) {
				root.removeAllChildren();
			}
		}
	}

	public static JTabbedPane getjTabPane() {
		if (jTabPane == null) {
			JTabbedPane tabPane = new JTabbedPane() {
				private static final long serialVersionUID = 7306438769246803305L;

				@Override
				protected void paintComponent(final Graphics g) {
					super.paintComponent(g);

					Image image = OsratUtil.getLogoOpaque().getImage();

					Dimension d = getSize();
					g.drawImage(image, 0, 0, d.width, d.height, null);
				}

			};
			setjTabPane(tabPane);
		}
		return jTabPane;
	}

	public static void setjTabPane(final JTabbedPane jTabPane) {
		MainFrame.jTabPane = jTabPane;
	}
}
