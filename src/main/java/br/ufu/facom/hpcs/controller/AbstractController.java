package br.ufu.facom.hpcs.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import br.ufu.facom.hpcs.action.AbstractAction;
import br.ufu.facom.hpcs.event.AbstractEvent;
import br.ufu.facom.hpcs.event.AbstractEventListener;

public abstract class AbstractController implements ActionListener, WindowListener {

	private static Logger log = Logger.getLogger(AbstractController.class);
	
	private AbstractController parent;
	
	private java.util.List<AbstractController> subControllers = new ArrayList<AbstractController>();
	
	private Map<String, AbstractAction> actions = 
			new HashMap<String, AbstractAction>();
	
	private Map<Class<?>, List<AbstractEventListener<?>>> eventListeners = 
			new HashMap<Class<?>, List<AbstractEventListener<?>>>();
	
	public AbstractController(){}
	
	/**
	 * Controller possui um auto-relacionamento, útil em situações aonde uma hierarquia de controladores deve ser respeitada.
	 * @param parent controller <i>pai</i>
	 */
	public AbstractController(AbstractController parent){
		if (parent != null) {
			this.parent = parent;
			this.parent.subControllers.add(this);
		}
	}
	
	protected void registerAction(AbstractButton source, AbstractAction action) {
		if (source.getActionCommand() == null) {
			throw new RuntimeException("Componente (Button) undefined action!");
		}
		log.debug("Registering action: " + action.getClass().getName() + " to button: " + source.getText());
        source.addActionListener(this);
        this.actions.put(source.getActionCommand(), action);
    }

	protected void registerAction(JComboBox source, AbstractAction action) {
		if (source.getActionCommand() == null) {
			throw new RuntimeException("Componente (JComboBox) undefined action!");
		}
		log.debug("Registering action: " + action.getClass().getName() + " to comboBox: " + source.getName());
		source.addActionListener(this);
		this.actions.put(source.getActionCommand(), action);
	}
	
	/**
	 * Aciona o <code>AbstractEventListener</code> relacionado ao <code>AbstractEvent</code>
	 * para que o <code>listener</code> trate o evento.
	 * 
	 * @param event referência do evento gerado
	 */
	@SuppressWarnings("unchecked")
	protected void fireEvent(AbstractEvent<?> event) {
		if (eventListeners.get(event.getClass()) != null) {
            for (AbstractEventListener eventListener : eventListeners.get(event.getClass())) {
                log.debug("Evento: " + event.getClass().getName() + " com listener: " + eventListener.getClass().getName());
                eventListener.handleEvent(event);
            }
        }
		if (parent != null)
			parent.fireEvent(event);
	}
	
	/**
	 * Registra um <code>listener</code> que deve ser acionado de acordo com o tipo do <code>evento</code>.
	 * 
	 * @param eventClass tipo do evento
	 * @param eventListener tratador (<code>listener</code>) do evento
	 */
	protected void registerEventListener(Class<?> eventClass, AbstractEventListener<?> eventListener) {
        log.debug("Registrando listener: " + eventListener + " para o evento: " + eventClass.getName());
        java.util.List<AbstractEventListener<?>> listenersForEvent = eventListeners.get(eventClass);
        if (listenersForEvent == null) {
        	listenersForEvent = new ArrayList<AbstractEventListener<?>>(); 
        }
        listenersForEvent.add(eventListener);
        eventListeners.put(eventClass, listenersForEvent);
    }
	
	protected AbstractAction getAction(ActionEvent actionEvent) {
		AbstractButton button = (AbstractButton) actionEvent.getSource();
		String actionCommand = button.getActionCommand();
		return actions.get(actionCommand);
	}
	
	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		try {
			AbstractAction action = getAction(actionEvent);

			if (action != null) {
				log.debug("Executando action: " + action.getClass());
				try {
					action.actionPerformed();
				} catch (Exception ex) {
					handlerException(ex);
				}
			}
		} catch (ClassCastException e) {
			handlerException(new IllegalArgumentException("Action source não é um Abstractbutton: " + actionEvent));
		}
	}
	
	/**
	 * Caso ocorra alguma falha durante a <code>ação</code> apresenta uma mensagem.
	 * 
	 * @param ex
	 */
	protected void handlerException(Exception ex) {
		log.error(ex);
		JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
	}
	
	public AbstractController getParentController() {
        return parent;
    }
	
	/**
	 * Método utilizado para liberar recursos carregados pela <code>Controller</code>.
	 */
	protected void cleanUp() {
		log.debug("Liberando recursos do controller "+this.getClass().getName());
		
		for (AbstractController subController : subControllers) {
            subController.cleanUp();
        }
	}
	
	public void windowClosing(WindowEvent windowEvent) { 
		cleanUp(); 
	}
	
    public void windowOpened(WindowEvent windowEvent) {}
    public void windowClosed(WindowEvent windowEvent) {}
    public void windowIconified(WindowEvent windowEvent) {}
    public void windowDeiconified(WindowEvent windowEvent) {}
    public void windowActivated(WindowEvent windowEvent) {}
    public void windowDeactivated(WindowEvent windowEvent) {}
    
}
