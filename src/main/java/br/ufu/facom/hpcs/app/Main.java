package br.ufu.facom.hpcs.app;

import br.ufu.facom.hpcs.controller.MainController;



/**
 * Ponto de entrada da aplicacao.
 * 
 */
public class Main {
	
	// exec:java -Dexec.mainClass="br.ufu.facom.hpcs.app.Main"

	public static void main(String[] args) {
		new MainController();
	}

}
