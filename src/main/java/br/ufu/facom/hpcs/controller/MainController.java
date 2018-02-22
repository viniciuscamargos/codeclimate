package br.ufu.facom.hpcs.controller;

import java.awt.Dialog.ModalityType;

import javax.swing.JFrame;

import br.ufu.facom.osrat.reader.ReadFailures;
import br.ufu.facom.osrat.ui.InitDialog;
import br.ufu.facom.osrat.ui.MainFrame;
import br.ufu.facom.osrat.util.JPAUtil;

public class MainController extends PersistenceController {

	private MainFrame frame;
	
	public MainController() {
		frame = new MainFrame();
		
		
		final InitDialog modalDialog = new InitDialog(frame, ModalityType.MODELESS);
		modalDialog.setLocationRelativeTo(frame);
		modalDialog.setVisible(true);
		
		ReadFailures.read();
		loadPersistenceContext();
		
		modalDialog.dispose();
		
		frame.addWindowListener(this);
		frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
	}
	
	@Override
	protected void cleanUp() {
		super.cleanUp();
		
		JPAUtil.closeEntityManagerFactory();
	}
}
